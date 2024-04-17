package Windows;

import Data.DataHandler;
import Filters.LengthDocumentFilter;
import Filters.NumericDocumentFilter;
import Person.Person;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class FindUserWindow extends JFrame {
    public FindUserWindow() {
        setTitle("Find User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 0, 15));

        JLabel personIdLabel = new JLabel("Person ID:");
        JTextField personIdField = new JTextField("");

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
        JCheckBox internalWorker = new JCheckBox("Internal employee");
        getContentPane().add(internalWorker, BorderLayout.NORTH);
        JButton addButton = new JButton("Find");
        addButton.addActionListener(e -> {
            String personId = personIdField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String mobile = mobileField.getText();
            String email = emailField.getText();
            String pesel = peselField.getText();
            boolean isInternal = internalWorker.isSelected();
            Person person = DataHandler.find(personId, firstName, lastName, mobile, email, pesel, isInternal);
            if (person == null) {
                JOptionPane.showMessageDialog(FindUserWindow.this, "No matches", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new EditUserWindow(person.getPersonId(), person.getFirstName(), person.getLastName(),person.getMobile(),person.getEmail(), person.getPesel(), person.isInternal());
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
