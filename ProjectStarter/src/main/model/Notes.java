package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// contains the list of notes tied to each application 
public class Notes implements Writable {

    private List<String> notes; 
   
    public Notes() { 
        this.notes = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds a note to the list of notes
    public void addNote(String note) {
        notes.add(note);
    }

    //getter
    public List<String> getNotes() {
        return notes;
    }

    //EFFECTS: returns the note at index i, if empty returns null
    public String getNoteI(int i) {
        if (notes.isEmpty()) {
            return null;
        }
        return notes.get(i);  
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("notes", notesToJson());
        return json;
    }

    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String s : notes) {
            jsonArray.put(s);       
        }
        return jsonArray;
    }
}
