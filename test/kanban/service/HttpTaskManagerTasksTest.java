package kanban.service;

import com.google.gson.Gson;
import kanban.model.Status;
import kanban.model.Task;
import kanban.server.HttpTaskServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTasksTest {

    TaskManager manager = Managers.getDefault();

    Gson gson = Managers.getGson();
    HttpTaskServer taskServer = new HttpTaskServer(manager, gson);


    public HttpTaskManagerTasksTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.removeAllTasks();
        manager.removeAllSubTasks();
        manager.removeAllEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2", "Testing task 2",
            Status.NEW.NEW, LocalDateTime.now(), Duration.ofMinutes(5));
        // конвертируем её в JSON
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.getAllTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }
}
