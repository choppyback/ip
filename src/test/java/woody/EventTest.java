package woody;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import woody.exception.InvalidSyntaxException;
import woody.task.Event;

public class EventTest {
    @Test
    void constructor_invalidDateSeparator_throwsException() {
        assertThrows(InvalidSyntaxException.class, () ->
                new Event("meeting",
                        "20-02-2026 1400",
                        "20/02/2026 1600"));
    }

    @Test
    void constructor_endBeforeStart_throwsException() {
        assertThrows(InvalidSyntaxException.class, () ->
                new Event("meeting",
                        "20/02/2026 1600",
                        "20/02/2026 1400"));
    }
}
