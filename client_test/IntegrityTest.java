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
        final TaskServer taskServer= new TaskServer();
        Thread t =new Thread(){
            public void run(){
                taskServer.startServer();
            }
        };
        t.start();
        if (!createUserTest())
        {
            System.out.println("Create user test fails");
            result=false;
        }
        if (!getAvailableTreesTest())
        {
            System.out.println("Get available trees test fails");
            result=false;
        }
        taskServer.stopServer();
        return result;
    }

    private static boolean getAvailableTreesTest(){
        try {

            List<String> trees=TaskClientNetDriver.getAvailableTrees("Dima2", "password");
            int i=0;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static boolean createUserTest(){
        try {

            TaskTree tree=TaskClientNetDriver.createUser("Dima2", "password");
            int i=0;

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
