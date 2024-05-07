package kanban.service;

import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class InMemoryTaskManagerTest {

    public InMemoryTaskManager taskManager;
    Task task;
    Epic epic;
    SubTask subTask;
    @BeforeEach // перед каждым тестом создаем новый менеджер и новую задачку
    public void beforeEach(){
        taskManager = new InMemoryTaskManager();
        task = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        epic = new Epic("Поехать в отпуск", "Поехать в отпуск с семьей");
        subTask = new SubTask("Взять семью", "Жена, дочка", Status.NEW, 2);

    }

    void addTasks() {
        taskManager.addNewTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addNewSubTask(subTask);
    }

    @Test // тест создания задачки
    void addNewTask() {
        addTasks();

        final Task savedTask = taskManager.getTaskById(1);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");

        // проверка неизменности задачи при добавлении ее в менаджер
        assertEquals(savedTask.getName(), task.getName());
        assertEquals(savedTask.getStatus(), task.getStatus());
        assertEquals(savedTask.getDescription(), task.getDescription());
        assertEquals(savedTask.getId(), task.getId());
    }

    @Test // тест создания эпика
    void addNewEpic() {
        addTasks();
        final Epic savedEpic = taskManager.getEpicById(2);

        final ArrayList<Integer> idList = savedEpic.getSubTasksIdList();

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");

        // проверка на добавлнение эпика самого в себя в виде подзадачи
        savedEpic.addSubTaskById(2);
        assertEquals(idList, savedEpic.getSubTasksIdList());
    }

    @Test // тест создания подзадачки
    void addNewSubTask() {
        addTasks();
        final SubTask savedSubTask = taskManager.getSubTaskById(3);

        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

        final List<SubTask> subTasks = taskManager.getAllSubTask();

        assertNotNull(subTasks, "Задачи не возвращаются.");

        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

}
