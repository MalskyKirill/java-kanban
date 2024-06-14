package kanban.model;

import java.util.Objects;

public class Task {
    // свойства таски
    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    // конструктор для обработки задачи менеджером
    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    // конструктор для создания задачи
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
