package woody.exception;

/**
 * Represents errors that occur during storage operations.
 */
public class StorageException extends WoodyException {

    /**
     * Constructs a StorageException with the specified message.
     *
     * @param message Description of the storage error.
     */
    public StorageException(String message) {
        super(message);
    }
}
