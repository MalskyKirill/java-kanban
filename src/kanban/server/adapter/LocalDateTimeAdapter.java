package kanban.server.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> { // класс адаптера для LocalDateTime

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) { // если localDateTime == null
            jsonWriter.nullValue(); // записываем значение null
        } else {
            jsonWriter.value(localDateTime.toString()); // записываем значение localDateTime
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) { // если JsonReader он считывает тип файла ровный null
            jsonReader.nextNull(); // получает следующий токен из потока JSON и утверждает, что это литеральный нуль.
            return null; // возвращаем null
        } else {
            return LocalDateTime.parse(jsonReader.nextString()); // считываем строку с помощью метода jsonReader.nextString()
        }
    }
}
