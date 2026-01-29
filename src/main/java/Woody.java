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
                String[] parts = input.split(" ", 2);

                switch (parts[0]) {
                    case "todo":
                        todo(parts);
                        break;

                    case "deadline":
                        deadline(parts);
                        break;

                    case "event":
                        event(parts);
                        break;

                    case "mark":
                        mark(parts);
                        break;

                    case "unmark":
                        unmark(parts);
                        break;
                    case "delete":
                        delete(parts);
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

    public void mark(String[] parts) throws InvalidSyntaxException { 
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        Task task = list.get(Integer.parseInt(parts[1]) - 1);
        task.markDone();
        ui.showTaskMarked(task);
    }

    public void unmark(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        Task task = list.get(Integer.parseInt(parts[1]) - 1);
        task.unmarkDone();
        ui.showTaskUnmarked(task);
    }

    public void delete(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        Task task = list.remove(Integer.parseInt(parts[1]) - 1);
        ui.showTaskRemoved(task, list.size());
    }

    public void todo(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        ToDo task = new ToDo(parts[1]);
        list.add(task);
        ui.showTaskAdded(task, list.size());
    }

    public void deadline(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2 || !parts[1].contains("/by")) {
            throw new InvalidSyntaxException();
        }
        String[] arguments = parts[1].split("/by ");
        Deadline task = new Deadline(arguments[0], arguments[1]);
        list.add(task);
        ui.showTaskAdded(task, list.size());
    }

    public void event(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2 || (!parts[1].contains("/by") && !parts[1].contains("/to"))) {
            throw new InvalidSyntaxException();
        }
        String[] arguments = parts[1].split("/from", 2);
        String[] datePart = arguments[1].split("/to", 2);
        Event task = new Event(arguments[0], datePart[0], datePart[1]);
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
