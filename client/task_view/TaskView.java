package task_view;

import exceptions.ControllerException;
import task_controller.TaskController;
import task_tree.TaskTree;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class TaskView extends JFrame {

    TaskController controller;
    private TaskTree[] trees;  // get from controller
    TaskTree defaulttree;   // Rest task
    TaskTree currenttree;   // current tree tab
    TaskTree activetask;    //

    JTabbedPane tabbedpane;
    JLabel statuslabel = new JLabel("Status...");
    JPanel statuspanel = new JPanel();
    JScrollPane availabletrees = new JScrollPane();

    public TaskController getController() {
        return controller;
    }
    public TaskTree getCurrentTree(){
        return currenttree;
    }
    public void setCurrentTree(TaskTree selectedtree){
        currenttree = selectedtree;
    }
    public TaskTree getDefaultTree(){
        return defaulttree;
    }
    public void setDefaultTree(TaskTree tree){
        defaulttree = tree;
    }
    public TaskTree getActiveTask(){
        return activetask;
    }
    public void setActiveTask(TaskTree task){
        activetask = task;
    }

    public TaskView (final TaskController controller){
        this.trees = trees;
        this.controller=controller;
    }

    public TaskView (TaskTree[] trees, final TaskController controller){
        super("TaskTracker");
        this.trees = trees;
        this.controller=controller;
        controller.setView((TaskViewNewByDima)(Object)this);
        setSize(600, 400);
        setAvailableTreesScrollPane();

        tabbedpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedpane.setMinimumSize(new Dimension(100, 50));

        setStatusPanel();
        setJMenuBar(createJMenu());
        add(setJPanels());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createJMenu(){
        JMenuBar jmenubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu conn = new JMenu("Connection");
        JMenu help = new JMenu("Help");
        jmenubar.add(file);
        jmenubar.add(edit);
        jmenubar.add(conn);
        jmenubar.add(help);
        JMenuItem login = new JMenuItem("Log In...");
        login.addActionListener(new LogInListener(this));
        JMenuItem signup = new JMenuItem("Sign Up...");
        signup.addActionListener(new SignUpListener(this));
        JMenuItem nw = new JMenuItem("Load Tasks");
        JMenuItem sv = new JMenuItem("Save");
        JMenuItem svall = new JMenuItem("Save All");
        file.add(login);
        file.add(signup);
        file.addSeparator();
        file.add(nw);
        file.addSeparator();
        file.add(sv);
        file.add(svall);
        JMenuItem cp = new JMenuItem("Copy");
        JMenuItem pst = new JMenuItem("Paste");
        JMenuItem rename = new JMenuItem("Rename...");
        JMenuItem select = new JMenuItem("Select As Active Task");
        JMenuItem split = new JMenuItem("Split");
        JMenuItem clone = new JMenuItem("Clone");
        edit.add(cp);
        edit.add(pst);
        edit.addSeparator();
        edit.add(select);
        edit.add(rename);
        edit.add(split);
        edit.add(clone);
        JMenuItem cnct = new JMenuItem("Connect...");
        JMenuItem dscnct = new JMenuItem("Disconnect");
        JMenuItem setup = new JMenuItem("Settings...");


        conn.add(cnct);
        conn.add(dscnct);
        conn.addSeparator();
        conn.add(setup);
        return jmenubar;
    }

    private JPanel setJPanels(){
        JPanel basic = new JPanel();
        basic.setLayout(new BorderLayout(3,3));
        basic.add(setTreePane(), BorderLayout.CENTER);
        basic.add(setControlPanel(), BorderLayout.EAST);
        basic.add(statuspanel, BorderLayout.SOUTH);
        basic.setVisible(true);
        return basic;
    }
    //TODO choose another layout manager to avoid these fat buttons
    private JPanel setControlPanel(){
        JPanel control = new JPanel();
        control.setMaximumSize(new Dimension(300, 50));
        control.setLayout(new GridLayout(10, 1, 3, 3));
        JButton loginbutton = new JButton("Log In...");
        loginbutton.addActionListener(new LogInListener(this));
        control.add(loginbutton);
        JButton signupbutton = new JButton("Sign Up...");
        signupbutton.addActionListener(new SignUpListener(this));
        control.add(signupbutton);
        JButton select = new JButton("Select As Active Task");

        control.add(select);
        JButton createnewbutton = new JButton("Create New...");
        control.add(createnewbutton);
        JButton addbutton = new JButton("Add...");
        control.add(addbutton);
        JButton editbutton = new JButton("Edit...");
        control.add(editbutton);
        JButton splitbutton = new JButton("Split");
        control.add(splitbutton);
        JButton clonebutton = new JButton("Clone");
        control.add(clonebutton);
        JButton save = new JButton("Save All Tasks");
        control.add(save);
        control.setVisible(true);
        return control;
    }

    private void setTreesTabbedPane(String str, int index){

        JTree tree = new JTree(trees[index]);
        tree.setShowsRootHandles(true);
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(tree);
        jsp.setVisible(true);
        JPanel jp1 = new JPanel();
        jp1.add(new JLabel("Some information about selected task"));
        JButton clsbutton = new JButton("Close");
        clsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedpane.removeTabAt(tabbedpane.getSelectedIndex());
            }
        });
        jp1.add(clsbutton);
        jp.add(jsp, BorderLayout.CENTER);
        jp.add(jp1, BorderLayout.SOUTH);
        jp.setVisible(true);
        tabbedpane.add(str, jp);
        tabbedpane.setVisible(true);
    }

    private JSplitPane setTreePane(){
        JSplitPane sp = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                availabletrees,
                tabbedpane);
        sp.setVisible(true);
        return sp;
    }

    private void setAvailableTreesScrollPane() {
//        try {
//            trees = controller.getAvailableTrees(login,password).toArray(trees);
//        } catch (ControllerException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        final JList list = new JList(trees);
        list.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    //TODO avoid opening of same tab twice
                    public void valueChanged(ListSelectionEvent e) {
                        Object selected = list.getSelectedValue();
                        setTreesTabbedPane(selected.toString(), list.getSelectedIndex());
                    }
                }
        );
        availabletrees = new JScrollPane(list);
        availabletrees.setMinimumSize(new Dimension(100,50));
        availabletrees.setVisible(true);
        //return jsp;
    }

    private void setStatusPanel(){
        //JPanel statuspanel = new JPanel();
        statuspanel.setLayout(new FlowLayout());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        statuspanel.add(new JLabel(dateFormat.format(cal.getTime())));
        statuspanel.setBorder(new SoftBevelBorder(1));
        statuspanel.add(new JLabel("   |   "));
        //statuslabel = new JLabel("Status...");
        statuspanel.add(statuslabel);
        statuspanel.setVisible(true);
        this.statuspanel = statuspanel;
        //return statuspanel;
    }

    private void setStatus(String status){
        statuslabel.setText(status);
        statuslabel.repaint();
        //setStatusPanel();
        statuspanel.repaint();
    }
