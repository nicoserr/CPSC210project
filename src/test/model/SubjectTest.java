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
        assertEquals(0, fancyNameSubject.getCourses().size());

        assertEquals("Subject", testSubject.getSubjectName());
        assertEquals(0, testSubject.getCourses().size());
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
        testSubject.addCourse("c1");
        assertEquals(1,testSubject.getCourses().size());
    }
}
