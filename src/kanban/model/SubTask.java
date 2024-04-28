package kanban.model;

public class SubTask extends Task{
    private int epicId; // айдишник епика которому принадлежин подзадача

    //переопредилил консруктор создания подзадачки
    public SubTask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);

        this.epicId = epicId;
    }

    //переопредилил консруктор апдейта подзадачки
    public SubTask(String name, String description, Status status) {
        super(name, description, status);

    }

    // вернули айди эпика которому принадлежин подзадачка
    public int getEpicId() {
        return epicId;
    }
}
