package Windows;

import Data.DataHandler;
import Filters.LengthDocumentFilter;
import Filters.MailFilter;
import Filters.NumericDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class EditUserWindow extends JFrame {
    public EditUserWindow(String id, String name, String lastname, String mobile, String email, String pesel, boolean isInternal) {
        setTitle("Find User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 0, 15));

        JLabel personIdLabel = new JLabel("Person ID:");
        JTextField personIdField = new JTextField(id);
        personIdField.setEditable(false);
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(name);

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(lastname);

        JLabel mobileLabel = new JLabel("Mobile:");
        JTextField mobileField = new JTextField(mobile);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(email);

        JLabel peselLabel = new JLabel("PESEL:");
        JTextField peselField = new JTextField(pesel);

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
        JCheckBox internalWorkerCheckBox = new JCheckBox("Internal employee");
        internalWorkerCheckBox.setSelected(isInternal);
        getContentPane().add(internalWorkerCheckBox, BorderLayout.NORTH);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            String pId = personIdField.getText();
            DataHandler.remove(pId);
            dispose();
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
        });
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {

            boolean isError = false;
            StringBuffer errorMsg = new StringBuffer();
            String _FirstName = firstNameField.getText();
            String _LastName = lastNameField.getText();
            String _Mobile = mobileField.getText();
            String _Email = emailField.getText();
            String _Pesel = peselField.getText();
            if (_FirstName.isEmpty())
            {
                firstNameField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("First Name error.\n");
                isError = true;
            }
            else
                firstNameField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (_LastName.isEmpty())
            {
                lastNameField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("Last Name error.\n");
                isError = true;
            }
            else
                lastNameField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (_Mobile.length() != 9)
            {
                mobileField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("Mobile error.\n");
                isError = true;
            }
            else
                mobileField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (!new MailFilter().isValidEmail(_Email))
            {
                emailField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("Email error.\n");
                isError = true;
            }
            else
                emailField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            if (_Pesel.length() != 11)
            {
                peselField.setBorder(BorderFactory.createLineBorder(Color.RED));
                errorMsg.append("PESEL must be 11 characters long.");
                isError = true;
            }
            else
                peselField.setBorder(BorderFactory.createLineBorder(Color.GREEN));

            if (isError) {
                JOptionPane.showMessageDialog(EditUserWindow.this, errorMsg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DataHandler.modify(personIdField.getText(), firstNameField.getText(), lastNameField.getText(),
                    mobileField.getText(), emailField.getText(), peselField.getText(), internalWorkerCheckBox.isSelected());
            JOptionPane.showMessageDialog(EditUserWindow.this, "Data saved!", "Save success", JOptionPane.WARNING_MESSAGE);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

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
