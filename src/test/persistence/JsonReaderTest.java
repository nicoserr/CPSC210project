package persistence;

import model.Note;
import model.Subject;
import model.Course;
import model.Topic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Note note = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (Exception e) {
            fail("IOException expected");
        }
    }

    @Test
    void testReaderEmptyNotes() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyNotes.json");
        try {
            Note note = reader.read();
            assertEquals(0, note.getSubjectsSize());
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralNotes() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralNotes.json");
        Note note = null;
        try {
            note = reader.read();
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
        ArrayList<Subject> subjects = note.getSubjects();

        Subject s1 = subjects.get(0);
        ArrayList<Course> coursesForS1 = s1.getCourses();
        Course c1 = coursesForS1.get(0);
        ArrayList<Topic> topicsForC1 = c1.getTopics();
        Topic t1 = topicsForC1.get(0);

        Course c2 = coursesForS1.get(1);
        ArrayList<Topic> topicsForC2 = c2.getTopics();
        Topic t2 = topicsForC2.get(0);
        Topic t3 = topicsForC2.get(1);

        Course c3 = coursesForS1.get(2);


        Subject s2 = subjects.get(1);
        ArrayList<Course> coursesForS2 = s2.getCourses();
        Course c4 = coursesForS2.get(0);

        Subject s3 = subjects.get(2);

        assertEquals(3, note.getSubjectsSize());

        checkSubject("s1", s1);
        assertEquals(3, s1.getCoursesSize());

        checkCourse("c1", s1, c1);
        assertEquals(1, c1.getTopicsSize());
        checkTopic("t1", c1, t1);

        checkCourse("c2", s1, c2);
        assertEquals(2, c2.getTopicsSize());
        checkTopic("t2", c2, t2);
        checkTopic("t3", c2, t3);

        checkCourse("c3", s1, c3);
        assertEquals(0, c3.getTopicsSize());


        checkSubject("s2", s2);
        assertEquals(1, s2.getCoursesSize());

        checkCourse("c4", s2, c4);
        assertEquals(0, c4.getTopicsSize());


        checkSubject("s3", s3);
        assertEquals(0, s3.getCoursesSize());
    }
}
