import java.io.IOException;
import java.util.Scanner;

public class Woody {
    private Storage storage;
    private static TaskList list;
    // private static ArrayList<Task> list;

    public Woody(String filePath) {
        storage = new Storage();
        loadTask();
    }

    public void run() {
        showLogo();
        greet();
        chat();
    }

    public void greet() {
        System.out.println("Hello! I'm Woody \nWhat can I do for you?\n");
        line();
    }

    public void line() {
        System.out.println("-----------------------");
    }

    public void saveList() {
        try {
            storage.save(list.getList());
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon!\n");
        saveList();
        line();
    }

    public void chat() {
        loadTask();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.print("You: ");
                String input = scanner.nextLine().trim();
                String[] parts = input.split(" ", 2);

                switch (parts[0]) {
                    case "todo":
                        line();
                        todo(parts);
                        line();
                        break;

                    case "deadline":
                        line();
                        deadline(parts);
                        line();
                        break;

                    case "event":
                        line();
                        event(parts);
                        line();
                        break;

                    case "mark":
                        line();
                        mark(parts);
                        line();
                        break;

                    case "unmark":
                        line();
                        unmark(parts);
                        line();
                        break;
                    case "delete":
                        line();
                        delete(parts);
                        line();
                        break;
                    case "list":
                        line();
                        displayList();
                        line();
                        break;
                    case "bye":
                        line();
                        exit();
                        return;
                    default:
                        line();
                        throw new UnknownCommandException(input);
                }
            } catch (WoodyException e) {
                System.out.println(e.getMessage());
                line();
            }
        }
    }

    public void displayList() {
        System.out.println("Here are the tasks in your list: ");
        for(int i = 1; i <= list.size(); i++) {
            System.out.println(i + ". " + list.get(i - 1));
        }
    }

    public void mark(String[] parts) throws InvalidSyntaxException { 
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        Task task = list.get(Integer.parseInt(parts[1]) - 1);
        task.markDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task + "\n");
    }

    public void unmark(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        Task task = list.get(Integer.parseInt(parts[1]) - 1);
        task.unmarkDone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task + "\n");
    }

    public void delete(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        Task task = list.remove(Integer.parseInt(parts[1]) - 1);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task + "\n");
        System.out.println("Now you have " + list.size() + " tasks in the list.\n");
    }

    public void todo(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2) {
            throw new InvalidSyntaxException();
        }
        ToDo task = new ToDo(parts[1]);
        list.add(task);
    }

    public void deadline(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2 || !parts[1].contains("/by")) {
            throw new InvalidSyntaxException();
        }
        String[] arguments = parts[1].split("/by ");
        Deadline task = new Deadline(arguments[0], arguments[1]);
        list.add(task);
    }

    public void event(String[] parts) throws InvalidSyntaxException {
        if (parts.length < 2 || (!parts[1].contains("/by") && !parts[1].contains("/to"))) {
            throw new InvalidSyntaxException();
        }
        String[] arguments = parts[1].split("/from", 2);
        String[] datePart = arguments[1].split("/to", 2);
        Event task = new Event(arguments[0], datePart[0], datePart[1]);
        list.add(task);
    }
        

    // public static void addTask(Task task) {
    //     list.add(task);
    //     System.out.println("Got it. I've added this task:");
    //     System.out.println("  " + task);
    //     System.out.println("Now you have " + list.size() + " tasks in the list.\n");
    // }

    public void loadTask() {
        try {
            list = new TaskList(storage.load());
        } catch (IOException e) {
            list = new TaskList();
        }
    }

    public void showLogo() {
        String logo =
                  "W       W   OOOO   OOOO   DDDDD   Y     Y\n"
                + "W       W  O    O O    O  D    D   Y   Y \n"
                + "W   W   W  O    O O    O  D     D    Y Y  \n"
                + " W W W W   O    O O    O  D    D      Y   \n"
                + "  W   W     OOOO   OOOO   DDDDD       Y   \n";

        System.out.println("Hello from\n" + logo);
        line();
    }

    public static void main(String[] args) {
        // Used chatgpt to generate the logo
        new Woody("data/tasks.txt").run();
        // showLogo();
        // greet();
        // chat();
    }
}
