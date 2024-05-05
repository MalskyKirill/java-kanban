package kanban.service;

import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;

import java.util.ArrayList;

public interface TaskManager {
    Task addNewTask(Task task);

    Epic addNewEpic(Epic epic);

    SubTask addNewSubTask(SubTask subTask);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTask();

    ArrayList<SubTask> getAllSubtasksByEpic(int epicId);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();

    Task getTaskById(int taskId);

    Epic getEpicById(int epicId);

    SubTask getSubTaskById(int subTaskId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void removeTaskById(int taskId);

    void removeEpicById(int epicId);

    void removeSubTaskById(int subTaskId);

    Task createTask(Task task);

    Epic createEpic(Epic epic);

    SubTask createSubTask(SubTask subTask);
}
