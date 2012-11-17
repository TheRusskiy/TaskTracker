import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class TaskServer {
    public static final int PORT = 6969;

    public static void main(String[] args)
    {
        TaskServer taskServer= new TaskServer();
        taskServer.startServer();
    }

    public void startServer(){
        ServerSocket server;
        try {
            server = new ServerSocket(PORT);
            while(true){
                InteractionThread interactionThread = new InteractionThread(server.accept());
                interactionThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static NetworkInteraction createResponse(NetworkInteraction interaction) {
        //TODO implementation
        return interaction;
    }
}
