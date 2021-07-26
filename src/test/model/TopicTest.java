package model;

import model.exceptions.EmptyNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TopicTest {
    Topic testTopic;
    Course parentCourse;
    Subject parentSubject;

    @BeforeEach
    public void setup(){
        try {
            parentSubject = new Subject("Parent Subject");
            parentCourse = new Course("Parent Course", parentSubject);
            testTopic = new Topic("Topic", parentCourse);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testTopicNoException() {
        Topic fancyNameTopic;
        try {
            fancyNameTopic = new Topic("Overloading Overriding and Overachieving", parentCourse);
        } catch (Exception e) {
            fail("Unexpected Exception");
            fancyNameTopic = null;
        }
        assertEquals("Overloading Overriding and Overachieving", fancyNameTopic.getTopicName());
        assertEquals(parentCourse, fancyNameTopic.getParentCourse());
        assertEquals("Topic", testTopic.getTopicName());
    }

    @Test
    public void testTopicEmptyNameException() {
        try {
            new Topic("", parentCourse);
            fail("Expected EmptyNameException");
        } catch (EmptyNameException e) {
            e.printStackTrace();
        }
    }
}
