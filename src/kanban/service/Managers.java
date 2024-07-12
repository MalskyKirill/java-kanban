package kanban.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kanban.server.DurationAdapter;
import kanban.server.LocalDateTimeAdapter;

import java.time.Duration;
import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() { // метод создания обьекта gson
        GsonBuilder gsonBuilder = new GsonBuilder(); // создаем обьект класса GsonBuilder
        gsonBuilder
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter());
            //.excludeFieldsWithoutExposeAnnotation(); //реализовал сериализатор или десериализатор с заданными параметрами
        return gsonBuilder.create(); // завершаем построение объекта и возвращаем его
    }
}
