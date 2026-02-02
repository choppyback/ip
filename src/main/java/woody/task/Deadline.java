package woody.task;
import java.time.LocalDateTime;

/**
 * Represents a deadline task with a description and a due date.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a deadline task using date-time string.
     *
     * @param description Description of the task.
     * @param by End date and time of the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    /**
     * Constructs a deadline task using date-time object.
     *
     * @param description Description of the task.
     * @param by End date and time of the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toFileString() {
        return String.format("D %s| %s", super.toFileString(), by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}

