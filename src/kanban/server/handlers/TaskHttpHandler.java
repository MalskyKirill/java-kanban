package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import kanban.exceptions.NotFoundTaskException;
import kanban.exceptions.TaskIntersectionTimeException;
import kanban.model.Task;
import kanban.server.ErrorResponse;
import kanban.server.HttpMethods;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public class TaskHttpHandler extends BaseHttpHandler {
    public TaskHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson); // конструктор
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();// достаем метод
        System.out.println();
        String path = exchange.getRequestURI().getPath(); // достаем путь
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                if (Pattern.matches("/tasks/\\d+$", path)) { // с помощью регулярки проверяем есть ли в пути после в URL-пути цифры
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    Task task = taskManager.getTaskById(id); // получаем задачу
                    if (task == null) {
                        ErrorResponse errRes = new ErrorResponse("Задачи не нашлось"); // создаем ошибку ответа
                        writeResponse(errRes, exchange, 404); // формируем ответ
                    }
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
                    if (task.getId() != 0) { // проверяем что айдишник задачи не равен нулю
                        taskManager.updateTask(task); // с помощью taskManager обновляем задачу
                        writeResponse(task, exchange, 201); // если все ок, вернули ответ
                    } else {
                        Task newTask = taskManager.createTask(task); // с помощью taskManager создаем обьект класса Task
                        taskManager.addNewTask(newTask); // добавили задачку в таск манагер
                        writeResponse(newTask, exchange, 201); // если все ок, вернули ответ
                    }
                } catch (TaskIntersectionTimeException ex) { // если поймали исключение
                    ErrorResponse errRes = new ErrorResponse("Произошло пересечение задач по времени"); // создаем ошибку ответа
                    writeResponse(errRes, exchange, 406); // отправляем
                } catch (NotFoundTaskException ex) {
                    ErrorResponse errRes = new ErrorResponse(ex.getMessage()); // создаем ошибку ответа
                    writeResponse(errRes, exchange, 404); // отправляем
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
}

