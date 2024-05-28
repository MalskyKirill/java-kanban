package kanban.service;

import kanban.model.Status;
import kanban.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public InMemoryHistoryManager historyManager;

    Task firstTask;
    Task secondTask;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", 0, Status.NEW);
        secondTask = new Task("Сходить на бокс", "Не получить по голове", 1, Status.NEW);
    }

    // проверка что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных
    @Test
    void addHistoryTasksList() {
        historyManager.addTask(firstTask);
        historyManager.addTask(secondTask);
        historyManager.addTask(firstTask);

        assertEquals(List.of(secondTask, firstTask), historyManager.getHistory(), "данные не совподают");
    }
}
