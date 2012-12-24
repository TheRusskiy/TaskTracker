import task_controller.TaskController;
import task_view.TaskViewNewByDima;

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
        if (!newByDimaTest())
        {
            System.out.println("newByDimaTest fails");
            result=false;
        }
        if (result){
            System.out.println("View test was successful!");
        }
        return result;
    }

    public static boolean newByDimaTest() {
        try{
            TaskController controller = new TaskController();
            TaskViewNewByDima view = new TaskViewNewByDima(controller);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args)
    {
        TaskViewTest.testAll();
    }
}
