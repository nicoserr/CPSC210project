package model;

import model.exceptions.EmptyNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course testCourse;
    Subject parentSubject;


    @BeforeEach
    public void setup() {
        try {
            parentSubject = new Subject("Parent Subject");
            testCourse = new Course("Course", parentSubject);
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testCourseNoException() {
        Course fancyNameCourse;
        try {
            fancyNameCourse = new Course("Analytics and Information Engineering", parentSubject);
        } catch (Exception e) {
            fail("Unexpected Exception");
            fancyNameCourse = null;
        }
        assertEquals("Analytics and Information Engineering", fancyNameCourse.getCourseName());
        assertEquals(0, testCourse.getTopicsSize());
        assertEquals(parentSubject, testCourse.getParentSubject());

        assertEquals("Course", testCourse.getCourseName());
    }

    @Test
    public void testCourseEmptyNameException() {
        try {
            Course emptyCourse = new Course("", parentSubject);
            fail("Expected EmptyNameException");
        } catch (EmptyNameException name) {
            name.printStackTrace();
        }
    }

    @Test
    public void testAddTopicToEmptyTopics() {
        assertTrue(testCourse.addTopic("yellow"));
        assertEquals(1, testCourse.getTopicsSize());
    }

    @Test
    public void testAddSameTopicManyTimes() {
        testCourse.addTopic("yellow");
        assertEquals(1, testCourse.getTopicsSize());

        assertFalse(testCourse.addTopic("yellow"));
        assertEquals(1, testCourse.getTopicsSize());

        assertFalse(testCourse.addTopic("yellow"));
        assertEquals(1, testCourse.getTopicsSize());

        assertFalse(testCourse.addTopic("yellow"));
        assertEquals(1, testCourse.getTopicsSize());

    }

    @Test
    public void testAddManyTopics() {
        testCourse.addTopic("yellow");
        testCourse.addTopic("blue");
        testCourse.addTopic("red");
        testCourse.addTopic("purple");
        assertEquals(4, testCourse.getTopicsSize());
    }

    @Test
    public void testAddBlankTopic() {
        assertFalse(testCourse.addTopic(""));
        assertEquals(0, testCourse.getTopicsSize());
    }

    @Test
    public void testAddAlreadyExistingTopic() {
        testCourse.addTopic("yellow");
        testCourse.addTopic("red");
        testCourse.addTopic("blue");

        assertFalse(testCourse.addTopic("red"));
        assertEquals(3, testCourse.getTopicsSize());
    }

    @Test
    public void testRemoveNonExistingTopic() {
        assertFalse(testCourse.removeTopic("yellow"));
    }

    @Test
    public void testRemoveOnlyExistingTopic() {
        testCourse.addTopic("yellow");
        assertEquals(1, testCourse.getTopicsSize());

        assertTrue(testCourse.removeTopic("yellow"));
        assertEquals(0, testCourse.getTopicsSize());
    }

    @Test
    public void testRemoveExistingTopic() {
        testCourse.addTopic("yellow");
        testCourse.addTopic("red");
        testCourse.addTopic("blue");
        assertEquals(3, testCourse.getTopicsSize());

        assertTrue(testCourse.removeTopic("red"));
        assertEquals(2, testCourse.getTopicsSize());
    }

    @Test
    public void testRetrieveNonExistingTopic() {
        assertNull(testCourse.retrieveTopic("yellow"));
    }

    @Test
    public void testRetrieveExistingTopic() {
        testCourse.addTopic("yellow");
        assertNotNull(testCourse.retrieveTopic("yellow"));
        assertEquals("yellow", testCourse.retrieveTopic("yellow").getTopicName());
    }

    @Test
    public void testGetEmptyListOfTopicNames() {
        assertEquals("This course has no topics!", testCourse.getListOfTopicNames());
    }

    @Test
    public void testGetListOfTopicNames() {
        testCourse.addTopic("yellow");
        testCourse.addTopic("red");
        testCourse.addTopic("blue");

        assertEquals("'yellow', 'red', 'blue'", testCourse.getListOfTopicNames());
    }
}