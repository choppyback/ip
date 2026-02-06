package woody;

import java.io.IOException;

import woody.exception.InvalidSyntaxException;
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
        try {
            String command = Parser.getCommand(input);
            String arguments = Parser.getArguments(input);

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
                return remove(arguments);
            case "list":
                return ui.showTaskList(tasks);
            case "find":
                return find(arguments);
            case "bye":
                storage.save(tasks.getTasks());
                return ui.showBye();
            default:
                throw new UnknownCommandException(input);
            }
        } catch (WoodyException e) {
            return ui.showError(e.getMessage());
        }
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
    public String mark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
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
    public String unmark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.unmarkDone();
        return ui.showTaskUnmarked(task);
    }

    /**
     * Removes the specified task from the task list.
     *
     * @param arguments User input containing the task index.
     * @return Confirmation message for the removed task.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist.
     */
    public String remove(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.remove(taskIndex);
        return ui.showTaskRemoved(task, tasks.size());
    }

    /**
     * Adds a new todo task to the task list.
     *
     * @param arguments Description of the todo task.
     * @return Confirmation message for the added task.
     */
    public String todo(String arguments) {
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
        Event task = new Event(parsedArguments[0], parsedArguments[1], parsedArguments[2]);
        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Loads tasks from persistent storage.
     * Initializes an empty task list if loading fails.
     */
    public void loadTask() {
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            tasks = new TaskList();
        }
    }
    /**
     * Returns the welcome message to be displayed
     */
    public String getWelcomeMessage() {
        return ui.showWelcome();
    }
}
