package kanban.service;

import kanban.model.Epic;
import kanban.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        save();
        return super.addNewTask(task);
    }

    private void save() {

    }


}
