package kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    // свойства таски
    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");

    // конструктор для обработки задачи менеджером со временем
    public Task(String name, String description, int id, Status status, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    // конструктор для создания задачи со временем
    public Task(String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    // конструктор для обработки задачи менеджером без времени
    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    // конструктор для создания задачи без времени
    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // геттер имени
    public String getName() {
        return name;
    }

    // сеттер имени
    public void setName(String name) {
        this.name = name;
    }

    // геттер описания
    public String getDescription() {
        return description;
    }

    // сеттер описания
    public void setDescription(String description) {
        this.description = description;
    }

    // геттер id
    public int getId() {
        return id;
    }

    // сеттер id
    public void setId(int id) {
        this.id = id;
    }

    // геттер статуса
    public Status getStatus() {
        return status;
    }

    // сеттер статуса
    public void setStatus(Status status) {
        this.status = status;
    }

    public TypeTask getType() {
        return TypeTask.TASK;
    }

    // геттер и сеттер продолжительности
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    // геттер и сеттер начала времени
    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        } else {
            return null;
        }
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    //получить
    public LocalDateTime getEndTime() {
        if (this.startTime == null) { // проверка startTime на null
            return null;
        }

        return this.startTime.plus(this.duration);
    }

    public String toStringTask() {
        return getId() + "," + getType() + "," + getName() + ","
            + getStatus() + "," + getDescription() + "," + getStartTime() + "," + getDuration() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, duration, startTime);
    }

    @Override
    public String toString() {
        return "Task{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", id=" + id +
            ", status=" + status +
            ", duration=" + duration +
            ", startTime=" + getStartTime() +
            '}';
    }
}
