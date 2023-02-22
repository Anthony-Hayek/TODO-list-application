package persistance;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String name, Boolean status, Task task) {
        assertEquals(name, task.getName());
        assertEquals(status, task.getStatus());
    }
}


