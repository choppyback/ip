package woody;

import woody.exception.InvalidSyntaxException;
import woody.exception.InvalidTaskIndexException;
import woody.exception.StorageException;
import woody.exception.UnknownCommandException;
import woody.exception.WoodyException;
import woody.parser.Parser;
import woody.storage.Storage;
import woody.task.Deadline;
import woody.task.Event;
import woody.task.Task;
import woody.task.TaskList;
import woody.task.ToDo;
import woody.ui.Ui;

/**
 * The main entry point of the Woody task management application.
 * Coordinates user input, command execution, and task persistence.
 */
public class Woody {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;
    private String startUpErrorMessage;

    /**
     * Constructs a new Woody application instance.
     */
    public Woody() {
        storage = new Storage();
        ui = new Ui();
        loadTask();
    }

    /**
     * Processes a single user command and returns the response message.
     * Any errors encountered during command processing are converted
     * into user-friendly messages.
     *
     * @param input The raw user input command.
     * @return A response message to be displayed to the user.
     */
    public String run(String input) {
        assert tasks != null : "TaskList should have been initialized";
        try {
            String command = Parser.getCommand(input);
            String arguments = Parser.getArguments(input);
            assert command != null : "Parser returned null command";

            switch (command) {
            case "todo":
                return todo(arguments);
            case "deadline":
                return deadline(arguments);
            case "event":
                return event(arguments);
            case "mark":
                return mark(arguments);
            case "unmark":
                return unmark(arguments);
            case "delete":
                return delete(arguments);
            case "list":
                return ui.showTaskList(tasks);
            case "find":
                return find(arguments);
            case "bye":
                return handleExit();
            default:
                throw new UnknownCommandException(input);
            }
        } catch (WoodyException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Saves tasks and returns the goodbye message.
     *
     * @return Goodbye message if save succeeds.
     * @throws StorageException If saving fails.
     */
    private String handleExit() throws StorageException {
        try {
            storage.save(tasks.getTasks());
        } catch (StorageException e) {
            throw new StorageException(
                    "Unable to save data. Please check file permissions "
                            + "and try again.");
        }
        return ui.showBye();
    }

    /**
     * Finds tasks whose descriptions contain the given keyword.
     *
     * @param keyword Keyword to search for.
     * @return A formatted list of matching tasks.
     */
    public String find(String keyword) {
        TaskList matchedTask = tasks.find(keyword);
        return ui.showMatchingTasks(matchedTask);
    }

    /**
     * Marks the specified task as completed.
     *
     * @param arguments User input containing the task index.
     * @return Confirmation message for the marked task.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist.
     */
    public String mark(String arguments) throws InvalidSyntaxException, InvalidTaskIndexException {
        Task task = getTaskFromArguments(arguments);
        task.markDone();
        return ui.showTaskMarked(task);
    }

    /**
     * Marks the specified task as not completed.
     *
     * @param arguments User input containing the task index.
     * @return Confirmation message for the unmarked task.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist.
     */
    public String unmark(String arguments) throws InvalidSyntaxException, InvalidTaskIndexException {
        Task task = getTaskFromArguments(arguments);
        task.unmarkDone();
        return ui.showTaskUnmarked(task);
    }

    /**
     * Deletes the specified task from the task list.
     *
     * @param arguments User input containing the task index.
     * @return Confirmation message for the removed task.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist.
     */
    public String delete(String arguments) throws InvalidSyntaxException, InvalidTaskIndexException {
        int taskIndex = Parser.getTaskIndex(arguments);
        tasks.validateIndex(taskIndex);
        Task task = tasks.delete(taskIndex);
        return ui.showTaskRemoved(task, tasks.size());
    }

    /**
     * Adds a new todo task to the task list.
     *
     * @param arguments Description of the todo task.
     * @return Confirmation message for the added task.
     * @throws InvalidSyntaxException If the description is null or empty.
     */
    public String todo(String arguments) throws InvalidSyntaxException {
        if (arguments == null || arguments.trim().isEmpty()) {
            throw new InvalidSyntaxException("The description of a todo cannot be empty.");
        }
        ToDo task = new ToDo(arguments);
        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Adds a new deadline task to the task list.
     *
     * @param arguments Description and deadline of the task.
     * @return Confirmation message for the added task.
     * @throws InvalidSyntaxException If the input format is invalid.
     */
    public String deadline(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getDeadlineArguments(arguments);
        assert parsedArguments.length == 2 : "Deadline arguments should have exactly 2 parts";
        Deadline task = new Deadline(parsedArguments[0], parsedArguments[1]);
        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Adds a new event task to the task list.
     *
     * @param arguments Description, start time, and end time of the task.
     * @return Confirmation message for the added task.
     * @throws InvalidSyntaxException If the input format is invalid.
     */
    public String event(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getEventArguments(arguments);
        assert parsedArguments.length == 3 : "Event arguments should have exactly 3 parts";
        Event newEvent = new Event(parsedArguments[0], parsedArguments[1], parsedArguments[2]);
        Event clashingEvent = tasks.findClashingEvent(newEvent);
        if (clashingEvent != null) {
            return ui.showClashingTask(clashingEvent);
        }
        tasks.add(newEvent);
        return ui.showTaskAdded(newEvent, tasks.size());
    }

    /**
     * Loads tasks from persistent storage.
     * Initializes an empty task list if loading fails.
     */
    public void loadTask() {
        try {
            tasks = new TaskList(storage.load());
        } catch (StorageException e) {
            tasks = new TaskList();
            startUpErrorMessage = e.getMessage();
        }
    }

    /**
     * Returns the welcome message to be displayed.
     * Includes any startup error message if present.
     */
    public String getWelcomeMessage() {
        return ui.showWelcome(startUpErrorMessage);
    }

    /**
     * Retrieves a task from the task list based on the given user input.
     *
     * @param arguments User input containing the task index.
     * @return The task corresponding to the given index.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist.
     */
    private Task getTaskFromArguments(String arguments) throws InvalidSyntaxException, InvalidTaskIndexException {
        int taskIndex = Parser.getTaskIndex(arguments);
        tasks.validateIndex(taskIndex);
        return tasks.get(taskIndex);
    }
}
