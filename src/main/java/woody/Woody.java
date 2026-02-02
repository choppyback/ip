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

public class Woody {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;

    public Woody() {
        storage = new Storage();
        ui = new Ui();
    }

    public void run() {
        loadTask();
        ui.showLogo();
        ui.showWelcome();
        chat();
    }

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
                case "find":
                    find(arguments);
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
     * Finds tasks whose descriptions contain the given keyword
     * and displays them to the user.
     *
     * @param keyword Keyword to search for.
     */
    public void find(String keyword) {
        TaskList matchedTask = tasks.find(keyword);
        ui.showMatchingTasks(matchedTask);
    }

    public void mark(String arguments) throws InvalidSyntaxException { 
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.markDone();
        ui.showTaskMarked(task);
    }

    public void unmark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.get(taskIndex);
        task.unmarkDone();
        ui.showTaskUnmarked(task);
    }

    public void remove(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = tasks.remove(taskIndex);
        ui.showTaskRemoved(task, tasks.size());
    }

    public void todo(String arguments) {
        ToDo task = new ToDo(arguments);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    public void deadline(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getDeadlineArguments(arguments);
        Deadline task = new Deadline(parsedArguments[0], parsedArguments[1]);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    public void event(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getEventArguments(arguments);
        Event task = new Event(parsedArguments[0], parsedArguments[1], parsedArguments[2]);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

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
