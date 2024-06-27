package kanban.service;

import kanban.exceptions.ManagerSaveException;
import kanban.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file; // переменная для хранения файла

    public FileBackedTaskManager(File file) { // конструктор менеджера который принимает файл
        this.file = file; // присваеваем переменной файл пришедшее значение
        if (!file.isFile()) { // проверяем что такой файл есть по укузанному пути
            try {
                Files.createFile(Paths.get("vendor" + File.separator + "data.scv")); // если файл отсутствует пытаемся его создать
            } catch (IOException e) { // если не получается
                throw new ManagerSaveException("Ошибка, не удалось создать файл"); // кидаем исключение
            }
        }
    }

    @Override
    public Task addNewTask(Task task) { // пререопределили метод длбавление задачки
        Task newTask = super.addNewTask(task); //записали в переменную результат родительского метода
        save(); // вызвали метод сохранения задачки в файл
        return newTask; // вернули задачку
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        Epic newEpic = super.addNewEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public SubTask addNewSubTask(SubTask subTask) {
        SubTask newSubTask = super.addNewSubTask(subTask);
        save();
        return newSubTask;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = super.getSubTaskById(subTaskId);
        save();
        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubTaskById(int subTaskId) {
        super.removeSubTaskById(subTaskId);
        save();
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        SubTask newSubTask = super.createSubTask(subTask);
        save();
        return newSubTask;
    }

    private void save() { // метод сохранения задачек в файл
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) { // создаем bufferedWriter для записи задачек в файл

            bufferedWriter.write("id,type,name,status,description,epic\n"); // записываем в файл хедер

            for (Task task : getAllTasks()) { // для каждой задачки из списка задачек
                bufferedWriter.write(task.toStringTask()); // записываем в файл следующие данные
            }

            for (Epic epic : getAllEpics()) {
                bufferedWriter.write(epic.toStringTask());
            }

            for (SubTask subTask : getAllSubTask()) { // для каждой задачки из списка задачек
                bufferedWriter.write(subTask.toStringTask()); // записываем в файл следующие данные
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    private static Task fromString(String value) { // метод создания задачки из строки

        String[] taskFields = value.split(","); // разбиваем строку по разделителю на массив полей задачки
        int id = Integer.parseInt(taskFields[0]); // в переменную id сохраняем первый эл-т массива (попутно его преобразовав из строки) и идем далее по массиву
        TypeTask type = TypeTask.valueOf(taskFields[1]); // сохраняем тип
        String name = taskFields[2]; // сохранием имя
        Status status = Status.valueOf(taskFields[3]); // сохраняем статус
        String description = taskFields[4]; // сохраняем описание
        int epicId = 0; // заводим переменную для присвоения подзадачки epicId

        if (taskFields.length == 6) { // если в массиве 6 элементов
            epicId = Integer.parseInt(taskFields[5]); // присваеваем epicId
        }

//        if (type == TypeTask.TASK) { // если тип равет TASK
//            return new Task(name, description, id, status); // возвращаем новую задачку
//        } else if (type == TypeTask.EPIC) { // ежели EPIC
//            return new Epic(name, description, id, status); // возвращаем новый эпик, создал доп конструктор
//        } else if (type == TypeTask.SUB_TASK) { // ежели SUB_TASK
//            return new SubTask(name, description, id, status, epicId); // возвращаем подзадачку
//        }

        return null; // иначе возвращаем null
    }

    public static FileBackedTaskManager loadFromFile(File file) { // метод востановления данных из файла
        FileBackedTaskManager backedManager = new FileBackedTaskManager(file); // создаем новый баккет манагер
        List<String> taskList = new ArrayList<>(); // создаем список
        int maxId = 0; // переменная для поиска максимального id у прочитанных задач

        try (FileReader fileReader = new FileReader(file); // в конструкции try-with-resourcestry, создаем файлреадер и баффередреадер
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) { // пока все строки не прочитаны
                String line = bufferedReader.readLine(); // читаем новую строчку
                taskList.add(line); // и добавляем ее в список
            }
        } catch (IOException e) { // если что то не ок, ловим исключение
            throw new ManagerSaveException(e.getMessage());
        }

        for (int i = 1; i < taskList.size(); i++) { // бежим по элементам списка
            Task task = fromString(taskList.get(i)); // для каждого элемента преобразовавыем строчку в задачку

            if (TypeTask.TASK == task.getType()) { // если тип задачка
                backedManager.tasksList.put(task.getId(), task); // кидаем ее в хешмапу задачек (в родительском классе поменял модификатор доступа с private на protected)
            } else if (TypeTask.EPIC == task.getType()) { // ежели тип задачки эпик
                Epic epic = (Epic) task; // приводим задачку к типу эпик
                backedManager.epicList.put(epic.getId(), epic); // кидаем ее в хешмапу эпиков
            } else if (TypeTask.SUB_TASK == task.getType()) { // ежели субтаска
                SubTask subTask = (SubTask) task; // приводим к типу субтаск
                backedManager.subTaskList.put(subTask.getId(), subTask); // кидаем ее в соответствующую мапу
                if (!backedManager.epicList.isEmpty()) { // если список эпиков не пустой
                    Epic epic = backedManager.epicList.get(subTask.getEpicId()); // находим эпик к которому относится подзадачки
                    if (epic != null) { // проверяем что эпик нашелся
                        epic.addSubTaskById(subTask.getId()); // кидаем субтаску в список субтаск эпика
                    }
                }
            }

            if (task.getId() > maxId) { // смотрим если айдишник задачки больше максимального в этом бакет манагере
                maxId = task.getId(); // то присваиваем его значение переменной maxId
            }
        }

        backedManager.taskId = maxId; // присваеваем maxId переменной отвечающей за значения id при создании файла
        return backedManager; // возвращаем бакет манагер
    }
}
