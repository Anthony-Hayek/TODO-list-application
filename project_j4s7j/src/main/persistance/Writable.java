package persistance;

import org.json.JSONObject;
//Citation: a lot of the Json parts are modelled from the "JsonSerializationDemo" program

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
