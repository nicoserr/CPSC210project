package model;

import model.exceptions.EmptyListException;
import model.exceptions.InvalidAdditionException;
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
            fancyNameCourse = new Course("Analytics And Information Engineering", parentSubject);
        } catch (Exception e) {
            fail("Unexpected Exception");
            fancyNameCourse = null;
        }
        assertEquals("Analytics And Information Engineering", fancyNameCourse.getCourseName());
        assertEquals("Course: Analytics And Information Engineering", fancyNameCourse.getCourseTreeName());
        assertEquals(0, testCourse.getTopicsSize());
        assertEquals(parentSubject, testCourse.getParentSubject());

        assertEquals("Course", testCourse.getCourseName());
    }

    @Test
    public void testCourseEmptyNameException() {
        try {
            new Course("", parentSubject);
            fail("Expected InvalidAdditionException");
        } catch (InvalidAdditionException name) {
            // do nothing
        }
    }

    @Test
    public void testAddTopicToEmptyTopics() {
        try {
            assertTrue(testCourse.addTopic("yellow"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testCourse.getTopicsSize());
    }

    @Test
    public void testAddSameTopicManyTimes() {
        try {
            testCourse.addTopic("yellow");
            assertEquals(1, testCourse.getTopicsSize());

            assertFalse(testCourse.addTopic("yellow"));
            assertEquals(1, testCourse.getTopicsSize());

            assertFalse(testCourse.addTopic("yellow"));
            assertEquals(1, testCourse.getTopicsSize());

            assertFalse(testCourse.addTopic("yellow"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testCourse.getTopicsSize());

    }

    @Test
    public void testAddManyTopics() {
        add3Topics();
        try {
            testCourse.addTopic("purple");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(4, testCourse.getTopicsSize());
    }

    @Test
    public void testAddBlankTopicEmptyNameException() {
        try {
            assertFalse(testCourse.addTopic(""));
            fail("Expected InvalidAdditionException");
        } catch (InvalidAdditionException e) {
            assertEquals(0, testCourse.getTopicsSize());
        }
    }

    @Test
    public void testAddAlreadyExistingTopic() {
        add3Topics();
        try {
            assertFalse(testCourse.addTopic("red"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(3, testCourse.getTopicsSize());
    }

    @Test
    public void testRemoveNonExistingTopic() {
        assertFalse(testCourse.removeTopic("yellow"));
    }

    @Test
    public void testRemoveOnlyExistingTopic() {
        try {
            testCourse.addTopic("yellow");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testCourse.getTopicsSize());

        assertTrue(testCourse.removeTopic("yellow"));
        assertEquals(0, testCourse.getTopicsSize());
    }

    @Test
    public void testRemoveExistingTopic() {
        add3Topics();
        assertEquals(3, testCourse.getTopicsSize());

        assertTrue(testCourse.removeTopic("red"));
        assertEquals(2, testCourse.getTopicsSize());
    }

    @Test
    public void testRetrieveNonExistingTopic() {
        add3Topics();
        try {
            assertNull(testCourse.retrieveTopic("purple"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testRetrieveExistingTopicNoException() {
        try {
            testCourse.addTopic("yellow");
            assertNotNull(testCourse.retrieveTopic("yellow"));
            assertEquals("yellow", testCourse.retrieveTopic("yellow").getTopicName());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testRetrieveFromEmptyList() {
        try {
            testCourse.retrieveTopic("c1");
            fail("Expected EmptyListException");
        } catch (EmptyListException e) {
            // do nothing
        }
    }

    @Test
    public void testGetOneCourse() {
        ArrayList<Topic> topics = testCourse.getTopics();
        try {
            testCourse.addTopic("yellow");
            Topic yellow = testCourse.retrieveTopic("yellow");
            assertEquals(1, topics.size());
            assertTrue(topics.contains(yellow));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testGetTopics(){
        add3Topics();
        ArrayList<Topic> topics = testCourse.getTopics();
        try {
            Topic yellow = testCourse.retrieveTopic("yellow");
            Topic blue = testCourse.retrieveTopic("blue");
            Topic red = testCourse.retrieveTopic("red");
            assertEquals(3, topics.size());
            assertTrue(topics.contains(yellow));
            assertTrue(topics.contains(red));
            assertTrue(topics.contains(blue));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    private void add3Topics() {
        try {
            testCourse.addTopic("yellow");
            testCourse.addTopic("red");
            testCourse.addTopic("blue");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}