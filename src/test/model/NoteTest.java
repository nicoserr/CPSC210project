package model;

import model.exceptions.EmptyListException;
import model.exceptions.EmptyNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class NoteTest {
    Note testNote;


    @BeforeEach
    public void setup() {
        try {
            testNote = new Note();
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testNote() {
        assertEquals(0, testNote.getSubjectsSize());
    }


    @Test
    public void testAddSubjectToEmptySubjects() {
        try {
            assertTrue(testNote.addSubject("s1"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testNote.getSubjectsSize());
    }

    @Test
    public void testAddSameSubjectManyTimes() {
        try {
            testNote.addSubject("s1");
            assertEquals(1, testNote.getSubjectsSize());

            assertFalse(testNote.addSubject("s1"));
            assertEquals(1, testNote.getSubjectsSize());

            assertFalse(testNote.addSubject("s1"));
            assertEquals(1, testNote.getSubjectsSize());

            assertFalse(testNote.addSubject("s1"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testNote.getSubjectsSize());

    }

    @Test
    public void testAddManySubjects() {
        add3Subjects();
        try {
            assertTrue(testNote.addSubject("s4"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(4, testNote.getSubjectsSize());
    }

    @Test
    public void testAddBlankSubjectEmptyNameException() {
        try {
            assertFalse(testNote.addSubject(""));
            fail("Expected EmptyNameException");
        } catch (EmptyNameException e) {
            assertEquals(0, testNote.getSubjectsSize());
        }
    }

    @Test
    public void testAddAlreadyExistingSubject() {
        add3Subjects();
        try {
            assertFalse(testNote.addSubject("s2"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(3, testNote.getSubjectsSize());
    }

    @Test
    public void testRemoveNonExistingSubject() {
        assertFalse(testNote.removeSubject("s1"));
    }

    @Test
    public void testRemoveOnlyExistingSubject() {
        try {
            testNote.addSubject("s1");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        assertEquals(1, testNote.getSubjectsSize());

        assertTrue(testNote.removeSubject("s1"));
        assertEquals(0, testNote.getSubjectsSize());
    }

    @Test
    public void testRemoveExistingSubject() {
        add3Subjects();
        assertEquals(3, testNote.getSubjectsSize());

        assertTrue(testNote.removeSubject("s3"));
        assertEquals(2, testNote.getSubjectsSize());
    }

    @Test
    public void testRetrieveNonExistingSubject() {
        add3Subjects();
        try {
            assertNull(testNote.retrieveSubject("s4"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testRetrieveExistingSubjectNoException() {
        try {
            testNote.addSubject("s1");
            assertNotNull(testNote.retrieveSubject("s1"));
            assertEquals("s1", testNote.retrieveSubject("s1").getSubjectName());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testRetrieveFromEmptyList() {
        try {
            testNote.retrieveSubject("s1");
            fail("Expected EmptyListException");
        } catch (EmptyListException e) {
            // do nothing
        }
    }

    @Test
    public void testGetOneSubject() {
        ArrayList<Subject> subjects = testNote.getSubjects();
        try {
            testNote.addSubject("s1");
            Subject s1 = testNote.retrieveSubject("s1");
            assertEquals(1, subjects.size());
            assertTrue(subjects.contains(s1));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testGetSubjects(){
        add3Subjects();
        ArrayList<Subject> subjects = testNote.getSubjects();
        try {
            Subject s1 = testNote.retrieveSubject("s1");
            Subject s2 = testNote.retrieveSubject("s2");
            Subject s3 = testNote.retrieveSubject("s3");
            assertEquals(3, subjects.size());
            assertTrue(subjects.contains(s1));
            assertTrue(subjects.contains(s2));
            assertTrue(subjects.contains(s3));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testTreeRetrieveFromEmptyList() {
        try {
            testNote.retrieveTreeSubject("s1");
            fail("Expected EmptyListException");
        } catch (EmptyListException e) {
            // do nothing
        }
    }

    @Test
    public void testTreeRetrieve() {
        try {
            testNote.addSubject("s1");
            Subject s1 = testNote.retrieveTreeSubject("Subject: s1");
            assertEquals("s1", s1.getSubjectName());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testTreeNullRetrieve() {
        try {
            testNote.addSubject("s1");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
        try {
            assertNull(testNote.retrieveTreeSubject("Subject: s2"));
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }

    private void add3Subjects() {
        try {
            testNote.addSubject("s1");
            testNote.addSubject("s2");
            testNote.addSubject("s3");
        } catch (Exception e) {
            fail("Unexpected Exception");
        }
    }
}
