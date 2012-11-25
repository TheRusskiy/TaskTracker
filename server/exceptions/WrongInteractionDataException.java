package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 2:07
 * To change this template use File | Settings | File Templates.
 */
public class WrongInteractionDataException extends Exception {
    public WrongInteractionDataException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WrongInteractionDataException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WrongInteractionDataException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WrongInteractionDataException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected WrongInteractionDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
