package kanban.service;

import kanban.model.Status;
import kanban.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public InMemoryHistoryManager historyManager;

    Task firstTask;
    Task secondTask;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        firstTask = new Task("Отвести дочку в школу", "Не забыть портфель и сменку", 1, Status.NEW, LocalDateTime.of(2024, 9, 1, 9, 0), Duration.ofMinutes(30));
        secondTask = new Task("Сходить на бокс", "Не получить по голове", 2, Status.NEW, LocalDateTime.of(2024, 9, 1, 18, 0), Duration.ofMinutes(60));
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
