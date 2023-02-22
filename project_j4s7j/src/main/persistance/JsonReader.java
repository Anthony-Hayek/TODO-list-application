package persistance;

import model.Task;
import model.TaskNotValidException;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;

public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads todolist from file and return it;
    //throws IOException if an error occurs reading data from file
    public TodoList read() throws IOException, TaskNotValidException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);

    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses todolist from JSON object and return it
    private TodoList parseTodoList(JSONObject jsonObject) throws TaskNotValidException {
        TodoList tl = new TodoList();
        addTaskList(tl, jsonObject);
        return tl;
    }

    //MODIFIES: tl
    //EFFECTS: parses taskList from JSON object and adds it to todolist
    private void addTaskList(TodoList tl, JSONObject jsonObject) throws TaskNotValidException {
        JSONArray jsonArray = jsonObject.getJSONArray("tasksList");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tl, nextTask);
        }

    }

    // MODIFIES: tl
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addTask(TodoList tl, JSONObject jsonObject) throws TaskNotValidException {
        String name = jsonObject.getString("task");
        Boolean status = Boolean.valueOf(jsonObject.getBoolean("status"));
        Task task = new Task(name, status);
        tl.addTask(task);
    }

}



