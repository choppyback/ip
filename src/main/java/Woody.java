import java.util.*;

public class Woody {
    private static ArrayList<Task> list = new ArrayList();

    public static void greet() {
        System.out.println("Hello! I'm Woody \nWhat can I do for you?\n");
        line();
    }

    public static void line() {
        System.out.println("-----------------------");
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!\n");
        line();
    }

    public static void chat() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if(parts[0].equals("mark")) {
                line();
                mark(parts[1]);
                continue;
            }

            if(parts[0].equals("list")) {
                line();
                displayList();
                continue;
            }

            if (parts[0].equals("bye")) {
                line();
                exit();
                break;
            }

            line();
            System.out.println("added: " + input);
            list.add(new Task(input));
            line();
        }

        scanner.close();
    }

    public static void displayList() {
        System.out.println("Here are the tasks in your list: ");
        for(int i = 1; i <= list.size(); i++) {
            System.out.println(i + ". " + list.get(i - 1));
        }
        line();
    }

    public static void mark(String num) {
        Task task = list.get(Integer.parseInt(num) - 1);
        task.markDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task + "\n");
        line();
    }

    public static void showLogo() {
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
        showLogo();
        greet();
        chat();
    }
}
