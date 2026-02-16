package woody.exception;

/**
 * Represents an exception thrown when a task index
 * is outside the valid range.
 */
public class InvalidTaskIndexException extends WoodyException {

    /**
     * Constructs an InvalidTaskIndexException with the
     * given maximum valid index.
     *
     * @param size Number of tasks in the list.
     */
    public InvalidTaskIndexException(int size) {
        super("Task number must be between 1 and " + size + ".");
    }
}
