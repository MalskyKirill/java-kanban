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
                    getTasksByCategory();
                    break;
                case "3":
                    deleteAllTasks();
                    break;
                case "4":
                    getTask();
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
        System.out.println("3 - Удалить все задачи");
        System.out.println("4 - Получить задачку по id");
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

    private static void getTasksByCategory() { // метод получения задачек из категории
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

    private static void deleteAllTasks() { // метод удаления всех задачек
        System.out.println("Уверены что хотите удалить все задачи");
        System.out.println("1 - да");
        System.out.println("Ели хотите отменить нажмите любую кнопку кроме 1");

        String del = scanner.nextLine().trim(); // считываем ответ

        switch (del) { // проверяем ответ
            case "1":
                taskManager.removeAllTasks(); // если да, удаляем
                break;
            default:
                System.out.println("Отлично, отменяем удаление :)");
        }
    }

    private static void getTask() { // метод получения задачки
        System.out.println("Введите id задачки которую хотите получить");

        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
            int taskId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
            Task task = taskManager.getTaskById(taskId); // записываем в переменную task обьект возврщенный из метода getTaskById
            if (task != null) { // если вернулся не null
                System.out.println(task.toString()); // вызываем переопределленый метод toString
            }
        } catch(NumberFormatException e){ // ловим исключение
            System.out.println("Введено не число");
        }
    }
}
