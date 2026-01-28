public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        isDone = true;
    }

    public void unmarkDone() {
        isDone = false;
    }

    public String toFileString() {
        return String.format("| %d | %s",
            isDone ? 1 : 0,
            description
        );
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description.trim();
    }
}
