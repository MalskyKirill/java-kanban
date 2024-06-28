package kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubTask extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");;
    private int epicId; // айдишник епика которому принадлежин подзадача

    //переопредилил консруктор обработки подзадачи менеджером
    public SubTask(String name, String description, int id, Status status, int epicId, LocalDateTime startTime, Duration duration) {
        super(name, description, id, status, startTime, duration);

        this.epicId = epicId;
    }

    //переопредилил консруктор создания подзадачки
    public SubTask(String name, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(name, description, status, startTime, duration);

        this.epicId = epicId;
    }

    // вернули айди эпика которому принадлежин подзадачка
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toStringTask() {
        return getId() + "," + getType() + "," + getName() + ","
            + getStatus() + "," + getDescription() + "," + getStartTime().format(FORMATTER) + "," + getDuration() + "," + getEpicId() + "\n";
    }

    @Override
    public TypeTask getType() {
        return TypeTask.SUB_TASK;
    }
}
