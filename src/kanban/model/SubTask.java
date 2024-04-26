package kanban.model;

public class SubTask extends Task{
    private int epicId;
    public SubTask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);

        this.epicId = epicId;
    }

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);

        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
