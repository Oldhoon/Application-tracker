// Attribution: Code Structure based on Teller App and JSONSERIALIZATIONDEMO
// user interface class, 

package ui;

import model.Status;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Application;
import model.ApplicationManager;

//Job search tracker app 
public class ApplicationTracker {
    private ApplicationManager manager;
    private Scanner input;
    private static final String JSON_STORE = "./data/applicationmanager.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the tracker app
    public ApplicationTracker() {
        manager = new ApplicationManager();
        input = new Scanner(System.in); // initialize scanner
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTracker();
    }
    
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        int command = 0;

        while (keepGoing) {
            displayMenu();
            command = getValidIntInput();
            if (command == 8) {
                askSave();
                keepGoing = false;
            } else {
                processCommand(command);
            }
            
        }
        System.out.println("\n Goodbye");
        // reject old applications 
        manager.rejectOldApplication();
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(int command) {
        switch (command) {
            case 1:
                doViewApplications();
                break;
            case 2:
                doAddApplication();
                break;
            case 3:
                doRemoveApplication();
                break;
            case 4:
                doUpdateStatus();
                break;
            case 5:
                doAddNotes();
                break;
            case 6:
                doSortApplications();
                break;
            case 7:
                loadSave();
                break;
            default:
                System.out.println("Selection invalid");
                break;
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n Co-op/Internship Search Status Tracker");
        System.out.println("\n Select from:");
        System.out.println("1. View Applications");
        System.out.println("2. Add New Application");
        System.out.println("3. Remove Application");
        System.out.println("4. Update Status");
        System.out.println("5. Add Notes to Application");
        System.out.println("6. Sort Applications");
        System.out.println("7. Load save file");
        System.out.println("8. Quit");
    }

    // EFFECTS: checks if user input is valid type and returns if true
    private int getValidIntInput() {
        while (!input.hasNextInt()) {
            System.out.println("Invalid input");
            input.next();
        }
        return input.nextInt();

    }

    // EFFECTS: displays all applications in the manager
    private void doViewApplications() {
        if (manager.getApplications().isEmpty()) {
            System.out.println("No applications found");
        } else {
            System.out.println("Applications: \n");
            int count = 1;
            for (Application app : manager.getApplications()) {
                System.out.println(count + ". " + app.getCompanyName() + " - " + app.getPosition() + " - "
                        + app.getStatus() + " - " + app.getNotes().getNotes());
                count++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a job application to the manager
    private void doAddApplication() {
        System.out.println("Enter company name: ");

        if (input.hasNextLine()) { // ensure input is available
            input.nextLine();
        }
        String company = input.nextLine();
        System.out.println("Enter position: ");
        String position = input.nextLine();
        manager.addApplication(new Application(company, position));
        System.out.println("Application for + " + company + " - " + position + " has been added");
    }

    // MODIFIES: this
    // EFFECTS: removes a job application from the manager
    private void doRemoveApplication() {
        if (manager.getApplications().isEmpty()) {
            System.out.println("No applications to remove");
            return;
        }
        doViewApplications();
        System.out.println("Select application to remove: ");
        
        int index = getValidIntInput();
        if (index > manager.getApplications().size()) {
            System.out.println("Invalid selection");
            return;
        }
        manager.removeApplication(manager.getApplications().get(index - 1));
        System.out.println("Application removed");
        
    }

    // MODIFIES: this
    // EFFECTS: updates the status of a job application
    private void doUpdateStatus() {
        if (manager.getApplications().isEmpty()) {
            System.out.println("No applications to update");
            return;
        }
        doViewApplications();
        System.out.println("Select application to update: ");
        int index = getValidIntInput();
        if (index > manager.getApplications().size()) {
            System.out.println("Invalid selection");
            return;
        }
        Status status = selectStatus();
        manager.updateStatus(manager.getApplications().get(index - 1), status);
        System.out.println("Status has been updated to: " + status);
        if (status.equals(Status.ACCEPTED)) {
            System.out.println("Congratulations on your new job at " 
                        + manager.getApplications().get(index - 1).getCompanyName() + "!");
        }
    }

    //helper for doUpdateStatus
    private Status selectStatus() {
        System.out.println("Select one of the following statuses: ");
        doViewStatuses();
        Status status = null;
        while (status == null) {
            int statusCount = getValidIntInput();
            status = selectSwitchStatus(statusCount);
        }
        return status; 
    }

    // helper for select status 
    private Status selectSwitchStatus(int statusCount) {
        Status status = null; 
        switch (statusCount) {
            case 1: 
                status = Status.IN_REVIEW;
                break;
            case 2:
                status = Status.INTERVIEW_PENDING;
                break;
            case 3:
                status = Status.REJECTED;
                break;
            case 4:
                status = Status.ACCEPTED;
                break;
            default: {
                System.out.println("invalid selection");
            }
        }
        return status; 
    }

    // MODIFIES: this
    // EFFECTS: adds notes to a job application
    private void doAddNotes() {
        if (manager.getApplications().isEmpty()) {
            System.out.println("No appllications to add notes to");
            return;
        }
        doViewApplications();
        System.out.println("Select application to add notes to: ");
        int index = getValidIntInput();
        if (index > manager.getApplications().size()) {
            System.out.println("Invalid selection");
            input.next();
        }
        System.out.println("Enter notes: ");
        if (input.hasNextLine()) {
            input.nextLine();
        }
        String note = input.nextLine();
        manager.updateNotes(manager.getApplications().get(index - 1), note);
    }

    // EFFECTS: displays the statuses of the applications
    private void doViewStatuses() {
        System.out.println("1. IN_REVIEW");
        System.out.println("2. INTERVIEW_PENDING");
        System.out.println("3. REJECTED");
        System.out.println("4. ACCEPTED");
    }

    // EFFECTS: displays the available sorting options 
    private void printSortOptions() {
        System.out.println("sortby: ");
        System.out.println("1. ID");
        System.out.println("2. Company Name");
        System.out.println("3. Position");
    }

    // MODIFIES: this
    // EFFECTS: sorts the application in the manager 
    private void sortApplicationSwitch() {
        boolean sorted = true; 
        switch (getValidIntInput()) {
            case 1:
                manager.sortApplicationsById();
                System.out.println("Applications have been sorted by ID");
                break;
            case 2:
                manager.sortApplicationsByCompanyName();
                System.out.println("Applications have been sorted by Company Name");
                break;
            case 3:
                manager.sortApplicationsByPosition();
                System.out.println("Applications have been sorted by Position");
                break;
            default: {
                System.out.println("Invalid selection");
                sorted = false;
            }
        }
        if (sorted) {
            doViewApplications();
        }
    }

    // MODIFIES: this
    // EFFECTS: sorts the applications in the manager
    private void doSortApplications() {
        if (manager.getApplications().isEmpty()) {
            System.out.println("No applications to sort");
            return;
        }
        printSortOptions();
        sortApplicationSwitch();
    }

    // EFFECTS: asks if user wants to save to file 
    private void askSave() {
        System.out.println("Would you like to save your changes?");
        System.out.println("1. yes");
        System.out.println("2. no");
        switch (getValidIntInput()) {
            case 1: 
                save();
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid selection");
                input.next();
        }
    }

    // EFFECTS: saves applicationManager to file 
    private void save() { 
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads application manager from file
    private void loadSave() {
        try {
            manager = jsonReader.read();
            System.out.println("Loaded file from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
