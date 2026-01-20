import java.util.Scanner;

public class Woody {

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

            if (input.equals("bye")) {
                line();
                exit();
                break;
            }

            line();
            System.out.println("Woody: " + input);
            line();
        }

        scanner.close();
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
