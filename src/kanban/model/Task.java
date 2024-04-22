package kanban.model;

import java.util.Objects;

public class Task {
    // свойства таски
    private final String name;
    private final String description;
    private final int id;
    private final Status status;

    // конструктор
    public Task (String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
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

    // переопределили метод equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
            Objects.equals(name, task.name) &&
            Objects.equals(description, task.description) &&
            status == task.status;
    }

    // переопределили метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
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
