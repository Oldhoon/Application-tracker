// attribution: Java GUI Tutorial #57 - JDialog Class In Java GUI Swing

package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Application;

public class NotesDialog extends JDialog {

    private JTextArea notesArea;
    private JTextField notesField;
    private Runnable onNotesUpdated;

    // EFFECTS: constructs a new dialog box for adding or editing applications
    public NotesDialog(JFrame parent, Application app, Runnable onNotesUpdated) {
        super(parent, "Notes - " + app.getCompanyName(), true);
        this.onNotesUpdated = onNotesUpdated;
        setUpDialogBox(parent);

        for (String note : app.getNotes().getNotes()) {
            notesArea.append(note + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(notesArea);
        add(scrollPane, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
        notesField = new JTextField();
        JButton addNoteButton = getAddNoteButton(app);

        addButton(inputPanel, addNoteButton);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the dialog box
    private void setUpDialogBox(JFrame parent) {
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        notesArea = new JTextArea();
        notesArea.setEditable(false);
        notesArea.setLineWrap(true); // wrap text if it exceeds the width of the text area
        notesArea.setWrapStyleWord(true); // wrap at word boundaries
    }

    // MODIFIES: this
    // EFFECTS: adds the notes field and add note button to the input panel
    private void addButton(JPanel inputPanel, JButton addNoteButton) {
        inputPanel.add(notesField, BorderLayout.CENTER);
        inputPanel.add(addNoteButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: returns a button that adds a note to the application
    private JButton getAddNoteButton(Application app) {
        JButton addNoteButton = new JButton("Add Note");
        addNoteButton.addActionListener(e -> {
            app.getNotes().addNote(notesField.getText());
            notesArea.append(notesField.getText() + "\n");
            notesField.setText("");
            onNotesUpdated.run();
        });
        return addNoteButton;
    }

}
