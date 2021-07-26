package ui;

import model.Course;
import model.Subject;
import model.Topic;
import model.exceptions.EmptyNameException;
import model.exceptions.EmptyListException;

import java.util.ArrayList;
import java.util.Scanner;

public class NotetakingApp {
    private ArrayList<Subject> subjects;
    private Scanner input;

    // EFFECTS: runs the notetaking application
    public NotetakingApp() {
        runNotetaking();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runNotetaking() {
        boolean keepGoing = true;
        subjects = new ArrayList<>();
        String command;
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (keepGoing) {
            displaySubjectMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processSubjectCommand(command);
            }
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for subject menu
    private void processSubjectCommand(String command) {
        String subjectName;
        if (command.equals("n")) {
            System.out.println("\nEnter Subject name to add:");
            subjectName = input.next();
            addSubject(subjectName);
        } else if (command.equals("d")) {
            System.out.println("\nEnter Subject name to delete:");
            subjectName = input.next();
            removeSubject(subjectName);
        } else if (command.equals("g")) {
            System.out.println("\nEnter Subject name to go to:");
            subjectName = input.next();
            try {
                Subject subject = retrieveSubject(subjectName);
                goToSubject(subject);
            } catch (EmptyListException e) {
                System.out.println("Subject list is empty");
            }
        } else {
            System.out.println("\nInvalid command");
        }
    }

    // MODIFIES: this and parentSubject
    // EFFECTS: processes user command for courses menu belonging to parentSubject
    private void processCourseCommand(String command, Subject parentSubject) {
        String courseName;
        if (command.equals("n")) {
            System.out.println("\nEnter Course name to add:");
            courseName = input.next();
            tryAddCourse(parentSubject, courseName);
        } else if (command.equals("d")) {
            System.out.println("\nEnter Course name to delete:");
            courseName = input.next();
            tryRemoveCourse(parentSubject, courseName);
        } else if (command.equals("g")) {
            System.out.println("\nEnter Course name to go to:");
            courseName = input.next();
            try {
                Course course = parentSubject.retrieveCourse(courseName);
                goToCourse(course);
            } catch (EmptyListException e) {
                System.out.println("Course list is empty");
            }
        } else {
            System.out.println("\nInvalid command");
        }
    }

    // MODIFIES: this and parentCourse
    // EFFECTS: processes user command for topics menu belonging to parentCourse
    private void processTopicCommand(String command, Course parentCourse) {
        String topicName;
        if (command.equals("n")) {
            System.out.println("\nEnter Topic name to add:");
            topicName = input.next();
            tryAddTopic(parentCourse, topicName);
        } else if (command.equals("d")) {
            System.out.println("\nEnter Topic name to delete:");
            topicName = input.next();
            tryRemoveTopic(parentCourse, topicName);
        } else {
            System.out.println("\nInvalid command");
        }
    }

    // MODIFIES: this
    // EFFECTS: if subject is null, signal that no subject was found
    //          otherwise, display course menu and process user input for that subject
    private void goToSubject(Subject subject) {
        if (subject == null) {
            System.out.println("No subject found with that name");
        } else {
            boolean keepGoing = true;
            String courseCommand;
            while (keepGoing) {
                displayCourseMenu(subject);
                courseCommand = input.next();
                if (courseCommand.equals("r")) {
                    keepGoing = false;
                } else {
                    processCourseCommand(courseCommand, subject);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if course is null, signal that no course was found
    //          otherwise, display topic menu and process user input for that course
    private void goToCourse(Course course) {
        if (course == null) {
            System.out.println("No course found with that name");
        } else {
            boolean keepGoing = true;
            String topicCommand;
            while (keepGoing) {
                displayTopicMenu(course);
                topicCommand = input.next();
                if (topicCommand.equals("r")) {
                    keepGoing = false;
                } else {
                    processTopicCommand(topicCommand, course);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a subject with given name to subjects
    private void addSubject(String name) {
        boolean notFound = true;
        for (Subject s : subjects) {
            if (s.getSubjectName().equals(name)) {
                notFound = false;
                break;
            }
        }
        if (notFound) {
            try {
                subjects.add(new Subject(name));
                System.out.println("Subject successfully added");
            } catch (EmptyNameException e) {
                System.out.println("Subject cannot have blank name");
            }
        } else {
            System.out.println("A subject with that name already exists");
        }
    }

    // MODIFIES: this
    // EFFECTS: if subject with given name exists in subjects, then remove it and signals success to the user
    //          if it doesn't, signal failure to the user
    private void removeSubject(String name) {
        boolean found = false;
        for (Subject s : subjects) {
            if (s.getSubjectName().equals(name)) {
                subjects.remove(s);
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Subject successfully deleted!");
        } else {
            System.out.println("No subject found with that name");
        }
    }

    // EFFECTS: if subject with given name exists, it returns it, otherwise null is returned
    private Subject retrieveSubject(String name) throws EmptyListException {
        if (subjects.size() == 0) {
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

    // MODIFIES: this and parentSubject
    // EFFECTS: tries to add a course under parentSubject with courseName, if possible signals success to the user
    //          if course name is already taken or name is invalid, signal failure to the user
    private void tryAddCourse(Subject parentSubject, String courseName) {
        try {
            if (parentSubject.addCourse(courseName)) {
                System.out.println("Course successfully added");
            } else {
                System.out.println("A course with that name already exists");
            }
        } catch (EmptyNameException e) {
            System.out.println("Cannot leave name empty!");
        }
    }

    // MODIFIES: this and parentSubject
    // EFFECTS: if course with courseName is found, remove it from courses and signals success to the user
    //          otherwise signal failure to the user
    private void tryRemoveCourse(Subject parentSubject, String courseName) {
        if (parentSubject.removeCourse(courseName)) {
            System.out.println("Course successfully deleted!");
        } else {
            System.out.println("No course found with that name");
        }
    }

    // MODIFIES: this and parentCourse
    // EFFECTS: tries to add a topic with topicName to parentCourse, if possible, do so and signals success to the user
    //          if topic name is already taken or name is invalid signal failure to the user
    private void tryAddTopic(Course parentCourse, String topicName) {
        try {
            if (parentCourse.addTopic(topicName)) {
                System.out.println("Topic successfully added");
            } else {
                System.out.println("A topic with that name already exists");
            }
        } catch (EmptyNameException e) {
            System.out.println("Cannot leave name empty!");
        }
    }

    // MODIFIES: this and parentCourse
    // EFFECTS: if topic with topicName is found, remove it from topics and signals success to the user
    //          otherwise signal failure to the user
    private void tryRemoveTopic(Course parentCourse, String topicName) {
        if (parentCourse.removeTopic(topicName)) {
            System.out.println("Topic successfully deleted!");
        } else {
            System.out.println("No Topic found with that name");
        }
    }

    // EFFECTS: displays menu of options while looking at subjects to user
    private void displaySubjectMenu() {
        System.out.println("\n-------------------------------------");
        try {
            displaySubjects();
        } catch (EmptyListException e) {
            System.out.println("\nSubject list is empty");
        } finally {
            System.out.println("\nCommands:");
            System.out.println("\tn -> add new Subject");
            System.out.println("\td -> delete Subject");
            System.out.println("\tg -> go to Subject");
            System.out.println("\tq -> quit");
        }
    }

    // EFFECTS: displays menu of options while looking at courses to user
    private void displayCourseMenu(Subject parentSubject) {
        System.out.println("\n-------------------------------------");
        System.out.println("\nWithin subject: " + parentSubject.getSubjectName());
        try {
            displayCourses(parentSubject);
        } catch (EmptyListException e) {
            System.out.println("\nCourse list is empty");
        } finally {
            System.out.println("\nCommands:");
            System.out.println("\tn -> add new Course");
            System.out.println("\td -> delete Course");
            System.out.println("\tg -> go to Course");
            System.out.println("\tr -> return to Subject list");
        }
    }

    // EFFECTS: displays menu of options while looking at topics to user
    private void displayTopicMenu(Course parentCourse) {
        System.out.println("\n-------------------------------------");
        System.out.println("\nWithin course: " + parentCourse.getCourseName());
        try {
            displayTopics(parentCourse);
        } catch (EmptyListException e) {
            System.out.println("Topic list is empty");
        } finally {
            System.out.println("\nCommands:");
            System.out.println("\tn -> add new Topic");
            System.out.println("\td -> delete Topic");
            System.out.println("\tr -> return to Course list");
        }
    }

    // EFFECTS: displays menu of Subjects to user, if subjects is empty throw EmptyListException
    private void displaySubjects() throws EmptyListException {
        if (subjects.size() == 0) {
            throw new EmptyListException();
        } else {
            System.out.println("\nSubjects:");
            for (Subject s : subjects) {
                System.out.println("\t- " + s.getSubjectName());
            }
        }
    }

    // EFFECTS: displays menu of Courses belonging to Subject s to user,
    // if courses is empty throw EmptyListException
    private void displayCourses(Subject s) throws EmptyListException {
        ArrayList<Course> courses = s.getCourses();
        if (courses.size() == 0) {
            throw new EmptyListException();
        } else {
            System.out.println("Courses:");
            for (Course c : courses) {
                System.out.println("\t- " + c.getCourseName());
            }
        }
    }

    // EFFECTS: displays menu of Topics belonging to Course c to user,
    // if topics is empty throw EmptyListException
    private void displayTopics(Course c) throws EmptyListException {
        ArrayList<Topic> topics = c.getTopics();
        if (topics.size() == 0) {
            throw new EmptyListException();
        } else {
            System.out.println("Topics:");
            for (Topic t : topics) {
                System.out.println("\t- " + t.getTopicName());
            }
        }
    }
}
