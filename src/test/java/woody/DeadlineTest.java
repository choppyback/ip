package woody;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import woody.exception.InvalidSyntaxException;
import woody.task.Deadline;

public class DeadlineTest {
    @Test
    public void toString_validDeadline_correctFormat() {
        Deadline deadline = new Deadline("music", "2/2/2002 1200");

        assertEquals("[D][ ] music (by: 02 Feb 2002 12:00)",
                deadline.toString(),
                "Deadline toString should return the correct formatted string");
    }

    @Test
    void constructor_invalidDateFormat_throwsException() {
        assertThrows(InvalidSyntaxException.class, () ->
                new Deadline("submit report",
                        "20-02-2026 1800"));
    }

    @Test
    void constructor_invalidTimeFormat_throwsException() {
        assertThrows(InvalidSyntaxException.class, () ->
                new Deadline("submit report",
                        "20/02/2026 18:00"));
    }
}
