package kanban.service;

import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

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

    public SubTask addNewSubTask(int epicId, SubTask subTask) { // метод добавления субтаски в хешмапу

        if (!epicList.containsKey(epicId)) { // проверяем есть ли айди эпика для которого создаем субтаску
            System.out.println("Эпика с таким id пока нет");
            return null; // если нет, вылетаем
        }

        Epic epic = epicList.get(epicId); // если эпик существует, то получаем его по айдишнику и заисываем ссылку на него в переменную

        epic.addSubTaskById(taskId); // добавляем подзадачку в эпик

        SubTask newSubTask = new SubTask(subTask.getName(), subTask.getDescription(), taskId, subTask.getStatus(), epicId); // создаем новую подзадачу и записываем в нее id эпика к которому она относится
        subTaskList.put(taskId, newSubTask); // добавляем подзадачу в мапу подзадач
        taskId++; // увеличиваем айдишник

        setEpicStatus(epicId); // проверили статус эпика

        return newSubTask; // вернули обьект подзадачи
    }

    private void setEpicStatus(int epicId) { // метод изменение статуса эпика
        if (!epicList.containsKey(epicId)) {
            System.out.println("Такого эпика нет в списке");
            return;
        }

        Epic epic = epicList.get(epicId);

        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список id поздадач эпика

        ArrayList<Status> subTaskListStatus = new ArrayList<>(); // создаем список для статусов каждой подзадачки

        // счетчики статусов
        int countnNewSubTask = 0;
        int countnProgressSubTask = 0;
        int countnDoneSubTask = 0;

        for (int SubTaskId : epicSubTaskIdList) { // пробигаемся циклом по списку айдишников
            subTaskListStatus.add(subTaskList.get(SubTaskId).getStatus()); // достаем для каждого айдишника статус и записываем в subTaskListStatus
        }

        for (Status status : subTaskListStatus) { // пробегаемся по списку статусов
            if (status == Status.NEW) { // если статус равен Status.NEW
                countnNewSubTask++; // увеличиваем счетчик
            } else if (status == Status.IN_PROGRESS) {
                countnProgressSubTask++;
            } else if (status == Status.DONE) {
                countnDoneSubTask++;
            }
        }

        if (countnNewSubTask == subTaskListStatus.size()) { // проверяем что если все задачки со статусом new
            Epic newEpic = new Epic(epic.getName(), epic.getDescription(), Status.NEW, epicSubTaskIdList); // создаем эпик ср статусом нью
            epicList.put(epic.getId(), newEpic); // и перезаписываем его в epicList
        } else if (countnDoneSubTask == subTaskListStatus.size()) { // ежели все со статусом done
            Epic newEpic = new Epic(epic.getName(), epic.getDescription(), Status.DONE, epicSubTaskIdList);
            epicList.put(epic.getId(), newEpic);
        } else { // а здесь если есть хоть один и не new и не done
            Epic newEpic = new Epic(epic.getName(), epic.getDescription(), Status.IN_PROGRESS, epicSubTaskIdList);
            epicList.put(epic.getId(), newEpic);
        }
    }

    public ArrayList<Task> getAllTasks() { // метод получения всех задачек
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
                System.out.println("Простых задачек пока нет");
                return null; // вылетаем
            }

        ArrayList<Task> allTasks = new ArrayList<>(); //
        for (Task task : tasksList.values()) {
            allTasks.add(task);
        }

        return allTasks;
    }

    public ArrayList<Epic> getAllEpics() { // метод получения всех задачек
        if (epicList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Простых задачек пока нет");
            return null; // вылетаем
        }

        ArrayList<Epic> allEpics = new ArrayList<>(); //
        for (Epic task : epicList.values()) {
            allEpics.add(task);
        }

        return allEpics;
    }

    public ArrayList<SubTask> getAllSubtasksByEpic(int epicId) { // метод получения подзадач эпика
        if (!epicList.containsKey(epicId)) { // проверяем что эпик есть в списке
            System.out.println("Такого эпика нет в списке");
            return null;
        }

        Epic epic = epicList.get(epicId); // получаем эпик

        ArrayList<SubTask> epicSubTaskList = new ArrayList<>(); // создали список для подзадач
        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список id поздадач эпика

        if (epicSubTaskIdList.isEmpty()) { // проверяем что задачки есть
            System.out.println("Эпик пока пустой");
            return null;
        }

        for (Integer subTaskId : epicSubTaskIdList) { // пробегаемся по списку подзадач
            SubTask newSubTask = subTaskList.get(subTaskId); // достаем подзадачку и мапы подзадачек
            epicSubTaskList.add(newSubTask); // добавляем ее в список эпика
        }

        return epicSubTaskList; // возвращаем список
    }

    public void removeAllTasks() { // удаляев все задачки
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        tasksList.clear();
    }

    public void removeAllEpics() { // удаляем все эпики
        if (epicList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        epicList.clear();
    }

    public void removeAllSubTasks() { // удаляем все подзадачи
        if (subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        subTaskList.clear();
    }

    public Task getTaskById(int taskId) { // удаляем задачку по айди
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return null; // вылетаем
        }

        if (tasksList.containsKey(taskId)) { // если в мапе есть ключик
            return tasksList.get(taskId); // достаем и возвращаем обьект
        } else { //ежели нет
            return null; //возвращаем null
        }
    }

    public Epic getEpicById(int epicId) { // удаляем эпик по айди
        if (epicList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return null; // вылетаем
        }

        if (epicList.containsKey(epicId)) {
            return epicList.get(epicId);
        } else {
            return null;
        }
    }

    public SubTask getSubTaskById(int subTaskId) { // удаляем подзадачку по айди
        if (subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return null; // вылетаем
        }

        if (subTaskList.containsKey(subTaskId)) {
            return subTaskList.get(subTaskId);
        } else {
            return null;
        }
    }

    public void updateTask(Task task) { // обновляем задачку
        if (task == null) { // если пришел null
            return; // вылетаем
        }

        int taskId = task.getId(); // берем айдишник новой задачки
        if (tasksList.containsKey(taskId)) { // если он есть как ключ в списке задач
            tasksList.put(taskId, task); // обновляем задачку
        } else { // ежели нет
            System.out.println("такой задачки нет");
        }
    }

    public void updateEpic(Epic epic) {  // обновляем эпик
        if (epic == null) { // проверка
            return;
        }

        int epicId = epic.getId(); // берем айдишник новго эпика
        Status epicStatus = epicList.get(epicId).getStatus(); // берем статус
        Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epicStatus, epic.getSubTasksIdList()); // создаем новый эпик для обновления

        if (epicList.containsKey(epicId)) { // если эпик с таким ключем есть в списке
            epicList.put(epicId, newEpic); // обновляем эпик
            setEpicStatus(newEpic.getId()); // проверяем его статус
        } else { // ежели нет
            System.out.println("такой задачки нет");
        }
    }

    public void updateSubTask(SubTask subTask) { // обновляем подзадачку
        if (subTask == null) { // проверка
            return;
        }

        int subTaskId = subTask.getId(); // берем айди подзадачи

        if (subTaskList.containsKey(subTaskId)) { // если такой ключ есть в списке
            subTaskList.put(subTaskId, subTask); // обновляем подзадачу
        }

        int subTaskIdByEpic = subTask.getEpicId(); // берем айдишник эпика из подзалдачи
        Epic epic = epicList.get(subTaskIdByEpic); // берем эпик
        setEpicStatus(epic.getId());// обновляем статус эпика
    }

    public void removeTaskById(int taskId) { // удалить задачку по айди
        if (tasksList.containsKey(taskId)) { // проверяем в списке есть ли ключ
            tasksList.remove(taskId); // удаляем задачку
        } else {
            System.out.println("такой задачки нет");
        }
    }

    public void removeEpicById(int epicId) { // удалить эпик по айди
        if (epicList.containsKey(epicId)) { // проверяем есть ли ключ в мапе
            Epic epic = epicList.get(epicId); // достаем эпик по айди
            for (int subTask : epic.getSubTasksIdList()) { // пробегаемся по спику айдишников подзадач эпика
                subTaskList.remove(subTask); // удаляем из списка подзадач те кторые принадлежат эпику
            }

            epicList.remove(epicId); // удаляем эпик
        } else {
            System.out.println("такого эпика нет");
        }
    }

    public void removeSubTaskById(int subTaskId) { // удалить подзадачу
        if (subTaskList.containsKey(subTaskId)) { // проверяем есть ли подзадача
            SubTask subTask = subTaskList.get(subTaskId); // получаем подзадачу из списка

            int epicId = subTask.getEpicId(); // получаем айди эпика из подзадачи
            Epic epic = epicList.get(epicId); // получаем эпик которому пренадлежит подзадача
            if (epic != null) { // проверяем что эпик не null
                epic.removeSubtaskById(epicId); // удаляем айдишник подзадачки из списка у эпика
                setEpicStatus(epic.getId()); // пересчитываем статус эпика
            }

            subTaskList.remove(subTaskId); // удаляем подзадачку из мапы
        } else {
            System.out.println("такого подзадачки нет");
        }
    }
}
