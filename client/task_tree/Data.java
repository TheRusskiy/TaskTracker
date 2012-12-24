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
    private int timeSpent = 0;
    //TODO spent time field

    public void increaseTimeBySeconds(int seconds){
        timeSpent+=seconds;
    }

    public int getSpentTime(){
        return timeSpent;
    }

    public String toString(){
        if (activityName!=null)return activityName+": "+timeSpent+"s";
        return text;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * Constructor without params (C) your Cap
     */
    public Data()
    {}

    /**
     * Constructor that takes String
     * (FOR TEST PURPOSES or just 4 fun)
     */
    public Data(String testText)
    {
        this.text=testText;
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
        return this.getActivityName().equals(data.getActivityName());
    }
}
