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
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    static TaskManager manager;
    static FileBackedTaskManager backedManager;


    public static void main(String[] args) throws IOException {
/*
        manager = Managers.getDefault();

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW
            , LocalDateTime.of(2024, 9, 1, 9, 0), Duration.ofMinutes(30));
        manager.addNewTask(firstTask);
        Task secondTask = new Task("Сходить на бокс", "Не получить по голове", Status.NEW, LocalDateTime.of(2024, 9, 1, 18, 0), Duration.ofMinutes(60));
        manager.addNewTask(secondTask);

        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        manager.addNewEpic(firstEpic);

        SubTask firstSubTask = new SubTask(
            "Взять семью", "Жена, дочка", Status.NEW, 3, LocalDateTime.of(2024, 8, 3, 9, 0), Duration.ofMinutes(70));
        manager.addNewSubTask(firstSubTask);

        SubTask secondSubTask = new SubTask(
            "Отпуск", "Отдохнуть", Status.IN_PROGRESS, 3, LocalDateTime.of(2024, 9, 1, 11, 0), Duration.ofDays(9));
        manager.addNewSubTask(secondSubTask);

        System.out.println("1 - Получение списков всех задач");
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTask());


        */
        // спринт 7
        Path path = Paths.get("vendor" + File.separator + "data.scv");

        if (!path.toFile().isFile()) {
            Files.createFile(path);
        }

        backedManager = new FileBackedTaskManager(path.toFile());

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW
            , LocalDateTime.of(2024, 9, 1, 9, 0), Duration.ofMinutes(30));
        backedManager.addNewTask(firstTask);
        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        backedManager.addNewEpic(firstEpic);

        SubTask firstSubTask = new SubTask(
            "Взять семью", "Жена дочка", Status.NEW, LocalDateTime.of(2024, 8, 3, 9, 0), Duration.ofMinutes(70), 2);
        backedManager.addNewSubTask(firstSubTask);

        SubTask secondSubTask = new SubTask(
            "Отпуск", "Отдохнуть", Status.IN_PROGRESS, LocalDateTime.of(2024, 9, 1, 11, 0), Duration.ofDays(9), 2);
        backedManager.addNewSubTask(secondSubTask);

        backedManager.updateTask(new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW
            , LocalDateTime.of(2024, 9, 1, 9, 0), Duration.ofMinutes(30)));

        FileBackedTaskManager backedManager2 = FileBackedTaskManager.loadFromFile(path.toFile());

        System.out.println(backedManager2.getEpicById(2));


        /*
        //спринт 6
        manager = Managers.getDefault();

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
