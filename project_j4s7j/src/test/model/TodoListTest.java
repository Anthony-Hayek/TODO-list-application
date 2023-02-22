package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TodoListTest {

    private TodoList todo;


    @BeforeEach
    private void setup(){
        todo = new TodoList();
    }

    @Test
    public void testConstructor(){
         TodoList todo2;
         todo2 = new TodoList();
         assertEquals(0, todo2.getTaskList().size());
    }

    @Test
    public void testAddTask(){
        assertEquals(0, todo.getTaskList().size());
        try {
            todo.addTask("Study");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(1, todo.getTaskList().size());

    }

    @Test
    public void testRemoveTask(){
        assertEquals(0, todo.getTaskList().size());
        try {
            todo.addTask("Study");
            todo.addTask("Read");
        } catch (TaskNotValidException e) {
           fail("Should not have thrown exception");
        }

        assertEquals(2, todo.getTaskList().size());
        todo.removeTask("Study");
        assertEquals(1, todo.getTaskList().size());

    }

    @Test
    public void testRemoveTaskNotFound(){
        assertEquals(0, todo.getTaskList().size());
        try {
            todo.addTask("Study");
            todo.addTask("Read");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(2, todo.getTaskList().size());
        todo.removeTask("write");
        assertEquals(2, todo.getTaskList().size());

    }

    @Test
    public void testIncomplete(){
        Task task = new Task("Study");
        Task task2 = new Task("Read");
        try {
            todo.addTask("Study");
            todo.addTask("Read");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown exception");
        }

        assertEquals(2, todo.countIncomplete());

    }

    @Test
    public void testCompleteTask(){
        Task task = new Task("Study");
        Task task2 = new Task("Read");
        try {
            todo.addTask("Study");
            todo.addTask("Read");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(2, todo.countIncomplete());
        todo.completeTask("Study");
        assertEquals(1, todo.countIncomplete());
    }

    @Test
    public void testNumTasks(){
        assertEquals(0, todo.numTasks());
        Task task1 = new Task("Study");
        try {
            todo.addTask("Study");
        } catch (TaskNotValidException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(1, todo.numTasks());

    }

    @Test
    public void testAddTaskInvalidString(){
        assertEquals(0, todo.getTaskList().size());
        try {
            todo.addTask("");
            fail("Should have thrown exception");
        } catch (TaskNotValidException e) {
            //expected
        }
        assertEquals(0, todo.getTaskList().size());

    }

    @Test
    public void testAddTaskInvalidTask(){
        assertEquals(0, todo.getTaskList().size());
        Task task = new Task("");
        try {
            todo.addTask(task);
            fail();
        } catch (TaskNotValidException e) {
            //expected
        }
        assertEquals(0, todo.getTaskList().size());
    }


}


