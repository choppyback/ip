package woody.task;
import java.time.LocalDateTime;

/**
 * Represents an event task with a description and a start and end time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an event task using date-time strings.
     *
     * @param description Description of the event.
     * @param from Start date and time of the event.
     * @param to End date and time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDateTime.parse(from.trim(), INPUT_FORMAT);
        this.to = LocalDateTime.parse(to.trim(), INPUT_FORMAT);
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

    @Override
    public String toFileString() {
        return String.format("E %s | %s | %s", super.toFileString(), from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
