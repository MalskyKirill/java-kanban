package kanban.model;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> subTasksIdList = new ArrayList<>(); // список для хранения айдишников подзадач

    public Epic(String name, String description, int id) {
        super(name, description, id, Status.NEW);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getSubTasksIdList() { // получить список айдишников подзадач
        return new ArrayList<>(subTasksIdList);
    }

    public void addSubTaskById(int subTaskId) { // добавить айди подзадачки в список айдишников подзадач
        subTasksIdList.add(subTaskId);
    }

    public void removeSubtaskById(int subTaskId) { // удалить айди подзадачки из списка айдишников подзадач
        if (subTasksIdList.contains(subTaskId)) {
            subTasksIdList.remove(subTaskId);
        }
    }

    public void clearSubTasksIdList() { // очистить список айдишников подзадач
        if (!subTasksIdList.isEmpty()) {
            subTasksIdList.clear();
        }
    }

}
