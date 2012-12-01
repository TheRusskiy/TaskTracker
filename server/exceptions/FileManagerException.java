package exceptions;

public class FileManagerException extends Exception {
    public FileManagerException() {
        super();
    }

    public FileManagerException(String message) {
        super(message);
    }

    public FileManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileManagerException(Throwable cause) {
        super(cause);
    }

    protected FileManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
