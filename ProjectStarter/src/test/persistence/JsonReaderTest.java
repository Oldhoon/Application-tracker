package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import model.ApplicationManager;
import model.Status;

//attribution: jsonserializationdemo from edx 
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() { 
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ApplicationManager am = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyApplicationManager() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            ApplicationManager am = reader.read();
            assertEquals(0, am.getApplications().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralApplicationManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            ApplicationManager am = reader.read();
            assertEquals(2, am.getApplications().size());
            assertEquals("apple", am.getApplications().get(0).getCompanyName());
            assertEquals("janitor", am.getApplications().get(0).getPosition());
            assertEquals(Status.IN_REVIEW, am.getApplications().get(0).getStatus());
            assertEquals("hello", am.getApplications().get(0).getNotes().getNoteI(0));
            assertEquals("world", am.getApplications().get(0).getNotes().getNoteI(1));
            assertEquals("2025-03-02", am.getApplications().get(0).getDate().toString());
            assertEquals("microsoft", am.getApplications().get(1).getCompanyName());
            assertEquals("janitor", am.getApplications().get(1).getPosition());
            assertEquals(Status.INTERVIEW_PENDING, am.getApplications().get(1).getStatus());
            assertEquals("goodbye", am.getApplications().get(1).getNotes().getNoteI(0));
            assertEquals("2025-03-01", am.getApplications().get(1).getDate().toString());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
