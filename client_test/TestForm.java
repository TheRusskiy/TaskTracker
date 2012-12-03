import task_tree.Data;
import task_tree.IDGenerator;
import task_tree.TaskTree;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 03.12.12
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
public class TestForm{
    private JTree tree1;
    private JFrame frame;
    private JPanel parentPanel;
    public static void main(String[] args) {
        TestForm form = new TestForm();
    }

    public TestForm() {
        frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parentPanel=new JPanel();
        parentPanel.setLayout(new GridLayout(1, 1));
        frame.add(parentPanel);

        IDGenerator idGenerator = new IDGenerator();
        Data data0 = new Data("parent");
        Data data01 = new Data("child01");
        Data data02 = new Data("child02");
        TaskTree taskTree0 = new TaskTree(idGenerator, data0);
        TaskTree taskTree01 = new TaskTree(idGenerator, data01);
        TaskTree taskTree02 = new TaskTree(idGenerator, data02);

        taskTree0.add(taskTree01);
        taskTree0.add(taskTree02);


        tree1 = new JTree(taskTree0);
        tree1.setRowHeight(100);

        parentPanel.add(tree1);


        frame.pack();
        frame.setVisible(true);
    }

}
