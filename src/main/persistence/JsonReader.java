package persistence;

import model.Course;
import model.Note;
import model.Subject;
import model.exceptions.EmptyListException;
import model.exceptions.EmptyNameException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Note from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Note read() throws IOException, EmptyNameException, EmptyListException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseNote(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Note from JSON object and returns it
    private Note parseNote(JSONObject jsonObject) throws EmptyNameException, EmptyListException {
        Note note = new Note();
        addSubjects(note, jsonObject);
        return note;
    }

    // MODIFIES: note
    // EFFECTS: parses subjects from JSON object and adds them to note
    private void addSubjects(Note note, JSONObject jsonObject) throws EmptyNameException, EmptyListException {
        JSONArray jsonArray = jsonObject.getJSONArray("subjects");
        for (Object json : jsonArray) {
            JSONObject nextSubject = (JSONObject) json;
            addSubject(note, nextSubject);
        }
    }

    // MODIFIES: note
    // EFFECTS: parses subject from JSON object and adds it to note
    private void addSubject(Note note, JSONObject jsonObject) throws EmptyListException, EmptyNameException {
        String name = jsonObject.getString("name");
        note.addSubject(name);
        Subject subject = note.retrieveSubject(name);
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(subject, nextCourse);
        }
    }

    // MODIFIES: subject
    // EFFECTS: parses course from JSON object and adds it to subject
    private void addCourse(Subject s, JSONObject jsonObject) throws EmptyNameException, EmptyListException {
        String name = jsonObject.getString("name");
        s.addCourse(name);
        Course course = s.retrieveCourse(name);
        JSONArray jsonArray = jsonObject.getJSONArray("topics");
        for (Object json : jsonArray) {
            JSONObject nextTopic = (JSONObject) json;
            addTopic(course, nextTopic);
        }
    }

    // MODIFIES: course
    // EFFECTS: parses topic from JSON object and adds it to course
    private void addTopic(Course c, JSONObject jsonObject) throws EmptyNameException {
        String name = jsonObject.getString("name");
        c.addTopic(name);
    }
}
