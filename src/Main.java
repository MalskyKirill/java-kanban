import kanban.model.Task;
import kanban.service.TaskManager;

import java.util.Scanner;

public class Main {

    static TaskManager taskManager;
    static Scanner scanner;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);

        while (true) { //запускаем цикл
            // печатаем менюшку
            printMenu();
            // в переменную command записываем данные считаные из консоли
            String command = scanner.nextLine().trim();

            switch (command){
                case "1":
                    createNewTask();
                    break;
                case "2":
                    getTaskByCategory();
                    break;
                case "10":
                    return;
                default:
                    System.out.println("Такой команды еще нет :)");

            }
        }
    }

    private static void printMenu() { // метод печати менюшки
        System.out.println("Выберите команду");
        System.out.println("1 - Создать задачу");
        System.out.println("2 - Посмотреть все задачи");
        System.out.println("10 - Выход");

    }

    private static void createNewTask() { // метод создания задачки
        System.out.println("Введите имя задачи:");
        String name = scanner.nextLine().trim(); // считываем имя
        System.out.println("Введите описание задачи:");
        String description = scanner.nextLine().trim(); // считываем описание
        Task task = new Task(name, description); // создаем обьект класса Task с аргументами имя и описание

        taskManager.addNewTask(task); // у менеджера задач вызываем метод добавление задачки
    }

    private static void getTaskByCategory() { // метод получения задачек из категории
        System.out.println("Введите категорию задач которые вы хотите распечатать: ");
        System.out.println("1 - простые");
        System.out.println("2 - эпики");
        String category = scanner.nextLine().trim(); // считываем имя

        switch (category) { // проверяем категорию
            case "1":
                taskManager.getAllTasks("простые");  
                break;
            case "2":
                taskManager.getAllTasks("эпики");
                break;
            default:
                System.out.println("Такой категории нет :)");
        }
    }
}
