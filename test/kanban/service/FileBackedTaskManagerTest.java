package kanban.service;

import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    File tmpFile;
    public FileBackedTaskManager backetManager;
    Task task;
    Epic epic;
    SubTask subTask;

    @BeforeEach // перед каждым тестом создаем новый менеджер и новую задачку
    public void beforeEach() throws IOException {
        tmpFile = File.createTempFile("data", ".scv");
        backetManager = new FileBackedTaskManager(tmpFile);
        task = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", Status.NEW);
        epic = new Epic("Поехать в отпуск", "Поехать в отпуск с семьей");
        subTask = new SubTask("Взять семью", "Жена, дочка", Status.NEW, 2);

    }

    @AfterEach
    public void afterEach() {
        tmpFile.deleteOnExit();
    }

    void addTasks() {
        backetManager.addNewTask(task);
        backetManager.addNewEpic(epic);
        backetManager.addNewSubTask(subTask);
    }

    @Test
    void whenAssertingNotNull() {
        assertNotNull(tmpFile, () -> "The tmpFile should not be null");
    }

    @Test
    void addNewTaskAndLoadFromFile() {
        addTasks();

        final Task savedTask = backetManager.getTaskById(1);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        FileBackedTaskManager newBM = FileBackedTaskManager.loadFromFile(tmpFile);

        final Task savedTask2 = newBM.getTaskById(1);

        assertNotNull(savedTask2, "Задача не найдена.");
        assertEquals(savedTask, savedTask2, "Задачи не совпадают.");
    }

    @Test
    void loadEmptyFile() {
        FileBackedTaskManager newBK = FileBackedTaskManager.loadFromFile(tmpFile);

        assertEquals(0, newBK.getAllTasks().size());
    }
}
