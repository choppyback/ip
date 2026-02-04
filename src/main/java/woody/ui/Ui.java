package woody.ui;

import woody.task.Task;
import woody.task.TaskList;

/**
 * Handles user interaction for the Woody application.
 * Provides formatted messages to be displayed to the user.
 */
public class Ui {
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
     * Returns a formatted list of all tasks in the task list.
     *
     * @param tasks The task list to be displayed.
     * @return Formatted task list.
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
     * Returns a formatted list of tasks that match a search keyword.
     *
     * @param tasks Task list containing matching tasks.
     * @return Formatted list of matching tasks.
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
