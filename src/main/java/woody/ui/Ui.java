package woody.ui;

import woody.task.Task;
import woody.task.TaskList;

/**
 * Handles user interaction for the Woody application.
 * Provides formatted messages to be displayed to the user.
 */
public class Ui {
    /**
     * Returns the application logo and welcome message.
     */
    public String showWelcome() {
        return "Welcome to Woody: ";
    }
    /**
     * Returns the goodbye message when bye command is received.
     */
    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns an error message to the user.
     *
     * @param message Error message to be shown.
     */
    public String showError(String message) {
        return message;
    }

    /**
     * Returns the task that was added and new number of task.
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
     * Returns the task that was removed and new number of task.
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
     * Returns confirmation after a task is marked as done.
     *
     * @param task The task that was marked.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + "  " + task;
    }

    /**
     * Returns confirmation after a task is unmarked.
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
        return "Here are the tasks in your list:"
                + System.lineSeparator()
                + formatTaskList(tasks);
    }

    /**
     * Returns a formatted list of tasks that match a search keyword.
     *
     * @param tasks Task list containing matching tasks.
     * @return Formatted list of matching tasks.
     */
    public String showMatchingTasks(TaskList tasks) {
        return "Here are the matching tasks in your list:"
                + System.lineSeparator()
                + formatTaskList(tasks);
    }

    /**
     * Returns a formatted message indicating that a task was not added
     * due to a conflict with an existing task.
     *
     * @param task The existing task that causes the clash.
     * @return Task-clash warning message.
     */
    public String showClashingTask(Task task) {
        return "Task not added, clashes with:\n"
                + "  " + task;
    }

    /**
     * Formats the given task list into a numbered string representation.
     *
     * Each task is displayed on its own line, prefixed with a 1-based index.
     * This method is used to construct user-facing messages for displaying
     * task lists.
     *
     * @param tasks The task list to be formatted.
     * @return A string containing the formatted task list.
     */
    private String formatTaskList(TaskList tasks) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            output.append(i + 1)
                    .append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }

        return output.toString();
    }
}
