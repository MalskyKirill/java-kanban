package kanban.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kanban.model.Task;
import kanban.service.InMemoryTaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

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
        String httpMethod = exchange.getRequestMethod();// достаем метод
        String path = exchange.getRequestURI().getPath(); // достаем путь
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                if (Pattern.matches("/tasks/\\d+$", path)) { // с помощью регулярки проверяем есть ли в пути после в URL-пути цифры
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    Task task = taskManager.getTaskById(id); // получаем задачу
                    System.out.println(task);
                    writeResponse(task, exchange, 200); // формируем ответ
                } else {
                    List<Task> tasks = taskManager.getAllTasks(); // получаем все задачки
                    writeResponse(tasks, exchange, 200); // формируем ответ
                }
            }
            case POST -> {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // вытаскиваем тело запроса в формате массива байтов
                Task task = gson.fromJson(requestBody, Task.class); // с помощью библиотеки gson десюарилизуем данные в обьект класса Task
                try { // обернули в трай кетч
                    Task newTask = taskManager.createTask(task); // с помощью taskManager создаем обьект класса Task
                    taskManager.addNewTask(newTask); // добавили задачку в таск манагер
                    writeResponse(newTask, exchange, 201); // если все ок, вернули ответ
                } catch (Exception ex) { // если поймали исключение
                    ErrorResponse errRes = new ErrorResponse("Произошло пересечение задач по времени"); // создаем ошибку ответа
                    writeResponse(errRes, exchange, 406); // отправляем
                }

            }
            case DELETE -> {
                if (Pattern.matches("/tasks/\\d+$", path)) { // проверяем что в URL есть айди
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    Task task = taskManager.getTaskById(id);// достаем задачу
                    taskManager.removeTaskById(id); // удаляем задачу из таск манагера
                    writeResponse(task, exchange, 200); // формируем ответ
                } else {
                    ErrorResponse errRes = new ErrorResponse("В URL не указан id задачки"); // создаем ошибку ответа
                    writeResponse(errRes, exchange, 400); // возвращаем со статус кодом 400
                }
            }
            default -> { // по дефолту
                ErrorResponse errRes = new ErrorResponse("Неверный HTTP-метод"); // создаем ошибку ответа
                writeResponse(errRes, exchange, 404); // возвращаем со статус кодом 404
            }
        }
    }

    private void writeResponse(Object body, HttpExchange exchange, int code) throws IOException {
        String resJson = gson.toJson(body); // преобразовываем createdTask в формат json в строку
        byte[] resBytes = resJson.getBytes(StandardCharsets.UTF_8); // преобразуем строку в массив байт в формате ютф-8

        exchange.getResponseHeaders().add("Content-Type", "application/json"); // начинаем наполнять обьект exchange заголовками
        exchange.sendResponseHeaders(code, resBytes.length); // добавляем код ответа
        exchange.getResponseBody().write(resBytes); // заполняем тело ответа
        exchange.close(); // закрываем exchange
    }
}

