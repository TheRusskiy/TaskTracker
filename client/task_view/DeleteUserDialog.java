package task_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 24.12.12
 * Time: 6:55
 * To change this template use File | Settings | File Templates.
 */
public class DeleteUserDialog extends JFrame{
    private Container parentPanel;
    private JLabel loginLabel = new JLabel("Username:");
    private JTextField loginField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordField = new JTextField();
    private JButton deleteButton = new JButton("Delete");
    private JButton cancelButton = new JButton("Cancel");
    TaskViewNewByDima parent;
    JFrame parentFrame;

    public DeleteUserDialog(TaskViewNewByDima parent) {
        super("Task Tracker (C) NetCracker");
        this.parent=parent;
        parentFrame = parent.getFrame();
        parentFrame.disable();


        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        

        this.setPreferredSize(new Dimension(300, 150));
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLocation(parentFrame.getBounds().getLocation());
        

        parentPanel=new JPanel();
        parentPanel.setLayout(new GridLayout(3, 2));
        
        parentPanel.add(loginLabel);
        parentPanel.add(loginField);
        parentPanel.add(passwordLabel);
        parentPanel.add(passwordField);
        parentPanel.add(deleteButton);
        parentPanel.add(cancelButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        
        this.add(parentPanel);
        this.pack();
        this.setVisible(true);
    }

    private void cancel() {
        parent.setDialogSuccessful(false);
        parentFrame.enable();
        this.setVisible(false);
    }

    private void deleteUser() {
        if (
                (!passwordField.getText().equals(""))
                &&(!loginField.equals(""))
                )
        {
            parent.setPassword(passwordField.getText());
            parent.setLogin(loginField.getText());
            parent.deleteUser();
            parentFrame.enable();
            this.setVisible(false);
        }

    }

}
