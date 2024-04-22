package kanban.service;

import kanban.model.Status;
import kanban.model.Task;

import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {

    //создал хеш-маппу для хранения тасочек
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private int taskId = 1;

    public void addNewTask(String name, String description) { // метод добавления задачки в хешмапу

        Task task = new Task(name, description, taskId, Status.NEW);
        tasksList.put(taskId, task);
        taskId++;
    }

    public void getAllTasks(String category) { // печатаем все задачки из категории
        if (category.equals("простые")) { // проверяем категорию
            if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
                System.out.println("Простых задачек пока нет");
                return; // вылетаем
            }

            for (Task task : tasksList.values()) { // провегаемся по значениям хешмапы и печатаем
                System.out.println("Задача - " + task.getName()); // имя
                System.out.println("Описание - " + task.getDescription()); // описание
                System.out.println("Статус - " + task.getStatus()); // статус
                System.out.println("Id - " + task.getId()); // получаем айдишник
                System.out.println("");

            }
        }
    }

    public void removeAllTasks() { // метод удаления всех задачек
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }
        tasksList.clear(); // очищаем мапу
    }

    public Task getTaskById(int id) {
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("задачек пока нет");
            return null; // возвращаем null
        }

        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
            Task task = tasksList.get(id); // если ок, достаем из мапы задачку по айдишнику
            return task; // возвращаем обьектик
        } else { // ежели нет
            System.out.println("задачи с таким id нет");
            return null; // возвращаем null
        }
    }

    public void updateTaskById(Scanner scanner, int id) {
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("задачек пока нет");
            return; // вылетаем
        }

        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
            Task oldTask = tasksList.get(id);

            System.out.println("Какой параметр вы хотите обновить?");
            System.out.println("1 - имя");
            System.out.println("2 - описание");
            System.out.println("3 - статус");
            String param = scanner.nextLine().trim();

            switch (param) {
                case "1":
                    System.out.println("Введите новое имя задачи:");
                    String newName = scanner.nextLine().trim();

                    Task newTaskName = new Task(newName, oldTask.getDescription(), oldTask.getId(), oldTask.getStatus());
                    tasksList.put(oldTask.getId(), newTaskName);
                    break;
                case "2":
                    System.out.println("Введите новое описание задачи:");
                    String newDescription = scanner.nextLine().trim();

                    Task newTaskDescription = new Task(oldTask.getName(), newDescription, oldTask.getId(), oldTask.getStatus());
                    tasksList.put(oldTask.getId(), newTaskDescription);
                    break;
                case "3":
                    System.out.println("Введите новый статус задачи:");
                    System.out.println("1 - новая");
                    System.out.println("2 - в прогрессе");
                    System.out.println("3 - выполнена");

                    String newStatus = scanner.nextLine().trim();
                    System.out.println(newStatus);
                    Task newTaskStatus;

                    if(newStatus.equals("1")) {
                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(), oldTask.getId(), Status.NEW);
                        tasksList.put(oldTask.getId(), newTaskStatus);
                    } else if (newStatus.equals("2")) {
                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(), oldTask.getId(), Status.IN_PROGRESS);
                        tasksList.put(oldTask.getId(), newTaskStatus);
                    } else if (newStatus.equals("3")) {
                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(), oldTask.getId(), Status.DONE);
                        tasksList.put(oldTask.getId(), newTaskStatus);
                    } else {
                        System.out.println("Такого статуса нет");
                    }
                    break;
                default:
                    System.out.println("Такого параметра нет");
            }
        } else { // ежели нет
            System.out.println("задачи с таким id нет");
        }

    }

    @Override
    public String toString() {
        String taskName = "";
        for(Task task : tasksList.values()) {
            taskName += task.getName();
        }

        return "TaskManager{" +
            "tasksList=" + taskName +
            '}';
    }
}
