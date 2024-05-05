import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.service.InMemoryTaskManager;

public class Main {

    static InMemoryTaskManager inMemoryTaskManager;

    public static void main(String[] args) {
        inMemoryTaskManager = new InMemoryTaskManager();

        Task firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        inMemoryTaskManager.addNewTask(firstTask);
        Task secondTask = new Task("Сходить на бокс", "Не получить по голове", Status.NEW);
        inMemoryTaskManager.addNewTask(secondTask);

        Epic firstEpic = new Epic("Поехать в отпуск",
            "Поехать в отпуск с семьей");
        inMemoryTaskManager.addNewEpic(firstEpic);

        SubTask firstSubTask = new SubTask(
        "Взять семью", "Жена, дочка", Status.NEW, 3);
        inMemoryTaskManager.addNewSubTask(firstSubTask);

        SubTask secondSubTask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 3);
        inMemoryTaskManager.addNewSubTask(secondSubTask);

        System.out.println("1 - Получение списков всех задач");
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubTask());

        System.out.println("2 - Получение по идентификатору");
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));

        System.out.println("2.1 - Получение истории");
        System.out.println(inMemoryTaskManager.getHistory());

        System.out.println("3 Удаление всех задач");
        inMemoryTaskManager.removeAllTasks();
        inMemoryTaskManager.removeAllEpics();
        inMemoryTaskManager.removeAllSubTasks();

        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubTask());

        System.out.println("4 Создание задачек");
        Task newFirstTask = inMemoryTaskManager.createTask(firstTask);
        inMemoryTaskManager.addNewTask(newFirstTask);
        Task newSecondTask = inMemoryTaskManager.createTask(secondTask);
        inMemoryTaskManager.addNewTask(newSecondTask);

        Epic newEpic = inMemoryTaskManager.createEpic(firstEpic);
        inMemoryTaskManager.addNewEpic(newEpic);

        SubTask firdSubTask = new SubTask(
            "Взять семью", "Жена, дочка", Status.NEW, 8);
        SubTask fourthSubTask = new SubTask(
            "Взять собак", "Пэди, Потап, Дик", Status.DONE, 8);

        SubTask newFirstSubTask = inMemoryTaskManager.createSubTask(firdSubTask);
        inMemoryTaskManager.addNewSubTask(newFirstSubTask);
        SubTask newSecondSubTask = inMemoryTaskManager.createSubTask(fourthSubTask);
        inMemoryTaskManager.addNewSubTask(newSecondSubTask);

        System.out.println(newFirstTask);
        System.out.println(newSecondTask);
        System.out.println(newEpic);
        System.out.println(newFirstSubTask);
        System.out.println(newSecondSubTask);

        System.out.println("5 Обновление задачек");

        inMemoryTaskManager.updateTask(new Task("Отвести дочку в школу", "Не забыть портфель и сменку и форму", 6, Status.IN_PROGRESS));
        inMemoryTaskManager.updateEpic(new Epic("Поехать в отпуск на море", "Поехать в отпуск с семьей", 8));
        inMemoryTaskManager.updateSubTask(new SubTask(
            "Взять семью", "Жена, дочка, я сам", 9, Status.IN_PROGRESS, 8));

        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubTask());

        System.out.println("6 Получение списка всех подзадач определённого эпика");
        System.out.println(inMemoryTaskManager.getAllSubtasksByEpic(8));

        System.out.println("7 Удаление по id");
        inMemoryTaskManager.removeTaskById(6);
//        taskManager.removeEpicById(8);
        inMemoryTaskManager.removeSubTaskById(9);
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println(inMemoryTaskManager.getAllSubTask());
    }
}
