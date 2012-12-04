package task_tree; /**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import exceptions.IDGeneratorsDoNotMatchException;
import exceptions.IDNotFoundException;
import exceptions.NoParentTreeException;

import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.*;

/**
 *
 * @author TheRusskiy
 */
public class TaskTree implements Serializable, Cloneable, TreeNode{
    private IDGenerator idGenerator;
    private ID id;
    private Data data;
    private boolean isSorted; // for search optimization
    private List<TaskTree> children = new LinkedList();
    private TaskTree parent;
    public static final boolean ID_EQUALITY=true;

    public String toString(){
        return getData().toString();
    }

    /**
     * Returns the number of children TreeNodes the receiver contains.
     */
    @Override
    public int getChildCount() {
        return children.size();
    }

    /**
     * Returns the children of the receiver as an Enumeration.
     */
    @Override
    public Enumeration children() {
        return Collections.enumeration(children);
    }

    /**
     * Returns true if the receiver is a leaf.
     */
    @Override
    public boolean isLeaf() {
        return children.size()==0;
    }

    /**
     * Returns the index of node in the receivers children. If the receiver does not contain node, -1 will be returned.
     */
    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    /**
     * Returns the child TreeNode at index childIndex.
     */
    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    /**
     * Returns true if the receiver allows children.
     */
    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    /**
     * @return the parent TreeNode of the receiver.
     */
    public TreeNode getParent() {
        return parent;
    }


    //___________________________________________________________________________________________________

    /**
     * @return parent of this task_tree.TaskTree
     */
    private TaskTree getTaskParent() {
        return parent;
    }


    /**
     * @return IDGenerator of this task_tree.TaskTree
     */
        public IDGenerator getIDGenerator() {
        return idGenerator;
    }



    /**
     * @param generator new IDGenerator for this task_tree.TaskTree
     * @deprecated - BE CAREFUL, DO NOT USE, REALLY, NEVER!!!
     * task_tree.TaskTree consistency at danger!
     */
    @Deprecated
        private void setIDGenerator(IDGenerator generator) {
        idGenerator=generator;
    }



    /**
     * @param parent set parent to this task_tree.TaskTree
     */
        private void setParent(TaskTree parent) {
        this.parent=parent;
    }


    public TaskTree(IDGenerator generator, Data data)
    {
        this.idGenerator=generator;
        this.id=idGenerator.nextID();
        this.data=data;
    }


    /**
     * @return Iterator of encapsulated children list
     */
        public Iterator<TaskTree> getChildrenIterator() {
        return children.iterator();
    }


    /**
     * @return array of children
     */
        public TaskTree[] getChildren() {
        return children.toArray(new TaskTree[0]);
    }


    /**
     * @param data set data to this value
     */
        public void setData(Data data) {
        this.data=data;
    }



    /**
     * @return data that this task_tree.TaskTree contains
     */
        public Data getData() {
        return this.data;
    }


    /**
     * Adds new task_tree.TaskTree to the one with specified id.
     * Checks if new task_tree.TaskTree has the same IDGenerator,
     * if false, then change all IDs of a new task_tree.TaskTree according
     * to current IDGenerator.
     * @param id ID of a Node to which you want to attach new task_tree.TaskTree
     */
        public void add(ID id, TaskTree TaskTree) {
        find(id).add(TaskTree);
    }



    /**
     * Adds new task_tree.TaskTree, to the children of current task_tree.TaskTree.
     * Checks if new task_tree.TaskTree has the same IDGenerator,k
     * if false, then throws IDGeneratorsDoNotMatchException
     */
        public void add(TaskTree TaskTree) {
        //!!!Be careful when use,
        //Do not add TaskTrees which are already in this task_tree.TaskTree!
        //safety overhead is too big!
        if (this.idGenerator!=TaskTree.getIDGenerator())
        {
            System.err.println("Added task_tree.TaskTree has different IDGenerator");
            throw new IDGeneratorsDoNotMatchException("Added task_tree.TaskTree has different IDGenerator");
        }
        children.add(TaskTree);
        TaskTree.setParent(this);
    }



    /**
     * Append to parent's list of task_tree.TaskTree children
     * and delete all links that lead to itself
     */
        public void split(){
        if (parent==null) throw new NoParentTreeException();
        for(TaskTree t: children){
            parent.add(t);
            t.setParent(null);
        }
        parent.delete(this.id);
        this.parent=null;
    }


