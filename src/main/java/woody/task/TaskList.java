package woody.task;
import java.util.ArrayList;

/**
 * Represents a list of tasks in the Woody application.
 * Provides basic operations to add, retrieve, and remove tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list using an existing list of tasks.
     *
     * @param tasks List of tasks to initialize this task list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword.
     * The original task list is not modified.
     *
     * @param keyword Keyword to search for.
     * @return A task list containing all matching tasks.
     */
    public TaskList find(String keyword) {
        TaskList results = new TaskList();
        for (Task task : tasks) {
            if (task.contains(keyword)) {
                results.add(task);
            }
        }
        return results;
    }

    /**
     * Returns an existing event that clashes with the given event,
     * or null if no such event exists.
     *
     * @param newEvent The event to be checked for clashes.
     * @return A clashing event if found, otherwise null.
     */
    public Event findClashingEvent(Event newEvent) {
        for (Task task : tasks) {
            if (!(task instanceof Event)) {
                continue;
            }
            Event existingEvent = (Event) task;
            if (existingEvent.clashesWith(newEvent)) {
                return existingEvent;
            }
        }
        return null;
    }
}
