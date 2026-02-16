package woody.parser;

import woody.exception.InvalidSyntaxException;

public class Parser {
    public static String getCommand(String input) {
        return input.trim().split(" ", 2)[0];
    }

    public static String getArguments(String input) {
        String[] parts = input.trim().split(" ", 2);
        return parts.length < 2 ? "" : parts[1];
    }

    public static int getTaskIndex(String arg) throws InvalidSyntaxException {
        if (arg.isBlank()) {
            throw new InvalidSyntaxException();
        }
        int index;
        try {
            index = Integer.parseInt(arg) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidSyntaxException();
        }
        return index;
    }

    public static String[] getDeadlineArguments(String args) throws InvalidSyntaxException {
        if (!args.contains("/by")) {
            throw new InvalidSyntaxException();
        }
        return args.split("/by ", 2);
    }

    public static String[] getEventArguments(String args) throws InvalidSyntaxException {
        if (!args.contains("/from") || !args.contains("/to")) {
            throw new InvalidSyntaxException();
        }
        String[] first = args.split("/from", 2);
        String[] second = first[1].split("/to", 2);
        return new String[] { first[0], second[0], second[1] };
    }
}
