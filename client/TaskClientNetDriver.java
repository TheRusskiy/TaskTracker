import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 0:24
 * To change this template use File | Settings | File Templates.
 */
public class TaskClientNetDriver {
    public static int SERVER_PORT = 6969;
    public static InetAddress SERVER_ADDRESS = null;
    //TODO ^^^values^^^ from file!!

    public static void saveTree(TaskTree tree) throws IOException {
        NetworkInteraction interaction = new NetworkInteraction();
        interaction.setTree(tree);
        NetworkInteraction result=sendReceive(interaction);
    }

    public static void saveTree(TaskTree tree, String login, String password) {
        //todo

    }

    public static TaskTree loadTree() throws IOException {
        NetworkInteraction interaction = new NetworkInteraction();
        TaskTree tree=sendReceive(interaction).getTree();
        return tree;
    }

    public static TaskTree loadTree(String login, String password) {
        //todo
        return null;
    }

    private static NetworkInteraction sendReceive(NetworkInteraction interaction) throws IOException {
        TaskTree tree=null;
        Socket socket=null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        NetworkInteraction readInteraction=null;
        try {
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
}
