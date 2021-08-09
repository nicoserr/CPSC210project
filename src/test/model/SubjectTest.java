package model;

import model.exceptions.EmptyListException;
import model.exceptions.InvalidAdditionException;
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
            // do nothing
        }
    }

    @Test
    public void testSubjectNoException() {
        Subject fancyNameSubject;
        try {
            fancyNameSubject = new Subject("Computer Engineering And Biochemicals");
        } catch (Exception e) {
            fail("Unexpected Exception");
            fancyNameSubject = null;

        }
        assertEquals("Computer Engineering And Biochemicals", fancyNameSubject.getSubjectName());
        assertEquals("Subject: Computer Engineering And Biochemicals", fancyNameSubject.getSubjectTreeName());
        assertEquals(0, fancyNameSubject.getCoursesSize());

        assertEquals("Subject", testSubject.getSubjectName());
        assertEquals(0, testSubject.getCoursesSize());
    }

    @Test
    public void testSubjectInvalidAdditionException() {
        try {
            new Subject("");
            fail("Expected InvalidAdditionException");
        } catch (InvalidAdditionException e) {
            // do nothing
        }
    }

    @Test
    public void testAddCourseToEmptyCourse() {
        try {
            assertTrue(testSubject.addCourse("c1"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1,testSubject.getCoursesSize());
    }

    @Test
    public void testAddSameCourseManyTimes() {
        try {
            testSubject.addCourse("c1");
            assertEquals(1, testSubject.getCoursesSize());

            assertFalse(testSubject.addCourse("c1"));
            assertEquals(1, testSubject.getCoursesSize());

            assertFalse(testSubject.addCourse("c1"));
            assertEquals(1, testSubject.getCoursesSize());

            assertFalse(testSubject.addCourse("c1"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testSubject.getCoursesSize());
    }

    @Test
    public void testAddManyCourses() {
        addCourses123();
        assertEquals(3, testSubject.getCoursesSize());
    }

    @Test
    public void testAddBlankCourse() {
        try {
            assertFalse(testSubject.addCourse(""));
            fail("Expected InvalidAdditionException");
        } catch (InvalidAdditionException e) {
            // do nothing
        }
        assertEquals(0, testSubject.getCoursesSize());
    }

    @Test
    public void testAddAlreadyExistingCourse() {
        addCourses123();
        try {
            assertFalse(testSubject.addCourse("c2"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(3, testSubject.getCoursesSize());
    }

    @Test
    public void testRemoveNonExistingCourse() {
        assertFalse(testSubject.removeCourse("c1"));
    }

    @Test
    public void testRemoveOnlyExistingCourse() {
        addCourseC1();
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
    public void testRetrieveNonExistingCourse() {
        addCourses123();
        try {
            assertNull(testSubject.retrieveCourse("c4"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testRetrieveExistingCourse() {
        addCourseC1();
        try {
            assertNotNull(testSubject.retrieveCourse("c1"));
            assertEquals("c1", testSubject.retrieveCourse("c1").getCourseName());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testRetrieveFromEmptyList() {
        try {
            testSubject.retrieveCourse("c1");
            fail("Expected EmptyListException");
        } catch (EmptyListException e) {
            // do nothing
        }
    }

    @Test
    public void testTreeRetrieveFromEmptyList() {
        try {
            testSubject.retrieveTreeCourse("c1");
            fail("Expected EmptyListException");
        } catch (EmptyListException e) {
            // do nothing
        }
    }

    @Test
    public void testTreeRetrieve() {
        try {
            testSubject.addCourse("c1");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        try {
            assertEquals("c1", testSubject.retrieveTreeCourse("Course: c1").getCourseName());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testTreeNullRetrieve() {
        try {
            testSubject.addCourse("c1");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        try {
            assertNull(testSubject.retrieveTreeCourse("Course: c2"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testGetOneCourse() {
        try {
            testSubject.addCourse("c1");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        ArrayList<Course> courses = testSubject.getCourses();
        try {
            Course c1 = testSubject.retrieveCourse("c1");
            assertEquals(1, courses.size());
            assertTrue(courses.contains(c1));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testGetCourses(){
        addCourses123();
        ArrayList<Course> courses = testSubject.getCourses();
        try {
            Course c1 = testSubject.retrieveCourse("c1");
            Course c2 = testSubject.retrieveCourse("c2");
            Course c3 = testSubject.retrieveCourse("c3");
            assertEquals(3, courses.size());
            assertTrue(courses.contains(c1));
            assertTrue(courses.contains(c2));
            assertTrue(courses.contains(c3));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    private void addCourseC1() {
        try {
            testSubject.addCourse("c1");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    private void addCourses123() {
        try {
            testSubject.addCourse("c1");
            testSubject.addCourse("c2");
            testSubject.addCourse("c3");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}
