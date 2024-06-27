package kanban.service;

import kanban.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    //создал хеш-маппу для хранения тасочек
    protected Map<Integer, Task> tasksList = new HashMap<>();
    protected Map<Integer, Epic> epicList = new HashMap<>();
    protected Map<Integer, SubTask> subTaskList = new HashMap<>();
    protected int taskId = 1;

    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public Task addNewTask(Task task) { // метод добавления задачки в хешмапу
        task.setId(taskId); // обогатили задачку id
        tasksList.put(taskId, task); // кидаем задачку в мапу
        taskId++; // увеличиваем айдишник
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) { // метод добавления эпика в хешмапу
        epic.setId(taskId); // обогатили эпик id
        epicList.put(taskId, epic); // кидаем эпик в мапу
        taskId++; // увеличиваем айдишник
        return epic;
    }

    @Override
    public SubTask addNewSubTask(SubTask subTask) { // метод добавления субтаски в хешмапу
        int epicId = subTask.getEpicId(); // получил айди эпика
        Epic epic = epicList.get(epicId); //  получили эпик

        if (epic != null) { // проверка на null
            subTask.setId(taskId); // обогатили подзадачку айдишником
            epic.addSubTaskById(subTask.getId()); // добавляем подзадачку в эпик
            subTaskList.put(taskId, subTask); // добавляем подзадачу в мапу подзадач
            taskId++; // увеличиваем айдишник

            setEpicStatus(epicId); // проверили статус эпика
            epicStartTime(epic);
        }
        return subTask; // вернули обьект подзадачи
    }

    @Override
    public ArrayList<Task> getAllTasks() { // метод получения всех задачек
        ArrayList<Task> allTasks = new ArrayList<>(); // создаем список для задачек
        for (Task task : tasksList.values()) {
            allTasks.add(task);
        }

        return allTasks;
    }

    @Override
    public ArrayList<Epic> getAllEpics() { // метод получения всех эпиков
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Epic task : epicList.values()) {
            allEpics.add(task);
        }

        return allEpics;
    }

    @Override
    public ArrayList<SubTask> getAllSubTask() { // метод получения всех подзадачек
        ArrayList<SubTask> allSubTask = new ArrayList<>();
        for (SubTask task : subTaskList.values()) {
            allSubTask.add(task);
        }

        return allSubTask;
    }

    @Override
    public ArrayList<SubTask> getAllSubtasksByEpic(int epicId) { // метод получения подзадач эпика
        ArrayList<SubTask> epicSubTaskList = new ArrayList<>(); // создали список для подзадач

        if (epicList.containsKey(epicId)) { // если эпик есть в списке
            Epic epic = epicList.get(epicId); // получаем эпик

            ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список id поздадач эпика

            if (epicSubTaskIdList.isEmpty()) { // проверяем что задачки есть
                System.out.println("Эпик пока пустой");
                return epicSubTaskList; // возвращаем пустой список
            }

            for (int subTaskId : epicSubTaskIdList) { // пробегаемся по списку подзадач
                SubTask newSubTask = subTaskList.get(subTaskId); // достаем подзадачку и мапы подзадачек
                epicSubTaskList.add(newSubTask); // добавляем ее в список эпика
            }
        }

        return epicSubTaskList; // возвращаем список
    }

    @Override
    public void removeAllTasks() { // удаляев все задачки
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        for (int id : tasksList.keySet()) { // пробегаемся по списку ключей из листа задач
            inMemoryHistoryManager.remove(id); // вызываем метод remove для каждой задачки в inMemoryHistoryManager
        }

        tasksList.clear();
    }

    @Override
    public void removeAllEpics() { // удаляем все эпики
        if (epicList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        for (int id : epicList.keySet()) { // пробегаемся по списку ключей из листа эпиков
            inMemoryHistoryManager.remove(id);
        }
        epicList.clear(); // удалили все эпики

        for (int id : subTaskList.keySet()) { // пробегаемся по списку ключей из листа подзадачек
            inMemoryHistoryManager.remove(id);
        }
        subTaskList.clear(); // удалили все подзадачи
    }

    @Override
    public void removeAllSubTasks() { // удаляем все подзадачи
        if (subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        for (int id : subTaskList.keySet()) { // пробегаемся по списку ключей из листа подзадачек
            inMemoryHistoryManager.remove(id);
        }
        subTaskList.clear(); // удалили все подзадачи

        for (Epic epic : epicList.values()) { // пробежались по списку эпиков
            epic.clearSubTasksIdList(); // у каждого эпика очистили список айдишников подзадач
            setEpicStatus(epic.getId()); // пересчитали статус
        }
    }

    @Override
    public Task getTaskById(int taskId) { // получаем задачку по айди
        Task task = tasksList.get(taskId);
        inMemoryHistoryManager.addTask(task);
        return task; // достаем и возвращаем обьект
    }

    @Override
    public Epic getEpicById(int epicId) { // получаем эпик по айди
        Epic epic = epicList.get(epicId);
        inMemoryHistoryManager.addTask(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) { // получаем подзадачку по айди
        SubTask subTask = subTaskList.get(subTaskId);
        inMemoryHistoryManager.addTask(subTask);
        return subTask;
    }

    @Override
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

    @Override
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

    @Override
    public void updateSubTask(SubTask subTask) { // обновляем подзадачку
        if (subTask == null) { // проверка
            return;
        }

        int subTaskId = subTask.getId(); // берем айди подзадачи
        if (subTaskList.containsKey(subTaskId)) { // проверяем что такая подзадача есть в списке
            int subTaskIdByEpic = subTask.getEpicId(); // берем айдишник эпика из подзалдачи
            int oldSubTaskIdByEpic = subTaskList.get(subTaskId).getEpicId(); // берем старую подзадачку по айдишнику из списка и достаем оттуда айди эпика

            if (subTaskIdByEpic == oldSubTaskIdByEpic) { // проверяем что айдишник пришедшей подзадачи и айдишни существующей подзадачи совпадают
                subTaskList.put(subTaskId, subTask); // обновляем подзадачу
                Epic epic = epicList.get(subTaskIdByEpic); // берем эпик
                setEpicStatus(epic.getId());// обновляем статус эпика
            }
        }
    }

    @Override
    public void removeTaskById(int taskId) { // удалить задачку по айди
        if (tasksList.containsKey(taskId)) { // проверяем в списке есть ли ключ
            inMemoryHistoryManager.remove(taskId); // удаляем задачку из inMemoryHistoryManager
            tasksList.remove(taskId); // удаляем задачку
        } else {
            System.out.println("такой задачки нет");
        }
    }

    @Override
    public void removeEpicById(int epicId) { // удалить эпик по айди
        if (epicList.containsKey(epicId)) { // проверяем есть ли ключ в мапе
            Epic epic = epicList.get(epicId); // достаем эпик по айди
            for (int subTask : epic.getSubTasksIdList()) { // пробегаемся по спику айдишников подзадач эпика
                inMemoryHistoryManager.remove(subTask); // удаляем подзадачки из inMemoryHistoryManager
                subTaskList.remove(subTask); // удаляем из списка подзадач те кторые принадлежат эпику
            }

            inMemoryHistoryManager.remove(epicId); // удаляем эпик из inMemoryHistoryManager
            epicList.remove(epicId); // удаляем эпик
        } else {
            System.out.println("такого эпика нет");
        }
    }

    @Override
    public void removeSubTaskById(int subTaskId) { // удалить подзадачу
        if (subTaskList.containsKey(subTaskId)) { // проверяем есть ли подзадача
            SubTask subTask = subTaskList.get(subTaskId); // получаем подзадачу из списка

            int epicId = subTask.getEpicId(); // получаем айди эпика из подзадачи
            Epic epic = epicList.get(epicId); // получаем эпик которому пренадлежит подзадача
            if (epic != null) { // проверяем что эпик не null
                epic.removeSubtaskById(subTask.getId()); // удаляем айдишник подзадачки из списка у эпика
                setEpicStatus(epic.getId()); // пересчитываем статус эпика
            }

            inMemoryHistoryManager.remove(subTaskId); // удаляем подзадачку из inMemoryHistoryManager
            subTaskList.remove(subTaskId); // удаляем подзадачку из мапы
        } else {
            System.out.println("такого подзадачки нет");
        }
    }

    @Override
    public Task createTask(Task task) { // создаем задачку
        return new Task(task.getName(), task.getDescription(), task.getStatus(), task.getStartTime(), task.getDuration());
    }

    @Override
    public Epic createEpic(Epic epic) { // создаем эпик
        return new Epic(epic.getName(), epic.getDescription());
    }

    @Override
    public SubTask createSubTask(SubTask subTask) { // создаем подзадачку
        return new SubTask(subTask.getName(), subTask.getDescription(), subTask.getStatus(), subTask.getEpicId(), subTask.getStartTime(), subTask.getDuration());
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
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

        // счетчики статусов
        int countnNewSubTask = 0;
        int countnProgressSubTask = 0;
        int countnDoneSubTask = 0;

        for (int subTaskId : epicSubTaskIdList) { // пробигаемся циклом по списку айдишников
            Status status = subTaskList.get(subTaskId).getStatus(); // достаем для каждого айдишника статус

            if (status == Status.NEW) { // если статус равен Status.NEW
                countnNewSubTask++; // увеличиваем счетчик
            } else if (status == Status.IN_PROGRESS) {
                countnProgressSubTask++;
            } else if (status == Status.DONE) {
                countnDoneSubTask++;
            }
        }

        if (countnNewSubTask == epicSubTaskIdList.size()) { // проверяем что если все задачки со статусом new
            epic.setStatus(Status.NEW);
        } else if (countnDoneSubTask == epicSubTaskIdList.size()) { // ежели все со статусом done
            epic.setStatus(Status.DONE);
        } else { // а здесь если есть хоть один и не new и не done
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    // расчет времени начала эпика
    private void epicStartTime(Epic epic) {
        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList();

        if (epicSubTaskIdList.size() == 0) {
            epic.setStartTime(null);
        }

        SubTask firstSubTask = subTaskList.get(epicSubTaskIdList.getFirst());
        LocalDateTime epicStartTime = firstSubTask.getStartTime();

        for (int subTaskId : epicSubTaskIdList) {
            SubTask subTask = subTaskList.get(subTaskId);

            if (subTask.getStartTime().isBefore(epicStartTime)) {
                epicStartTime = subTask.getStartTime();
            }
        }
        epic.setStartTime(epicStartTime);
    }
}
