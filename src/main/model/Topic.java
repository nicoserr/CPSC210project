package model;

import model.exceptions.EmptyNameException;

public class Topic {

    private String topicName;
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
}
