package Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame {
    public StartWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("XML DB");

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JButton findUserButton = createButton("Find User", e -> new FindUserWindow());
        JButton addUserButton = createButton("Add User", e -> new AddUserWindow());

        panel.add(findUserButton);
        panel.add(addUserButton);

        getContentPane().add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setSize(150, 200);
        setResizable(false);
        setVisible(true);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
}
