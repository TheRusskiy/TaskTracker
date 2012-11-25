import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class IntegrityTest {
    public static boolean testAll()
    {
        boolean result=true;
//        if (!createUserTest())
//        {
//            System.out.println("Create user test fails");
//            result=false;
//        }
        if (!getAvailableTreesTest())
        {
            System.out.println("Get available trees test fails");
            result=false;
        }
        return result;
    }

    private static boolean getAvailableTreesTest(){
        try {
            final TaskServer taskServer= new TaskServer();
            Thread t =new Thread(){
                public void run(){
                    taskServer.startServer();
                }
            };
            t.start();
            List<String> trees=TaskClientNetDriver.getAvailableTrees("Dima", "password");
           taskServer.stopServer();
            int i=0;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

//todo make repeateable
    private static boolean createUserTest(){
        try {
            final TaskServer taskServer= new TaskServer();
            Thread t =new Thread(){
                public void run(){
                    taskServer.startServer();
                }
            };
            t.start();
            TaskTree tree=TaskClientNetDriver.createUser("Dima", "password");
            int i=0;
            taskServer.stopServer();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void startTests(){
        System.out.println("Integrity test: " + IntegrityTest.testAll());
    }

    public static void main(String[] args)
    {
        startTests();
    }
}
