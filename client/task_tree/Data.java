/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package task_tree;

import java.io.Serializable;

/**
 *
 * @author TheRusskiy
 */
public class Data implements Serializable{
    //Data fields:
    private String text;
    private String activityName;
    //TODO spent time field

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    
    /**
     * Constructor that takes String
     */
    public Data(String text)
    {
        this.text=text;
    }
    
    
    /**
     * Set text field
     */
    public String getText()
    {
        return text;
    }
    
    
    /**
     * Set text field
     */
    public void setText(String text)
    {
        this.text=text;
    }
    
    
    /**
     * Checks if data is equal
     */
    public boolean equals(Data data)
    {
        return this.getText().equals(data.getText());
    }
}
