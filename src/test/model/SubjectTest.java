package model;

import model.exceptions.EmptyNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectTest {
    Subject testSubject;

    @BeforeEach
    public void setup() {
        try {
            testSubject = new Subject("Subject");
        } catch (Exception e) {
            fail("Unexpected Exception");
            e.printStackTrace();
        }
    }

    @Test
    public void testSubjectNoException() {
        Subject fancyNameSubject;
        try {
            fancyNameSubject = new Subject("Computer Engineering and Biochemicals");
        } catch (Exception e) {
            fail("Unexpected Exception");
            fancyNameSubject = null;
            e.printStackTrace();
        }
        assertEquals("Computer Engineering and Biochemicals", fancyNameSubject.getSubjectName());
        assertEquals(0, fancyNameSubject.getCoursesSize());

        assertEquals("Subject", testSubject.getSubjectName());
        assertEquals(0, testSubject.getCoursesSize());
    }

    @Test
    public void testSubjectEmptyNameException() {
        try {
            Subject emptySubject = new Subject("");
            fail("Expected EmptyNameException");
        } catch (EmptyNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddCourseToEmptyCourse() {
        assertTrue(testSubject.addCourse("c1"));
        assertEquals(1,testSubject.getCoursesSize());
    }

    @Test
    public void testAddSameCourseManyTimes() {
        testSubject.addCourse("c1");
        assertEquals(1, testSubject.getCoursesSize());

        assertFalse(testSubject.addCourse("c1"));
        assertEquals(1, testSubject.getCoursesSize());

        assertFalse(testSubject.addCourse("c1"));
        assertEquals(1, testSubject.getCoursesSize());

        assertFalse(testSubject.addCourse("c1"));
        assertEquals(1, testSubject.getCoursesSize());
    }

    @Test
    public void testAddManyCourses() {
        addCourses123();
        assertEquals(3, testSubject.getCoursesSize());
    }

    @Test
    public void testAddBlankCourse() {
        assertFalse(testSubject.addCourse(""));
        assertEquals(0, testSubject.getCoursesSize());
    }

    @Test
    public void testAddAlreadyExistingCourse() {
        addCourses123();

        assertFalse(testSubject.addCourse("c2"));
        assertEquals(3, testSubject.getCoursesSize());
    }

    @Test
    public void testRemoveNonExistingCourse() {
        assertFalse(testSubject.removeCourse("c1"));
    }

    @Test
    public void testRemoveOnlyExistingCourse() {
        testSubject.addCourse("c1");
        assertEquals(1, testSubject.getCoursesSize());

        assertTrue(testSubject.removeCourse("c1"));
        assertEquals(0,testSubject.getCoursesSize());
    }

    @Test
    public void testRemoveExistingCourse() {
        addCourses123();
        assertEquals(3,testSubject.getCoursesSize());

        assertTrue(testSubject.removeCourse("c2"));
        assertEquals(2,testSubject.getCoursesSize());
    }

    @Test
    public void testRetrieveNonExistingTopic() {
        assertNull(testSubject.retrieveCourse("c1"));
    }

    @Test
    public void testRetrieveExistingCourse() {
        testSubject.addCourse("c1");
        assertNotNull(testSubject.retrieveCourse("c1"));
        assertEquals("c1", testSubject.retrieveCourse("c1").getCourseName());
    }

    @Test
    public void testGetEmptyListOfCourseNames() {
        assertEquals("This subject has no courses!", testSubject.getListOfCourseNames());
    }

    @Test
    public void testGetListOfCourseNames() {
        addCourses123();
        assertEquals("'c1', 'c2', 'c3'", testSubject.getListOfCourseNames());
    }

    private void addCourses123() {
        testSubject.addCourse("c1");
        testSubject.addCourse("c2");
        testSubject.addCourse("c3");
    }
}
