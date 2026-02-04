package woody.ui;
import java.util.Scanner;

import woody.task.Task;
import woody.task.TaskList;

/**
 * Handles all user interaction for the Woody application.
 * Responsible for displaying messages and reading user input.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui instance for reading user input from standard input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the application logo.
     */
    public void showLogo() {
        String logo =
                  "W       W   OOOO   OOOO   DDDDD   Y     Y\n"
                + "W       W  O    O O    O  D    D   Y   Y \n"
                + "W   W   W  O    O O    O  D     D    Y Y  \n"
                + " W W W W   O    O O    O  D    D      Y   \n"
                + "  W   W     OOOO   OOOO   DDDDD       Y   \n";

        System.out.println("Hello from\n\n" + logo);
        showLine();
    }

    /**
     * Displays the line divider.
     */
    public void showLine() {
        System.out.println("-----------------------");
    }

    /**
     * Displays the welcome message when application starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Woody");
        System.out.println("What can I do for you?\n");
        showLine();
    }

    /**
     * Reads a command entered by the user.
     *
     * @return The trimmed user input.
     */
    public String readCommand() {
        System.out.print("You: ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays the goodbye message when bye command is received.
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays an error message to the user.
     *
     * @param message Error message to be shown.
     */
    public String showError(String message) {
        return message;
    }

    /**
     * Displays the task that was added and new number of task.
     *
     * @param task The task that was added.
     * @param size The number of task in the list.
     */
    public String showTaskAdded(Task task, int size) {
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    /**
     * Displays the task that was removed and new number of task.
     *
     * @param task The task that was removed.
     * @param size The number of task in the list.
     */
    public String showTaskRemoved(Task task, int size) {
        return "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    /**
     * Displays confirmation after a task is marked as done.
     *
     * @param task The task that was marked.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + "  " + task;
    }

    /**
     * Displays confirmation after a task is unmarked.
     *
     * @param task The task that was unmarked.
     */
    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + "  " + task;
    }

    /**
     * Displays all tasks currently in the task list.
     *
     * @param tasks The task list to be displayed.
     */
    public String showTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }

        return sb.toString().trim();
    }

    /**
     * Displays the list of tasks that match a search keyword.
     *
     * @param tasks Task list containing the matching tasks.
     */
    public String showMatchingTasks(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }

        return sb.toString().trim();
    }
}
