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
public class NewNodeDialog extends JFrame{
    private Container parentPanel;
    private JLabel activityNameLabel = new JLabel("ActivityName:");
    private JTextField activityNameField = new JTextField();
    private JButton createButton = new JButton("Create");
    private JButton cancelButton = new JButton("Cancel");
    TaskViewNewByDima parent;
    JFrame parentFrame;

    public NewNodeDialog(TaskViewNewByDima parent) {
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
        parentPanel.setLayout(new GridLayout(2, 2));
        
        parentPanel.add(activityNameLabel);
        parentPanel.add(activityNameField);
        parentPanel.add(createButton);
        parentPanel.add(cancelButton);

        createButton.addActionListener(new ActionListener() {
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
                (!activityNameField.getText().equals("")))

        {
            parent.setNewNode(activityNameField.getText());
            parent.newNode();
            parentFrame.enable();
            this.setVisible(false);
        }

    }

}
