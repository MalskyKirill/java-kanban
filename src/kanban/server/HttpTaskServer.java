package kanban.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import kanban.server.handlers.*;
import kanban.service.Managers;
import kanban.service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer { // создали класс сервера
    public static final int PORT = 8080; // константа для порта который будет слушать сервер

    private HttpServer httpServer; // переменная для сервера
    private Gson gson; // переменная для gson
    private TaskManager taskManager; // переменная для менеджера задач


    public HttpTaskServer() throws IOException { // конструктор с параметрами для менеджера
        this.taskManager = Managers.getDefault();
        this.gson = Managers.getGson();
        httpServer = HttpServer.create(new InetSocketAddress("localhost",8080), 0);
        httpServer.createContext("/tasks", new TaskHttpHandler(taskManager, gson));
        httpServer.createContext("/epics", new EpicHttpHandler(taskManager, gson));
        httpServer.createContext("/subtasks", new SubTaskHttpHandler(taskManager, gson));
        httpServer.createContext("/history", new HistoryHandler(taskManager, gson));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }

    public void start() { // метод для запуска сервера
        httpServer.start();
        System.out.println("Сервер запущен на порту " + PORT);
    }

    public void stop() { // метод остановки сервера
        httpServer.stop(0);
        System.out.println("Сервер остановлен на порту " + PORT);
    }

}
