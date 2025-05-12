package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import model.Application;
import model.ApplicationManager;
import model.Status;

//test class for jsonwriter
//attribution: jsonserializationdemo from edx 
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ApplicationManager am = new ApplicationManager();
            JsonWriter writer = new JsonWriter("./data/illegal\0:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyApplicationManager() {
        try {
            ApplicationManager am = new ApplicationManager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(am);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            am = reader.read();
            assertEquals(0, am.getApplications().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralApplicationManager() {
        Application a1 = new Application("a", "a");
        a1.setStatus(Status.INTERVIEW_PENDING);
        a1.getNotes().addNote("hello");
        a1.setDate(LocalDate.of(2025, 3, 2));
        Application a2 = new Application("b", "b");
        a2.setStatus(Status.REJECTED);
        a2.getNotes().addNote("goodbye");
        a2.getNotes().addNote("world");
        a2.setDate(LocalDate.of(2025, 3, 2));

        try {
            ApplicationManager am = new ApplicationManager();
            am.addApplication(a1);
            am.addApplication(a2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(am);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            am = reader.read();
            assertEquals(2, am.getApplications().size());
            assertEquals("a", am.getApplications().get(0).getCompanyName());
            assertEquals("a", am.getApplications().get(0).getPosition());
            assertEquals(Status.INTERVIEW_PENDING, am.getApplications().get(0).getStatus());
            assertEquals("hello", am.getApplications().get(0).getNotes().getNoteI(0));
            assertEquals("2025-03-02", am.getApplications().get(0).getDate().toString());
            assertEquals("b", am.getApplications().get(1).getCompanyName());
            assertEquals("b", am.getApplications().get(1).getPosition());
            assertEquals(Status.REJECTED, am.getApplications().get(1).getStatus());
            assertEquals("goodbye", am.getApplications().get(1).getNotes().getNoteI(0));
            assertEquals("world", am.getApplications().get(1).getNotes().getNoteI(1));
            assertEquals("2025-03-02", am.getApplications().get(1).getDate().toString());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
