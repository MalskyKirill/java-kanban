package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import kanban.model.Task;
import kanban.server.ErrorResponse;
import kanban.server.HttpMethods;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();// достаем метод
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                System.out.println(1);
                List<Task> history = taskManager.getHistory();
                System.out.println(history);
                writeResponse(history, exchange, 200); // формируем ответ
            }
            default -> { // по дефолту
                ErrorResponse errRes = new ErrorResponse("Неверный HTTP-метод"); // создаем ошибку ответа
                writeResponse(errRes, exchange, 404); // возвращаем со статус кодом 404
            }
        }
    }
}
