import java.io.IOException;

public class Woody {
    private Storage storage;
    private UI ui;
    private TaskList list;

    public Woody(String filePath) {
        storage = new Storage();
        ui = new UI();
        loadTask();
    }

    public void run() {
        ui.showLogo();
        ui.showWelcome();
        chat();
    }

    public void saveList() {
        try {
            storage.save(list.getList());
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
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
                    ui.showTaskList(list);
                    break;
                case "bye":
                    ui.showBye();
                    saveList();
                    return;
                default:
                    throw new UnknownCommandException(input);
                }
            } catch (WoodyException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public void mark(String arguments) throws InvalidSyntaxException { 
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = list.get(taskIndex);
        task.markDone();
        ui.showTaskMarked(task);
    }

    public void unmark(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = list.get(taskIndex);
        task.unmarkDone();
        ui.showTaskUnmarked(task);
    }

    public void remove(String arguments) throws InvalidSyntaxException {
        int taskIndex = Parser.getTaskIndex(arguments);
        Task task = list.remove(taskIndex);
        ui.showTaskRemoved(task, list.size());
    }

    public void todo(String arguments) throws InvalidSyntaxException {
        ToDo task = new ToDo(arguments);
        list.add(task);
        ui.showTaskAdded(task, list.size());
    }

    public void deadline(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getDeadlineArguments(arguments);
        Deadline task = new Deadline(parsedArguments[0], parsedArguments[1]);
        list.add(task);
        ui.showTaskAdded(task, list.size());
    }

    public void event(String arguments) throws InvalidSyntaxException {
        String[] parsedArguments = Parser.getEventArguments(arguments);
        Event task = new Event(parsedArguments[0], parsedArguments[1], parsedArguments[2]);
        list.add(task);
        ui.showTaskAdded(task, list.size());
    }

    public void loadTask() {
        try {
            list = new TaskList(storage.load());
        } catch (IOException e) {
            list = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Woody("data/tasks.txt").run();
    }
}
