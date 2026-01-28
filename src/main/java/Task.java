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
    public static Task fileToTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            System.out.println(parts[1]);
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            Task task;

            switch (type) {
            case "T":
                task = new ToDo(parts[2]);
                break;
            case "D":
                task = new Deadline(parts[2], parts[3]);
                break;
            case "E":
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                return null;
            }

            if (isDone) {
                task.markDone();
            }
            return task;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description.trim();
    }
}
