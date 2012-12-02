package exceptions;

import task_network.NetworkInteraction;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 2:07
 * To change this template use File | Settings | File Templates.
 */
public class WrongInteractionDataException extends Exception {
    NetworkInteraction.ReplyCode replyCode;

    public NetworkInteraction.ReplyCode getReplyCode() {
        return replyCode;
    }

    public WrongInteractionDataException() {
        super();
    }

    public WrongInteractionDataException(NetworkInteraction.ReplyCode replyCode) {
        this.replyCode=replyCode;
    }

    public WrongInteractionDataException(String message) {
        super(message);
    }

    public WrongInteractionDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongInteractionDataException(Throwable cause) {
        super(cause);
    }

    protected WrongInteractionDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
