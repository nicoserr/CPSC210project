package model;

import model.exceptions.EmptyListException;
import model.exceptions.InvalidAdditionException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a subject that has a name and a collection of courses
public class Subject implements Writable {

    private ArrayList<Course> courses;
    private String subjectName;
    private String subjectTreeName;

    // EFFECTS: if name length is zero throws InvalidAdditionException
    //          otherwise, creates a new subject with given name and without any courses
    public Subject(String name) throws InvalidAdditionException {
        if (name.length() == 0) {
            throw new InvalidAdditionException();
        }
        courses = new ArrayList<>();
        subjectName = name;
        subjectTreeName = "Subject: " + name;
    }

    // MODIFIES: this
    // EFFECTS: if there is no course with the same name in courses, a new course is created and added to the courses
    //          under this Subject and returns true
    //          otherwise return false
    public boolean addCourse(String name) throws InvalidAdditionException {
        boolean notFound = true;
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            Course newCourse;
            newCourse = new Course(name, this);
            courses.add(newCourse);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if a Course with given name is found in courses, it is removed and true is returned,
    //          otherwise false is returned
    public boolean removeCourse(String name) {
        boolean result = false;
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) {
                courses.remove(c);
                result = true;
                break;
            }
        }
        return result;
    }

    // EFFECTS: if the list is empty, an EmptyListException is thrown, otherwise:
    //          if a Course with given name is found in courses, it is retrieved
    //          if no course is found, returns null
    public Course retrieveCourse(String name) throws EmptyListException {
        Course result = null;
        if (courses.size() == 0) {
            throw new EmptyListException();
        } else {
            for (Course c : courses) {
                if (c.getCourseName().equals(name)) {
                    result = c;
                    break;
                }
            }
        }
        return result;
    }

    // EFFECTS: if the list is empty, an EmptyListException is thrown, otherwise:
    //          if a Course with treeName matching given treeName is found in courses, it is retrieved
    //          if no course is found, returns null
    public Course retrieveTreeCourse(String treeName) throws EmptyListException {
        Course result = null;
        if (courses.size() == 0) {
            throw new EmptyListException();
        } else {
            for (Course c : courses) {
                if (c.getCourseTreeName().equals(treeName)) {
                    result = c;
                    break;
                }
            }
        }
        return result;
    }

    // EFFECTS: returns the size of courses
    public int getCoursesSize() {
        return courses.size();
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectTreeName() {
        return subjectTreeName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", subjectName);
        json.put("courses", coursesToJson());
        return json;
    }

    // EFFECTS: returns courses in this subject as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : courses) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
