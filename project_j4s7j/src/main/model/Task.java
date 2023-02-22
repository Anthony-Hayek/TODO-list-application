package model;

import org.json.JSONObject;
import persistance.Writable;

public class Task implements Writable {
    private String name;
    private boolean status;

    //EFFECTS: sets the name of the task to oneTask
    //         sets the status to false

    public Task(String oneTask) {

        this.name = oneTask;
        this.status = false;

    }

    //EFFECTS: sets the name of the task to oneTask
    //         sets the status of the task to state
    public Task(String oneTask, Boolean state) {

        this.name = oneTask;
        this.status = state;

    }

    //MODIFIES: this
    //EFFECTS: changes status from false to true;
    public void markAsComplete() {
        this.status = true;

    }


    //EFFECTS: returns name of task
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns name value of status
    public boolean getStatus() {
        return status;
    }

    //EFFECTS: returns a string representing the status of task
    public String checkStatus() {
        if (status == false) {
            return "Incomplete";
        } else {
            return "Complete";
        }
    }


    //EFFECTS: changes the task into a String
    @Override
    public String toString() {
        return name
                + " : "
                + checkStatus();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("task", name);
        json.put("status", status);
        return json;
    }


}
