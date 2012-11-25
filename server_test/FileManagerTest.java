import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 25.11.12
 * Time: 4:17
 * To change this template use File | Settings | File Templates.
 */
public class FileManagerTest {
    public static boolean testAll()
    {
        boolean result=true;
        if (!fileExistsTest())
        {
            System.out.println("File exists test fails");
            result=false;
        }
        return result;
    }

    private static boolean fileExistsTest(){
        File file = new File(System.getProperty("user.dir")+"qwertyuioopasddfghjk");
        try {
            file.createNewFile();
        } catch (IOException e) {
            return false;
        }
        if (!FileManager.fileExists("qwertyuioopasddfghjk")) return false;
        return true;
    }

    public static void startTests(){
        System.out.println("File manager test: " + FileManagerTest.testAll());
    }

    public static void main(String[] args)
    {
        startTests();
    }
}
