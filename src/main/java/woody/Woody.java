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
    }

    /**
     * Starts the application by loading tasks, displaying the welcome
     * message, and entering the command processing loop.
     */
    public void run() {
        loadTask();
        ui.showLogo();
        ui.showWelcome();
        chat();
    }

    /**
     * Continuously reads and processes user commands until the 'bye'
     * command is received.
     *
     * @throws WoodyException If an invalid or unknown command is entered.
     */
    public void chat() {
        while (true) {
            try {
                String input = ui.readCommand();
                String command = Parser.getCommand(input);
                String arguments = Parser.getArguments(input);

                switch (command) {
                case "todo":
                    todo(arguments);
                    break;

                case "deadline":
                    deadline(arguments);
                    break;

                case "event":
                    event(arguments);
                    break;

                case "mark":
                    mark(arguments);
                    break;

                case "unmark":
                    unmark(arguments);
                    break;
                case "delete":
                    remove(arguments);
                    break;
                case "list":
                    ui.showTaskList(tasks);
                    break;
                case "bye":
                    ui.showBye();
                    storage.save(tasks.getTasks());
                    return;
                default:
                    throw new UnknownCommandException(input);
                }
            } catch (WoodyException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Marks a specified task as completed.
     *
     * @param arguments User input containing task index
     * @throws InvalidSyntaxException If task index is invalid or does not exist
     */
    public void mark(String arguments) throws InvalidSyntaxException { 
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.markDone();
        ui.showTaskMarked(task);
    }

    /**
     * Marks a specified task as not completed.
     *
     * @param arguments User input containing the task index.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist
     */
    public void unmark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.unmarkDone();
        ui.showTaskUnmarked(task);
    }

    /**
     * Removes a specified task from the task list
     *
     * @param arguments User input containing the task index.
     * @throws InvalidSyntaxException If the task index is invalid or does not exist
     */
    public void remove(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.remove(taskIndex);
        ui.showTaskRemoved(task, tasks.size());
    }

    /**
     * Adds a new todo task to the task list.
     *
     * @param arguments Description of the todo task.
     */
    public void todo(String arguments) {
        ToDo task = new ToDo(arguments);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Adds a new deadline task to the task list.
     *
     * @param arguments Description and deadline of the task.
     * @throws InvalidSyntaxException If the input format is invalid.
     */
    public void deadline(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getDeadlineArguments(arguments);
        Deadline task = new Deadline(parsedArguments[0], parsedArguments[1]);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Adds a new event task to the task list.
     *
     * @param arguments Description, from and to of the task.
     * @throws InvalidSyntaxException If the input format is invalid.
     */
    public void event(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getEventArguments(arguments);
        Event task = new Event(parsedArguments[0], parsedArguments[1], parsedArguments[2]);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
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
        new Woody().run();
    }
}
