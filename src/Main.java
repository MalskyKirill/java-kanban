import kanban.model.Status;
import kanban.model.Task;
import kanban.service.TaskManager;

import java.util.Scanner;

public class Main {

    static TaskManager taskManager;
    static Scanner scanner;

    public static void main(String[] args) {
        taskManager = new TaskManager();

        Task task = new Task("name", "description", Status.NEW);

        taskManager.addNewTask(task);
    }
}
