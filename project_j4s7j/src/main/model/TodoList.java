package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;

public class TodoList implements Writable {

    private ArrayList<Task> taskList;

    public TodoList() {
        taskList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds a task to the taskList
    //         throws an exception if the name of the task is ""
    public void addTask(String taskName) throws TaskNotValidException {
        if (taskName == "") {
            throw new TaskNotValidException();
        }
        Task task = new Task(taskName);
        addTask(task);

    }

    //MODIFIES: this
    //EFFECTS: adds a task to the taskList
    //         throws an exception if the name of the task is ""
    public void addTask(Task task) throws TaskNotValidException {
        if (task.getName().equals("")) {
            throw new TaskNotValidException();
        }
        taskList.add(task);
    }

    //MODIFIES: this
    //EFFECTS: removes a task from the taskList
    public void removeTask(String task) {
        for (int i = 0; i < getTaskList().size(); i++) {
            if (getTaskList().get(i).getName().equals(task)) {
                getTaskList().remove(i);
                i--;
            }

        }
    }

    //EFFECTS: returns the taskList
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    //EFFECTS: returns number of incomplete tasks
    public int countIncomplete() {
        int count = 0;
        for (int i = 0; i < getTaskList().size(); i++) {
            if (getTaskList().get(i).getStatus() == false) {
                count++;
            }
        }
        return count;

    }


    //this
    //EFFECTS: a tasks status is changed from incomplete to complete
    public void completeTask(String task) {
        for (int i = 0; i < getTaskList().size(); i++) {
            if (getTaskList().get(i).getName().equals(task)) {
                getTaskList().get(i).markAsComplete();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: returns number of tasks in todolist
    public int numTasks() {
        return taskList.size();
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasksList", tasksToJson());
        return json;
    }

    //EFFECTS: returns tasks in this tasklist as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : taskList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;

    }

}


