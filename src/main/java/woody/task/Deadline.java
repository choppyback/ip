package woody.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import woody.exception.InvalidSyntaxException;

/**
 * Represents a deadline task with a description and a due date.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a deadline task from a date-time string.
     * Validates that the date-time follows the expected format.
     *
     * @param description Description of the task.
     * @param by End date and time of the task in dd-MM-yyyy HHmm format.
     * @throws InvalidSyntaxException If the date-time format is invalid.
     */
    public Deadline(String description, String by) throws InvalidSyntaxException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidSyntaxException(
                    "Deadline must be in format: dd-MM-yyyy HHmm");
        }
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

