package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationManagerTest {
    private ApplicationManager testManager; 
    private Application app1; 
    private Application app2; 
    private Application app3;

    @BeforeEach
    void runBefore() {
        testManager = new ApplicationManager();
    }

    @Test
    //test if manager is empty when first constructed 
    public void testConstructor() { 
        assertTrue(testManager.getApplications().isEmpty());
    }

    @Test
    public void testAddApplication() {
        app1 = new Application("c1", "p1");
        app1 = new Application("c2", "p2");
        testManager.addApplication(app1);
        assertEquals(1, testManager.getApplications().size());
        assertEquals(app1, testManager.getApplications().get(0));
    }

    @Test
    public void testRemoveApplication() {
        app1 = new Application("c1", "p1");
        app2 = new Application("c2", "p2");
        testManager.addApplication(app1);
        testManager.addApplication(app2);
        testManager.removeApplication(app1);
        assertEquals(1, testManager.getApplications().size());
        assertTrue(testManager.getApplications().contains(app2));
    }

    @Test 
    public void testUpdateStatus() {
        app1 = new Application("c1", "p1");
        app2 = new Application("c2", "p2");
        testManager.addApplication(app1);
        testManager.addApplication(app2);
        testManager.updateStatus(app1, Status.IN_REVIEW);
        assertEquals(Status.IN_REVIEW, app1.getStatus());
        testManager.updateStatus(app2, Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, app2.getStatus());
    }

    @Test
    public void testUpdateNotes() {
        app1 = new Application("c1", "p1");
        app2 = new Application("c2", "p2");
        testManager.addApplication(app1);
        testManager.addApplication(app2);
        testManager.updateNotes(app1, "note1");
        assertEquals("note1", app1.getNotes().getNoteI(0));
        testManager.updateNotes(app2, "note2");
        assertEquals("note2", app2.getNotes().getNoteI(0));
    }

    @Test
    public void testSortApplicationsById() { 
        //id is set when application is added to this List
        app1 = new Application("c1", "p1");
        app2 = new Application("c1", "p1");
        app3 = new Application("c1", "p1");
        testManager.addApplication(app2);
        testManager.addApplication(app1);
        testManager.addApplication(app3);
        testManager.sortApplicationsById();
        assertEquals(app3, testManager.getApplications().get(2));
        assertEquals(app2, testManager.getApplications().get(0));
        assertEquals(app1, testManager.getApplications().get(1));
    }

    @Test 
    public void testSortApplicaitonByIdSame() {
        app1 = new Application("c", "p");
        app2 = new Application("c", "p");
        app3 = new Application("c", "p");
        testManager.addApplication(app1);
        testManager.addApplication(app2);
        testManager.addApplication(app3);
        testManager.getApplications().get(0).setId(1);
        testManager.getApplications().get(1).setId(2);
        testManager.getApplications().get(2).setId(1);
        testManager.sortApplicationsById();
        assertEquals(app1, testManager.getApplications().get(0));
        assertEquals(app2, testManager.getApplications().get(2));
        assertEquals(app3, testManager.getApplications().get(1));


        
    }

    @Test
    public void testSortApplicationsByCompanyName() {
        app1 = new Application("a", "p1");
        app2 = new Application("b", "p2");
        testManager.addApplication(app2);
        testManager.addApplication(app1);
        testManager.sortApplicationsByCompanyName();
        assertEquals(app1, testManager.getApplications().get(0));
        assertEquals(app2, testManager.getApplications().get(1));
    }

    @Test
    public void testSortApplicationsByCompanyNameSame() {
        app1 = new Application("a", "p1");
        app2 = new Application("a", "p2");
        app1.setId(1);
        app2.setId(2);
        testManager.addApplication(app2);
        testManager.addApplication(app1);
        testManager.sortApplicationsByCompanyName();
        assertEquals(app1, testManager.getApplications().get(1));
        assertEquals(app2, testManager.getApplications().get(0));
    }

    @Test
    public void testSortApplicationsByPosition() {
        app1 = new Application("c1", "b");
        app2 = new Application("c1", "a");
        app3 = new Application("c1", "c");
        testManager.addApplication(app1);
        testManager.addApplication(app2);
        testManager.addApplication(app3);
        testManager.sortApplicationsByPosition();
        assertEquals(app2, testManager.getApplications().get(0));
        assertEquals(app1, testManager.getApplications().get(1));
        assertEquals(app3, testManager.getApplications().get(2));
    }

    @Test
    public void testRejectOldApplication() { 
        app1 = new Application("c1", "p1");
        app2 = new Application("c2", "p2");
        app3 = new Application("c3", "p3");
        testManager.addApplication(app1);
        testManager.addApplication(app2);
        testManager.addApplication(app3);
        testManager.getApplications().get(2).setStatus(Status.ACCEPTED);
        testManager.getApplications().get(0).setDate(LocalDate.of(2025, 2, 1));
        testManager.getApplications().get(1).setDate(LocalDate.now());
        testManager.rejectOldApplication();
        assertEquals(Status.REJECTED, testManager.getApplications().get(0).getStatus());
        assertEquals(Status.IN_REVIEW, testManager.getApplications().get(1).getStatus());
        assertEquals(Status.ACCEPTED, testManager.getApplications().get(2).getStatus());
    }
}