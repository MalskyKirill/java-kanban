package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import kanban.model.Task;
import kanban.server.ErrorResponse;
import kanban.server.HttpMethods;
import kanban.service.TaskManager;

import java.io.IOException;
import java.util.List;

public class PrioritizedHandler extends BaseHttpHandler{
    public PrioritizedHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();// достаем метод
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                List<Task> prioritized = taskManager.getPrioritizedTasks();
                writeResponse(prioritized, exchange, 200); // формируем ответ
            }
            default -> { // по дефолту
                ErrorResponse errRes = new ErrorResponse("Неверный HTTP-метод"); // создаем ошибку ответа
                writeResponse(errRes, exchange, 404); // возвращаем со статус кодом 404
            }
        }
    }
}
