import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.service.TaskManager;

public class Main {

    static TaskManager taskManager;

    public static void main(String[] args) {
        taskManager = new TaskManager();

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        taskManager.addNewTask(firstTask);
        Task secondTask = new Task("Сходить на бокс", "Не получить по голове", Status.NEW);
        taskManager.addNewTask(secondTask);

        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        taskManager.addNewEpic(firstEpic);

        SubTask firstSubTask = new SubTask(
        "Взять семью", "Жена, дочка", Status.NEW, 3);
        taskManager.addNewSubTask(firstSubTask);

        SubTask secondSubTask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 3);
        taskManager.addNewSubTask(secondSubTask);

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

        System.out.println("4 Создание задачек");
        Task newFirstTask = taskManager.createTask(firstTask);
        taskManager.addNewTask(newFirstTask);
        Task newSecondTask = taskManager.createTask(secondTask);
        taskManager.addNewTask(newSecondTask);

        Epic newEpic = taskManager.createEpic(firstEpic);
        taskManager.addNewEpic(newEpic);

        SubTask firdSubTask = new SubTask(
            "Взять семью", "Жена, дочка", Status.NEW, 8);
        SubTask fourthSubTask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 8);

        SubTask newFirstSubTask = taskManager.createSubTask(firdSubTask);
        taskManager.addNewSubTask(newFirstSubTask);
        SubTask newSecondSubTask = taskManager.createSubTask(fourthSubTask);
        taskManager.addNewSubTask(newSecondSubTask);

        System.out.println(newFirstTask);
        System.out.println(newSecondTask);
        System.out.println(newEpic);
        System.out.println(newFirstSubTask);
        System.out.println(newSecondSubTask);

        System.out.println("5 Обновление задачек");

        taskManager.updateTask(new Task("Отвести дочку в школу", "Не забыть портфель и сменку и форму", 6, Status.IN_PROGRESS));
        taskManager.updateEpic(new Epic("Поехать в отпуск на море", "Поехать в отпуск с семьей", 8));
        taskManager.updateSubTask(new SubTask(
            "Взять семью", "Жена, дочка, я сам", 9, Status.IN_PROGRESS, 8));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTask());

        System.out.println("6 Получение списка всех подзадач определённого эпика");
        System.out.println(taskManager.getAllSubtasksByEpic(8));

        System.out.println("7 Удаление по id");
        taskManager.removeTaskById(6);
//        taskManager.removeEpicById(8);
        taskManager.removeSubTaskById(9);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTask());
    }
}
