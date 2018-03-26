package wang.yiwangchunyu.community.dataStructures;

import java.util.ArrayList;

/**
 * Created by yiwangchunyu on 2018/3/22.
 */

public class TasksArrayList {
    private ArrayList<TasksShowOnIndex> tasks;

    public ArrayList<TasksShowOnIndex> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<TasksShowOnIndex> tasks) {
        this.tasks = tasks;
    }

    public TasksArrayList(ArrayList<TasksShowOnIndex> tasks) {
        this.tasks = tasks;
    }
}