//    private void redrawTree(TaskTree loaderTree){
//        currenttree.setModel(new DefaultTreeModel(loaderTree));
//    }

    ////////////////////////////////////////////////////////////////

    class SignUpListener implements ActionListener{

        JFrame frame;

        public SignUpListener(JFrame frame){
           this.frame = frame;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO a single window for both inputs
            String login = (String)JOptionPane.showInputDialog(frame,
                    "Input login:", "Sign Up | Login", JOptionPane.WARNING_MESSAGE);
            String password = (String)JOptionPane.showInputDialog(frame,
                    "Input password:", "Sign Up | Password", JOptionPane.WARNING_MESSAGE);
            try {
                controller.createUser(login, password);
                List<String> trees = controller.getAvailableTrees(login, password);
                TaskTree loadedTree =  controller.loadTree(trees.get(0));
                        //redrawTree(loadedTree);

                setStatus(controller.getStatus());
            } catch (ControllerException e1) {
                setStatus(e1.getMessage());
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    class LogInListener implements ActionListener{

        JFrame frame;

        public LogInListener(JFrame frame){
            this.frame = frame;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO a single window for both inputs
            String login = (String)JOptionPane.showInputDialog(frame,
                    "Input login:", "Log In | Login", JOptionPane.WARNING_MESSAGE);
            String password = (String)JOptionPane.showInputDialog(frame,
                    "Input password:", "Log In | Password", JOptionPane.WARNING_MESSAGE);
            try {
                TaskTree loadedTree = controller.loadTree("Rest");
                //redrawTree(loadedTree);
                //setAvailableTreesScrollPane(login,password);
                //availabletrees.repaint();
                setStatus(controller.getStatus());

            } catch (ControllerException e1) {
                setStatus(e1.getMessage());
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    class TreeListener implements TreeSelectionListener{

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(TreeSelectionEvent e) {

        }
    }
}
