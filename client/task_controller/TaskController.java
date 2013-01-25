package task_controller;

import exceptions.ControllerException;
import exceptions.NetworkInteractionException;
import task_network.TaskClientNetDriver;
import task_tree.Data;
import task_tree.ID;
import task_tree.IDGenerator;
import task_tree.TaskTree;
import task_view.TaskView;

import java.io.IOException;
import java.util.LinkedList;
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
    private TaskView view;
    private List<ControllerTree> controllerTrees=new LinkedList<>();
    private String login=null;
    private String password=null;
    private TaskTree currentCategoryTree;
    TickThread tickThread=null;
    private static final int MAXIMUM_ACTIVITY_NAME_LENGTH = 14;

    public TaskController() {
        tickThread = new TickThread();
        tickThread.setDaemon(true);
        tickThread.start();
    }


    private class TickThread extends Thread{
        private TaskTree chosenTree = null;
        private int tickTimeInSeconds=5;

        public void setChosenTree(TaskTree chosenTree) {
            this.chosenTree = chosenTree;
        }

        @Override
        public void run() {
            while(true){
                try {
                    sleep(tickTimeInSeconds*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    assert false;
                }
                if (chosenTree!=null) increaseTreeTime(chosenTree, tickTimeInSeconds);
            }
        }
    }

    private void increaseTreeTime(TaskTree tree, int bySeconds){
        tree.getData().increaseTimeBySeconds(bySeconds);
        view.redrawTree(currentCategoryTree);
        if (tree.getTaskParent()!=null){
            increaseTreeTime(tree.getTaskParent(), bySeconds);
        }
    }

    private class ControllerTree{
        private TaskTree tree=null;
        private String name = null;
        private boolean isChanged = false;
        private boolean isInitialized = false;

        private ControllerTree(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public TaskTree getTree() {
            if (tree==null) throw new RuntimeException("Tree wasn't initialized");
            return tree;
        }

        public void setTree(TaskTree tree) {
            if (tree!=null) isInitialized=true;
            this.tree = tree;
        }

        public boolean isInitialized() {
            return isInitialized;
        }

        /*
       Feature for exit notifications
        */
        public boolean isChanged() {
            return isChanged;
        }

    }

    private ControllerTree getControllerTree(String name){
        for(ControllerTree currTree: controllerTrees){
            if (currTree.getName().equals(name)){
                return currTree;
            }
        }
        throw new RuntimeException("No such tree:"+name);
    }


    public void setView(TaskView view) {
        this.view = view;
    }

    public String getStatus() {
        return status;
    }

    public boolean wasSuccessful() {
        return operationState;
    }

    public void chooseTree(ID treeID) throws ControllerException {
        TaskTree tempTree = currentCategoryTree.find(treeID);
        if (tempTree==null) throw new RuntimeException("No such ID");
        tickThread.setChosenTree(tempTree);
        operationState=true;
        status="Tree "+tempTree.getData().getActivityName()+" was successfully chosen!";
    }

    public TaskTree newNode(ID parentTreeID, String activityName)throws ControllerException{
        if (activityName==null) {
            status="activity name==null";
            operationState=false;
            throw new ControllerException();
        }
        if (activityName.length()==0||activityName.length()>=MAXIMUM_ACTIVITY_NAME_LENGTH) {
            status="activity name is empty or it's length is greater than "+MAXIMUM_ACTIVITY_NAME_LENGTH;
            operationState=false;
            throw new ControllerException();
        }
        TaskTree parentTree = currentCategoryTree.find(parentTreeID);
        if (parentTree==null) {
            status="Tree with such ID wasn't found!";
            operationState=false;
            throw new ControllerException();
        }
        IDGenerator generator = currentCategoryTree.getIDGenerator();
        Data newData= new Data();
        newData.setActivityName(activityName);
        TaskTree newTree = new TaskTree(generator, newData);
        parentTree.add(newTree);
        operationState=true;
        status="New node: "+activityName+" was created successfully";
        return currentCategoryTree;
    }
    public TaskTree deleteNode(ID treeToDeleteID)throws ControllerException{
        // <OPTIONAL>
        TaskTree treeToDelete = currentCategoryTree.find(treeToDeleteID);
        if (treeToDelete==currentCategoryTree) {
            status="Can't delete root tree!";
            operationState=false;
            throw new ControllerException();
        }
        if (treeToDelete==null) {
            status="Tree with such ID wasn't found!";
            operationState=false;
            throw new ControllerException();
        }
        // </OPTIONAL>
        currentCategoryTree.delete(treeToDeleteID);
        operationState=true;
        status="Node: "+treeToDelete.getData().getActivityName()+" was deleted successfully";
        return currentCategoryTree;
    }

    public TaskTree splitNode(ID treeToSplitID)throws ControllerException{
        TaskTree treeToSplit = currentCategoryTree.find(treeToSplitID);
        if (treeToSplit==currentCategoryTree) {
            status="Can't split root tree!";
            operationState=false;
            throw new ControllerException();
        }
        if (treeToSplit==null) {
            status="Tree with such ID wasn't found!";
            operationState=false;
            throw new ControllerException();
        }
        if (treeToSplit.getChildCount()==0) {
            status="Tree doesn't have any children!";
            operationState=false;
            throw new ControllerException();
        }
        treeToSplit.split();
        operationState=true;
        status="Node: "+treeToSplit.getData().getActivityName()+" was split successfully";
        return currentCategoryTree;
    }

    public boolean createUser(String login, String password) throws ControllerException {
        try {
            TaskClientNetDriver.createUser(login, password);
            setCredentials(login, password);
            operationState=true;
            status="User "+login+" was successfully created!";
            return true;
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
            setCredentials(login, password);
            controllerTrees=new LinkedList<>();
            List<String> names=TaskClientNetDriver.getAvailableTrees(login, password);
            for(String name: names){
                controllerTrees.add(new ControllerTree(name));
            }
            operationState=true;
            status="Available trees were was successfully loaded!";
            return names;
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

    public List<String> getAvailableTrees() throws ControllerException {
        List<String> names = new LinkedList<>();
        for(ControllerTree controllerTree: controllerTrees){
            names.add(controllerTree.getName());
        }
        operationState=true;
        status="Available trees were was successfully loaded!";
        return names;
    }

    private void setCredentials(String login, String password) {
        this.login=login;
        this.password=password;
    }

    public TaskTree loadTree() throws ControllerException{
        return loadTree(currentCategoryTree.getData().getActivityName());
    }
    public TaskTree loadTree(String treeName) throws ControllerException {
        try {
            TaskTree result;
            ControllerTree treeToLoad = getControllerTree(treeName);
            if (treeToLoad.isInitialized()){
                result=treeToLoad.getTree();
            }
            else{
                treeToLoad.setTree(TaskClientNetDriver.loadTree(login, password, treeName));
                result=treeToLoad.getTree();
            }
            operationState=true;
            status="Tree "+treeName+" was successfully loaded!";
            currentCategoryTree=result;
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

    public void saveTrees() throws ControllerException{
        for(ControllerTree currTree:controllerTrees){
            if (currTree.isInitialized()){
                saveTree(currTree.getName());
            }
        }

    }

    public void createTree(String treeName) throws ControllerException {
        try {
            IDGenerator generator = new IDGenerator();
            Data data = new Data();
            data.setActivityName(treeName);
            TaskTree tree = new TaskTree(generator, data);
            TaskClientNetDriver.saveTree(tree, login, password, treeName);
            controllerTrees.add(new ControllerTree(treeName));
            operationState=true;
            status="Tree "+treeName+" was successfully created!";
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

    public void saveTree(String treeName) throws ControllerException {
        try {
            TaskTree tree;
            ControllerTree controllerTree = getControllerTree(treeName);
            if (controllerTree.isInitialized()){
                tree=controllerTree.getTree();
            }
            else{
                status="Tree wasn't loaded!";
                operationState=false;
                throw new ControllerException();
            }
            TaskClientNetDriver.updateTree(tree, login, password, treeName);
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

    public void deleteTree(String treeName) throws ControllerException {
        try {
            TaskClientNetDriver.deleteTree(login, password, treeName);
            controllerTrees.remove(getControllerTree(treeName));
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

    public static void main(String[] args){
        TaskController controller = new TaskController();
        TaskView view = new TaskView(controller);
    }


}
