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
        task.setId(taskId); // обогатили задачку id
        tasksList.put(taskId, task); // кидаем задачку в мапу
        taskId++; // увеличиваем айдишник
        return task;
    }

    public Epic addNewEpic(Epic epic) { // метод добавления эпика в хешмапу
        epic.setId(taskId); // обогатили эпик id
        epicList.put(taskId, epic); // кидаем эпик в мапу
        taskId++; // увеличиваем айдишник
        return epic;
    }

    public SubTask addNewSubTask(SubTask subTask) { // метод добавления субтаски в хешмапу
        int epicId = subTask.getEpicId(); // получил айди эпика
        Epic epic = epicList.get(epicId); //  получили эпик

        subTask.setId(taskId); // обогатили подзадачку айдишником
        epic.addSubTaskById(subTask.getId()); // добавляем подзадачку в эпик
        subTaskList.put(taskId, subTask); // добавляем подзадачу в мапу подзадач
        taskId++; // увеличиваем айдишник

        setEpicStatus(epicId); // проверили статус эпика
        return subTask; // вернули обьект подзадачи
    }

    private void setEpicStatus(int epicId) { // метод изменение статуса эпика
        if (!epicList.containsKey(epicId)) {
            System.out.println("Такого эпика нет в списке");
            return;
        }

        Epic epic = epicList.get(epicId); // достаем эпик
        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список id поздадач эпика
        if (epicSubTaskIdList.isEmpty()) { // проверяем, ежели в список пуст
            epic.setStatus(Status.NEW); // устанавливаем на статус нью
            return; // завершаем метод
        }

        ArrayList<Status> subTaskListStatus = new ArrayList<>(); // создаем список для статусов каждой подзадачки
        // счетчики статусов
        int countnNewSubTask = 0;
        int countnProgressSubTask = 0;
        int countnDoneSubTask = 0;

        for (int SubTaskId : epicSubTaskIdList) { // пробигаемся циклом по списку айдишников
            Status status = subTaskList.get(SubTaskId).getStatus(); // достаем для каждого айдишника статус
            subTaskListStatus.add(status); // статус записываем в subTaskListStatus

            if (status == Status.NEW) { // если статус равен Status.NEW
                countnNewSubTask++; // увеличиваем счетчик
            } else if (status == Status.IN_PROGRESS) {
                countnProgressSubTask++;
            } else if (status == Status.DONE) {
                countnDoneSubTask++;
            }
        }

        if (countnNewSubTask == subTaskListStatus.size()) { // проверяем что если все задачки со статусом new
            epic.setStatus(Status.NEW);
        } else if (countnDoneSubTask == subTaskListStatus.size()) { // ежели все со статусом done
            epic.setStatus(Status.DONE);
        } else { // а здесь если есть хоть один и не new и не done
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    public ArrayList<Task> getAllTasks() { // метод получения всех задачек
        ArrayList<Task> allTasks = new ArrayList<>(); // создаем список для задачек
        for (Task task : tasksList.values()) {
            allTasks.add(task);
        }

        return allTasks;
    }

    public ArrayList<Epic> getAllEpics() { // метод получения всех эпиков
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic task : epicList.values()) {
            allEpics.add(task);
        }

        return allEpics;
    }

    public ArrayList<SubTask> getAllSubTask() { // метод получения всех подзадачек
        ArrayList<SubTask> allSubTask = new ArrayList<>();
        for (SubTask task : subTaskList.values()) {
            allSubTask.add(task);
        }

        return allSubTask;
    }

    public ArrayList<SubTask> getAllSubtasksByEpic(int epicId) { // метод получения подзадач эпика
        ArrayList<SubTask> epicSubTaskList = new ArrayList<>(); // создали список для подзадач

        if (epicList.containsKey(epicId)) { // если эпик есть в списке
            Epic epic = epicList.get(epicId); // получаем эпик

            ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список id поздадач эпика

            if (epicSubTaskIdList.isEmpty()) { // проверяем что задачки есть
                System.out.println("Эпик пока пустой");
                return epicSubTaskList; // возвращаем пустой список
            }

            for (Integer subTaskId : epicSubTaskIdList) { // пробегаемся по списку подзадач
                SubTask newSubTask = subTaskList.get(subTaskId); // достаем подзадачку и мапы подзадачек
                epicSubTaskList.add(newSubTask); // добавляем ее в список эпика
            }
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

        epicList.clear(); // удалили все эпики
        subTaskList.clear(); // удалили все подзадачи
    }

    public void removeAllSubTasks() { // удаляем все подзадачи
        if (subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        subTaskList.clear(); // удалили все подзадачи

        for (Epic epic : epicList.values()) { // пробежались по списку эпиков
            epic.clearSubTasksIdList(); // у каждого эпика очистили список айдишников подзадач
            setEpicStatus(epic.getId()); // пересчитали статус
        }
    }

    public Task getTaskById(int taskId) { // получаем задачку по айди
        return tasksList.get(taskId); // достаем и возвращаем обьект
    }

    public Epic getEpicById(int epicId) { // получаем эпик по айди
        return epicList.get(epicId);
    }

    public SubTask getSubTaskById(int subTaskId) { // получаем подзадачку по айди
        return subTaskList.get(subTaskId);
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

        int epicId = epic.getId(); // берем айдишник эпика
        if (epicList.containsKey(epicId)) { // если эпик с таким ключем есть в списке
            Epic updateEpic = epicList.get(epicId); // находим эпик для обновления в списке
            updateEpic.setName(epic.getName()); // устанавливаем новое имя
            updateEpic.setDescription(epic.getDescription()); // и описание
        } else { // ежели нет
            System.out.println("такой задачки нет");
        }
    }

    public void updateSubTask(SubTask subTask) { // обновляем подзадачку
        if (subTask == null) { // проверка
            return;
        }

        int subTaskId = subTask.getId(); // берем айди подзадачи
        int subTaskIdByEpic = subTask.getEpicId(); // берем айдишник эпика из подзалдачи
        int oldSubTaskIdByEpic = subTaskList.get(subTaskId).getEpicId(); // берем старую подзадачку по айдишнику из списка и достаем оттуда айди эпика

        if (subTaskList.containsKey(subTaskId) && subTaskIdByEpic == oldSubTaskIdByEpic) { // если такой ключ есть в списке
            subTaskList.put(subTaskId, subTask); // обновляем подзадачу
            Epic epic = epicList.get(subTaskIdByEpic); // берем эпик
            setEpicStatus(epic.getId());// обновляем статус эпика
        }
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
