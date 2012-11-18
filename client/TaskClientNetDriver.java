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

    public static void saveTree(TaskTree tree) {
        //TODO exceptions to "outside"
        NetworkInteraction interaction = new NetworkInteraction();
        interaction.setTree(tree);
        Socket socket=null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(interaction);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            NetworkInteraction readInteraction = (NetworkInteraction) in.readObject();
            //todo analyze readInteraction
        } catch (Exception e) {
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
    }

    public static void saveTree(TaskTree tree, String login, String password) {
        //todo
    }

    public static TaskTree loadTree() {
        //TODO exceptions to "outside"
        NetworkInteraction interaction = new NetworkInteraction();
        TaskTree tree=null;
        Socket socket=null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(interaction);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            NetworkInteraction readInteraction = (NetworkInteraction) in.readObject();
            tree=readInteraction.getTree();
            //todo analyze readInteraction
        } catch (Exception e) {
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
        return tree;
    }

    public static TaskTree loadTree(String login, String password) {
        //todo
        return null;
    }
}
