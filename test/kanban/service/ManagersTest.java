package kanban.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    public TaskManager taskManager;

    @Test
    void createInMemoryTaskManager() {
        taskManager = Managers.getDefault();

        assertNotNull(taskManager);
    }

    @Test
    void createInMemoryHistoryManager() {
        taskManager = Managers.getDefault();

        assertNotNull(taskManager);
    }
}
