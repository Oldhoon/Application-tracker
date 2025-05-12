//attribution : Java GUI Tutorial #57 - JDialog Class In Java GUI Swing

package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Application;
import model.Status;

// dialog box for initial addition of application and editing applications
public class ApplicationDialog extends JDialog {
    private JTextField companyField;
    private JTextField positionField;
    private JComboBox<Status> statusDropdown;
    private boolean confirmed = false;
    private Application application;

    // EFFECTS: constructs a new dialog box for adding or editing applications
    public ApplicationDialog(JFrame parent, Application existingApp) {
        super(parent, true);
        // condition ? valueIfTrue : valueIfFalse
        setTitle(existingApp == null ? "Add Application" : "Edit Application");
        setSize(400, 200);
        // center dialog box relative to frame
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setFieldsForExistingApp(existingApp);
        getFormPanel();
        addButtons();

    }

    // EFFECTS: adds confirm and cancel buttons to panel
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        okButton.addActionListener(e -> {
            confirmed = true;
            application = new Application(
                    companyField.getText().trim(),
                    positionField.getText().trim()); // trim removes leading and trailing whitespace
            application.setStatus((Status) statusDropdown.getSelectedItem());
            // closes the current window
            dispose();
        });
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: form panel to display info
    private void getFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Company:"));
        formPanel.add(companyField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusDropdown);

        add(formPanel, BorderLayout.CENTER);
    }

    // EFFECTS: if dialog has existing application fill the fields
    // else return empty textfields
    private void setFieldsForExistingApp(Application existingApp) {
        companyField = new JTextField();
        positionField = new JTextField();
        // dropdown with enum status
        statusDropdown = new JComboBox<>(Status.values());
        if (existingApp != null) {
            companyField.setText(existingApp.getCompanyName());
            positionField.setText(existingApp.getPosition());
            statusDropdown.setSelectedItem(existingApp.getStatus());
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Application getApplication() {
        return application;
    }
}
