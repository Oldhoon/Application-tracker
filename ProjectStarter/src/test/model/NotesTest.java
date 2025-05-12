package model;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.json.JSONArray;
import org.json.JSONObject;

public class NotesTest {

    private Notes testNotes;

    @BeforeEach
    void runBefore() {
        testNotes = new Notes();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testNotes.getNotes().size());
    }
    
    @Test
    public void testAddNote() {
        testNotes.addNote("test note");
        assertEquals(1, testNotes.getNotes().size());
        assertEquals("test note", testNotes.getNoteI(0));
    }

    @Test
    public void testGetNotes() {
        testNotes.addNote("a"); 
        assertEquals(1, testNotes.getNotes().size());
        assertEquals("a", testNotes.getNotes().get(0));
    }

    @Test
    public void testGetNoteI() {
        testNotes.addNote("a");
        assertEquals("a", testNotes.getNoteI(0));
    }

    @Test
    public void testGetNoteIEmpty() {
        assertNull(testNotes.getNoteI(0));
    }

    @Test
    public void testToJson() {
        testNotes.addNote("note1");
        testNotes.addNote("note2");
        JSONObject json = testNotes.toJson();
        JSONArray jsonArray = json.getJSONArray("notes");
        assertEquals(2, jsonArray.length());
        assertEquals("note1", jsonArray.getString(0));
        assertEquals("note2", jsonArray.getString(1));
    }
}