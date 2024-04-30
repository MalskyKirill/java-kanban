import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.service.TaskManager;

import java.util.Scanner;

public class Main {

    static TaskManager taskManager;
    static Scanner scanner;

    public static void main(String[] args) {
        taskManager = new TaskManager();

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        taskManager.addNewTask(firstTask);
        Task secondTask = new Task("Сходить на бокс", "Не получить по голове", Status.NEW);
        taskManager.addNewTask(secondTask);

        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        taskManager.addNewEpic(firstEpic);

        SubTask firstSubtask = new SubTask(
        "Взять семью", "Жена, дочка", Status.NEW, 3);
        taskManager.addNewSubTask(firstSubtask);

        SubTask secondSubtask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 3);
        taskManager.addNewSubTask(secondSubtask);

        System.out.println("1 - Получение списков всех задач");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTask());

        System.out.println("2 - Получение по идентификатору");
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getEpicById(3));
        System.out.println(taskManager.getSubTaskById(4));

        taskManager.removeAllTasks();
        taskManager.removeAllEpics();
        taskManager.removeAllSubTasks();

        System.out.println("3 Удаление всех задач");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTask());


    }
}
