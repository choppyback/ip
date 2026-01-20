public class Woody {

    public static void greet() {
        System.out.println("Hello! I'm Woody \nWhat can I do for you?");
    }

    public static void line() {
        System.out.println("-----------------------");
    }
    public static void main(String[] args) {
        // Used chatgpt to generate the logo
        String logo =
                  "W       W   OOOO   OOOO   DDDDD   Y     Y\n"
                + "W       W  O    O O    O  D    D   Y   Y \n"
                + "W   W   W  O    O O    O  D     D    Y Y  \n"
                + " W W W W   O    O O    O  D    D      Y   \n"
                + "  W   W     OOOO   OOOO   DDDDD       Y   \n";

        System.out.println("Hello from\n" + logo);
        line();
        greet();
        line();
    }
}
