package kanban.service;

import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {

    //создал хеш-маппу для хранения тасочек
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskList = new HashMap<>();
    private int taskId = 1;

    public Task addNewTask(Task task) { // метод добавления задачки в хешмапу
        Task newTask = new Task(task.getName(), task.getDescription(), taskId, task.getStatus()); // создаем новую задачку
        tasksList.put(taskId, task); // кидаем задачку в мапу
        taskId++; // увеличиваем айдишник
        return newTask;
    }

    public Epic addNewEpic(Epic epic) { // метод добавления эпика в хешмапу
        Epic newEpic = new Epic(epic.getName(), epic.getDescription(), taskId); // создаем новый эпик
        epicList.put(taskId, epic); // кидаем эпик в мапу
        taskId++; // увеличиваем айдишник
        return newEpic;
    }

//    public void addNewSubTask(int epicId, Scanner scanner) { // метод добавления субтаски в хешмапу
//
//        if (!epicList.containsKey(epicId)) { // проверяем есть ли айди эпика для которого создаем субтаску
//            System.out.println("Эпика с таким id пока нет");
//            return; // если нет, вылетаем
//        }
//
//        Epic epic = epicList.get(epicId); // если эпик существует, то получаем его по айдишнику и заисываем ссылку на него в переменную
//
//        ArrayList<Integer> getSubTaskIdList = epic.getSubTaskId(); // получаем список айдишников подзадач у эпика
//        getSubTaskIdList.add(taskId); // добавляем в него айдишник новой подзадачи
//        epic.setSubTaskId(getSubTaskIdList); // обнавляем список айдишников подзадач у эпика
//
//        System.out.println("Введите имя подзадачи:");
//        String name = scanner.nextLine().trim(); // считываем имя
//        System.out.println("Введите описание подзадачи:");
//        String description = scanner.nextLine().trim(); // считываем описание
//
//        SubTask subTask = new SubTask(name, description, taskId, Status.NEW, epicId); // создаем новую подзадачу и записываем в нее id эпика к которому она относится
//        subTaskList.put(taskId, subTask); // добавляем подзадачу в мапу подзадач
//        taskId++; // увеличиваем айдишник
//
//        System.out.println("Подзадача " + subTask.getName() + " добавлена в эпик " + epic.getName());
//    }

//    public void getAllTasks(String category) { // печатаем все задачки из категории
//        if (category.equals("простые")) { // проверяем категорию
//            if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
//                System.out.println("Простых задачек пока нет");
//                return; // вылетаем
//            }
//
//            for (Task task : tasksList.values()) { // провегаемся по значениям хешмапы и печатаем
//                System.out.println("Задача - " + task.getName()); // имя
//                System.out.println("Описание - " + task.getDescription()); // описание
//                System.out.println("Статус - " + task.getStatus()); // статус
//                System.out.println("Id - " + task.getId()); // получаем айдишник
//                System.out.println("");
//
//            }
//        } else if (category.equals("эпики")) {
//            if (epicList.isEmpty()) { // проверяем что хешмапа не пустая
//                System.out.println("Эпиков пока нет");
//                return; // вылетаем
//            }
//
//            for (Epic task : epicList.values()) { // провегаемся по значениям хешмапы и печатаем
//                System.out.println("Эпик - " + task.getName()); // имя
//                System.out.println("Описание - " + task.getDescription()); // описание
//                System.out.println("Статус - " + task.getStatus()); // статус
//                System.out.println("Id - " + task.getId()); // получаем айдишник
//            }
//        }
//    }

//    public void getSubTasksByEpicId(int epicId) {
//        Epic epic = epicList.get(epicId); // получаем эпик по айди
//
//        if (epic == null) { // проверяем что эпик есть
//            System.out.println("Такого эпика не существует");
//            return;// если нет вылетаем
//        }
//
//        if (epic.getSubTaskId().isEmpty()) { // проверяем что в эпики есть айдишники подзадачек
//            System.out.println("В эпике нет подзадач");
//            return;// если нет вылетаем
//        }
//
//        ArrayList<Integer> epicSubTaskIdList = epic.getSubTaskId(); // получаем список id поздадач эпика
//
//        for (Integer subtaskId : epicSubTaskIdList) { // пробегаемся по списочку id поздадач
//            SubTask subtask = subTaskList.get(subtaskId); // достаем подзадачку
//            System.out.println(subtask.toString()); // вызываем у нее переопределенный метод toString
//        }
//    }

//    public void removeAllTasks() { // метод удаления всех задачек
//        if (tasksList.isEmpty() && epicList.isEmpty() && subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
//            System.out.println("Пока нечего удалять :)");
//            return; // вылетаем
//        }
//        tasksList.clear(); // очищаем мапу
//        System.out.println("Задачет теперь нет!");
//    }
//
//    public Task getTaskById(int id, int category) {
//        if (tasksList.isEmpty() && epicList.isEmpty() && subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
//            System.out.println("задачек пока нет");
//            return null; // возвращаем null
//        }
//
//        switch (category) {
//            case 1:
//                if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
//                    Task task = tasksList.get(id); // если ок, достаем из мапы задачку по айдишнику
//                    return task; // возвращаем обьектик
//                } else { // ежели нет
//                    System.out.println("задачи с таким id нет");
//                    return null; // возвращаем null
//                }
//            case 2:
//                if (epicList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
//                    Task task = epicList.get(id); // если ок, достаем из мапы эпик по айдишнику
//                    return task; // возвращаем обьектик
//                } else { // ежели нет
//                    System.out.println("эпика с таким id нет");
//                    return null; // возвращаем null
//                }
//            case 3:
//                if (subTaskList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
//                    Task task = subTaskList.get(id); // если ок, достаем из мапы подзадачку по айдишнику
//                    return task; // возвращаем обьектик
//                } else { // ежели нет
//                    System.out.println("подзадачки с таким id нет");
//                    return null; // возвращаем null
//                }
//            default:
//                System.out.println("такой категории нет");
//                return null;
//        }
//    }
//
//    public void updateTaskById(Scanner scanner, int id) { // меотод обнавления задачки
//        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
//            System.out.println("задачек пока нет");
//            return; // вылетаем
//        }
//
//        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
//            Task oldTask = tasksList.get(id); // в мапе находим задачку которую нужно изменить
//
//            System.out.println("Какой параметр вы хотите обновить?"); // менюшка
//            System.out.println("1 - имя");
//            System.out.println("2 - описание");
//            System.out.println("3 - статус");
//            String param = scanner.nextLine().trim(); // считаваем условие
//
//            switch (param) { // в цикл передаем параметр
//                case "1": // если один то поменять имя
//                    System.out.println("Введите новое имя задачи:");
//                    String newName = scanner.nextLine().trim(); // сохраняем введенное новае имя
//                    // создаем новый обьект с аргумантами новое имя, а остальные аргументы берем из старого обьекта
//                    Task newTaskName = new Task(newName, oldTask.getDescription(), oldTask.getId(), oldTask.getStatus());
//                    tasksList.put(oldTask.getId(), newTaskName); // перезаписываем старый оьект на новый
//                    System.out.println("Имя задачки изменено");
//                    break;
//                case "2": // тоже что и в первом кейсе только с описанием
//                    System.out.println("Введите новое описание задачи:");
//                    String newDescription = scanner.nextLine().trim();
//
//                    Task newTaskDescription = new Task(oldTask.getName(), newDescription, oldTask.getId(), oldTask.getStatus());
//                    tasksList.put(oldTask.getId(), newTaskDescription);
//                    System.out.println("Описание задачки изменено");
//                    break;
//                case "3": // если три то меняем статус
//                    System.out.println("Введите новый статус задачи:");
//                    System.out.println("1 - новая");
//                    System.out.println("2 - в прогрессе");
//                    System.out.println("3 - выполнена");
//
//                    String newStatus = scanner.nextLine().trim(); // считываем номар
//                    Task newTaskStatus; // обьявляем переменную для ссылки на задачу
//
//                    if(newStatus.equals("1")) { // если надо поменять на новую
//                        // инициализируем переменную ссылкой на новый обьект
//                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(),
//                            oldTask.getId(), Status.NEW);
//                        tasksList.put(oldTask.getId(), newTaskStatus); // перезаписываем обект в мапу
//                        System.out.println("Задачка " + oldTask.getName() + " вернулась в статус новая");
//                    } else if (newStatus.equals("2")) { // если поменять на в прогрессе
//                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(),
//                            oldTask.getId(), Status.IN_PROGRESS);
//                        tasksList.put(oldTask.getId(), newTaskStatus);
//                        System.out.println("Задачка " + oldTask.getName() + " теперь в прогрессе");
//                    } else if (newStatus.equals("3")) { // если нужно поменять на завершенную
//                        newTaskStatus = new Task(oldTask.getName(), oldTask.getDescription(),
//                            oldTask.getId(), Status.DONE);
//                        tasksList.put(oldTask.getId(), newTaskStatus);
//                        System.out.println("Задачка " + oldTask.getName() + " завершена :)");
//                    } else {
//                        System.out.println("Такого статуса нет");
//                    }
//                    break;
//                default:
//                    System.out.println("Такого параметра нет");
//            }
//        } else { // ежели нет
//            System.out.println("задачи с таким id нет");
//        }
//    }

//    public void updateSubTaskById (Scanner scanner, int subTaskId, int epicId) {
//        Epic epic = epicList.get(epicId);
//
//        if (!epic.getSubTaskId().contains(subTaskId)) {
//            System.out.println("В эпике нет задачки с данным id");
//            return;
//        }
//        Task oldSubTask = subTaskList.get(subTaskId); // в мапе находим подзадачу которую нужно изменить
//
//        System.out.println("Какой параметр вы хотите обновить?"); // менюшка
//        System.out.println("1 - имя");
//        System.out.println("2 - описание");
//        System.out.println("3 - статус");
//        String param = scanner.nextLine().trim(); // считаваем условие
//
//        switch (param) { // в цикл передаем параметр
//            case "1": // если один то поменять имя
//                System.out.println("Введите новое имя задачи:");
//                String newName = scanner.nextLine().trim(); // сохраняем введенное новае имя
//                // создаем новый обьект с аргумантами новое имя, а остальные аргументы берем из старого обьекта
//                SubTask newSubTaskName = new SubTask(newName, oldSubTask.getDescription(), oldSubTask.getId(), oldSubTask.getStatus(), epicId);
//                tasksList.put(oldSubTask.getId(), newSubTaskName); // перезаписываем старый оьект на новый
//                System.out.println("Имя задачки изменено");
//                break;
//            case "2": // тоже что и в первом кейсе только с описанием
//                System.out.println("Введите новое описание задачи:");
//                String newDescription = scanner.nextLine().trim();
//
//                SubTask newSubTaskDescription = new SubTask(oldSubTask.getName(), newDescription, oldSubTask.getId(), oldSubTask.getStatus(), epicId);
//                tasksList.put(oldSubTask.getId(), newSubTaskDescription);
//                System.out.println("Описание задачки изменено");
//                break;
//            case "3": // если три то меняем статус
//                System.out.println("Введите новый статус задачи:");
//                System.out.println("1 - новая");
//                System.out.println("2 - в прогрессе");
//                System.out.println("3 - выполнена");
//
//                String newStatus = scanner.nextLine().trim(); // считываем номар
//                SubTask newSubTaskStatus; // обьявляем переменную для ссылки на задачу
//
//                if (newStatus.equals("1")) { // если надо поменять на новую
//                    // инициализируем переменную ссылкой на новый обьект
//                    newSubTaskStatus = new SubTask(oldSubTask.getName(), oldSubTask.getDescription(),
//                        oldSubTask.getId(), Status.NEW, epicId);
//                    subTaskList.put(oldSubTask.getId(), newSubTaskStatus); // перезаписываем обект в мапу
//                    System.out.println("Задачка " + oldSubTask.getName() + " вернулась в статус новая");
//                } else if (newStatus.equals("2")) { // если поменять на в прогрессе
//                    newSubTaskStatus = new SubTask(oldSubTask.getName(), oldSubTask.getDescription(),
//                        oldSubTask.getId(), Status.IN_PROGRESS, epicId);
//                    subTaskList.put(oldSubTask.getId(), newSubTaskStatus);
//                    System.out.println("Задачка " + oldSubTask.getName() + " теперь в прогрессе");
//                } else if (newStatus.equals("3")) { // если нужно поменять на завершенную
//                    newSubTaskStatus = new SubTask(oldSubTask.getName(), oldSubTask.getDescription(),
//                        oldSubTask.getId(), Status.DONE, epicId);
//                    subTaskList.put(oldSubTask.getId(), newSubTaskStatus);
//                    System.out.println("Задачка " + oldSubTask.getName() + " завершена :)");
//                } else {
//                    System.out.println("Такого статуса нет");
//                }
//                break;
//            default:
//                System.out.println("Такого параметра нет");
//        }
//
//        // установка значения статуса эпика
//        ArrayList<Integer> epicSubTaskIdList = epic.getSubTaskId(); // получаем список id поздадач эпика
//
//        ArrayList<Status> subTaskListStatus = new ArrayList<>(); // создаем список для статусов каждой подзадачки
//
//        // счетчики статусов
//        int countnNewSubTask = 0;
//        int countnProgressSubTask = 0;
//        int countnDoneSubTask = 0;
//
//        for (int SubTaskId : epicSubTaskIdList) { // пробигаемся циклом по списку айдишников
//            subTaskListStatus.add(subTaskList.get(SubTaskId).getStatus()); // достаем для каждого айдишника статус и записываем в subTaskListStatus
//        }
//
//        for (Status status : subTaskListStatus) { // пробегаемся по списку статусов
//            if (status == Status.NEW) { // если статус равен Status.NEW
//                countnNewSubTask++; // увеличиваем счетчик
//            } else if (status == Status.IN_PROGRESS) {
//                countnProgressSubTask++;
//            } else if (status == Status.DONE) {
//                countnDoneSubTask++;
//            }
//        }
//
//        if (countnNewSubTask == subTaskListStatus.size()) { // проверяем что если все задачки со статусом new
//            Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId(), Status.NEW, epic.getSubTaskId()); // создаем эпик ср статусом нью
//            epicList.put(epic.getId(), newEpic); // и перезаписываем его в epicList
//            System.out.println("эпик" + oldSubTask.getName() + " опять со статусом new");
//        } else if (countnDoneSubTask == subTaskListStatus.size()) { // ежели все со статусом done
//            Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId(), Status.DONE, epic.getSubTaskId());
//            epicList.put(epic.getId(), newEpic);
//            System.out.println("эпик" + oldSubTask.getName() + " со статусом done");
//        } else { // а здесь если есть хоть один и не new и не done
//            Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId(), Status.IN_PROGRESS, epic.getSubTaskId());
//            epicList.put(epic.getId(), newEpic);
//            System.out.println("эпик" + oldSubTask.getName() + " со статусом IN_PROGRESS");
//        }
//    }

//    public void removeTaskById(int id) {
//        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
//            System.out.println("задачек пока нет");
//            return; // вылетаем
//        }
//
//        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
//            String removeTask = tasksList.get(id).getName();
//            tasksList.remove(id); // если ок, удаляем из мапы задачку по айдишнику
//            System.out.println("задачка " + removeTask + " удалена");
//        } else { // ежели нет
//            System.out.println("задачи с таким id нет");
//        }
//    }
//
//    @Override
//    public String toString() {
//        String taskName = "";
//        for(Task task : tasksList.values()) {
//            taskName += task.getName();
//        }
//
//        return "TaskManager{" +
//            "tasksList=" + taskName +
//            '}';
//    }
}
