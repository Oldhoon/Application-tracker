package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Application;
import model.ApplicationManager;
import model.Status;

//Json reader to load the saved json files 
//Attribution: jsonserializationdemo file from edx
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file 
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads applicationManager file and returns it; 
    //throws IOException if an error occurs reading data from file 
    public ApplicationManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseApplicationManager(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses applicationManager from JSON object and returns it 
    private ApplicationManager parseApplicationManager(JSONObject jsonObject) {
        ApplicationManager am = new ApplicationManager();
        addApplications(am, jsonObject);
        return am; 
    }

    //MODIFIES: am
    //EFFECTS: parses applications from JSON object and returns it
    private void addApplications(ApplicationManager am, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("applications");
        for (Object json : jsonArray) {
            JSONObject nextApplication = (JSONObject) json;
            addApplication(am, nextApplication);
        }
    }

    //MODIFIES: am
    //EFFECTS: parses application from JSON object and adds it to applicationManager
    private void addApplication(ApplicationManager am, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String companyName = jsonObject.getString("companyName");
        String position = jsonObject.getString("position");
        Status status = Status.valueOf(jsonObject.getString("status"));
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));

        Application application = new Application(companyName, position);
        application.setId(id);
        application.setStatus(status);
        application.setDate(date);

        JSONArray notesArray = jsonObject.getJSONArray("notes");
        for (Object n : notesArray) {
            String note = (String) n; 
            application.getNotes().addNote(note);
        }

        am.addApplication(application);
    }
}


