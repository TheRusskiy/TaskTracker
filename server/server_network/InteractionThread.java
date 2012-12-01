package server_network;

import exceptions.NetworkInteractionIsNotReadyException;
import task_network.NetworkInteraction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 1:31
 * To change this template use File | Settings | File Templates.
 */
public class InteractionThread extends Thread {
    //TODO timeout!!!(everywhere)
    private volatile boolean isDone=false;
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
            NetworkInteraction temp=TaskServer.processInput(interaction);
            out.writeObject(temp);
            out.flush();
            isDone=true;
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out!=null) out.close();
                if (in!=null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