    /**
     * @param id
     * @return task_tree.TaskTree with specified ID
     */
        public TaskTree find(ID id) {
        if (this.id.equals(id)) return this;
        else if (children==null||children.isEmpty())
            return null;
        else
        {
            for(TaskTree t: children){
                TaskTree f=t.find(id);
                if (f!=null) return f;
            }
        }
        return null;
    }

        public boolean equals(Object toTaskTree) {
        boolean IDEquality=ID_EQUALITY;
        if (!this.data.equals(((TaskTree)toTaskTree).getData())) return false;
        if (IDEquality)
        {
            if (!this.id.equals(((TaskTree)toTaskTree).getID())) return false;
        }
        TaskTree[] kids1 = this.getChildren();
        TaskTree[] kids2 = ((TaskTree)toTaskTree).getChildren();
        if (kids1.length!=kids2.length) return false;
        for(int i=0; i<kids1.length; i++)
        {
            if (!kids1[i].equals(kids2[i]))
                return false;
        }
        return true;
    }


    /**
     * @param data
     * @return task_tree.TaskTree with specified Data
     */
        public TaskTree find(Data data) {
        if (this.data.equals(data)) return this;
        else if (children==null||children.isEmpty())
            return null;
        else
        {
            for(TaskTree t: children){
                TaskTree f=t.find(data);
                if (f!=null) return f;
            }
        }
        return null;
    }


    /**
     * @param path where to save current task_tree.TaskTree
     */
        public void saveToFile(File path) throws FileNotFoundException, IOException{
        FileOutputStream fos=null;
        ObjectOutputStream ous=null;
        try{
            fos = new FileOutputStream(path);
            ous = new ObjectOutputStream(fos);
            ous.writeObject(this);
        }
        finally
        {
            if (fos!=null) fos.close();
            if (ous!=null) ous.close();
        }
    }



