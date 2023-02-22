package persistance;

import model.Task;
import model.TaskNotValidException;
import model.TodoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TodoList tl = new TodoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOexception was expected");
        } catch (IOException e) {
            // pass;
        }
    }

    @Test
    void testWriterEmptyTodolist() {
        try {
            TodoList tl = new TodoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
            tl = reader.read();
            assertEquals(0, tl.numTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (TaskNotValidException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    void testGeneralTodoList(){
        try{
            TodoList tl = new TodoList();
            tl.addTask("Read");
            tl.addTask("Study");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json");
            tl = reader.read();
            List<Task> tasks = tl.getTaskList();
            assertEquals(2, tasks.size());
            checkTask("Read", false, tasks.get(0));
            checkTask("Study", false, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (TaskNotValidException e) {
            fail("Exception should not have been thrown");
        }
    }

}


