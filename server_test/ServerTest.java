import server_network.InteractionThread;
import server_network.TaskServer;
import task_network.NetworkInteraction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class ServerTest {
    public static final int TEST_PORT = 6970;
    public static final int TEST_PORT2 = 6971;

    public static boolean testAll()
    {
        boolean result=true;
        if (!serverPortTest())
        {
            System.out.println("port test fails");
            result=false;
        }
        if (!testingSocketTest(ServerTest.TEST_PORT))
        {
            System.out.println("testing socket test fails");
            result=false;
        }
        if (!testingSocketTest(ServerTest.TEST_PORT2))
        {
            System.out.println("testing socket test fails");
            result=false;
        }
        if (!serverInteractionTest())
        {
            System.out.println("getting object test fails");
            result=false;
        }
        if (!interactionEqualityTest())
        {
            System.out.println("interaction equality test fails");
            result=false;
        }
        if (!processInputTest())
        {
            System.out.println("create response test fails");
            result=false;
        }
        return result;
    }

    public static boolean serverPortTest(){
        ServerSocket server=null;
        try {
            server = new ServerSocket(TaskServer.PORT);
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (server!=null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Cant' close server socket!");
            }
        }
        return true;
    }

    public static boolean testingSocketTest(int port){
        Socket socket=null;
        ServerSocket server=null;
        try {
            server = new ServerSocket(port);
            InetAddress address=InetAddress.getLocalHost();
            socket = new Socket(address, port);

        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (socket!=null) socket.close();
                if (server!=null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Cant' close testing socket socket!");
            }
        }
        return true;
    }

    public static boolean serverInteractionTest()
    {
        Socket outputSocket=null;
        Socket inputSocket=null;
        ServerSocket server = null;
        ObjectOutputStream ous=null;
        ObjectInputStream resultStream=null;
        try {
            server = new ServerSocket(ServerTest.TEST_PORT);
            InetAddress address=InetAddress.getLocalHost();
            outputSocket = new Socket(address, ServerTest.TEST_PORT);
            inputSocket=server.accept();
            TaskServer.fetchUsers();
            InteractionThread thread = new InteractionThread(inputSocket);
            thread.start();
            ous = new ObjectOutputStream(outputSocket.getOutputStream());
            NetworkInteraction networkInteraction= new NetworkInteraction("test string");
            networkInteraction.setLogin("login");
            networkInteraction.setPassword("password");
            ous.writeObject(networkInteraction);
            ous.flush();
            thread.join();
            while (!thread.isDone()){}
            NetworkInteraction readInteraction;
            if (!thread.isDone()) throw new Exception("Join finished but no 'IsDone'!");
            readInteraction=thread.getNetworkInteraction();
            if (readInteraction==null) throw new Exception("Null interaction received");
            if (!readInteraction.getText().equals("test string")) throw new Exception("Wrong text arrived");
            resultStream = new ObjectInputStream(outputSocket.getInputStream());
            NetworkInteraction resultInteraction=(NetworkInteraction) resultStream.readObject();
            if (!(TaskServer.processInput(readInteraction)).equals(resultInteraction)) return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputSocket!=null) outputSocket.close();
                if (server!=null) server.close();
                if (ous!=null) ous.close();
                if (resultStream!=null) resultStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean processInputTest(){
        //todo implementation
        return true;
    }

    public static boolean interactionEqualityTest(){
        //todo implementation
        return true;
    }


    public static void startTests(){
        System.out.println("Server test: " + ServerTest.testAll());
    }

    public static void main(String[] args)
    {
          startTests();
    }
}
