package model;

import model.exceptions.EmptyNameException;
import model.exceptions.InvalidAdditionException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a topic that belongs to a course and has a name
public class Topic implements Writable {

    private String topicName;
    private String topicTreeName;
    private Course parentCourse;

    // EFFECTS: creates a new empty topic with given name that belongs to parentCourse c
    public Topic(String name, Course c) throws EmptyNameException {
        if (name.length() == 0) {
            throw new EmptyNameException();
        }
        topicName = name;
        parentCourse = c;
    }

    public String getTopicName() {
        return topicName;
    }

    public Course getParentCourse() {
        return parentCourse;
    }

    // TODO: Make sure to check with TA
    public void addInvalid() throws InvalidAdditionException {
        throw new InvalidAdditionException();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", topicName);
        return json;
    }
}