    /**
     * @param path
     * @return task_tree.TaskTree from the specified path
     */
        public TaskTree loadFromFile(File path) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis=null;
        ObjectInputStream ois =null;
        try{
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            return (TaskTree) ois.readObject();
        }
        finally{
            fis.close();
            ois.close();
        }
    }


    /**
     * @return clone of self with THE SAME IDs!!
     */
        public TaskTree clone() {
        ByteArrayOutputStream baos=null;
        ByteArrayInputStream bais=null;
        ObjectInputStream ois =null;
        ObjectOutputStream ous =null;
        try{
            baos=new ByteArrayOutputStream();
            ous = new ObjectOutputStream(baos);
            ous.writeObject(this);
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois=new ObjectInputStream(bais);
            return (TaskTree) ois.readObject();
        }
        catch(IOException|ClassNotFoundException e)
        {
            //Consume exception and return null
            e.printStackTrace();
            return null;
        }
        finally
        {
            try{
                baos.close();
                bais.close();
                ois.close();
                ous.close();
            }
            catch(IOException e)
            {
                //Consume exception
                e.printStackTrace();
            }
        }
    }


    /**
     * @param id ID of a node you want to get a copy off
     * @param newGenerator IDGenerator that will be used to assign new IDs
     * (use IDGenerator of a task_tree.TaskTree, to which you want attach this copy)
     * @return deep copy with NEW IDs
     */
        public TaskTree copy(ID id, IDGenerator newGenerator) {
        TaskTree copy=find(id).clone();
        List<ID> oldIds = copy.getContainingIDs();
        List<ID> newIds = new <ID>LinkedList();
        for(int i=0; i<oldIds.size(); i++)
        {
            newIds.add(newGenerator.nextID());
        }
        TaskTree.assignIDs(newIds, copy);
        TaskTree.assignIDGenerator(newGenerator, copy);
        return copy;
    }


    /**
     * @param id ID of a node you want to get a copy off
     * This method will generate Trees with NEW IDs
     * using IDGenerator of this Tree
     * @return deep copy with NEW IDs
     */
    public TaskTree copy(ID id) {
        return this.copy(id, this.idGenerator);
    }


    /**
     * @param id ID of a DIRECT child task_tree.TaskTree to be deleted
     * throws IDNotFoundException
     * @deprecated - not sure if this method is useful for anything
     * other than 'inside' implementation
     */
    @Deprecated
        private void excludeFromChildren(ID id) {
        TaskTree f=null;
        for(TaskTree t: children){
            if (t.getID().equals(id))
            {
                f=t;
                break;
            }
        }
        if (f!=null) children.remove(f);
    }



    /**
     * Delete this node
     * throws NoParentTaskTreeException(Runtime exception) if has no parents
     */
        public void delete() {
        if (parent==null) throw new NoParentTreeException("Can't delete this node,"
                + "because it has no parent!");
        this.parent.delete(this.id);
    }




    /**
     * @param id ID of a task_tree.TaskTree to be deleted
     * throws IDNotFoundException(Runtime exception)
     */
        public void delete(ID id) {
        TaskTree found;
        if (this.id.equals(id)) found=this;
        else found= this.find(id);
        if (found==null) throw new IDNotFoundException("Can't delete node,"
                + "because it can't be found");
        TaskTree foundParent = found.getTaskParent();
        if (foundParent!=null)
        {
            foundParent.excludeFromChildren(found.getID());
        }
        else{
            throw new NoParentTreeException("Can't delete this node,"
                    + "because it has no parent!");
        }
        if (!(found.getChildren()==null||found.getChildren().length==0))
        {
            TaskTree[] foundKids=found.getChildren();
            for(int i=0; i<foundKids.length; i++)
            {
                foundKids[i].setParent(null);
            }
        }
    }



    /**
     * @return ID of this task_tree.TaskTree
     */
        public ID getID()
    {
        return this.id;
    }



    /**
     * @param id new ID for this task_tree.TaskTree
     * @deprecated - BE CAREFULL, DO NOT USE, REALLY, NEVER!!!
     * task_tree.TaskTree consistency at danger!
     */
    @Deprecated
        private void setID(ID id)
    {
        //Thin ice here...
        //Do not f*** consistency of this task_tree.TaskTree!!!
        //To big overhead if you want to check every time
        this.id=id;
    }


    /**
     * @return List<ID> of IDs this task_tree.TaskTree and it's children contains
     * @deprecated - not sure if this method is useful for anything
     * other than 'inside' implementation
     */
    @Deprecated
        private List<ID> getContainingIDs() {
        List<ID> ids= new <ID> LinkedList();
        TaskTree[] kids = this.getChildren();
        ids.add(this.getID());
        if (kids.length!=0)
        {
            for(int i=0; i<kids.length; i++)
            {
                ids.addAll(kids[i].getContainingIDs());
            }
        }
        return ids;
    }


    /**
     * Assigns new IDs to all Trees
     * @param ids list of new IDs, must match in length with number of all condescending trees
     * @param t Tree to which structure new IDs will be assigned
     */
    private static void assignIDs(List<ID> ids, TaskTree t)
    {
        t.setID(ids.remove(0));
        TaskTree[] kids=(TaskTree[])t.getChildren();
        for(int i=0; i<kids.length; i++)
        {
            assignIDs(ids,  kids[i]);
        }
    }


    /**
     * Assigns IDGenerator to specified tree
     * @param generator new IDGenerator
     * @param t Tree to which new IDGenerator will be assigned
     */
    private static void assignIDGenerator(IDGenerator generator, TaskTree t)
    {
        t.setIDGenerator(generator);
        TaskTree[] kids=(TaskTree[])t.getChildren();
        for(int i=0; i<kids.length; i++)
        {
            assignIDGenerator(generator,  kids[i]);
        }
    }



    /**
     * sorts task_tree.TaskTree, so that children ID always bigger than one parent has
     * switches isSorted to "true"
     * @deprecated - can't find a reason to use this method. Useless shit =(
     */
    @Deprecated
        private void sort() {
        //??? Do we need it?
        List<ID> ids = this.getContainingIDs();
        //OPTIMIZE not fucking bubble! =(
        for(int j=0; j<ids.size(); j++){
            for(int i=0; i<ids.size()-1; i++)
            {
                ID t;
                if (ids.get(i).compareTo(ids.get(i+1))>0)
                {
                    t=ids.get(i);
                    ids.set(i, ids.get(i+1));
                    ids.set(i+1, t);
                }
            }}
        assignIDs(ids, this);
    }


    /**
     * Uploads tree to the selected user
     */
        public void saveToServer(String login, String password, TaskTree tree) throws IOException {
            //TODO implementation
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * Loads tree from the selected user
     */
        public TaskTree loadFromServer(String login, String password) throws IOException, ClassNotFoundException {
            //TODO implementation
        throw new UnsupportedOperationException("Not supported yet.");
    }


}

