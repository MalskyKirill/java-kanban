package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import kanban.model.SubTask;
import kanban.server.ErrorResponse;
import kanban.server.HttpMethods;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public class SubTaskHttpHandler extends BaseHttpHandler {

    public SubTaskHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson); // конструктор
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();// достаем метод
        String path = exchange.getRequestURI().getPath(); // достаем путь
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                if (Pattern.matches("/subtasks/\\d+$", path)) { // с помощью регулярки проверяем есть ли в пути после в URL-пути цифры
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    SubTask subTask = taskManager.getSubTaskById(id); // получаем субтаску
                    if (subTask == null) {
                        ErrorResponse errRes = new ErrorResponse("Задачи не нашлось"); // создаем ошибку ответа
                        writeResponse(errRes, exchange, 404); // формируем ответ
                    } else {
                        writeResponse(subTask, exchange, 200); // формируем ответ
                    }
                } else if (Pattern.matches("/subtasks", path)) {
                    List<SubTask> subTasks = taskManager.getAllSubTask(); // получаем все субтаски
                    writeResponse(subTasks, exchange, 200); // формируем ответ
                }
            }
            case POST -> {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // вытаскиваем тело запроса в формате массива байтов
                SubTask subTask = gson.fromJson(requestBody, SubTask.class); // с помощью библиотеки gson десюарилизуем данные в обьект класса SubTask
                try { // обернули в трай кетч
                    SubTask newSubTask = taskManager.createSubTask(subTask); // с помощью taskManager создаем обьект класса SubTask
                    taskManager.addNewSubTask(newSubTask); // добавили subtask в таск манагер
                    writeResponse(newSubTask, exchange, 201); // если все ок, вернули ответ
                } catch (Exception ex) { // если поймали исключение
                    ErrorResponse errRes = new ErrorResponse("Произошло пересечение задач по времени"); // создаем ошибку ответа
                    writeResponse(errRes, exchange, 406); // отправляем
                }
            }
            case DELETE -> {
                if (Pattern.matches("/subtasks/\\d+$", path)) { // проверяем что в URL есть айди
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    SubTask subTask = taskManager.getSubTaskById(id);// достаем subtask
                    taskManager.removeSubTaskById(id); // удаляем subtask из таск манагера
                    writeResponse(subTask, exchange, 200); // формируем ответ
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
