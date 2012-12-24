import task_controller.TaskController;
import task_view.TaskViewNewByDima;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class TaskControllerTest {
    public static boolean testAll()
    {
        boolean result=true;
        System.out.println("Failed tests:");
        TaskController controller = new TaskController();
        TaskViewNewByDima view = new TaskViewNewByDima(controller);
//        if (!createTaskTree())
//        {
//            System.out.println("create tree:"+createTaskTree());
//            result=false;
//        }
        return result;
    }

    public static void main(String[] args)
    {
        TaskControllerTest.testAll();
    }
}
