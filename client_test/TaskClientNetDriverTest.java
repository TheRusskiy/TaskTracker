import exceptions.NoParentTreeException;
import tree_content.Data;
import tree_content.IDGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class TaskClientNetDriverTest {
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

    public static void main(String[] args)
    {
        TaskClientNetDriverTest.testAll();
    }
}
