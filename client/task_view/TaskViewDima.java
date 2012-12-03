package task_view;

import exceptions.ControllerException;
import task_controller.TaskController;
import task_tree.TaskTree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 04.12.12
 * Time: 2:43
 * To change this template use File | Settings | File Templates.
 */
public class TaskViewDima extends TaskView {
    TaskController controller;

    JFrame frame;
    JPanel parentPanel;

    JPanel userPanel;

    JPanel userControlPanel;
    JButton userControlCreateUserButton=new JButton("Sign Up");
    JButton userControlLoginButton= new JButton("Log In");
    JLabel userControlLoginLabel=new JLabel("Login");
    JTextField userControlLoginText = new JTextField();
    JLabel userControlPasswordLabel=new JLabel("Password");
    JTextField userControlPasswordText=new JTextField();

    JPanel treePanel;
    JPanel treeControlPanel;
    JButton treeControlCreateNodeButton = new JButton("Create new Node");
    //todo other controls

    JPanel treeViewPanel;
    JTree jTree;

    JPanel statusPanel;
    JLabel statusLabel=new JLabel("Status...");
    Dimension dimensionFrame = new Dimension(400, 400);
    Dimension dimensionStatus = new Dimension(100, 30);


    public TaskViewDima(final TaskController newController){
        super(newController);
        this.controller=super.getController();
        controller.setView(this);

        frame = new JFrame("Task Tracker");
        parentPanel=new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        frame.add(parentPanel);

        userPanel= new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        parentPanel.add(userPanel);

        userControlPanel = new JPanel();
        userControlPanel.setLayout(new GridLayout(3, 2));
        userControlPanel.add(userControlCreateUserButton);
        userControlPanel.add(userControlLoginButton);
        userControlPanel.add(userControlLoginLabel);
        userControlPanel.add(userControlLoginText);
        userControlPanel.add(userControlPasswordLabel);
        userControlPanel.add(userControlPasswordText);
        userPanel.add(userControlPanel);

        treePanel = new JPanel();
        treePanel.setLayout(new BoxLayout(treePanel, BoxLayout.Y_AXIS));

        treeControlPanel = new JPanel();
        treeControlPanel.setLayout(new GridLayout());
        //TODO tree controls here
        treeControlPanel.add(treeControlCreateNodeButton);
        //
        treePanel.add(treeControlPanel);

        treeViewPanel = new JPanel();
        treeViewPanel.setLayout(new GridLayout(1, 1));
        jTree = new JTree();
        treeViewPanel.add(jTree);
        treePanel.add(treeViewPanel);

        parentPanel.add(treePanel);

        statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1, 1));
        statusPanel.add(statusLabel);
        statusPanel.setPreferredSize(dimensionStatus);
        parentPanel.add(statusPanel);

        //_______ACTION LISTENERS:_____________________
        userControlCreateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TaskTree loadedTree = controller.createUser(userControlLoginText.getText(), userControlPasswordText.getText());
                    redrawTree(loadedTree);
                    setStatus(controller.getStatus());
                } catch (ControllerException e1) {
                    setStatus(e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
        userControlLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.util.List<String> trees = controller.getAvailableTrees(userControlLoginText.getText(), userControlPasswordText.getText());
                    //redrawTree(loadedTree);
                    setStatus(controller.getStatus());
                } catch (ControllerException e1) {
                    setStatus(e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(dimensionFrame);
        frame.pack();
        frame.setVisible(true);
    }

    private void setStatus(String status){
        statusLabel.setText(status);
    }

    private void redrawTree(TaskTree loaderTree){
        jTree.setModel(new DefaultTreeModel(loaderTree));
    }
}
