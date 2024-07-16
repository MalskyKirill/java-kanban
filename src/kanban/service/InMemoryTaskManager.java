package kanban.service;

import kanban.exceptions.TaskIntersectionTimeException;
import kanban.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    //создал хеш-маппу для хранения тасочек
    protected Map<Integer, Task> tasksList = new HashMap<>();
    protected Map<Integer, Epic> epicList = new HashMap<>();
    protected Map<Integer, SubTask> subTaskList = new HashMap<>();
    protected int taskId = 1;

    private HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private Set<Task> prioritizedTask = new TreeSet<>(Comparator.comparing(task -> task.getStartTime())); // коллекция уникальных элементов отсортированных в порядке возрастания с помощью компаратора

    @Override
    public Task addNewTask(Task task) { // метод добавления задачки в хешмапу
        checkerIntersectionTimeTask(task);
        task.setId(taskId); // обогатили задачку id
        tasksList.put(taskId, task); // кидаем задачку в мапу
        taskId++; // увеличиваем айдишник
        prioritizedTask.add(task);
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
        checkerIntersectionTimeTask(subTask);
        int epicId = subTask.getEpicId(); // получил айди эпика
        Epic epic = epicList.get(epicId); //  получили эпик

        if (epic != null) { // проверка на null
            subTask.setId(taskId); // обогатили подзадачку айдишником
            epic.addSubTaskById(subTask.getId()); // добавляем подзадачку в эпик
            subTaskList.put(taskId, subTask); // добавляем подзадачу в мапу подзадач
            taskId++; // увеличиваем айдишник

            setEpicStatus(epic); // проверили статус эпика
            epicStartTime(epic); // установили время начала
            epicDuration(epic); // продолжительность
            epicEndTime(epic); // время окончания

        }

        prioritizedTask.add(subTask);
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

        for (Task task : tasksList.values()) { // пробегаемся по списку ключей из листа задач
            int id = task.getId();
            inMemoryHistoryManager.remove(id); // вызываем метод remove для каждой задачки в inMemoryHistoryManager
            prioritizedTask.remove(task); // по айдишнику нашли и удалили звадачку
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

        for (Task subTask : subTaskList.values()) { // пробегаемся по списку значени из листа подзадачек
            int id = subTask.getId();
            inMemoryHistoryManager.remove(id);
            prioritizedTask.remove(subTask);
        }
        subTaskList.clear(); // удалили все подзадачи
    }

    @Override
    public void removeAllSubTasks() { // удаляем все подзадачи
        if (subTaskList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }

        for (Task subTask : subTaskList.values()) { // пробегаемся по списку ключей из листа подзадачек
            int id = subTask.getId();
            inMemoryHistoryManager.remove(id);
            prioritizedTask.remove(subTask);
        }
        subTaskList.clear(); // удалили все подзадачи

        for (Epic epic : epicList.values()) { // пробежались по списку эпиков
            epic.clearSubTasksIdList(); // у каждого эпика очистили список айдишников подзадач
            setEpicStatus(epic); // пересчитали статус
            epicStartTime(epic); // установили время начала
            epicDuration(epic); // продолжительность
            epicEndTime(epic); // время окончания
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

        checkerIntersectionTimeTask(task); // проверка на пересечении по времени

        int taskId = task.getId(); // берем айдишник новой задачки
        if (tasksList.containsKey(taskId)) { // если он есть как ключ в списке задач
            prioritizedTask.removeIf(t -> t.getId() == taskId); // если задачка с taskId есть в приорити лист удаляем
            tasksList.put(taskId, task); // обновляем задачку
            prioritizedTask.add(task); // добавляем в приорити лист новую задачку
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

        checkerIntersectionTimeTask(subTask); // проверка на пересечении по времени

        int subTaskId = subTask.getId(); // берем айди подзадачи
        if (subTaskList.containsKey(subTaskId)) { // проверяем что такая подзадача есть в списке
            int subTaskIdByEpic = subTask.getEpicId(); // берем айдишник эпика из подзалдачи
            int oldSubTaskIdByEpic = subTaskList.get(subTaskId).getEpicId(); // берем старую подзадачку по айдишнику из списка и достаем оттуда айди эпика

            if (subTaskIdByEpic == oldSubTaskIdByEpic) { // проверяем что айдишник пришедшей подзадачи и айдишни существующей подзадачи совпадают
                prioritizedTask.removeIf(t -> t.getId() == subTaskId); // удаляем из приорити листа
                subTaskList.put(subTaskId, subTask); // обновляем подзадачу
                prioritizedTask.add(subTask); // добавляем в приорити лист

                Epic epic = epicList.get(subTaskIdByEpic); // берем эпик
                setEpicStatus(epic);// обновляем статус эпика
                epicStartTime(epic); // установили время начала
                epicDuration(epic); // продолжительность
                epicEndTime(epic); // время окончания
            }
        }
    }

    @Override
    public void removeTaskById(int taskId) { // удалить задачку по айди
        if (tasksList.containsKey(taskId)) { // проверяем в списке есть ли ключ
            inMemoryHistoryManager.remove(taskId); // удаляем задачку из inMemoryHistoryManager
            prioritizedTask.removeIf(t -> t.getId() == taskId); // удаляем из приорити листа
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
                prioritizedTask.removeIf(sub -> sub.getId() == subTask); // пробегаемся по prioritizedTask и удаляем подзадачку если ее айдишник совпадает с subTask
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
                setEpicStatus(epic); // пересчитываем статус эпика
                epicStartTime(epic); // установили время начала
                epicDuration(epic); // продолжительность
                epicEndTime(epic); // время окончания
            }

            inMemoryHistoryManager.remove(subTaskId); // удаляем подзадачку из inMemoryHistoryManager
            prioritizedTask.removeIf(t -> t.getId() == subTaskId); //удаляем из приорити листа
            subTaskList.remove(subTaskId); // удаляем подзадачку из мапы
        } else {
            System.out.println("такого подзадачки нет");
        }
    }

    @Override
    public Task createTask(Task task) { // создаем задачку
        checkerIntersectionTimeTask(task); // проверка на пересечение времени
        return new Task(task.getName(), task.getDescription(), task.getStatus(), task.getStartTime(), task.getDuration());
    }

    @Override
    public Epic createEpic(Epic epic) { // создаем эпик
        return new Epic(epic.getName(), epic.getDescription());
    }

    @Override
    public SubTask createSubTask(SubTask subTask) { // создаем подзадачку
        checkerIntersectionTimeTask(subTask); // проверка на пересечение по времени
        return new SubTask(subTask.getName(), subTask.getDescription(), subTask.getStatus(), subTask.getStartTime(), subTask.getDuration(), subTask.getEpicId());
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    private void setEpicStatus(Epic epic) { // метод изменение статуса эпика

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
    private void epicStartTime(Epic epic) { // получаем эпик
        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список айдишников подзадач эпика

        if (epicSubTaskIdList.size() == 0) { // если нет подзадачек
            epic.setStartTime(null); // устанавливаем null
        }

        List<SubTask> epicStartTime = epicSubTaskIdList.stream() // если подзадачки в списке есть создаем стрим
            .map(id -> subTaskList.get(id)) // каждый элемент списка преобразуем в субтаску
            .filter(sub -> sub.getStartTime() != null) // проверяем что у субтаски есть время начала
            .sorted(Comparator.comparing(Task::getStartTime)).toList(); // с помощью sorted сортируем таски по getStartTime и собираем все в список

        if (epicStartTime.size() != 0) { // проверяем что список получился не пустой
            epic.setStartTime(epicStartTime.getFirst().getStartTime()); // устанавливаем эпику время getStartTime первого элемента списка
        } else { // иначе
            epic.setStartTime(null); // начальное время устанавливаем null
        }
    }

    //продолжительность эпика
    private void epicDuration(Epic epic) {
        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList(); // получаем список

        if (epicSubTaskIdList.size() == 0) { // если в списке нет элементов
            epic.setDuration(null); // продолжительность устанавливаем null
        }

        Duration epicDuration = epicSubTaskIdList.stream() // если элементы есть преобразовываем список в стрим
            .map(id -> subTaskList.get(id).getDuration()) // для каждого элемента преобразуем id в Duration
            .filter(Objects::nonNull) // фильтруем Duration на null
            .reduce(Duration.ZERO, Duration::plus); // с помощью метода reduce находим сумму

        if (epicDuration != Duration.ZERO) { // если список не равен Duration.ZERO
            epic.setDuration(epicDuration); // устанавливаем продолжительность
        } else { // иначе
            epic.setDuration(null);
        }
    }

    // расчет времени завершения эпика
    private void epicEndTime(Epic epic) {
        ArrayList<Integer> epicSubTaskIdList = epic.getSubTasksIdList();

        if (epicSubTaskIdList.size() == 0) {
            epic.setEndTime(null);
        }

        List<SubTask> endTime = epicSubTaskIdList.stream()  // если элементы есть преобразовываем список в стрим
            .map(id -> subTaskList.get(id)) // каждый элемент списка преобразуем в субтаску
            .filter(sub -> sub.getEndTime() != null) // фильтруем субтаски у которых нет времени окончания
            .sorted(Comparator.comparing(Task::getEndTime)).toList(); // сортируем субтаски по времени и собираем в список

        if (endTime.size() != 0) {
            epic.setEndTime(endTime.getLast().getEndTime()); // эпику устанавливаем значение getEndTime последней субтаскив списке
        } else {
            epic.setEndTime(null);
        }
    }

    @Override
    public List<Task> getPrioritizedTasks() { // вернули список задачек по приретету времени
        return new ArrayList<>(prioritizedTask); // обернули в ArrayList
    }

    private void checkerIntersectionTimeTask(Task task) { // проверка пересечения задач по времени
        List<Task> tasks = getPrioritizedTasks(); // получили список задач у которых указано время

        LocalDateTime startTimeNewTask = task.getStartTime(); // взяли время начала новой задачки
        LocalDateTime endTimeNewTask = task.getEndTime(); //и время ее окончания

        for (Task t : tasks) { // для каждой задачки в списке
            if (t.getId() != task.getId()) { // проверяем что такой задачки в списке еще нет, что бы избежать пересечений при апдейте
                LocalDateTime taskStart = t.getStartTime(); // получаем время начала задачки
                LocalDateTime taskEnd = t.getEndTime(); // получаем время окончания задачки

                //делаем проверку
                if ((taskStart.isAfter(startTimeNewTask)  // ежели время начала задачи из списка ПОЗЖЕ времени начала новой задачи
                    && taskStart.isBefore(endTimeNewTask)) // И время начала задачи из списка РАНЬШЕ времени окончания новой задачи
                    || (taskEnd.isAfter(startTimeNewTask) // ИЛИ время окончания задачи из списка ПОЗЖЕ времени начала новой задачи
                    && taskEnd.isBefore(endTimeNewTask)) // И время окончания задачи из списка РАНЬШЕ времени окончания новой задачи
                    || (startTimeNewTask.isAfter(taskStart) // ИЛИ время начала новой задачи ПОЗЖЕ времени начала задачи из списка
                    && endTimeNewTask.isBefore(taskEnd)) // и время окончания новой задачи РАНЬШЕ времени окончания задачи из списка
                || (startTimeNewTask.equals(taskStart) && taskEnd.equals(endTimeNewTask))) { // ИЛИ если время начала новой задачи и время окончания новой задачи совпадают с задачей из списка
                    throw new TaskIntersectionTimeException("Произошло пересечение задач по времени"); // кидаем исключение
                }
            }
        }
    }
}
