package kanban.model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIdList = new ArrayList<>(); // список для хранения айдишников подзадач

    // конструктор для обработки эпика
    public Epic(String name, String description, int id) {
        super(name, description, id, Status.NEW);
    }

    // конструктор для создания эпика
    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
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
