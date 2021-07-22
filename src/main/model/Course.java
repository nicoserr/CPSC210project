package model;

import model.exceptions.EmptyNameException;

import java.util.ArrayList;

public class Course {

    private ArrayList<Topic> topics;
    private String courseName;
    private Subject parentSubject;

    // EFFECTS: creates a new course with given name, empty list of topics and belonging to Subject s
    //          if length of name is zero throws EmptyNameException
    public Course(String name, Subject s) throws EmptyNameException {
        if (name.length() == 0) {
            throw new EmptyNameException();
        }
        courseName = name;
        topics = new ArrayList<Topic>();
        parentSubject = s;
    }

    // MODIFIES: this
    // EFFECTS: if there is no topic with the same name in topics, a new topic is created and added it to the topics
    //          under this course and returns true.
    //          if EmptyNameException is thrown when creating anew Topic, it will catch it and return false.
    //          otherwise return false.
    public boolean addTopic(String name) {
        boolean notFound = true;
        for (Topic t : topics) {
            if (t.getTopicName().equals(name)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            Topic newTopic;
            try {
                newTopic = new Topic(name, this);
            } catch (EmptyNameException e) {
                System.out.println("Cannot leave name empty!");
                return false;
            }
            topics.add(newTopic);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if a Topic with given name is found in topics, it is removed and true is returned,
    // otherwise false is returned
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

    // EFFECTS: if a Topic with given name is found in topics, it is retrieved
    //          if no topic is found, return null.
    public Topic retrieveTopic(String name) {
        boolean isSearching = true;
        Topic result = null;
        for (Topic t : topics) {
            if (t.getTopicName().equals(name)) {
                result = t;
                break;
            }
        }
        return result;
    }

    // EFFECTS: prints the list of topics for the course, if the list is empty, then prints an empty list message.
    public String getListOfTopicNames() {
        String list = "";
        for (Topic t : topics) {
            if (list != "") {
                list = list + ", '" + t.getTopicName() + "'";
            } else {
                list = list + "'" + t.getTopicName() + "'";
            }
        }
        if (list.equals("")) {
            list = "This course has no topics!";
        }
        return list;
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
