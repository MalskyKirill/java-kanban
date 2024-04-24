package kanban.model;

import java.util.ArrayList;

public class Epic extends Task{
    protected ArrayList<Integer> subTaskId;
    public Epic(String name, String description, int id, Status status, ArrayList<Integer> subTasksId) {
        super(name, description, id, status);

        this.subTaskId = subTasksId;
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(ArrayList<Integer> subTaskId) {
        this.subTaskId = subTaskId;
    }
}
