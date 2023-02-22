package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TaskTest {
    private Task task;

    @BeforeEach
    public void setup(){
            task = new Task("Study for CPSC 210");
    }

    @Test
    public void testConstructor(){
        assertEquals("Study for CPSC 210", task.getName());
    }

    @Test
    public void testMarkAsComplete(){
        assertFalse(task.getStatus());
        task.markAsComplete();
        assertTrue(task.getStatus());

    }

    @Test
    public void testCheckStatus(){
        assertEquals("Incomplete", task.checkStatus());
        task.markAsComplete();
        assertEquals("Complete", task.checkStatus());

    }

    @Test
    public void testToString(){

        assertEquals("Study for CPSC 210 : Incomplete", task.toString());

    }






}
