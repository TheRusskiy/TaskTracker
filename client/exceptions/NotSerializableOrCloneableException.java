/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author TheRusskiy
 */
public class NotSerializableOrCloneableException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotSerializableOrCloneableException</code> without detail message.
     */
    public NotSerializableOrCloneableException() {
    }

    /**
     * Constructs an instance of
     * <code>NotSerializableOrCloneableException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NotSerializableOrCloneableException(String msg) {
        super(msg);
    }
}
