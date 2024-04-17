package Windows;
import Data.DataHandler;
import Filters.LengthDocumentFilter;
import Filters.MailFilter;
import Filters.NumericDocumentFilter;
import Person.Person;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
public class AddUserWindow extends JFrame {
    public AddUserWindow() {
        setTitle("Add User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 0, 15));

        JLabel personIdLabel = new JLabel("Person ID:");
        JTextField personIdField = new JTextField("autoincrement");
        personIdField.setEditable(false);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();

        JLabel mobileLabel = new JLabel("Mobile:");
        JTextField mobileField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel peselLabel = new JLabel("PESEL:");
        JTextField peselField = new JTextField();

        panel.add(personIdLabel);
        panel.add(personIdField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(mobileLabel);
        panel.add(mobileField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(peselLabel);
        panel.add(peselField);
        JCheckBox internalWorkerCheckBox = new JCheckBox("Internal worker");
        getContentPane().add(internalWorkerCheckBox, BorderLayout.NORTH);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            boolean isError = false;
            StringBuffer errorMsg = new StringBuffer();
            String personId = "0";
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String mobile = mobileField.getText();
            String email = emailField.getText();
            String pesel = peselField.getText();
            boolean internal = internalWorkerCheckBox.isSelected();
            if (firstName.isEmpty()) {
                firstNameField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("First Name error.\n");
                isError = true;
            } else
                firstNameField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (lastName.isEmpty()) {
                lastNameField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("Last Name error.\n");
                isError = true;
            } else
                lastNameField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (mobile.length() != 9) {
                mobileField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("Mobile error.\n");
                isError = true;
            } else
                mobileField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (!new MailFilter().isValidEmail(email)) {
                emailField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("Email error.\n");
                isError = true;
            } else
                emailField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (pesel.length() != 11) {
                peselField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("PESEL must be 11 characters long.");
                isError = true;
            } else
                peselField.setBorder(BorderFactory.createLineBorder(Color.GREEN));

            if (isError) {
                JOptionPane.showMessageDialog(AddUserWindow.this, errorMsg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataHandler.create(new Person(personId, firstName, lastName, mobile, email, pesel, internal));
            JOptionPane.showMessageDialog(AddUserWindow.this, "Done!", "Success", JOptionPane.WARNING_MESSAGE);
            dispose();
            new AddUserWindow();
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new StartWindow();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        firstNameField.setColumns(15);

        ((AbstractDocument) peselField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((AbstractDocument) mobileField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((AbstractDocument) mobileField.getDocument()).setDocumentFilter(new LengthDocumentFilter(9));
        ((AbstractDocument) peselField.getDocument()).setDocumentFilter(new LengthDocumentFilter(11));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}