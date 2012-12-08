package task_view;

import task_controller.TaskController;
import task_tree.TaskTree;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class TaskView extends JFrame {

    TaskController controller;

    public TaskController getController() {
        return controller;
    }

    public TaskView(final TaskController controller){
        this.controller=controller;
        controller.setView(this);
    }

    private TaskTree[] trees;

    public TaskView (TaskTree[] trees){

        super("TaskTracker");
        this.trees = trees;
        setSize(600, 400);
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
        JMenuItem signup = new JMenuItem("Sign Up...");
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
        basic.add(setStatusPanel(), BorderLayout.SOUTH);
        basic.setVisible(true);
        return basic;
    }
    //TODO choose another layout manager to avoid these fat buttons
    private JPanel setControlPanel(){
        JPanel control = new JPanel();
        control.setMaximumSize(new Dimension(300, 50));
        control.setLayout(new GridLayout(10, 1, 3, 3));
        JButton loginbutton = new JButton("Log In...");
        control.add(loginbutton);
        JButton signupbutton = new JButton("Sign Up...");
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

    private JTabbedPane setTreesTabbedPane(){

        JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tp.setMinimumSize(new Dimension(100, 50));

        for (int i=0; i<trees.length; i++){
            JTree tree = new JTree(trees[i]);
            tree.setShowsRootHandles(true);
            JPanel jp = new JPanel();
            jp.setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(tree);
            jsp.setVisible(true);
            JPanel jp1 = new JPanel();
            jp1.add(new JLabel("Some information about selected task"));
            jp1.add(new JButton("Close"));
            jp.add(jsp, BorderLayout.CENTER);
            jp.add(jp1, BorderLayout.SOUTH);
            jp.setVisible(true);
            tp.add("Tree_"+i, jp);
        }
        tp.setVisible(true);
        return tp;
    }

    private JSplitPane setTreePane(){
        JSplitPane sp = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                setAvailableTreesScrollPane(),
                setTreesTabbedPane());
        sp.setVisible(true);
        return sp;
    }

    private JScrollPane setAvailableTreesScrollPane() {
        JList list = new JList(trees);
        JScrollPane jsp = new JScrollPane(list);
        jsp.setMinimumSize(new Dimension(100,50));
        jsp.setVisible(true);
        return jsp;
    }

    private JPanel setStatusPanel(){
        JPanel statuspanel = new JPanel();
        statuspanel.setLayout(new FlowLayout());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        statuspanel.add(new JLabel(dateFormat.format(cal.getTime())));
        statuspanel.setBorder(new SoftBevelBorder(1));
        statuspanel.add(new JLabel("   |   "));
        statuspanel.add(new JLabel("Some extra information about connection status " +
                "or(and) active task"));
        statuspanel.setVisible(true);
        return statuspanel;
    }
}
