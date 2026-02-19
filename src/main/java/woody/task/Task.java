package woody.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a generic task in the Woody application.
 * A task has a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    protected static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    protected static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    /**
     * Constructs a task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is completed.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkDone() {
        isDone = false;
    }

    /**
     * Returns the string representation of this task for file storage.
     *
     * @return File-compatible string representation of the task.
     */
    public String toFileString() {
        return String.format("| %d | %s",
            isDone ? 1 : 0,
            description
        );
    }

    /**
     * Creates a task from a line read from the storage file.
     *
     * @param line Line read from the storage file.
     * @return The reconstructed task, or {@code null} if the line is invalid.
     */
    public static Task fileToTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            Task task;

            switch (type) {
            case "T":
                task = new ToDo(parts[2]);
                break;
            case "D":
                task = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                break;
            case "E":
                task = new Event(parts[2], LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                break;
            default:
                return null;
            }

            if (isDone) {
                task.markDone();
            }
            return task;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks whether the task description contains the given keyword.
     *
     * @param keyword Keyword to search for.
     * @return {@code true} if the description contains the keyword, {@code false} otherwise.
     */
    public boolean contains(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return description.toLowerCase().contains(lowerKeyword);
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description.trim();
    }
}
