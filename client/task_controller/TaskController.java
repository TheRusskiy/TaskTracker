package task_controller;

import exceptions.ControllerException;
import exceptions.NetworkInteractionException;
import task_network.TaskClientNetDriver;
import task_tree.TaskTree;
import task_view.TaskView;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class TaskController {
    private String status="";
    private boolean operationState=true;
    TaskView view;

    public void setView(TaskView view) {
        this.view = view;
    }

    public String getStatus() {
        return status;
    }

    public boolean isOperationState() {
        return operationState;
    }

    public TaskTree createUser(String login, String password) throws ControllerException {
        try {
            TaskTree result=TaskClientNetDriver.createUser(login, password);
            operationState=true;
            status="User "+login+" was successfully created!";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
        }
        throw new ControllerException();
    }


    public void deleteUser(String login, String password) throws ControllerException {
        try {
            TaskClientNetDriver.deleteUser(login, password);
            operationState=true;
            status="User "+login+" was successfully deleted!";
            return;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
            throw new ControllerException();
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
            throw new ControllerException();
        }
    }

    public List<String> getAvailableTrees(String login, String password) throws ControllerException {
        try {
            List<String> result=TaskClientNetDriver.getAvailableTrees(login, password);
            operationState=true;
            status="Available trees were was successfully loaded!";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
            throw new ControllerException();
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
            throw new ControllerException();
        }
    }

    public TaskTree loadTree(String login, String password, String treeName) throws ControllerException {
        try {
            TaskTree result=TaskClientNetDriver.loadTree(login, password, treeName);
            operationState=true;
            status="Tree "+treeName+" was successfully loaded!";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
            throw new ControllerException();
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
            throw new ControllerException();
        }
    }

    public void saveTree(String login, String password, TaskTree tree, String treeName) throws ControllerException {
        try {
            TaskClientNetDriver.saveTree(tree, login, password, treeName);
            operationState=true;
            status="Tree "+treeName+" was successfully saved!";
            return;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
            throw new ControllerException();
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
            throw new ControllerException();
        }
    }

    public void updateTree(String login, String password, TaskTree tree, String treeName) throws ControllerException {
        try {
            TaskClientNetDriver.updateTree(tree, login, password, treeName);
            operationState=true;
            status="Tree "+treeName+" was successfully updated!";
            return;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
            throw new ControllerException();
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
            throw new ControllerException();
        }
    }

    public void deleteTree(String login, String password, String treeName) throws ControllerException {
        try {
            TaskClientNetDriver.deleteTree(login, password, treeName);
            operationState=true;
            status="Tree "+treeName+" was successfully deleted!";
            return;
        } catch (IOException e) {
            e.printStackTrace();
            status="Network error!";
            operationState=false;
            throw new ControllerException();
        } catch (NetworkInteractionException e) {
            e.printStackTrace();
            status=e.getReply().toString();
            operationState=false;
            throw new ControllerException();
        }
    }


}
