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



public class ID implements Serializable, Comparable{
    /**
     * value that defines "identity" of this ID
     */
    private long value;
    
    
    /**
     * Create new ID
     */
    public ID()
    {
        value=0;
    }
    
    
    /**
     * Create new ID with specified identity
     * @param value parameter of type Long
     */
    private ID(long value)
    {
        this.value=value;
    }
    
    
    /**
     * @return value that represents identity
     */
    private long getValue()
    {
        return value;
    }
    
    
    /**
     * @param id ID with which current ID should be compared
     * @return true if IDs are equal
     */
    public boolean equals(ID id)
    {
        return (this.getValue()==id.getValue());
    }
    
    
    /**
     * @return new unique ID that follows this one
     */
    public ID nextValue()
    {
        return new ID(value+1);
    }

    @Override
    public int compareTo(Object o) {
        ID toID = (ID) o;
        if (toID.getValue()>this.getValue()) return -1;
        if (toID.getValue()<this.getValue()) return 1;
        return 0;
    }
}
