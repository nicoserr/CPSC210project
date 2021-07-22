package model;

import model.exceptions.EmptyNameException;

import java.util.ArrayList;

public class Subject {

    private ArrayList<Course> courses;
    private String subjectName;

    //TODO: specify all methods that must be later implemented

    // EFFECTS: creates a new subject with given name and without any courses,
    // if name length is zero throws EmptyNameException
    public Subject(String name) throws EmptyNameException {
        if (name.length() == 0) {
            throw new EmptyNameException();
        }
        courses = new ArrayList<Course>();
        subjectName = name;
    }

    // MODIFIES: this
    // EFFECTS: if there is no course with the same name in courses, a new course is created and added to the courses
    //          under this Subject and returns true.
    //          if EmptyNameException is thrown when creating a new Course, it will catch it and return false.
    //          otherwise return false.
    public boolean addCourse(String name) {
        boolean notFound = true;
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            Course newCourse;
            try {
                newCourse = new Course(name, this);
            } catch (EmptyNameException e) {
                System.out.println("Cannot leave name empty!");
                return false;
            }
            courses.add(newCourse);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if a Course with given name is found in courses, it is removed and true is returned,
    // otherwise false is returned
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

    // EFFECTS: if a Course with given name is found in courses, it is retrieved
    //          if no course is found, return null.
    public Course retrieveCourse(String name) {
        boolean isSearching = true;
        Course result = null;
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) {
                result = c;
                break;
            }
        }
        return result;
    }

    // EFFECTS: prints the list of topics for the course, if the list is empty, then prints an empty list message.
    public String getListOfTopicNames() {
        String list = "";
        for (Course c : courses) {
            if (list != "") {
                list = list + ", '" + c.getCourseName() + "'";
            } else {
                list = list + "'" + c.getCourseName() + "'";
            }
        }
        if (list.equals("")) {
            list = "This course has no topics!";
        }
        return list;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getSubjectName() {
        return subjectName;
    }

}
