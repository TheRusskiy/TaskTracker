import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 25.11.12
 * Time: 4:07
 * To change this template use File | Settings | File Templates.
 */
public class FileManager {
    private static String logFileName="server_log.txt";
    private static String usersFileName="users.txt";
    private static String storeFolder="server_data";



    public static ConcurrentLinkedDeque<TaskUser> getUsersFromFile() {
        try {
            ConcurrentLinkedDeque<TaskUser> tempUsers=loadFromFile(new File(usersFileName));
            if (tempUsers==null){
                placeToLog("Users file was empty, creating new one");
                tempUsers=new ConcurrentLinkedDeque<TaskUser>();
            }
            return tempUsers;
        } catch (IOException e) {
            e.printStackTrace();
            placeToLog(e.getMessage());
        }
        throw new RuntimeException("Can't load users file!");
    }

    public static void saveUsersToFile(ConcurrentLinkedDeque<TaskUser> usersMap) {
        try {
            saveToFile(new File(usersFileName), usersMap);
        } catch (IOException e) {
            e.printStackTrace();
            placeToLog(e.getMessage());
        }
    }

    public static <ObjectType> ObjectType loadFromFile(String folder, String file) throws IOException {
        File userFolder=new File(System.getProperty("user.dir"), folder);
        return loadFromFile(new File(userFolder, file));
    }
    public static <ObjectType> ObjectType loadFromFile(File file) throws IOException {
        file=new File(storeFolder, file.getPath());
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileInputStream fileInputStream=null;
        ObjectInputStream ois=null;
        try{
            ois = new ObjectInputStream(new FileInputStream(file));
            return (ObjectType)ois.readObject();
        }
        catch (IOException e){
            placeToLog(e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            placeToLog("File type not found!");
        }
        finally {
            if (fileInputStream!=null) fileInputStream.close();
            if (ois!=null) ois.close();
        }
        throw new IOException("Error while writing to \""+file.getPath()+"\"");
    }

    public static <ObjectType> void saveToFile(String folder, String file, ObjectType objectToSave) throws IOException {
        //File userFolder=new File(System.getProperty("user.dir"), storeFolder);
        //userFolder=new File(userFolder, folder);
        saveToFile(new File(folder, file), objectToSave);
    }
    public static <ObjectType> void saveToFile(File file, ObjectType objectToSave) throws IOException {
        file=new File((new File(System.getProperty("user.dir"), storeFolder)),file.getPath());
        file.getParentFile().mkdirs();
        file.createNewFile();
        FileInputStream fileOutputStream=null;
        ObjectOutputStream oos=null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(objectToSave);
        }
        catch (IOException e){
            placeToLog(e.getMessage() + "; Error while writing to \"" + file.getPath() + "\"");
            throw e;
        }
        finally {
            if (fileOutputStream!=null) fileOutputStream.close();
            if (oos!=null) oos.close();
        }
    }

    public static void placeToLog(String message) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(storeFolder+File.separator+logFileName, true)));
            out.println(dateFormat.format(date)+": "+message);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileExists(String fileName){
        File currDir = new File(System.getProperty("user.dir"));
        currDir=new File(currDir.getPath(), storeFolder);
        String[] currentDirectoryFiles=currDir.list();
        for (int i=0; i<currentDirectoryFiles.length; i++){
            if (fileName.equals(currentDirectoryFiles[i])) return true;
        }
        return false;
    }
}
