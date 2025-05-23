package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.ApplicationManager;

// attribution: jsonserializationdemo from edx
// Represents a writer that writes JSON representation of applicationManager to file 
public class JsonWriter {

    private static final int TAB = 4; 
    private PrintWriter writer; 
    private String destination; 

    //EFFECTS: constructs writer to write to destination file 
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened for writing 
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: WRITES json representation of applicationManager to file
    public void write(ApplicationManager am) {
        JSONObject json = am.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file 
    private void saveToFile(String json) {
        writer.print(json);
    }

}
