package task_view;

import task_controller.TaskController;
import task_tree.TaskTree;

import javax.swing.*;
import java.awt.*;

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
        setSize(600, 400); //Dimension???
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
        JMenuItem nw = new JMenuItem("Load...");
        JMenuItem sv = new JMenuItem("Save");
        JMenuItem svall = new JMenuItem("Save All");
        file.add(nw);
        file.add(sv);
        file.add(svall);
        JMenuItem cp = new JMenuItem("Copy");
        JMenuItem pst = new JMenuItem("Paste");
        edit.add(cp);
        edit.add(pst);
        JMenuItem cnct = new JMenuItem("Connect..."); // automatically??
        JMenuItem dscnct = new JMenuItem("Disconnect");
        conn.add(cnct);
        conn.add(dscnct);
        return jmenubar;
    }

    private JPanel setJPanels(){
        JPanel basic = new JPanel();
        basic.setLayout(new BorderLayout(3,3));
        basic.add(setTreePane(), BorderLayout.CENTER);
        basic.add(setControlPanel(), BorderLayout.EAST);
        basic.setVisible(true);
        return basic;
    }

    private JPanel setControlPanel(){
        JPanel control = new JPanel();
        control.setLayout(new GridLayout(10,1,3,3));
        control.add(new JButton("Create New..."));
        control.add(new JButton("Add..."));
        control.add(new JButton("Split"));
        control.setVisible(true);
        return control;
    }

    private JTabbedPane setTreesTabbedPane(){

        JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tp.setPreferredSize(new Dimension(100,100));
        for (int i=0; i<trees.length; i++){
            JTree tree = new JTree(trees[i]);
            tree.setShowsRootHandles(true);
            tree.setEditable(true);
            tp.add("Tree_"+i, tree);
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

    private JPanel setAvailableTreesScrollPane() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        JList list = new JList(trees);
        JScrollPane jsp = new JScrollPane(p);
        p.add(list);
        p.setMinimumSize(new Dimension(100,50));
        jsp.setVisible(true);
        return p;
    }
}
