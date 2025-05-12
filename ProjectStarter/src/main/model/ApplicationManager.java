package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Represents manager class; manages the list of applications; CRUD and sort 

public class ApplicationManager implements Writable {
    private List<Application> applications;
    private Comparator<Application> sortByX;
    private static int id = 1;

    // EFFECTS: construct new application manager with no applications added
    public ApplicationManager() {
        this.applications = new ArrayList<>();

    }

    // MODIFIES: this
    // EFFECTS: add application to the list of applications and sets the id of the
    // application
    // progressed by 1 from the last application added
    public void addApplication(Application application) {
        application.setId(id);
        this.applications.add(application);
        id++;
        EventLog.getInstance().logEvent(
                new Event("Application added: " + application.getCompanyName() + " " + application.getPosition()));
    }

    // REQUIRES: application must be in the list of applications
    // MODIFIES: this
    // EFFECTS: remove application from the list of applications
    public void removeApplication(Application application) {
        this.applications.remove(application);
        EventLog.getInstance().logEvent(
                new Event("Application removed: " + application.getCompanyName() + " " + application.getPosition()));
    }

    // MODIFIES: this
    // EFFECTS: return the list of applications. if the list is empty, print "No
    // applications found"
    public List<Application> getApplications() {
        return applications;
    }

    // REQUIRES: application must be in the list of applications
    // MODIFIES: this
    // EFFECTS: update the status of the application to the given status
    public void updateStatus(Application application, Status status) {
        for (Application app : applications) {
            if (app.equals(application)) {
                app.setStatus(status);
                EventLog.getInstance().logEvent(new Event("Application status updated: " + application.getCompanyName()
                        + " " + application.getPosition() + " " + status));
            }
        }
    }

    // REQUIRES: application must be in the list of applications
    // MODIFIES: this
    // EFFECTS: update the notes of the application with the given note
    public void updateNotes(Application application, String note) {
        for (Application app : applications) {
            if (app.equals(application)) {
                app.getNotes().addNote(note);
                EventLog.getInstance().logEvent(new Event("Application notes updated: " + application.getCompanyName()
                        + " " + application.getPosition() + " " + note));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sort the applications by id in ascending order
    // Attribution : w3 schools java advanced sorting
    public void sortApplicationsById() {
        sortByX = new SortById();
        Collections.sort(applications, sortByX);
        EventLog.getInstance().logEvent(new Event("Application sorted by id"));
    }

    // MODIFIES: this
    // EFFECTS: sort the applications by company name in alphabetical order, if the
    // company names are the same,
    // sort by order in list
    public void sortApplicationsByCompanyName() {
        sortByX = new SortByCompanyName();
        Collections.sort(applications, sortByX);
        EventLog.getInstance().logEvent(new Event("Application sorted by company name"));
    }

    // MODIFIES: this
    // EFFECTS: sort the applications by position in alphabetical order, if position
    // names are the same
    // sort by order in list
    public void sortApplicationsByPosition() {
        sortByX = new SortByPosition();
        Collections.sort(applications, sortByX);
        EventLog.getInstance().logEvent(new Event("Application sorted by position"));
    }

    // MODIFIES: this
    // EFFECTS: sort the applications by status in alphabetical order
    public void sortApplicationsByStatus() {
        sortByX = new SortByStatus();
        Collections.sort(applications, sortByX);
        EventLog.getInstance().logEvent(new Event("Application sorted by status"));
    }

    // MODIFIES: this
    // EFFECTS: if the application has been in review for more than 2 weeks,
    // change the status to "Rejected"
    public void rejectOldApplication() {
        for (Application app : applications) {
            if (app.getStatus().equals(Status.IN_REVIEW)
                    && LocalDate.now().isAfter(app.getDate().plusDays(14))) {
                app.setStatus(Status.REJECTED);
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("applications", applicationsToJson());
        return json;
    }

    // EFFECTS: returns applications in applicationManager as a JSON array
    private JSONArray applicationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Application a : applications) {
            jsonArray.put(a.toJson());
        }
        return jsonArray;
    }
}