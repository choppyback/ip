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
     * Continuously reads and processes user commands until the 'bye'
     * command is received.
     *
     * @throws WoodyException If an invalid or unknown command is entered.
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
     * Finds tasks whose descriptions contain the given keyword
     * and displays them to the user.
     *
     * @param keyword Keyword to search for.
     */
    public String find(String keyword) {
        TaskList matchedTask = tasks.find(keyword);
        return ui.showMatchingTasks(matchedTask);
    }

    /**
     * Marks a specified task as completed.
     *
     * @param arguments User input containing task index
     * @throws InvalidSyntaxException If task index is invalid or does not exist
     */
    public String mark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.markDone();
        return ui.showTaskMarked(task);
    }

    /**
     * Marks a specified task as not completed.
     *
     * @param arguments User input containing the task index.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist
     */
    public String unmark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.unmarkDone();
        return ui.showTaskUnmarked(task);
    }

    /**
     * Removes a specified task from the task list
     *
     * @param arguments User input containing the task index.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist
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
     * @param arguments Description, from and to of the task.
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

    public static void main(String[] args) {
        new Woody().run("");
    }
}
