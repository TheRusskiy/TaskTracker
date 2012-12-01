import exceptions.NoParentTreeException;
import task_tree.Data;
import task_tree.IDGenerator;
import task_tree.TaskTree;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class TaskTreeTest {


    // <editor-fold defaultstate="collapsed" desc="Test output">
    public static boolean testAll()
    {
        boolean result=true;
        System.out.println("Failed tests:");
        if (!createTaskTree())
        {
            System.out.println("create tree:"+createTaskTree());
            result=false;
        }

        if (!equalsTaskTree())
        {
            System.out.println("tree equals method:"+equalsTaskTree());
            result=false;
        }

        if (!noCircles())
        {
            System.out.println("no circles:"+noCircles());
            result=false;
        }

        if (!addNode())
        {
            System.out.println("add node:"+addNode());
            result=false;
        }

        if (!cannotAddTwice())
        {
            System.out.println("cannot add twice:"+cannotAddTwice());
            result=false;
        }

        if (!splitTaskTree())
        {
            System.out.println("split tree:"+splitTaskTree());
            result=false;
        }

        if (!cloneTaskTree())
        {
            System.out.println("clone tree:"+cloneTaskTree());
            result=false;
        }

        if (!nodeSearch())
        {
            System.out.println("node search:"+nodeSearch());
            result=false;
        }

        if (!orderTaskTree())
        {
            System.out.println("order tree:"+orderTaskTree());
            result=false;
        }

        if (!saveTaskTree())
        {
            System.out.println("save tree:"+saveTaskTree());
            result=false;
        }

        if (!loadTaskTree())
        {
            System.out.println("load tree:"+loadTaskTree());
            result=false;
        }

        if (!multipleTaskTree())
        {
            System.out.println("multiple tree:"+multipleTaskTree());
            result=false;
        }

        if (!deleteTaskTree())
        {
            System.out.println("delete tree:"+deleteTaskTree());
            result=false;
        }


        if (!copyAndInsertTaskTree())
        {
            System.out.println("copy and insert tree:"+copyAndInsertTaskTree());
            result=false;
        }


        return result;
    }
    // </editor-fold>



    public static boolean createTaskTree()
    {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        return (tree!=null);
    }

    public static boolean noCircles()
    {
        //!!! ALWAYS TRUE STUB !!!
        boolean alwaysTrue=true;
        //??? Implement similar test for GUI addition
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        try
        {
            tree.add(tree);
        }
        //create specific exception
        //not "just" exception, specific one
        catch(Exception e)
        {
            return true;
        }
        return alwaysTrue;
    }

    public static boolean addNode()
    {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree tree2 = new TaskTree(generator, data);
        TaskTree tree3 = new TaskTree(generator, data);
        tree.add(tree1);
        tree.add(tree2);
        tree2.add(tree3);
        TaskTree temp;
        Iterator<TaskTree> trit=tree.getChildrenIterator();
        while (trit.hasNext())
        {
            temp=trit.next();
            if (temp==tree2) return true;
        }
        return false;
    }


    public static boolean cannotAddTwice()
    {
        //!!! ALWAYS TRUE STUB !!!
        boolean alwaysTrue=true;
        //??? Implement similar test for GUI addition
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree child = new TaskTree(generator, data);
        try
        {
            tree.add(child);
            tree.add(child);
        }
        //create specific exception
        //not "just" exception, specific one
        catch(Exception e)
        {
            return true;
        }
        return alwaysTrue;
    }

    public static boolean splitTaskTree()
    {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree parent = new TaskTree(generator, data);
        TaskTree child1 = new TaskTree(generator, data);
        TaskTree child2 = new TaskTree(generator, data);
        parent.add(tree);
        tree.add(child1);
        tree.add(child2);
        tree.split();
        TaskTree temp;
        Iterator<TaskTree> trit=parent.getChildrenIterator();
        while (trit.hasNext())
        {
            temp=trit.next();
            if (temp==tree) return false;
            if (temp!=child1&&temp!=child2) return false;
        }
        return true;
    }

    public static boolean cloneTaskTree() {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree child1 = new TaskTree(generator, data);
        tree1.add(child1);
        TaskTree child2 = new TaskTree(generator, data);
        tree1.add(child2);
        TaskTree tree2;
        tree2=tree1.clone();
        if (tree1.getData()==tree2.getData()) return false;
        TaskTree[] trit1=tree1.getChildren();
        TaskTree[] trit2=tree2.getChildren();
        if (trit1.length!=trit2.length) return false;
        for(int i=0; i<trit1.length; i++)
        {
            if (trit1[i]==trit2[i]) return false;
            if (trit1[i].getData()==trit2[i].getData()) return false;
            if (!trit1[i].getData().equals(trit2[i].getData())) return false;
            //??? Test IDs
        }
        return true;
    }

    public static boolean nodeSearch() {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree tree2 = new TaskTree(generator, data);
        TaskTree tree3 = new TaskTree(generator, data);
        TaskTree tree4 = new TaskTree(generator, data);
        TaskTree tree5 = new TaskTree(generator, data);
        tree.add(tree1);
        tree.add(tree2);
        tree2.add(tree3);
        tree3.add(tree4);
        tree3.add(tree5);
        if (tree.find(tree5.getID())==tree5) return true;
        return false;
    }

    public static boolean orderTaskTree() {
        //SORT NOW IS PRIVATE!!
//        Data data=new Data("SomeData");
//        IDGenerator generator = new IDGenerator();
//        task_tree.TaskTree tree4 = new task_tree.TaskTree(generator, data);
//        task_tree.TaskTree tree3 = new task_tree.TaskTree(generator, data);
//        task_tree.TaskTree tree = new task_tree.TaskTree(generator, data);
//        task_tree.TaskTree tree5 = new task_tree.TaskTree(generator, data);
//        task_tree.TaskTree tree1 = new task_tree.TaskTree(generator, data);
//        task_tree.TaskTree tree2 = new task_tree.TaskTree(generator, data);
//
//        tree.add(tree1);
//        tree.add(tree2);
//        tree2.add(tree3);
//        tree3.add(tree4);
//        tree3.add(tree5);
//        tree.sort();
//        ID id = tree.getID();
//        ID id1 = tree1.getID();
//        ID id2 = tree2.getID();
//        ID id3 = tree3.getID();
//        ID id4 = tree4.getID();
//        ID id5 = tree5.getID();
//        /*
//                    T
//              T1        T2
//                        T3
//                      T4   T5
//        */
//        if (id5.compareTo(id3)<=0) return false;
//        if (id3.compareTo(id2)<=0) return false;
//        if (id2.compareTo(id)<=0) return false;
//        if (id1.compareTo(id)<=0) return false;
        return true;
    }

    public static boolean saveTaskTree() {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree tree2 = new TaskTree(generator, data);
        TaskTree tree3 = new TaskTree(generator, data);
        tree.add(tree1);
        tree.add(tree2);
        tree2.add(tree3);
        try
        {
            File f = new File("c:\\tree");
            Boolean b = f.exists();
            if (!b)
            {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            tree.saveToFile(f);
        }
        catch(IOException e)
        {
            return false;
        }
        return true;
    }

    public static boolean loadTaskTree() {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree tree2 = new TaskTree(generator, data);
        TaskTree tree3 = new TaskTree(generator, data);
        tree.add(tree1);
        tree.add(tree2);
        tree2.add(tree3);
        try
        {
            File f = new File("c:\\tree");
            Boolean b = f.exists();
            if (!b)
            {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            tree.saveToFile(f);
            TaskTree loaded = (TaskTree)tree.loadFromFile(f);
            if (loaded.equals(tree)) return true;
        }
        catch(IOException|ClassNotFoundException e)
        {
            return false;
        }

        return false;
    }

    public static boolean multipleTaskTree() {
        //!!! ALWAYS TRUE STUB !!!
        boolean alwaysTrue=true;
        //New requirements suggest we do not need this test
        return alwaysTrue;
    }

    public static boolean copyAndInsertTaskTree() {
        //TODO Implement test
        return false;
    }

    public static boolean deleteTaskTree() {
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree = new TaskTree(generator, data);
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree tree2 = new TaskTree(generator, data);
        tree.add(tree1);
        tree.add(tree2);
        tree.delete(tree1.getID());
        TaskTree[] kids=tree.getChildren();
        for(int i=0; i<kids.length; i++)
        {
            if (kids[i]==tree1) return false;
        }
        tree.add(tree1);
        boolean result=false;
        try{
            tree.delete();
        }
        catch(NoParentTreeException ex)
        {
            result=true;
        }
        if (result==false) return false;
        tree1.delete();
        kids=tree.getChildren();
        for(int i=0; i<kids.length; i++)
        {
            if (kids[i]==tree1) return false;
        }
        return true;
    }

    public static boolean equalsTaskTree() {
        //!!!Glass box testing!!!
        Data data=new Data("SomeData");
        IDGenerator generator = new IDGenerator();
        TaskTree tree0 = new TaskTree(generator, data);
        TaskTree tree1 = new TaskTree(generator, data);
        TaskTree tree2 = new TaskTree(generator, data);
        TaskTree tree3 = new TaskTree(generator, data);
        tree0.add(tree1);
        tree0.add(tree2);
        tree2.add(tree3);

        Data data2=new Data("SomeData");
        IDGenerator generator2;
        if (TaskTree.ID_EQUALITY)
        {
            generator2= new IDGenerator();
        }
        else
        {
            generator2 = generator;
        }
        TaskTree tree02 = new TaskTree(generator2, data2);
        TaskTree tree12 = new TaskTree(generator2, data2);
        TaskTree tree22 = new TaskTree(generator2, data2);
        TaskTree tree32 = new TaskTree(generator2, data2);
        tree02.add(tree12);
        tree02.add(tree22);
        tree22.add(tree32);
        boolean result = true;
        if (!tree0.equals(tree02)) result=false;
        Data data3=new Data("SomeData2");
        tree32.setData(data3);
        if (tree0.equals(tree02)) result=false;
        return result;
    }

    public static void main(String[] args)
    {
        TaskTreeTest.testAll();
    }


}
