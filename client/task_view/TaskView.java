package task_view;

import task_controller.TaskController;

/**
 * Created with IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 10.11.12
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class TaskView {
    TaskController controller;

    public TaskController getController() {
        return controller;
    }

    public TaskView(final TaskController controller){
        this.controller=controller;
        controller.setView(this);

    }
}
