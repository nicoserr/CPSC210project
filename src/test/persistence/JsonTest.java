package persistence;

import model.Course;
import model.Subject;
import model.Topic;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSubject(String name, Subject subject) {
        assertEquals(name, subject.getSubjectName());
    }

    protected void checkCourse(String name, Subject parentSubject, Course course) {
        assertEquals(name, course.getCourseName());
        assertEquals(parentSubject, course.getParentSubject());
    }

    protected void checkTopic(String name, Course parentCourse, Topic topic) {
        assertEquals(name, topic.getTopicName());
        assertEquals(parentCourse, topic.getParentCourse());
    }
}
