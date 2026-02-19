package woody.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import woody.exception.InvalidSyntaxException;

/**
 * Represents an event task with a description and a start and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an event task from date-time strings.
     * Validates that both date-time values follow the expected format
     * and that the end time is not before the start time.
     *
     * @param description Description of the event.
     * @param from Start date and time of the event in dd-MM-yyyy HHmm format.
     * @param to End date and time of the event in dd-MM-yyyy HHmm format.
     * @throws InvalidSyntaxException If the date-time format is invalid
     *                                or the end time is before the start time.
     */
    public Event(String description, String from, String to) {
        super(description);
        try {
            this.from = LocalDateTime.parse(from.trim(), INPUT_FORMAT);
            this.to = LocalDateTime.parse(to.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidSyntaxException(
                    "Event must be in format: event <desc> "
                            + "/from dd-MM-yyyy HHmm "
                            + "/to dd-MM-yyyy HHmm");
        }

        if (this.to.isBefore(this.from)) {
            throw new InvalidSyntaxException(
                    "Event end time cannot be before start time.");
        }
    }

    /**
     * Constructs an event task using date-time objects.
     *
     * @param description Description of the event.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns true if this event clashes with the given event.
     *
     * @param other The other event to check against.
     * @return True if the two events overlap in time.
     */
    public boolean clashesWith(Event other) {
        return this.from.isBefore(other.to)
                && other.from.isBefore(this.to);
    }

    @Override
    public String toFileString() {
        return String.format("E %s | %s | %s", super.toFileString(), from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
