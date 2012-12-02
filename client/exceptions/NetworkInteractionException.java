package exceptions;

import task_network.NetworkInteraction.ReplyCode;

/**
 *
 * @author TheRusskiy
 */
public class NetworkInteractionException extends Exception {
    private ReplyCode reply;

    public NetworkInteractionException() {
    }
    public NetworkInteractionException(ReplyCode reply) {
        this.reply=reply;
    }

    public ReplyCode getReply() {
        return reply;
    }

    public NetworkInteractionException(String msg) {
        super(msg);
    }
}
