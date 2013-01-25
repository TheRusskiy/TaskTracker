package task_view;

import exceptions.ControllerException;
import task_controller.TaskController;
import task_tree.Data;
import task_tree.TaskTree;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.*;
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

    TaskView This = this;
    TaskController controller;
    String login;
    String password;
    private TaskTree[] trees;  // get from controller
    TaskTree defaulttree;   // Rest task
    TaskTree currenttree;   // current tree tab
    TaskTree selectedtask;  // selected node
    TaskTree activetask;    // ???????
    JTree tree;
    JList list;
    JSplitPane splitpane;


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
    public void setLogin(String s){
        login = s;
    }
    public String getLogin(){
        return login;
    }
    public void setPassword(String s){
        password = s;
    }
    public String getPassword(){
        return password;
    }
    public TaskTree getActiveTask(){
        return activetask;
    }
    public void setActiveTask(TaskTree task){
        activetask = task;
    }

    public TaskView (final TaskController controller){
        super("TaskTracker");
        trees = new TaskTree[0];
        this.controller=controller;
        controller.setView(this);
        setSize(600, 400);
        setAvailableTreesScrollPane(trees);

        tabbedpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedpane.setMinimumSize(new Dimension(100, 50));

        setTreePane();
        setStatusPanel();
        setJMenuBar(createJMenu());
        add(setJPanels());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

//    public TaskView (TaskTree[] trees, final TaskController controller){
//        super("TaskTracker");
//        this.trees = trees;
//        this.controller=controller;
//        controller.setView(this);
//        setSize(600, 400);
//        setAvailableTreesScrollPane();
//
//        tabbedpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
//        tabbedpane.setMinimumSize(new Dimension(100, 50));
//
//        setStatusPanel();
//        setJMenuBar(createJMenu());
//        add(setJPanels());
//
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
//    }

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
        basic.add(splitpane, BorderLayout.CENTER);
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
        addbutton.addActionListener(new AddListener(this));
        control.add(addbutton);
        JButton editbutton = new JButton("Edit...");
        control.add(editbutton);
        JButton deletebutton = new JButton("Delete");
        deletebutton.addActionListener(new DeleteListener(this));
        control.add(deletebutton);
        JButton splitbutton = new JButton("Split");
        splitbutton.addActionListener(new SplitListener(this));
        control.add(splitbutton);
        JButton clonebutton = new JButton("Clone");
        control.add(clonebutton);
        JButton save = new JButton("Save All Tasks");
        save.addActionListener(new SaveAllListener(this));
        control.add(save);
        control.setVisible(true);
        return control;
    }

    private void setTreesTabbedPane(String str, int index){

        final JTree tree = new JTree(trees[index]);   // wtf?
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                selectedtask = (TaskTree)tree.getSelectionPath().getLastPathComponent();
            }
        });
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
                tabbedpane.removeTabAt(tabbedpane.getSelectedIndex());        ////////
            }
        });
        jp1.add(clsbutton);
        jp.add(jsp, BorderLayout.CENTER);
        jp.add(jp1, BorderLayout.SOUTH);
        jp.setVisible(true);
        tabbedpane.add(str, jp);
        tabbedpane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String s = tabbedpane.getTitleAt(tabbedpane.getSelectedIndex());
                try {
                    currenttree = controller.loadTree(s);
                } catch (ControllerException e1) {
                    setStatus(e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
        tabbedpane.setVisible(true);
    }

    private void setTreePane(){
        splitpane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                availabletrees,
                tabbedpane);
        splitpane.setVisible(true);
    }

    private void setAvailableTreesScrollPane(TaskTree[] trees) {
        this.trees = trees;
        list = new JList(trees);
        list.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()){
                            Object selected = list.getSelectedValue();
                            setTreesTabbedPane(((TaskTree)selected).getData().getActivityName(), list.getSelectedIndex());
                        }
                    }
                }
        );
        availabletrees = new JScrollPane(list);
        availabletrees.setMinimumSize(new Dimension(100,50));
        availabletrees.setPreferredSize(new Dimension(50,50));
        availabletrees.setVisible(true);
    }

    private void setStatusPanel(){
        statuspanel.setLayout(new FlowLayout());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        statuspanel.add(new JLabel(dateFormat.format(cal.getTime())));
        statuspanel.setBorder(new SoftBevelBorder(1));
        statuspanel.add(new JLabel("   |   "));
        statuspanel.add(statuslabel);
        statuspanel.setVisible(true);
    }

    private void setStatus(String status){
        statuslabel.setText(status);
        statuslabel.repaint();
        statuspanel.repaint();
    }

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
                TaskTree loadedTree = controller.loadTree("Rest");
                setDefaultTree(loadedTree);
                setStatus(controller.getStatus());
            } catch (ControllerException e1) {
                setStatus(e1.getMessage());
                e1.printStackTrace();
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
            login = (String)JOptionPane.showInputDialog(frame,
                    "Input login:", "Log In | Login", JOptionPane.WARNING_MESSAGE);
            password = (String)JOptionPane.showInputDialog(frame,
                    "Input password:", "Log In | Password", JOptionPane.WARNING_MESSAGE);
            //new LoginDialog(This);
            try {
                List<String> strlist = controller.getAvailableTrees(login, password);
                trees = new TaskTree[strlist.size()];
                System.out.println("Size of list  "+strlist.size());
                //trees[0] = controller.loadTree("Rest");
//                for (int i=1; i<strlist.size(); i++){
//                    trees[i] = controller.loadTree(login,password,strlist.get(i));
//                }
                int i = 0;
                for (String x : strlist)  {
                    trees[i]=controller.loadTree(x);
                    i++;
                }
                setDefaultTree(controller.loadTree("Rest"));
                list.setListData(trees);
                availabletrees.setViewportView(list);
                availabletrees.repaint();
                splitpane.repaint();
                setStatus(controller.getStatus());
            } catch (ControllerException e1) {
                setStatus(e1.getMessage());
                e1.printStackTrace();
            }
        }
    }

    class AddListener implements ActionListener{
        JFrame frame;

        public AddListener(JFrame frame){
            this.frame = frame;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                currenttree = controller.loadTree(tabbedpane.getTitleAt(tabbedpane.getSelectedIndex()));
                tree = new JTree(currenttree);
                tree.addTreeSelectionListener(new TreeSelectionListener() {
                    @Override
                    public void valueChanged(TreeSelectionEvent e) {
                        selectedtask = (TaskTree)tree.getLastSelectedPathComponent();
                    }
                });
                String nodename = (String)JOptionPane.showInputDialog(frame,
                        "Input task name:", "Add new task", JOptionPane.WARNING_MESSAGE);
                Data data = new Data();
                data.setActivityName(nodename);
                selectedtask.add(new TaskTree(selectedtask.getIDGenerator(), data));
                tabbedpane.repaint();
                setStatus(controller.getStatus());
            } catch (ControllerException e1) {
                e1.printStackTrace();
                setStatus(e1.getMessage());
            }
        }
    }

    class SplitListener implements ActionListener{
        JFrame frame;

        public SplitListener(JFrame frame){
            this.frame = frame;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                currenttree = controller.loadTree(tabbedpane.getTitleAt(tabbedpane.getSelectedIndex()));
                tree = new JTree(currenttree);
                tree.addTreeSelectionListener(new TreeSelectionListener() {
                    @Override
                    public void valueChanged(TreeSelectionEvent e) {
                        selectedtask = (TaskTree)tree.getLastSelectedPathComponent();
                    }
                });
                controller.splitNode(selectedtask.getID());
                tree.repaint();
                tabbedpane.repaint();
                setStatus(controller.getStatus());
            } catch (ControllerException e1) {
                e1.printStackTrace();
                setStatus(e1.getMessage());
            }
        }
    }

    class SaveAllListener implements ActionListener{
        JFrame frame;

        public SaveAllListener(JFrame frame){
            this.frame = frame;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
            String treename = tabbedpane.getTitleAt(tabbedpane.getSelectedIndex());
            currenttree = controller.loadTree(treename);
            controller.saveTree(treename);
            } catch (ControllerException e1) {
                e1.printStackTrace();
                setStatus(e1.getMessage());
            }
        }
    }

    class DeleteListener implements ActionListener{
        JFrame frame;

        public DeleteListener(JFrame frame){
            this.frame = frame;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                currenttree = controller.loadTree(tabbedpane.getTitleAt(tabbedpane.getSelectedIndex()));
                tree = new JTree(currenttree);
                tree.addTreeSelectionListener(new TreeSelectionListener() {
                    @Override
                    public void valueChanged(TreeSelectionEvent e) {
                        selectedtask = (TaskTree)tree.getLastSelectedPathComponent();
                    }
                });
                controller.deleteNode(selectedtask.getID());
                tabbedpane.repaint();
            } catch (ControllerException e1) {
                e1.printStackTrace();
                setStatus(e1.getMessage());
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

    public void redrawTree(TaskTree tree){

    }
}
