package kanban.service;

import kanban.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file; // переменная для хранения файла

    public FileBackedTaskManager(File file) { // конструктор менеджера который принимает файл
        this.file = file; // присваеваем переменной файл пришедшее значение
        if (!file.isFile()) { // проверяем что такой файл есть по укузанному пути
            try {
                Files.createFile(Paths.get("vendor" + File.separator + "data.scv")); // если файл отсутствует пытаемся его создать
            } catch (IOException e) { // если не получается
                throw new RuntimeException(e); // кидаем исключение
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

    private void save() { // метод сохранения задачек в файл
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) { // создаем bufferedWriter для записи задачек в файл

            bufferedWriter.write("id,type,name,status,description,epic\n"); // записываем в файл хедер

            for (Task task : getAllTasks()) { // для каждой задачки из списка задачек
                bufferedWriter.write(task.getId() + "," + TypeTask.TASK + "," + task.getName() + ","
                    + task.getStatus() + "," + task.getDescription() + "\n"); // записываем в файл следующие данные
            }

            for (Epic epic : getAllEpics()) {
                bufferedWriter.write(epic.getId() + "," + TypeTask.EPIC + "," + epic.getName() + ","
                    + epic.getStatus() + "," + epic.getDescription() + "\n");
            }

            for (SubTask subTask : getAllSubTask()) { // для каждой задачки из списка задачек
                bufferedWriter.write(subTask.getId() + "," + TypeTask.SUB_TASK + "," + subTask.getName() + ","
                    + subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId() + "\n"); // записываем в файл следующие данные
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Task fromString(String value) { // метод создания задачки из строки

        String[] taskFields = value.split(","); // разбиваем строку по разделителю на массив полей задачки
        int id = Integer.parseInt(taskFields[0]); // в переменную id сохраняем первый эл-т массива (попутно его преобразовав из строки) и идем далее по массиву
        TypeTask type = TypeTask.valueOf(taskFields[1]); // сохраняем тип
        String name = taskFields[2]; // сохранием имя
        Status status = Status.valueOf(taskFields[3]); // сохраняем статус
        String description = taskFields[4]; // сохраняем описание
        int epicId = 0; // заводим переменную для присвоения подзадачки epicId

        if (taskFields.length == 6) { // если в массиве полуй 6 элементов
            epicId = Integer.parseInt(taskFields[5]); // присваеваем epicId
        }

        if (type == TypeTask.TASK) { // если тип равет TASK
            return new Task(name, description, id, status); // возвращаем новую задачку
        } else if (type == TypeTask.EPIC) { // ежели EPIC
            return new Epic(name, description, id, status); // возвращаем новый эпик, создал доп конструктор
        } else if (type == TypeTask.SUB_TASK) { // ежели SUB_TASK
            return new SubTask(name, description, id, status, epicId); // возвращаем подзадачку
        }

        return null; // иначе возвращаем null
    }


}
