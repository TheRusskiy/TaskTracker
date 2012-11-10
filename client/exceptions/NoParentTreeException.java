/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author TheRusskiy
 */
public class NoParentTreeException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>NoParentTreeException</code> without detail message.
     */
    public NoParentTreeException() {
    }

    /**
     * Constructs an instance of
     * <code>NoParentTreeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoParentTreeException(String msg) {
        super(msg);
    }
}
