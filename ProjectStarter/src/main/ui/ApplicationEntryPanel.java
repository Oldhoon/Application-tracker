package ui;

// attribution: brocode youtube channel

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Application;
import model.ApplicationManager;
import model.Status;

//represents one application entry 
public class ApplicationEntryPanel extends JPanel {
    private JLabel companyLabel;
    private JLabel positionLabel;
    private JLabel statusLabel;
    private JLabel dateLabel;
    private JLabel notesLabel;
    private JButton editButton;
    private JButton removeButton;

    // EFFECTS: constructs new application entry panel
    public ApplicationEntryPanel(Application app, ApplicationManager manager, JFrame parentFrame, Runnable onRefresh) {
        setLayout(new GridLayout(1, 6));
        setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        setPreferredSize(new Dimension(800, 30));
        addLabels(app);
        editButtonListener(app, parentFrame, onRefresh, editButton);
        removeButtonListener(app, manager, onRefresh, removeButton);
        setBackground(getStatusColor(app.getStatus()));
        addDoubleClickListener(app, parentFrame);
    }

    // MODIFIES: this
    // EFFECTS: remove the application from the list of applications and refresh the
    // list in the UI
    private void editButtonListener(Application app, JFrame parentFrame, Runnable onRefresh, JButton editButton) {
        editButton.addActionListener(e -> {
            ApplicationDialog dialog = new ApplicationDialog(parentFrame, app);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                Application updatedApp = dialog.getApplication();
                app.setCompanyName(updatedApp.getCompanyName());
                app.setPosition(updatedApp.getPosition());
                app.setStatus(updatedApp.getStatus());

                if (updatedApp.getStatus().equals(Status.ACCEPTED)) {
                    showCongrats(parentFrame);
                }
            }
            onRefresh.run(); // refresh the list in the UI
        });
    }

    // attribution: brocode youtube channel
    // MODIFIES: this
    // EFFECTS: remove the application from the list of applications and refresh the
    // list in the UI
    private void removeButtonListener(Application app, ApplicationManager manager, Runnable onRefresh,
            JButton removeButton) {
        // lambda expression for action listener
        removeButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this application?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                manager.removeApplication(app);
                onRefresh.run(); // refresh the list in the UI
            }
        });
    }

    // EFFECTS: add labels to the panel
    private void addLabels(Application app) {
        companyLabel = new JLabel(app.getCompanyName());
        positionLabel = new JLabel(app.getPosition());
        statusLabel = new JLabel(app.getStatus().toString());
        dateLabel = new JLabel(app.getDate().toString());
        notesLabel = new JLabel(app.getNotes().getNotes().size() + "");
        editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(80, 25));
        removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(80, 25));
        add(companyLabel);
        add(positionLabel);
        add(statusLabel);
        add(dateLabel);
        add(notesLabel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: refresh the notes label
    private void refreshNotesLabel(Application app) {
        notesLabel.setText(app.getNotes().getNotes().size() + "");
        revalidate();
        repaint();
    }

    // EFFECTS: return color based on the status of the application
    private Color getStatusColor(Status status) {
        switch (status) {
            case IN_REVIEW:
                return new Color(255, 255, 204);
            case REJECTED:
                return new Color(255, 204, 204);
            case INTERVIEW_PENDING:
                return new Color(204, 204, 255);
            case ACCEPTED:
                return new Color(204, 255, 204);
            default:
                return Color.WHITE;
        }
    }

    // MODIFIES: this
    // EFFECTS: add double click listener to the panel
    private void addDoubleClickListener(Application app, JFrame parentFrame) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    new NotesDialog(parentFrame, app, new Runnable() {
                        @Override
                        public void run() {
                            refreshNotesLabel(app);
                        }
                    });
                }
            }
        });
    }

    private void showCongrats(JFrame parentFrame) {
        ImageIcon icon = new ImageIcon("src/main/resources/congrats.jpg");
        JOptionPane.showMessageDialog(parentFrame, "Congratulations on your offer to: " + companyLabel.getText()
                + "!!!", "Congratulations", JOptionPane.INFORMATION_MESSAGE, icon);
    }
}
