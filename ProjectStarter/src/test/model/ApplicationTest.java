package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ApplicationTest {

    private Application testApp1; 
    private Application testApp2; 

    @BeforeEach
    void runBefore() { 
        testApp1 = new Application("company1", "developer");
        testApp2 = new Application("company2", "data scientist"); 

    }

    @Test 
    public void testConstructor() {
        assertEquals(0, testApp1.getId());
        assertEquals(Status.IN_REVIEW, testApp2.getStatus());
        assertEquals("company1", testApp1.getCompanyName());
        assertEquals("developer", testApp1.getPosition());


    }


    @Test
    public void testGetCompanyName() { 
        assertEquals("company1", testApp1.getCompanyName());
        assertEquals("company2", testApp2.getCompanyName());        
    }

    @Test
    public void testGetPosition() {
        assertEquals("developer", testApp1.getPosition());
        assertEquals("data scientist", testApp2.getPosition());
    }

    @Test
    public void testGetStatus() {
        assertEquals(Status.IN_REVIEW, testApp1.getStatus());
    }

    @Test
    public void testGetNotes() {
        assertEquals(0, testApp1.getNotes().getNotes().size());
    }

    @Test 
    public void testGetDate() {
        assertEquals(testApp1.getDate(), testApp1.getDate());
    }


    @Test
    public void testSetId() {
        testApp1.setId(1);
        assertEquals(1, testApp1.getId());
    }

    @Test
    public void testSetCompanyName() {
        testApp1.setCompanyName("UBC");
        assertEquals("UBC", testApp1.getCompanyName());
    }

    @Test
    public void testSetPosition() {
        testApp2.setPosition("Janitor");
        assertEquals("Janitor", testApp2.getPosition());
    }

    @Test
    public void testSetStatus() {
        testApp1.setStatus(Status.REJECTED);
        assertEquals(Status.REJECTED, testApp1.getStatus());
        testApp2.setStatus(Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, testApp2.getStatus());
    }

    @Test
    public void testSetDate() {
        LocalDate date = LocalDate.of(2025, 2, 16);
        testApp1.setDate(date);
        assertEquals(date, testApp1.getDate());
    }

}
