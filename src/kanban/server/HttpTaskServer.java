package kanban.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import kanban.service.InMemoryTaskManager;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer { // создали класс сервера
    public static final int PORT = 8080; // константа для порта который будет слушать сервер

    private HttpServer server; // переменная для сервера
    private Gson gson; // переменная для gson
    private TaskManager taskManager; // переменная для менеджера задач

    public HttpTaskServer() throws IOException { // консруктор по дефолту
        this(Managers.getDefault()); // передается InMemoryTaskManager
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException { // конструктор с параметрами для менеджера
        this.taskManager = taskManager; // в менеджер записываем переданый taskManager
        gson = Managers.getGson(); // в gson записываем gsonBuilder полученный из метода getGson()
        server = HttpServer.create(); // создаем сервер по адресу localhost и привезали его к порту 8080
        server.bind(new InetSocketAddress(8080), 0);
      //  server.createContext("/tasks", new TaskHttpHandler(taskManager, gson)); // добавили обработчик для пути /tasks
    }


    public void start() { // метод для запуска сервера
        server.start();
        System.out.println("Сервер запущен на порту " + PORT);
    }

    public void stop() { // метод остановки сервера
        server.stop(0);
        System.out.println("Сервер остановлен на порту " + PORT);
    }

}
