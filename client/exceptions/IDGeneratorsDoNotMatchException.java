/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author TheRusskiy
 */
public class IDGeneratorsDoNotMatchException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>IDGeneratorsDoNotMatchException</code> without detail message.
     */
    public IDGeneratorsDoNotMatchException() {
    }

    /**
     * Constructs an instance of
     * <code>IDGeneratorsDoNotMatchException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public IDGeneratorsDoNotMatchException(String msg) {
        super(msg);
    }
}
