package model;

import model.exceptions.EmptyListException;
import model.exceptions.EmptyNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a note that has a collection of subjects
public class Note implements Writable {
    private ArrayList<Subject> subjects;

    // EFFECTS: constructs a new note with an empty list of subjects
    public Note() {
        subjects = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if there is no subject with the same name in subjects, a new subject is created and added to subjects
    //          and returns true, otherwise returns false
    public boolean addSubject(String name) throws EmptyNameException {
        boolean notFound = true;
        for (Subject s : subjects) {
            if (s.getSubjectName().equals(name)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            subjects.add(new Subject(name));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if subject with given name exists in subjects, then remove it and return true
    //          otherwise return false
    public boolean removeSubject(String name) {
        boolean result = false;
        for (Subject s : subjects) {
            if (s.getSubjectName().equals(name)) {
                subjects.remove(s);
                result = true;
                break;
            }
        }
        return result;
    }

    // EFFECTS: if the list is empty, an EmptyListException is thrown, otherwise:
    //          if a Subject with given name is found in subjects, it is retrieved
    //          if no subject is found, null is returned
    public Subject retrieveSubject(String name) throws EmptyListException {
        if (getSubjectsSize() == 0) {
            throw new EmptyListException();
        } else {
            Subject result = null;
            for (Subject s : subjects) {
                if (s.getSubjectName().equals(name)) {
                    result = s;
                    break;
                }
            }
            return result;
        }
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    // EFFECTS: returns the size of subjects
    public int getSubjectsSize() {
        return subjects.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subjects", subjectsToJson());
        return json;
    }

    // EFFECTS: returns subjects in this note as a JSON array
    private JSONArray subjectsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subject s : subjects) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
