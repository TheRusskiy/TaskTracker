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
public class LoginDialog extends JFrame{
    private Container parentPanel;
    private JLabel loginLabel = new JLabel("Username:");
    private JTextField loginField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField passwordField = new JTextField();
    private JButton loginButton = new JButton("Log In");
    private JButton cancelButton = new JButton("Cancel");
    TaskView parent;
    JFrame parentFrame;

    public LoginDialog(TaskView parent) {
        super("Task Tracker (C) NetCracker");
        this.parent=parent;
        parentFrame = parent;
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
        parentPanel.add(loginButton);
        parentPanel.add(cancelButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
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
        //parent.setDialogSuccessful(false);
        parentFrame.enable();
        this.setVisible(false);
    }

    private void login() {
        if (
                (!passwordField.getText().equals(""))
                &&(!loginField.equals(""))
                )
        {
            parent.setPassword(passwordField.getText());
            parent.setLogin(loginField.getText());
            //parent.getAvailableTrees();
            parentFrame.enable();
            this.setVisible(false);
        }

    }

}
