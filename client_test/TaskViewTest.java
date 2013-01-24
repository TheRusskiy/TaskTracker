import task_controller.TaskController;
import task_tree.Data;
import task_tree.IDGenerator;
import task_tree.TaskTree;
import task_view.TaskView;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class TaskViewTest {
    public static boolean testAll()
    {
        boolean result=true;
        System.out.println("Failed tests:");
//        if (!createTaskTree())
//        {
//            System.out.println("create tree:"+createTaskTree());
//            result=false;
//        }
        return result;
    }

    public static void shittyTest() {
        IDGenerator idg = new IDGenerator();
        Data d = new Data("Not Working");
        Data g = new Data("Eating");
        Data d1 = new Data("Eating bananas");
        TaskTree tt = new TaskTree(idg, d);
        TaskTree t2 = new TaskTree(idg, g);
        TaskTree t3 = new TaskTree(idg, d1);
        tt.add(t2);
        t2.add(t3);

        //System.out.println(tt.getAllowsChildren());
        TaskController controller = new TaskController();
        //TaskView tv = new TaskView(new TaskTree[] {}, controller);
        TaskView testview = new TaskView(controller);
    }

    public static void main(String[] args)
    {
        //TaskViewTest.testAll();
        TaskViewTest.shittyTest();
    }
}
