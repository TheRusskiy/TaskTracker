import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 0:24
 * To change this template use File | Settings | File Templates.
 */
public class TaskClientNetDriver {
    public static int SERVER_PORT = 6969;
    public static InetAddress SERVER_ADDRESS;
    //TODO ^^^values^^^ from file!!



    public static boolean saveTree(TaskTree tree, String login, String password, String treeName) throws IOException {
        NetworkInteraction interaction = new NetworkInteraction();
        interaction.setLogin(login);
        interaction.setPassword(password);
        interaction.setTree(tree);
        interaction.setRequestCode(NetworkInteraction.RequestCode.SAVE_TO_SERVER);
        interaction.setTreeName(treeName);
        NetworkInteraction result=sendReceive(interaction);
        return result.getReplyCode()== NetworkInteraction.ReplyCode.SUCCESS;
        //todo check if result is "successful", if not throw new Exception
    }

    public static TaskTree loadTree(String login, String password, String treeName) throws IOException {
        NetworkInteraction interaction = new NetworkInteraction();
        interaction.setLogin(login);
        interaction.setPassword(password);
        interaction.setRequestCode(NetworkInteraction.RequestCode.LOAD_FROM_SERVER);
        //interaction.setTreeName(treeName);
        TaskTree tree=sendReceive(interaction).getTree();
        return tree;
    }

    public static TaskTree createUser(String login, String password) throws IOException {
        NetworkInteraction interaction = new NetworkInteraction();
        interaction.setLogin(login);
        interaction.setPassword(password);
        interaction.setRequestCode(NetworkInteraction.RequestCode.CREATE_NEW_USER);
        TaskTree tree=sendReceive(interaction).getTree();
        return tree;
    }

    public static List<String> getAvailableTrees(String login, String password) throws IOException {
        NetworkInteraction interaction = new NetworkInteraction();
        interaction.setLogin(login);
        interaction.setPassword(password);
        interaction.setRequestCode(NetworkInteraction.RequestCode.GET_AVAILABLE_TREES);
        return sendReceive(interaction).getTreeNames();
    }


    private static NetworkInteraction sendReceive(NetworkInteraction interaction) throws IOException {
        TaskTree tree=null;
        Socket socket=null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        NetworkInteraction readInteraction=null;
        try {
            SERVER_ADDRESS=InetAddress.getLocalHost();
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(interaction);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            readInteraction = (NetworkInteraction) in.readObject();
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket!=null) socket.close();
                if (in!=null) in.close();
                if (out!=null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return readInteraction;
    }

    //for test
    public static TaskTree loadTree() throws IOException {
        return loadTree("foo", "bar", "");
    }
    public static void saveTree(TaskTree tree) throws IOException {
        saveTree(tree, "foo", "bar", "");
    }
}
