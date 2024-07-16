package kanban.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import kanban.model.Epic;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.server.ErrorResponse;
import kanban.server.HttpMethods;
import kanban.service.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public class EpicHttpHandler extends ParrentHttpHandler{
    public EpicHttpHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String httpMethod = exchange.getRequestMethod();// достаем метод
        String path = exchange.getRequestURI().getPath(); // достаем путь
        switch (HttpMethods.valueOf(httpMethod)) {
            case GET -> {
                if (Pattern.matches("/epics/\\d+$", path)) { // с помощью регулярки проверяем есть ли в пути после в URL-пути цифры
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    Epic epic = taskManager.getEpicById(id); // получаем эпик
                    writeResponse(epic, exchange, 200); // формируем ответ
                } else if (Pattern.matches("/epics/\\d+$*/subtasks", path)) {
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    List<SubTask> subTasksByEpic = taskManager.getAllSubtasksByEpic(id);
                    writeResponse(subTasksByEpic, exchange, 200);
                } else if (Pattern.matches("/epics", path)) {
                    List<Epic> epics = taskManager.getAllEpics(); // получаем все эпики
                    writeResponse(epics, exchange, 200); // формируем ответ
                }
            }
            case POST -> {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // вытаскиваем тело запроса в формате массива байтов
                Epic epic = gson.fromJson(requestBody, Epic.class); // с помощью библиотеки gson десюарилизуем данные в обьект класса Epic
                try { // обернули в трай кетч
                    Epic newEpic = taskManager.createEpic(epic); // с помощью taskManager создаем обьект класса Epic
                    taskManager.addNewEpic(newEpic); // добавили эпик в таск манагер
                    writeResponse(newEpic, exchange, 201); // если все ок, вернули ответ
                } catch (Exception ex) { // если поймали исключение
                    ErrorResponse errRes = new ErrorResponse("Произошло пересечение задач по времени"); // создаем ошибку ответа
                    writeResponse(errRes, exchange, 406); // отправляем
                }
            }
            case DELETE -> {
                if (Pattern.matches("/epics/\\d+$", path)) { // проверяем что в URL есть айди
                    int id = Integer.parseInt(path.split("/")[2]); // достаем айдишник из пути
                    System.out.println(id);
                    Epic epic = taskManager.getEpicById(id);// достаем эпик
                    taskManager.removeEpicById(id); // удаляем эпик из таск манагера
                    writeResponse(epic, exchange, 200); // формируем ответ
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
