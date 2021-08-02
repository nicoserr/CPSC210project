package persistence;

import org.json.JSONObject;

// represents an Object that can be transformed into a JSON object (is writable)
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
