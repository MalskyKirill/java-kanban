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
        Task task = new Task(name, description, taskId, Status.NEW); // создаем новую задачку
        tasksList.put(taskId, task); // кидаем задачку в мапу
        taskId++; // увеличиваем айдишник
        System.out.println("Задачка " + task.getName() + " добавлена на доску!");
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
        System.out.println("Простых задачет теперь нет!");
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

    public void updateTaskById(Scanner scanner, int id) { // меотод обнавления задачки
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("задачек пока нет");
            return; // вылетаем
        }

        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
            Task oldTask = tasksList.get(id); // в мапе находим задачку которую нужно изменить

            System.out.println("Какой параметр вы хотите обновить?"); // менюшка
            System.out.println("1 - имя");
            System.out.println("2 - описание");
            System.out.println("3 - статус");
            String param = scanner.nextLine().trim(); // считаваем условие

            switch (param) { // в цикл передаем параметр
                case "1": // если один то поменять имя
                    System.out.println("Введите новое имя задачи:");
                    String newName = scanner.nextLine().trim(); // сохраняем введенное новае имя
                    // создаем новый обьект с аргумантами новое имя, а остальные аргументы берем из старого обьекта
                    Task newTaskName = new Task(newName, oldTask.getDescription(), oldTask.getId(), oldTask.getStatus());
                    tasksList.put(oldTask.getId(), newTaskName); // перезаписываем старый оьект на новый
                    System.out.println("Имя задачки изменено");
                    break;
                case "2": // тоже что и в первом кейсе только с описанием
                    System.out.println("Введите новое описание задачи:");
                    String newDescription = scanner.nextLine().trim();

                    Task newTaskDescription = new Task(oldTask.getName(), newDescription, oldTask.getId(), oldTask.getStatus());
                    tasksList.put(oldTask.getId(), newTaskDescription);
                    System.out.println("Описание задачки изменено");
                    break;
                case "3": // если три то меняем статус
                    System.out.println("Введите новый статус задачи:");
                    System.out.println("1 - новая");
                    System.out.println("2 - в прогрессе");
                    System.out.println("3 - выполнена");

                    String newStatus = scanner.nextLine().trim(); // считываем номар
                    Task newTaskStatus; // обьявляем переменную для ссылки на задачу

                    if(newStatus.equals("1")) { // если надо поменять на новую
                        // инициализируем переменную ссылкой на новый обьект
                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(),
                            oldTask.getId(), Status.NEW);
                        tasksList.put(oldTask.getId(), newTaskStatus); // перезаписываем обект в мапу
                        System.out.println("Задачка " + oldTask.getName() + " вернулась в статус новая");
                    } else if (newStatus.equals("2")) { // если поменять на в прогрессе
                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(),
                            oldTask.getId(), Status.IN_PROGRESS);
                        tasksList.put(oldTask.getId(), newTaskStatus);
                        System.out.println("Задачка " + oldTask.getName() + " теперь в прогрессе");
                    } else if (newStatus.equals("3")) { // если нужно поменять на завершенную
                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(),
                            oldTask.getId(), Status.DONE);
                        tasksList.put(oldTask.getId(), newTaskStatus);
                        System.out.println("Задачка " + oldTask.getName() + " завершена :)");
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

    public void removeTaskById(int id) {
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("задачек пока нет");
            return; // вылетаем
        }

        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
            String removeTask = tasksList.get(id).getName();
            tasksList.remove(id); // если ок, удаляем из мапы задачку по айдишнику
            System.out.println("задачка " + removeTask + " удалена");
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
