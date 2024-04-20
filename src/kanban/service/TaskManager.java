package kanban.service;

import kanban.model.Task;

import java.util.HashMap;

public class TaskManager {

    //создал хеш-маппу для хранения тасочек
    private HashMap<Integer, Task> tasksList = new HashMap<>();

    public void addNewTask(int id, Task task) {
        tasksList.put(id, task);
    }

    @Override
    public String toString() {
        String taskName = "";
        for(Task task : tasksList.values()) {
            taskName += task.getName();
        }

        return "TaskManager{" +
            "tasksList=" + taskName +
            '}';
    }
}
