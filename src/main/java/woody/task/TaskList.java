package woody.task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

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
            if(task.contains(keyword)) {
                results.add(task);
            }
        }
        return results;
    }
}
