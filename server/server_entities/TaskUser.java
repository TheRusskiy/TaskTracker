package server_entities;

import exceptions.WrongInteractionDataException;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 25.11.12
 * Time: 3:19
 * To change this template use File | Settings | File Templates.
 */
public class TaskUser implements Serializable {
    private String password;
    private String login;

    private LinkedList<String> treeNames;

    /**
     * @return names of all trees this user has.
     */
    public LinkedList<String> getTreeNames() {
        return treeNames;
    }

    public TaskUser() {
        treeNames = new LinkedList<String>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void addTreeName(String activityName) throws WrongInteractionDataException {
        if (treeNames.contains(activityName)) throw new WrongInteractionDataException("Tree with this name already exists!");
        treeNames.add(activityName);
    }

    public void ensureTreeExists(String activityName) throws WrongInteractionDataException {
        if (!treeNames.contains(activityName)) throw new WrongInteractionDataException("Tree with this name does not exist!");
    }

    public boolean equals(Object taskUser){
         if (taskUser==null) return false;
         if (!( taskUser instanceof TaskUser)) return false;
         return this.getLogin().equals(((TaskUser)taskUser).getLogin());
    }
}
