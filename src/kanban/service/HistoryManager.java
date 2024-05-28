package kanban.service;

import kanban.model.Node;
import kanban.model.Task;

import java.util.List;
import java.util.Map;

public interface HistoryManager {
    void addTask(Task task);
    void remove(int id);
    List<Task> getHistory();
}
