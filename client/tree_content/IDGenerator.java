/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree_content;

import java.io.Serializable;


/**
 *
 * @author TheRusskiy
 */

/**
 * 
 * @author TheRusskiy
 */
public class IDGenerator implements Serializable{
    private ID currentID;    
    
    
    /**
     * Creates new IDGenerator with "fresh" ID
     */
    public IDGenerator(){
        currentID=new ID();
    }
    
    
    /**
     * @return next ID
     */
    public ID nextID(){
        currentID=currentID.nextValue();
        return currentID;
    }   
    
    /**
     * @return look at what ID this generator points
     */
    public ID currentID(){
        return currentID;
    }
}
