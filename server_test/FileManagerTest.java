import server_persistence.FileManager;

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
        if (!deleteFileTest())
        {
            System.out.println("Delete file test fails");
            result=false;
        }
        return result;
    }

    private static boolean fileExistsTest(){
        File file = new File("fileManagerTestFile");
        file.deleteOnExit();
        try {
            FileManager.saveToFile(file, new Integer(0));
        } catch (IOException e) {
            return false;
        }
        if (!FileManager.fileExists("fileManagerTestFile")) return false;
        return true;
    }

    private static boolean deleteFileTest(){
        File file = new File("fileManagerTestFile");
        //file.deleteOnExit();
        try {
            FileManager.saveToFile(file, new Integer(0));
            FileManager.saveToFile("file_test_folder", "file_test_file", new Integer(0));

            FileManager.deleteFile(file);
            FileManager.deleteFile("file_test_folder", "file_test_file");
            FileManager.deleteFile(new File("file_test_folder"));

            if (FileManager.fileExists(file.getName())) return false;
            File file2=new File("file_test_folder", "file_test_file");
            if (FileManager.fileExists(file2.getName())) return false;
        } catch (IOException e) {
            return false;
        }
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
