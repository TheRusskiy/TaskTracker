import exceptions.NetworkInteractionException;
import server_network.TaskServer;
import task_network.TaskClientNetDriver;
import task_tree.Data;
import task_tree.IDGenerator;
import task_tree.TaskTree;

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
    private static final String TEST_USER="TestUser";
    private static final String TEST_PASSWORD="TestPassword";
    private static final String TEST_TREE_NAME="TestTree";
    private static List<String> betweenTestTreeNames;
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
        if (!saveTreeTest())
        {
            System.out.println("Save tree test fails");
            result=false;
        }
        if (!getAvailableTreesTest())
        {
            System.out.println("Get available trees test fails");
            result=false;
        }
        if (!betweenTestTreeNames.contains(TEST_TREE_NAME)){
            System.out.println("Save'n'load tree name test fails");
            result=false;
        }
        if (!loadTreeTest())
        {
            System.out.println("Load tree test fails");
            result=false;
        }
        if (!deleteTreeTest())
        {
            System.out.println("Delete tree test fails");
            result=false;
        }
        if (!getAvailableTreesTest())
        {
            System.out.println("Get available trees test fails");
            result=false;
        }
        if (betweenTestTreeNames.contains(TEST_TREE_NAME)){
            System.out.println("Delete'n'load tree name test fails");
            result=false;
        }
        if (!deleteUserTest())
        {
            System.out.println("Delete user test fails");
            result=false;
        }
        taskServer.stopServer();
        return result;
    }

    private static boolean getAvailableTreesTest(){
        try {

            List<String> trees=TaskClientNetDriver.getAvailableTrees(TEST_USER, TEST_PASSWORD);
            betweenTestTreeNames=trees;
        } catch (IOException|NetworkInteractionException e) {
            return false;
        }
        return true;
    }

    private static boolean loadTreeTest(){
        try {

            TaskTree tree=TaskClientNetDriver.loadTree(TEST_USER, TEST_PASSWORD, TEST_TREE_NAME);
            if (!tree.getData().getActivityName().equals(TEST_TREE_NAME)) return false;
        } catch (IOException|NetworkInteractionException e) {
            return false;
        }
        return true;
    }

    private static boolean saveTreeTest(){
        try {
            IDGenerator idGenerator = new IDGenerator();
            Data data = new Data("TestString");
            data.setActivityName(TEST_TREE_NAME);
            TaskTree tree = new TaskTree(idGenerator, data);
            TaskClientNetDriver.saveTree(tree, TEST_USER, TEST_PASSWORD, TEST_TREE_NAME);
        } catch (IOException|NetworkInteractionException e) {
            return false;
        }
        return true;
    }

    private static boolean createUserTest(){
        try {

            TaskTree tree= TaskClientNetDriver.createUser(TEST_USER, TEST_PASSWORD);

        } catch (IOException|NetworkInteractionException e) {
            return false;
        }
        return true;
    }

    private static boolean deleteUserTest(){
        try {

            TaskClientNetDriver.deleteUser(TEST_USER, TEST_PASSWORD);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        catch (NetworkInteractionException e) {
            e.printStackTrace();
            System.err.println(e.getReply());
            return false;
        }
        return true;
    }

    private static boolean deleteTreeTest(){
        try {

            TaskClientNetDriver.deleteTree(TEST_USER, TEST_PASSWORD, TEST_TREE_NAME);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        catch (NetworkInteractionException e) {
            e.printStackTrace();
            System.err.println(e.getReply());
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
