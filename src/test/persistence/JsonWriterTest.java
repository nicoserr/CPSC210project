package persistence;

import model.Course;
import model.Note;
import model.Subject;
import model.Topic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Note note = new Note();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyNote() {
        try {
            Note note = new Note();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyNote.json");
            writer.open();
            writer.write(note);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyNote.json");
            note = reader.read();
            assertEquals(0, note.getSubjectsSize());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralNote() {
        try {
            Note note = new Note();
            note.addSubject("s1");
            note.addSubject("s2");
            ArrayList<Subject> subjects = note.getSubjects();
            Subject s1 = subjects.get(0);
            Subject s2 = subjects.get(1);

            s1.addCourse("c1");
            s1.addCourse("c2");
            ArrayList<Course> coursesForS1 = s1.getCourses();
            s2.addCourse("c3");

            Course c1 = coursesForS1.get(0);

            c1.addTopic("t1");
            c1.addTopic("t2");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralNote.json");
            writer.open();
            writer.write(note);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralNote.json");
            note = reader.read();
            subjects = note.getSubjects();
            s1 = subjects.get(0);
            s2 = subjects.get(1);


            coursesForS1 = s1.getCourses();
            ArrayList<Course> coursesForS2 = s2.getCourses();

            c1 = coursesForS1.get(0);
            Course c2 = coursesForS1.get(1);
            Course c3 = coursesForS2.get(0);

            ArrayList<Topic> topicsForC1 = c1.getTopics();
            Topic t1 = topicsForC1.get(0);
            Topic t2 = topicsForC1.get(1);

            assertEquals(2, subjects.size());
            checkSubject("s1", s1);
            checkSubject("s2", s2);

            assertEquals(2, coursesForS1.size());
            checkCourse("c1", s1, c1);
            checkCourse("c2", s1, c2);
            assertEquals(1, coursesForS2.size());
            checkCourse("c3", s2, c3);

            assertEquals(2, topicsForC1.size());
            checkTopic("t1", c1, t1);
            checkTopic("t2", c1, t2);

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }
}
