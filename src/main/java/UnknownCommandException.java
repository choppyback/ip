public class UnknownCommandException extends WoodyException {
    public UnknownCommandException(String command) {
        super("I do not recognise the command: " + command + "\nDo you mean (todo, deadline, event, list, bye)?");
    }
}
