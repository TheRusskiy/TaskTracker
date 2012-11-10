/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author TheRusskiy
 */
public class IDNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>IDNotFoundException</code> without detail message.
     */
    public IDNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>IDNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IDNotFoundException(String msg) {
        super(msg);
    }
}
