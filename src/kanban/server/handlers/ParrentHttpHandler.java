package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ParrentHttpHandler implements HttpHandler {
    protected final TaskManager taskManager; // обьявляем taskManager
    protected final Gson gson; // обьявили gson

    public ParrentHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    protected void writeResponse(Object body, HttpExchange exchange, int code) throws IOException {
        String resJson = gson.toJson(body); // преобразовываем createdTask в формат json в строку
        byte[] resBytes = resJson.getBytes(StandardCharsets.UTF_8); // преобразуем строку в массив байт в формате ютф-8

        exchange.getResponseHeaders().add("Content-Type", "application/json"); // начинаем наполнять обьект exchange заголовками
        exchange.sendResponseHeaders(code, resBytes.length); // добавляем код ответа
        exchange.getResponseBody().write(resBytes); // заполняем тело ответа
        exchange.close(); // закрываем exchange
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
