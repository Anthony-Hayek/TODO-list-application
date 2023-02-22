package persistance;

import model.Task;
import model.TaskNotValidException;
import model.TodoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TodoList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (TaskNotValidException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            TodoList tl = reader.read();
            assertEquals(0, tl.numTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testReaderGeneralTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList.json");
        try {
            TodoList tl = reader.read();
            List<Task> tasks = tl.getTaskList();
            assertEquals(2, tasks.size());
            checkTask("Read", false, tasks.get(0));
            checkTask("Study", false, tasks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown");
        }
    }

}



