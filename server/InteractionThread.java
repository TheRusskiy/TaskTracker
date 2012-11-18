import exceptions.NetworkInteractionIsNotReadyException;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 1:31
 * To change this template use File | Settings | File Templates.
 */
public class InteractionThread extends Thread {
    //TODO timeout!!!(everywhere)
    //TODO close all sockets!
    private boolean isDone=false;
    private NetworkInteraction interaction = null;
    private Socket client =null;

    public boolean isDone() {
        return isDone;
    }

    public InteractionThread(Socket client) {
        this.client=client;
    }

    public NetworkInteraction getNetworkInteraction() {
        if(!isDone) throw new NetworkInteractionIsNotReadyException();
        return interaction;
    }

    public void run(){
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            interaction=(NetworkInteraction) in.readObject();
            out.writeObject(TaskServer.createResponse(interaction));
            out.flush();
            isDone=true;
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (out!=null) out.close();
                if (in!=null) in.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}
