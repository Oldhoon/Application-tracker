//attribution: Java GUI: Full course by Bro code (Youtube) 

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import model.Application;
import model.ApplicationManager;
import model.Status;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.util.List;

// main window of app
public class MainWindow extends JFrame {

    private ApplicationManager manager;
    private JPanel applicationListPanel; // scrollable panel
    private static final String JSON_STORE = "./data/applications.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /// EFFECTS: constructs new main window
    public MainWindow() {
        super("Application Tracker");

        setUpJFrame();
        this.manager = new ApplicationManager();
        addDummyEntries();
        setupMenuBar();
        setupTopPanel();
        setLocationRelativeTo(null); // center start location
        setupApplicationListPanel();
        setUpJsonReaderWriter();

        refreshApplicationList();
        setVisible(true);
        printEventWhenClose();
    }

    // EFFECTS: prints event log when window is closed
    private void printEventWhenClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
            }
        });
    }

    // EFFECTS: prints the event log to the console
    private void printEventLog() {
        for (model.Event event : model.EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    // EFFECTS: sets up the JFrame
    private void setUpJFrame() {
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    // EFFECTS: sets up the json reader and writer
    private void setUpJsonReaderWriter() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: sets up the menu bar
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        saveItem.addActionListener(e -> save());
        loadItem.addActionListener(e -> load());
    }

    // EFFECTS: sets up the top panel
    private void setupTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Application");
        // dropdown list of items when user clicks on drop down arrow.
        JComboBox<String> sortDropdown = new JComboBox<>(
                new String[] { "Sort by ID", "Sort by Company", "Sort by Position", "Sort by Status" });
        addApplicationListener(addButton);
        sortDropdown.addActionListener(e -> {
            sortApplications((String) sortDropdown.getSelectedItem());
            refreshApplicationList();
        });
        topPanel.add(addButton);
        topPanel.add(sortDropdown);

        add(topPanel, BorderLayout.NORTH);
    }

    // EFFECTS: sorts applications based on the selected sort by option
    private void sortApplications(String sortBy) {
        switch (sortBy) {
            case "Sort by ID":
                manager.sortApplicationsById();
                break;
            case "Sort by Company":
                manager.sortApplicationsByCompanyName();
                break;
            case "Sort by Position":
                manager.sortApplicationsByPosition();
                break;
            case "Sort by Status":
                manager.sortApplicationsByStatus();
                break;
        }
    }

    // EFFECTS: adds an application to the list of applications
    private void addApplicationListener(JButton addButton) {
        addButton.addActionListener(e -> {
            ApplicationDialog dialog = new ApplicationDialog(this, null);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                manager.addApplication(dialog.getApplication());
                refreshApplicationList();
            }
        });
    }

    // EFFECTS: reloads list of applications
    // attribution :
    // https://stackoverflow.com/questions/17608421/how-to-reload-a-jpanel
    private void refreshApplicationList() {
        applicationListPanel.removeAll();
        setupHeaderRow(applicationListPanel);
        List<Application> apps = manager.getApplications();
        manager.rejectOldApplication();
        for (int i = 0; i < apps.size(); i++) {
            applicationListPanel.add(
                    new ApplicationEntryPanel(apps.get(i), manager, this, this::refreshApplicationList)
            // passes refreshApplicationList as a runnable to be called when needed
            // allows for refreshing the list of applications in the UI
            );
        }
        // recalculate layout based on new components
        applicationListPanel.revalidate();
        // redraw updated list
        applicationListPanel.repaint();
    }

    // EFFECTS: sets up the application list panel
    private void setupApplicationListPanel() {
        applicationListPanel = new JPanel();
        applicationListPanel.setLayout(new BoxLayout(applicationListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(applicationListPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: saves applicationManager to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved successfully to:\n" + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads applicationManager from file
    private void load() {
        try {
            manager = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Loaded successfully from:\n" + JSON_STORE);
            refreshApplicationList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load from file: " + JSON_STORE);
        }
    }

    // EFFECTS: sets up the header row for the application list
    private void setupHeaderRow(JPanel panel) {
        JPanel header = new JPanel(new GridLayout(1, 6));
        header.add(new JLabel("Company"));
        header.add(new JLabel("Position"));
        header.add(new JLabel("Status"));
        header.add(new JLabel("Date"));
        header.add(new JLabel("Notes"));
        header.add(new JLabel(""));

        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        panel.add(header);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    // dummy entries for testing
    private void addDummyEntries() {
        manager.addApplication(new Application("Google", "Software Engineer"));
        manager.addApplication(new Application("Facebook", "Product Manager"));
        manager.addApplication(new Application("Amazon", "Data Scientist"));
        manager.addApplication(new Application("Microsoft", "Software Engineer"));
        manager.addApplication(new Application("Apple", "Product Manager"));
        manager.getApplications().get(0).setStatus(Status.INTERVIEW_PENDING);
        manager.getApplications().get(1).setDate(LocalDate.now().minusDays(15));
        manager.getApplications().get(2).setStatus(Status.ACCEPTED);
        manager.getApplications().get(3).setDate(LocalDate.now().minusDays(14));
        manager.getApplications().get(4).setDate(LocalDate.now().minusDays(16));
    }
}
