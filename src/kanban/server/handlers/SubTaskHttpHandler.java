package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import kanban.service.TaskManager;

import java.io.IOException;

public class SubTaskHttpHandler extends ParrentHttpHandler {

    public SubTaskHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson); // конструктор
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();// достаем метод
        String path = exchange.getRequestURI().getPath(); // достаем путь

    }
}
