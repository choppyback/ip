package woody;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import woody.task.TaskList;
import woody.task.ToDo;

public class TaskListTest {
    @Test
    public void add_oneTask_sizeIncreases() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read book"));

        assertEquals(1, tasks.size(), "TaskList size should increase to 1 after adding one task");
    }
}
