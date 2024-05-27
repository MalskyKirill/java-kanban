package kanban.service;

import kanban.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> historyTasksList = new ArrayList<>();
    private int MAX_SIZE_HISTORY = 10;
    @Override
    public void addTask(Task task) {
        if (task != null) { // проверка на null
            if (historyTasksList.size() >= MAX_SIZE_HISTORY) { // если список равен или больше максимального размера
                historyTasksList.remove(0); // удаляем первую в списке
            }
            historyTasksList.add(task); // добавляем в список задачку
        }
    }

    @Override
    public List<Task> getHistory() {
        return  new ArrayList<>(historyTasksList);
    }

    @Override
    public void remove(int id) {

    }
}
