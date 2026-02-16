package woody.exception;

/**
 * Represents an exception thrown when an unknown command is entered.
 */
public class UnknownCommandException extends WoodyException {

    /**
     * Constructs an UnknownCommandException for the given command.
     *
     * @param command The unrecognized command entered by the user.
     */
    public UnknownCommandException(String command) {
        super("Unknown command: \"" + command + "\".\n"
                + "Try one of the following commands:\n"
                + "todo | deadline | event | mark | unmark | "
                + "delete | list | find | bye");
    }
}
