package server_network;

import exceptions.FileManagerException;
import exceptions.WrongInteractionDataException;
import server_entities.TaskUser;
import server_persistence.FileManager;
import task_network.NetworkInteraction;
import task_tree.Data;
import task_tree.IDGenerator;
import task_tree.TaskTree;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
//Feature multiuser SET check
//Feature check that user name != key file name
//TODO "update" tree method
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
    public static final int TIMEOUT_DELAY = 2000;


    public static void main(String[] args)
    {
        TaskServer taskServer= new TaskServer();
        taskServer.startServer();
    }

    /**
     * Stop server
     * server will stop accepting new connections
     * after TIMEOUT_DELAY expires
     * and save users to HDD
     */
    public void stopServer(){
        working=false;
        FileManager.saveUsersToFile(users);
    }


    /**
     * Start server, wait in loop for connections, create new thread for each connected client
     */
    public void startServer(){
        ServerSocket server=null;
        try {
            try{
                fetchUsers();
            }
            catch (FileManagerException e){
                FileManager.placeToLog(e.getMessage());
                users=new ConcurrentLinkedDeque<>();
            }
            server = new ServerSocket(PORT);
            server.setSoTimeout(TIMEOUT_DELAY);
            while(working){
                try{
                InteractionThread interactionThread = new InteractionThread(server.accept());
                interactionThread.start();
                }
                catch (SocketTimeoutException e) {
                    System.out.println("Timeout...");
                }
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

    /**
     * Initialize users list
     * @throws FileManagerException
     */
    public static void fetchUsers() throws FileManagerException {
        users =FileManager.getUsersFromFile();
    }

    /**
     * Add new user to list of users
     */
    private static void insertNewUser(TaskUser user){
        users.add(user);
    }

    /**
     * Processes information about incoming request
     * and creates response complying to interaction protocol.
     * Places all information in logs.
     */
    public static NetworkInteraction processInput(NetworkInteraction interaction) {
        //task_network.NetworkInteraction result = new task_network.NetworkInteraction();
        try {
            if (interaction.getRequestCode()!= NetworkInteraction.RequestCode.CREATE_NEW_USER){
                if(!correctCredentials(interaction.getLogin(), interaction.getPassword())){
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.WRONG_CREDENTIALS.name()+": "
                            +interaction.getLogin()+", "
                            +interaction.getPassword());
                    throw new WrongInteractionDataException(NetworkInteraction.ReplyCode.WRONG_CREDENTIALS);
                }
            }
            switch (interaction.getRequestCode()){
                case CREATE_NEW_USER:{
                    if (userExists(interaction.getLogin())) {
                        interaction.setReplyCode(NetworkInteraction.ReplyCode.USER_ALREADY_EXISTS);
                        FileManager.placeToLog(
                                NetworkInteraction.ReplyCode.USER_ALREADY_EXISTS.name()+":"
                                +interaction.getLogin());
                        break;
                    }
                    TaskTree newTree = createNewTree();
                    TaskUser newUser = createNewUser(interaction.getLogin(), interaction.getPassword(), newTree);
                    insertNewUser(newUser);
                    FileManager.saveUsersToFile(users);
                    interaction.setTree(newTree);
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS+": "
                            + NetworkInteraction.RequestCode.CREATE_NEW_USER+": "
                            +interaction.getLogin());
                    break;
                }
                case GET_AVAILABLE_TREES:{
                    TaskUser user =getUserByLogin(interaction.getLogin());
                    interaction.setTreeNames(user.getTreeNames());
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS.name()+": "
                            + NetworkInteraction.RequestCode.GET_AVAILABLE_TREES+": "
                            + interaction.getLogin());
                    break;
                }
                case LOAD_FROM_SERVER:{
                    TaskTree tree = openTree(interaction.getLogin(), interaction.getTreeName());
                    interaction.setTree(tree);
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS.name()+": "
                            + NetworkInteraction.RequestCode.LOAD_FROM_SERVER+": "
                            + interaction.getLogin()+": "
                            + interaction.getTreeName());
                    break;
                }
                case SAVE_TO_SERVER:{
                    TaskUser user =getUserByLogin(interaction.getLogin());
                    user.addTreeName(interaction.getTreeName());
                    saveTree(interaction.getLogin(), interaction.getTree(), interaction.getTreeName());
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS.name()+": "
                            + NetworkInteraction.RequestCode.SAVE_TO_SERVER+": "
                            + interaction.getLogin()+": "
                            + interaction.getTreeName());
                    break;
                }
                case UPDATE_TREE:{
                    TaskUser user =getUserByLogin(interaction.getLogin());
                    user.ensureTreeExists(interaction.getTreeName());
                    updateTree(interaction.getLogin(), interaction.getTree(), interaction.getTreeName());
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS.name()+": "
                            + NetworkInteraction.RequestCode.UPDATE_TREE+": "
                            + interaction.getLogin()+": "
                            + interaction.getTreeName());
                    break;
                }
                case DELETE_USER:{
                    TaskUser user =getUserByLogin(interaction.getLogin());
                    users.removeFirstOccurrence(user);
                    List<String> treesToDelete=user.getTreeNames();
                    for(String treeToDelete:treesToDelete){
                        FileManager.deleteFile(user.getLogin(),treeToDelete);
                    }
                    FileManager.deleteFile(user.getLogin());
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS.name()+": "
                            + NetworkInteraction.RequestCode.DELETE_USER+": "
                            + interaction.getLogin());
                    break;
                }
                case DELETE_TREE:{
                    TaskUser user =getUserByLogin(interaction.getLogin());
                    if (!user.getTreeNames().contains(interaction.getTreeName())){
                        interaction.setReplyCode(NetworkInteraction.ReplyCode.TREE_DOES_NOT_EXIST);
                        FileManager.placeToLog(NetworkInteraction.ReplyCode.TREE_DOES_NOT_EXIST.name()+":"+interaction.getTree());
                        break;
                    }
                    user.getTreeNames().removeFirstOccurrence(interaction.getTreeName());
                    FileManager.deleteFile(user.getLogin(), interaction.getTreeName());
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.SUCCESS);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.SUCCESS.name()+": "
                            + NetworkInteraction.RequestCode.DELETE_TREE+": "
                            + interaction.getLogin()+": "
                            + interaction.getTreeName());
                    break;
                }
                default:{
                    interaction.setReplyCode(NetworkInteraction.ReplyCode.UNKNOWN_REQUEST_CODE);
                    FileManager.placeToLog(NetworkInteraction.ReplyCode.UNKNOWN_REQUEST_CODE.name()+ ":"
                            + interaction.getRequestCode().name());
                    break;
                }
            }
        } catch (WrongInteractionDataException e) {
            if (e.getReplyCode()==null){
            interaction.setReplyCode(NetworkInteraction.ReplyCode.ERROR);
            interaction.setText(e.getMessage());
            FileManager.placeToLog(e.getMessage()); }
        }
        catch (IOException e) {
            interaction.setReplyCode(NetworkInteraction.ReplyCode.ERROR);
            interaction.setText("Server File System Error...");
            FileManager.placeToLog(e.getMessage());
        }
        return interaction;
    }

    /**
     * Save tree to HDD
     * @param login user login, corresponds to directory name
     * @param tree which will be saved
     * @param treeName name of the tree, corresponds to file name
     */
    private static void saveTree(String login, TaskTree tree, String treeName) throws IOException {
        FileManager.saveToFile(login, treeName, tree);
    }

    /**
     * Replace old tree on HDD
     * @param login user login, corresponds to directory name
     * @param tree which will be updated
     * @param treeName name of the tree, corresponds to file name
     */
    private static void updateTree(String login, TaskTree tree, String treeName) throws IOException {
        FileManager.saveToFile(login, treeName, tree);
    }

    /**
     * Open tree from HDD
     * @param login user login, corresponds to directory name
     * @param treeName name of the tree, corresponds to file name
     */
    private static TaskTree openTree(String login, String treeName) throws IOException {
        return FileManager.loadFromFile(login, treeName);
    }

    /**
     * Check user with specified login exists and has specified password
     */
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

    /**
     * Check if user exists
     */
    private static boolean userExists(String login) {
        for (TaskUser o : users){
            if (o.getLogin().equals(login)) return true;
        }
        return false;
    }

    /**
     * Creates new tree with default values.
     * Responsible for instantiating tree object for newly created user
     * @return
     */
    private static TaskTree createNewTree() {
        IDGenerator idGenerator = new IDGenerator();
        Data data = new Data("New tree");
        data.setActivityName(firstTreeName);
        TaskTree tree = new TaskTree(idGenerator, data);
        return tree;
    }

    /**
     * Create new TaskUser and corresponding folder on HDD
     * @param login login of a new user
     * @param password password of a new user
     * @param tree first tree, which will be linked with this user
     * @return TaskUser object
     */
    private static TaskUser createNewUser(String login, String password, TaskTree tree) throws WrongInteractionDataException, IOException {
//        if (server_persistence.FileManager.fileExists(login)){
//            server_persistence.FileManager.placeToLog("User with this name already exists: "+login);
//            throw new WrongInteractionDataException("User with this name already exists: "+login);
//        }
        FileManager.saveToFile(login, firstTreeName, tree);
        TaskUser user=new TaskUser();
        user.setLogin(login);
        user.setPassword(password);
        user.addTreeName(tree.getData().getActivityName());
        return user;
    }

    /**
     * Returns a TaskUser object by specified login
     */
    private static TaskUser getUserByLogin(String login) throws WrongInteractionDataException {
        for (TaskUser o : users){
            if (o.getLogin().equals(login)) return o;
        }
        FileManager.placeToLog(NetworkInteraction.ReplyCode.USER_DOES_NOT_EXIST.name()+":"+login);
        throw new WrongInteractionDataException(NetworkInteraction.ReplyCode.USER_DOES_NOT_EXIST);
    }
}
