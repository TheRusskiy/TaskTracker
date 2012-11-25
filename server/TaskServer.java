import exceptions.WrongInteractionDataException;
import tree_content.Data;
import tree_content.IDGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentLinkedDeque;
//Feature multiuser SET check
//Feature check that user name != key file name
//FIXME check all directories, if absent => create
//FIXME alter network communication to contain single integer constant
/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class TaskServer {
    public static final int PORT = 6969;
    public static final String firstTreeName="Rest";
    private static ConcurrentLinkedDeque<TaskUser> users;
    private boolean working=true;

    public static void main(String[] args)
    {
        TaskServer taskServer= new TaskServer();
        taskServer.startServer();
    }

    public void stopServer(){
        working=false;
        FileManager.saveUsersToFile(users);
    }


    public void startServer(){
        ServerSocket server=null;
        try {
            users =FileManager.getUsersFromFile();
            server = new ServerSocket(PORT);
            while(working){
                InteractionThread interactionThread = new InteractionThread(server.accept());
                interactionThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (server!=null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileManager.saveUsersToFile(users);
        }
    }

    private static void insertNewUser(TaskUser user){
        users.add(user);
    }

    public static NetworkInteraction processInput(NetworkInteraction interaction) {
        //NetworkInteraction result = new NetworkInteraction();
        try {
        while (true)
                    {
                    if (interaction.isCreateNewUser()) {
                        if (userExists(interaction.getLogin()))
                            throw new WrongInteractionDataException("User already exist!");
                        TaskTree newTree = createNewTree();
                        TaskUser newUser = createNewUser(interaction.getLogin(), interaction.getPassword(), newTree);
                        insertNewUser(newUser);
                        FileManager.saveUsersToFile(users);
                        interaction.setTree(newTree);
                        interaction.setCool();
                        break;
                    }
                    {
                        if (!userExists(interaction.getLogin()))
                            throw new WrongInteractionDataException("User does not exist!");
                        if (!correctCredentials(interaction.getLogin(), interaction.getPassword()))
                            throw new WrongInteractionDataException("Wrong password!");
                        if (interaction.isGetAvailableTrees())
                        {
                            TaskUser user =getUserByLogin(interaction.getLogin());
                            interaction.setTreeNames(user.getTreeNames());
                            interaction.setCool();
                            break;// selection;
                        }
                        if (interaction.isLoadFromServer())
                        {
                            TaskTree tree = openTree(interaction.getLogin(), interaction.getTreeName());
                            interaction.setTree(tree);
                            interaction.setCool();
                            break;// selection;
                        }
                        if (interaction.isSaveToServer())
                        {
                            saveTree(interaction.getLogin(), interaction.getTree(), interaction.getTreeName());
                            interaction.setCool();
                            break;// selection;
                        }
                    }
                  }
        } catch (WrongInteractionDataException | IOException e) {
            interaction.setNotCool();
            if (e instanceof IOException) {
                interaction.setText("Server File System Error...");
            } else {
                interaction.setText(e.getMessage());
            }
            FileManager.placeToLog(e.getMessage());
        }
        return interaction;
    }

    private static void saveTree(String login, TaskTree tree, String treeName) throws IOException {
        FileManager.saveToFile(login, treeName, tree);
    }

    private static TaskTree openTree(String login, String treeName) throws IOException {
        return FileManager.loadFromFile(login, treeName);
    }

    private static boolean correctCredentials(String login, String password) {
        Boolean validLogin=false;
        TaskUser user=null;
        for (TaskUser user_iter : users){
            if (user_iter.getLogin().equals(login)){
                validLogin=true;
                user=user_iter;
                break;
            }
        }
        if (validLogin&&user.getPassword().equals(password)) return true;
        return false;
    }

    private static boolean userExists(String login) {
        for (TaskUser o : users){
            if (o.getLogin().equals(login)) return true;
        }
        return false;
    }

    private static TaskTree createNewTree() {
        IDGenerator idGenerator = new IDGenerator();
        Data data = new Data("New tree");
        data.setActivityName(firstTreeName);
        TaskTree tree = new TaskTree(idGenerator, data);
        return tree;
    }

    private static TaskUser createNewUser(String login, String password, TaskTree tree) throws WrongInteractionDataException, IOException {
//        if (FileManager.fileExists(login)){
//            FileManager.placeToLog("User with this name already exists: "+login);
//            throw new WrongInteractionDataException("User with this name already exists: "+login);
//        }
        FileManager.saveToFile(login, firstTreeName, tree);
        TaskUser user=new TaskUser();
        user.setLogin(login);
        user.setPassword(password);
        user.addTreeName(tree.getData().getActivityName());
        return user;
    }

    private static TaskUser getUserByLogin(String login) throws WrongInteractionDataException {
        for (TaskUser o : users){
            if (o.getLogin().equals(login)) return o;
        }
        throw new WrongInteractionDataException("No such user exist!");
    }
}
