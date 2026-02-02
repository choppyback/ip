package woody.task;

/**
 * Represents a todo task with a description.
 */
public class ToDo extends Task {
    /**
     * Constructs a todo task with the given description.
     *
     * @param description Description of the todo task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        return "T " + super.toFileString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
