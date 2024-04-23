package kanban.model;

public class SubTask extends Task{
    protected int epicId;
    public SubTask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);

        this.epicId = epicId;
    }
}
