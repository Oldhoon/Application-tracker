package model;

import java.time.LocalDate;

import org.json.JSONObject;

import persistence.Writable;

// this class represents a job application with details such as company name, position, status, notes, and date
public class Application implements Writable {
    private int id; 
    private String companyName; 
    private String position; 
    private Status status;
    private Notes notes; 
    private LocalDate date;
    
    //Attribution : for LocalDate w3 schools Java Date and Time 
    //EFFECTS: constructs a new job application with id set to 0 and IN_REVIEW status and empty notes
    //and todays date 
    public Application(String companyName, String position) {
        this.id = 0; 
        this.companyName = companyName;
        this.position = position; 
        this.status = Status.IN_REVIEW;
        this.notes = new Notes();
        this.date = LocalDate.now(); 
    }

    //getters
    public int getId() {
        return id; 
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }
    
    public Notes getNotes() {
        return notes;
    }

    public LocalDate getDate() {
        return date; 
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date; 
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("companyName", companyName);
        json.put("position", position);
        json.put("status", status.toString());
        json.put("date", date.toString());
        json.put("notes", notes.getNotes());
        return json;
    }
}


