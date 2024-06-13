import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.service.FileBackedTaskManager;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static TaskManager manager;
    static FileBackedTaskManager backedManager;


    public static void main(String[] args) throws IOException {

        Path path = Paths.get("vendor" + File.separator + "data.scv");

        if (!path.toFile().isFile()) {
            Files.createFile(path);
        }

        backedManager = new FileBackedTaskManager(path.toFile());

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        backedManager.addNewTask(firstTask);
        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        backedManager.addNewEpic(firstEpic);

        SubTask firstSubTask = new SubTask(
            "Взять семью", "Жена, дочка", Status.NEW, 2);
        backedManager.addNewSubTask(firstSubTask);

        Task secondTask = backedManager.fromString("2,TASK,Отвести дочку в школу,DONE,Не забыть портфель и сменку");


        System.out.println(secondTask);

 /*       manager = Managers.getDefault();

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        manager.addNewTask(firstTask);
        Task secondTask = new Task("Сходить на бокс", "Не получить по голове", Status.NEW);
        manager.addNewTask(secondTask);

        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        manager.addNewEpic(firstEpic);

        SubTask firstSubTask = new SubTask(
            "Взять семью", "Жена, дочка", Status.NEW, 3);
        manager.addNewSubTask(firstSubTask);

        SubTask secondSubTask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 3);
        manager.addNewSubTask(secondSubTask);

        System.out.println("1 - Получение списков всех задач");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTask());

        System.out.println("2 - Получение по идентификатору");
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getEpicById(3));
        System.out.println(manager.getSubTaskById(4));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getEpicById(3));
        System.out.println(manager.getSubTaskById(4));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getEpicById(3));
        System.out.println(manager.getSubTaskById(4));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getEpicById(3));
        System.out.println(manager.getSubTaskById(4));

        System.out.println("2.1 - Получение истории");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }

        System.out.println("3 Удаление всех задач");
        manager.removeAllTasks();
        manager.removeAllEpics();
        manager.removeAllSubTasks();

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTask());

        System.out.println("4 Создание задачек");
        Task newFirstTask = manager.createTask(firstTask);
        manager.addNewTask(newFirstTask);
        Task newSecondTask = manager.createTask(secondTask);
        manager.addNewTask(newSecondTask);

        Epic newEpic = manager.createEpic(firstEpic);
        manager.addNewEpic(newEpic);

        SubTask firdSubTask = new SubTask(
            "Взять семью", "Жена, дочка", Status.NEW, 8);
        SubTask fourthSubTask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 8);

        SubTask newFirstSubTask = manager.createSubTask(firdSubTask);
        manager.addNewSubTask(newFirstSubTask);
        SubTask newSecondSubTask = manager.createSubTask(fourthSubTask);
        manager.addNewSubTask(newSecondSubTask);

        System.out.println(newFirstTask);
        System.out.println(newSecondTask);
        System.out.println(newEpic);
        System.out.println(newFirstSubTask);
        System.out.println(newSecondSubTask);

        System.out.println("5 Обновление задачек");

        manager.updateTask(new Task("Отвести дочку в школу", "Не забыть портфель и сменку и форму", 6, Status.IN_PROGRESS));
        manager.updateEpic(new Epic("Поехать в отпуск на море", "Поехать в отпуск с семьей", 8));
        manager.updateSubTask(new SubTask(
            "Взять семью", "Жена, дочка, я сам", 9, Status.IN_PROGRESS, 8));

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTask());

        System.out.println("6 Получение списка всех подзадач определённого эпика");
        System.out.println(manager.getAllSubtasksByEpic(8));

        System.out.println("7 Удаление по id");
        manager.removeTaskById(6);
//        taskManager.removeEpicById(8);
        manager.removeSubTaskById(9);
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTask());

 */
    }
}
