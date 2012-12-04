/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 18.11.12
 * Time: 6:25
 * To change this template use File | Settings | File Templates.
 */
public class TestException extends RuntimeException {
    public TestException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TestException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TestException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
