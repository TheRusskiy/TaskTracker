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
public class TaskViewNewByDima {
    TaskController controller;
    private JFrame frame;
    private Container parentPanel;

    private Container mainPanel;
        private Container treeListPanel;
            private Container treeListViewPanel;
                private DefaultListModel<String> listModel;
                private JList<String> treeList;
            private Container treeListControlPanel;
                JButton treeListNewButton;
                JButton treeListOpenButton;
                JButton treeListDeleteButton;
        private Container treeActivitiesPanel;
            private Container treeActivitiesViewPanel;
                JTree jTree;
                DefaultTreeModel treeModel;
            private Container treeActivitiesControlPanel;
                JButton treeControlsChooseButton;
                JButton treeControlsNewButton;
                JButton treeControlsDeleteButton;
                JButton treeControlsSplitButton;
    private Container statusPanel;
        private JLabel statusLabel;

    public TaskViewNewByDima(final TaskController controller) {
        this.controller = controller;
        frame = new JFrame("Task Tracker (C) NetCracker");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        parentPanel=new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        frame.add(parentPanel);

        mainPanel=new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        parentPanel.add(mainPanel);

            treeListPanel=new JPanel();
            treeListPanel.setLayout(new BoxLayout(treeListPanel, BoxLayout.Y_AXIS));
            mainPanel.add(treeListPanel);

                treeListViewPanel=new JPanel();
                treeListViewPanel.setLayout(new GridLayout(1,1));
                treeListPanel.add(treeListViewPanel);

                    listModel = new DefaultListModel<>();
                    treeList = new JList<>(listModel);
                    listModel.addElement("Log In to get available trees");
                    treeListViewPanel.add(treeList);

                treeListControlPanel=new JPanel();
                treeListControlPanel.setLayout(new VerticalFlowLayout());
                treeListPanel.add(treeListControlPanel);

                    treeListNewButton = new JButton("Create new tree");
                    treeListControlPanel.add(treeListNewButton);
                    treeListOpenButton = new JButton("Open selected tree");
                    treeListControlPanel.add(treeListOpenButton);
                    treeListDeleteButton = new JButton("Delete selected tree");
                    treeListControlPanel.add(treeListDeleteButton);

            treeActivitiesPanel=new JPanel();
            treeActivitiesPanel.setLayout(new BoxLayout(treeActivitiesPanel, BoxLayout.X_AXIS));
            mainPanel.add(treeActivitiesPanel);

                treeActivitiesViewPanel=new JPanel();
                treeActivitiesViewPanel.setLayout(new GridLayout(1,1));
                treeActivitiesPanel.add(treeActivitiesViewPanel);
                    jTree = new JTree();
                    treeActivitiesViewPanel.add(jTree);
                    treeModel = new DefaultTreeModel(null);
                    jTree.setModel(treeModel);

                treeActivitiesControlPanel=new JPanel();
                treeActivitiesControlPanel.setLayout(new VerticalFlowLayout());
                treeActivitiesPanel.add(treeActivitiesControlPanel);
                    treeControlsChooseButton = new JButton("Choose selected");
                    treeActivitiesControlPanel.add(treeControlsChooseButton);
                    treeControlsNewButton = new JButton("New activity");
                    treeActivitiesControlPanel.add(treeControlsNewButton);
                    treeControlsDeleteButton = new JButton("Delete selected");
                    treeActivitiesControlPanel.add(treeControlsDeleteButton);
                    treeControlsSplitButton = new JButton("Split selected");
                    treeActivitiesControlPanel.add(treeControlsSplitButton);

        statusPanel=new JPanel();
        statusPanel.setLayout(new GridLayout(1,1));
        parentPanel.add(statusPanel);
            statusLabel = new JLabel("Welcome!");
            statusPanel.add(statusLabel);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //File  menu:
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        //File menu items:
        JMenuItem exitFileItem = new JMenuItem("Exit");
        fileMenu.add(exitFileItem);

        //User menu:
        JMenu userMenu = new JMenu("User");
        menuBar.add(userMenu);
        //User menu items:
        JMenuItem signUpMenuItem=new JMenuItem("Sign Up");
        userMenu.add(signUpMenuItem);
        JMenuItem logInMenuItem=new JMenuItem("Log In");
        userMenu.add(logInMenuItem);

        //User menu:
        JMenu dataMenu = new JMenu("Data");
        menuBar.add(dataMenu);
        //User menu items:
        JMenuItem getAvailableTreesMenuItem=new JMenuItem("Get available trees");
        dataMenu.add(getAvailableTreesMenuItem);
        JMenuItem saveSelectedTreeMenuItem=new JMenuItem("Save selected tree");
        dataMenu.add(saveSelectedTreeMenuItem);
        JMenuItem saveAllTreesMenuItem=new JMenuItem("Save all trees");
        dataMenu.add(saveAllTreesMenuItem);

        menuBar.add(getLookAndFeelMenu(frame));


        //ACTION LISTENERS:
        signUpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });




        frame.pack();
        frame.setVisible(true);
    }

    public static JMenu getLookAndFeelMenu(final JFrame frame){
        JMenu lookAndFeelMenu = new JMenu("Look&Feel");
        //Get look&feel from system:
        final UIManager.LookAndFeelInfo[] looks=UIManager.getInstalledLookAndFeels();
        //Creating look and feel buttons
        final ButtonGroup lookGroup=new ButtonGroup();
        for(int i=0; i<looks.length; i++){
            final JRadioButtonMenuItem lookItem = new JRadioButtonMenuItem(looks[i].getName());
            //if this is current look and feel then select corresponding radio button:
            if(looks[i].getName().equals(UIManager.getLookAndFeel().getName())){
                lookItem.setSelected(true);
            }
            //add to menu and radio group:
            lookAndFeelMenu.add(lookItem);
            lookGroup.add(lookItem);
            //separator:
            lookAndFeelMenu.addSeparator();
            final int finalI = i;
            //Action listener
            lookItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        UIManager.setLookAndFeel(looks[finalI].getClassName());
                        SwingUtilities.updateComponentTreeUI(frame);
                        lookGroup.clearSelection();
                        lookGroup.setSelected(lookItem.getModel(), true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return lookAndFeelMenu;
    }

}
