package kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIdList = new ArrayList<>(); // список для хранения айдишников подзадач

    private LocalDateTime endTime;

    // конструктор для обработки эпика
    public Epic(String name, String description, int id) {
        super(name, description, id, Status.NEW, null, Duration.ZERO);
    }

    // конструктор для создания эпика
    public Epic(String name, String description) {
        super(name, description, Status.NEW, LocalDateTime.now(), Duration.ZERO);
    }

    public Epic(String name, String description, int id, Status status, LocalDateTime startTime, Duration duration) {
        super(name, description, id, status, startTime, duration);
    }

    public ArrayList<Integer> getSubTasksIdList() { // получить список айдишников подзадач
        return new ArrayList<>(subTasksIdList);
    }

    public void addSubTaskById(int subTaskId) { // добавить айди подзадачки в список айдишников подзадач
        if (subTaskId != this.id) {
            subTasksIdList.add(subTaskId);
        } else {
            System.out.println("Вы пытаетесь добавить эпик сам в себя в виде подзадачи");
        }
    }

    // сеттер установки конца эпка
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    //геттер получения конца эпика
    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void removeSubtaskById(Integer subTaskId) { // удалить айди подзадачки из списка айдишников подзадач
        if (subTasksIdList.contains(subTaskId)) {
            subTasksIdList.remove(subTaskId);
        }
    }

    public void clearSubTasksIdList() { // очистить список айдишников подзадач
        if (!subTasksIdList.isEmpty()) {
            subTasksIdList.clear();
        }
    }

    @Override
    public TypeTask getType() {
        return TypeTask.EPIC;
    }
}
