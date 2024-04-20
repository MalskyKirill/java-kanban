package kanban.service;

import kanban.model.Task;

import java.util.HashMap;

public class TaskManager {

    //создал хеш-маппу для хранения тасочек
    private HashMap<Integer, Task> tasksList = new HashMap<>();

    public void addNewTask(Task task) { // метод добавления задачки в хешмапу
        int id = task.getId(); // получаем id задачки
        for (int taskId : tasksList.keySet()){ // пробегаемся по ключам-айдишникам
            if (id == taskId) { // если задача с таким айдишником существует
                System.out.println("Задача с таким именем и описанием уже существует");
                return; // вылетаем
            }
        }

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
