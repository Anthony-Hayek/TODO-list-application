package ui;

import model.TaskNotValidException;
import model.TodoList;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
//SOURCE: The template for this ui comes from the "tellerApp" program example

public class TodoApp extends JFrame {
    private static final String JSON_STORE = "./data/todolist.json";
    private TodoList list1;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: constructs the todolist the runs the application
    public TodoApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        list1 = new TodoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTodo();

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runTodo() {
        boolean keepGoing = true;
        String command = null;

        init();


        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("6")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }
        System.out.println("Until next time!");
    }

    //MODIFIES: this
    //EFFECTS: initializes todolist
    private void init() {
        list1 = new TodoList();
        input = new Scanner(System.in);
    }

    //EFFECTS: displays menu with options
    private void displayMenu() {
        System.out.println("Choose one of the following:");
        System.out.println("1 - To view your to-do list");
        System.out.println("2 - To add something to your todo list");
        System.out.println("3 - To remove something from your to-do list");
        System.out.println("4 - To mark something as complete");
        System.out.println("5 - To view the number of tasks you have left to complete");
        System.out.println("6 - To quit the to-do list application");
        System.out.println("7 - To save your to-do list");
        System.out.println("8 - To load your previous to-do list");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewList();

        } else if (command.equals("2")) {
            System.out.println("Enter a task to add");
            String task = input.next();
            addTaskToList(task);

        } else if (command.equals("3")) {
            System.out.println("Enter a task to remove");
            String task = input.next();
            removeTaskFromList(task);

        } else if (command.equals("4")) {
            System.out.println("Enter a task to mark as complete");
            String task = input.next();
            markTaskAsComplete(task);

        } else if (command.equals("5")) {
            amountOfTasksLeft();

        } else if (command.equals("7")) {
            saveTodoList();

        } else if (command.equals("8")) {
            loadTodoList();

        } else {
            System.out.println("Input not valid");

        }
    }

    //EFFECTS: prints the number of tasks left incomplete
    private void amountOfTasksLeft() {
        System.out.println("You have " + list1.countIncomplete() + " incomplete tasks");
        System.out.println();

    }

    //MODIFIES: this
    //EFFECTS: marks the specified task as completed
    private void markTaskAsComplete(String task) {
        list1.completeTask(task);
        System.out.println(task + " has been marked as complete!");
        System.out.println();
    }

    //MODIFIES: this
    //EFFECTS: removes a task from the list
    private void removeTaskFromList(String task) {
        list1.removeTask(task);
        System.out.println(task + " has been removed!");
        System.out.println();
    }

    //MODIFIES: this
    //EFFECTS: adds a task to the list
    private void addTaskToList(String task) {
        try {
            list1.addTask(task);
        } catch (TaskNotValidException e) {
            System.out.println("Task not added");
            return;
        }
        System.out.println(task + " has been added!");
        System.out.println();
    }

    //EFFECTS: prints  all the tasks
    private void viewList() {
        if (list1.getTaskList().size() == 0) {
            System.out.println("Your to-do list is empty");

        } else {
            System.out.println("TO-DO LIST:");
            for (int i = 0; i < list1.getTaskList().size(); i++) {
                System.out.println(list1.getTaskList().get(i).getName() + ": "
                        + list1.getTaskList().get(i).checkStatus());

            }
        }
        System.out.println();
    }

    //EFFECTS: saves the todolist to file
    private void saveTodoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list1);
            jsonWriter.close();
            System.out.println("Saved TO-DO list to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads todolist from file
    private void loadTodoList() {
        try {
            list1 = jsonReader.read();
            System.out.println("Loaded TO-DO list from " + JSON_STORE);
        } catch (IOException | TaskNotValidException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}


