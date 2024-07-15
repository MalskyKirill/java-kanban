package kanban.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kanban.model.Epic;
import kanban.model.Status;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.service.InMemoryTaskManager;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TaskHttpHandler implements HttpHandler {
    private final InMemoryTaskManager taskManager; // обьявляем taskManager
    private final Gson gson; // обьявили gson

    public TaskHttpHandler(InMemoryTaskManager taskManager, Gson gson) { // конструктор
        this.taskManager = taskManager;
        this.gson = gson;
    }

    String json = "{\n" +
        "  \"name\": \"This is my app name\",\n" +
        "  \"description\": \"This is my app name\",\n" +
        "  \"status\": \"NEW\"\n" +
        "}";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                String path = exchange.getRequestURI().getPath(); // достаем путь
                int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                Task task = taskManager.getTaskById(id); // получаем зад
                writeResponse(task, exchange, 200);
            }
            case POST -> {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // вытаскиваем тело запроса в формате массива байтов
                Task task = gson.fromJson(requestBody, Task.class); // с помощью библиотеки gson десюарилизуем данные в обьект класса Task

                Task addTask = taskManager.addNewTask(task); // с помощью taskManager добавляем обьект класса Task
                writeResponse(addTask, exchange, 201);
            }
            default -> { // по дефолту
                ErrorResponse errRes = new ErrorResponse("Неверный HTTP-метод"); // создаем ошибку ответа
                writeResponse(errRes, exchange, 404); // возвращаем со статус кодом 404
            }
        }

    }

    private  void writeResponse(Object body, HttpExchange exchange, int code) throws IOException {
        String resJson = gson.toJson(body); // преобразовываем createdTask в формат json в строку
        byte[] resBytes = resJson.getBytes(StandardCharsets.UTF_8); // преобразуем строку в массив байт в формате ютф-8

        exchange.getResponseHeaders().add("Content-Type", "application/json"); // начинаем наполнять обьект exchange заголовками
        exchange.sendResponseHeaders(code, resBytes.length); // добавляем код ответа
        exchange.getResponseBody().write(resBytes); // заполняем тело ответа
        exchange.close(); // закрываем exchange
    }


}

