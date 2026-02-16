package woody.parser;

import woody.exception.InvalidSyntaxException;

/**
 * Parses user input into commands and arguments.
 */
public class Parser {

    /**
     * Extracts the command word from the given input.
     *
     * @param input Raw user input.
     * @return The command word.
     */
    public static String getCommand(String input) {
        return input.trim().split(" ", 2)[0];
    }

    /**
     * Extracts the arguments from the given input.
     *
     * @param input Raw user input.
     * @return The arguments string, or an empty string if none is provided.
     */
    public static String getArguments(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts.length < 2 ? "" : parts[1];
    }

    /**
     * Parses the task index from the given argument string.
     *
     * @param arg Argument containing the task number.
     * @return Zero-based task index.
     * @throws InvalidSyntaxException If the argument is blank or not a valid number.
     */
    public static int getTaskIndex(String arg)
            throws InvalidSyntaxException {

        if (arg.isBlank()) {
            throw new InvalidSyntaxException("Command cannot be empty.");
        }
        try {
            return Integer.parseInt(arg) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidSyntaxException("Index must be a number.");
        }
    }

    /**
     * Parses description and deadline from deadline arguments.
     *
     * @param args Argument string after the deadline command.
     * @return Array containing description and deadline date.
     * @throws InvalidSyntaxException If the format is invalid.
     */
    public static String[] getDeadlineArguments(String args) throws InvalidSyntaxException {
        if (!args.contains("/by")) {
            throw new InvalidSyntaxException(
                    "Deadline must be in format: deadline <desc> /by <date>.");
        }
        return args.split("/by ", 2);
    }

    /**
     * Parses description, start time, and end time from event arguments.
     *
     * @param args Argument string after the event command.
     * @return Array containing description, start time, and end time.
     * @throws InvalidSyntaxException If the format is invalid.
     */
    public static String[] getEventArguments(String args)
            throws InvalidSyntaxException {

        if (!args.contains("/from") || !args.contains("/to")) {
            throw new InvalidSyntaxException(
                    "Event must be in format: event <desc> /from <start> /to <end>.");
        }
        String[] first = args.split("/from", 2);
        String[] second = first[1].split("/to", 2);
        return new String[] { first[0], second[0], second[1] };
    }
}
