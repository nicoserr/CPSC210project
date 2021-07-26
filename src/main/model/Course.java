package model;

import model.exceptions.EmptyListException;
import model.exceptions.EmptyNameException;

import java.util.ArrayList;

public class Course {

    private ArrayList<Topic> topics;
    private String courseName;
    private Subject parentSubject;

    // EFFECTS: if length of name is zero throws EmptyNameException,
    //          otherwise creates a new course with given name, empty list of topics and belonging to Subject s
    public Course(String name, Subject s) throws EmptyNameException {
        if (name.length() == 0) {
            throw new EmptyNameException();
        }
        courseName = name;
        topics = new ArrayList<>();
        parentSubject = s;
    }

    // MODIFIES: this
    // EFFECTS: if there is no topic with the same name in topics, a new topic is created and added it to the topics
    //          under this course and returns true
    //          otherwise returns false
    public boolean addTopic(String name) throws EmptyNameException {
        boolean notFound = true;
        for (Topic t : topics) {
            if (t.getTopicName().equals(name)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            Topic newTopic;
            newTopic = new Topic(name, this);
            topics.add(newTopic);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if a Topic with given name is found in topics, it is removed and true is returned,
    //          otherwise false is returned
    public boolean removeTopic(String name) {
        boolean result = false;
        for (Topic t : topics) {
            if (t.getTopicName().equals(name)) {
                topics.remove(t);
                result = true;
                break;
            }
        }
        return result;
    }

    // EFFECTS: if the list is empty, an EmptyListException is thrown, otherwise:
    //          if a Topic with given name is found in topics, it is retrieved
    //          if no topic is found, null is returned
    public Topic retrieveTopic(String name) throws EmptyListException {
        Topic result = null;
        if (topics.size() == 0) {
            throw new EmptyListException();
        } else {
            for (Topic t : topics) {
                if (t.getTopicName().equals(name)) {
                    result = t;
                    break;
                }
            }
        }
        return result;
    }

    // EFFECT: return the size of topics
    public int getTopicsSize() {
        return topics.size();
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public String getCourseName() {
        return courseName;
    }

    public Subject getParentSubject() {
        return parentSubject;
    }

}
