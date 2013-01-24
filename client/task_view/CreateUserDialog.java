package task_view;

import task_controller.TaskController;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
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
public class CreateUserDialog extends JFrame{
    private Container parentPanel;
    private JLabel loginLabel = new JLabel("Username:");
    private JTextField loginField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordField = new JTextField();
    private JLabel password2Label = new JLabel("Retype your password:");
    private JTextField password2Field = new JTextField();
    private JButton createButton = new JButton("Sign Up");
    private JButton cancelButton = new JButton("Cancel");
    TaskViewNewByDima parent;
    JFrame parentFrame;

    public CreateUserDialog(TaskViewNewByDima parent) {
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
        parentPanel.setLayout(new GridLayout(4, 2));
        
        parentPanel.add(loginLabel);
        parentPanel.add(loginField);
        parentPanel.add(passwordLabel);
        parentPanel.add(passwordField);
        parentPanel.add(password2Label);
        parentPanel.add(password2Field);
        parentPanel.add(createButton);
        parentPanel.add(cancelButton);
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
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

    private void createUser() {
        if (
                passwordField.getText().equals(password2Field.getText())
                &&(!passwordField.getText().equals(""))
                &&(!loginField.equals(""))
                )
        {
            parent.setPassword(passwordField.getText());
            parent.setLogin(loginField.getText());
            parent.signUp();
            parentFrame.enable();
            this.setVisible(false);
        }

    }

}
