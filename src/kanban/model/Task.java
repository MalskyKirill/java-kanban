package kanban.model;

import java.util.Objects;

public class Task {
    // свойства таски
    private final String name;
    private final String description;
    private final int id;
    private  Status status;

    // конструктор
    public Task (String name, String description) {
        this.name = name;
        this.description = description;
        this.id = hashCode(); // генерируем айдишник через метод hashCode
        this.status = Status.NEW; // берем значение из енама
    }

    // геттер имени
    public String getName() {
        return name;
    }

    // геттер описания
    public String getDescription() {
        return description;
    }

    // геттер id
    public int getId() {
        return id;
    }

    // геттер статуса
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // переопределили метод equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return
            Objects.equals(name, task.name) &&
            Objects.equals(description, task.description) &&
            status == task.status;
    }

    // переопределили метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }

    // переопределили метод hashCode
    @Override
    public String toString() {
        return "Task{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", id=" + id +
            ", status=" + status +
            '}';
    }
}
