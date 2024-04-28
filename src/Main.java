import kanban.model.Status;
import kanban.model.Task;
import kanban.service.TaskManager;

import java.util.Scanner;

public class Main {

    static TaskManager taskManager;
    static Scanner scanner;

    public static void main(String[] args) {
        taskManager = new TaskManager();

        Task task = new Task("name", "description", Status.NEW);

        taskManager.addNewTask(task);

//        while (true) { //запускаем цикл
//            // печатаем менюшку
//            printMenu();
//            // в переменную command записываем данные считаные из консоли
//            String command = scanner.nextLine().trim();
//
//            switch (command){
//                case "1":
//                    createNewTask();
//                    break;
//                case "2":
//                    getTasksByCategory();
//                    break;
//                case "3":
//                    deleteAllTasks();
//                    break;
//                case "4":
//                    getTask();
//                    break;
//                case "5":
//                    updateTask();
//                    break;
//                case "6":
//                    deleteTask();
//                    break;
//                case "7":
//                    createNewEpic();
//                    break;
//                case "8":
//                    createNewSubTask();
//                    break;
//                case "9":
//                    updateSubTask();
//                    break;
//                case "10":
//                    getSubTasks();
//                    break;
//                case "11":
//                    return;
//                default:
//                    System.out.println("Такой команды еще нет :)");
//            }
//        }
    }

//    private static void printMenu() { // метод печати менюшки
//        System.out.println("Выберите команду");
//        System.out.println("1 - Создать задачу");
//        System.out.println("2 - Посмотреть все задачи");
//        System.out.println("3 - Удалить все задачи");
//        System.out.println("4 - Получить задачку по id");
//        System.out.println("5 - Обновить задачку по id");
//        System.out.println("6 - Удалить задачку по id");
//        System.out.println("7 - Создать эпик");
//        System.out.println("8 - Создать подзадачку для эпика");
//        System.out.println("9 - Обновить подзадачку по id");
//        System.out.println("10 - Получить список подзадач у конкретного эпика");
//        System.out.println("11 - Выход");
//    }
//
//    private static void createNewTask() { // метод создания задачки
//        System.out.println("Введите имя задачи:");
//        String name = scanner.nextLine().trim(); // считываем имя
//        System.out.println("Введите описание задачи:");
//        String description = scanner.nextLine().trim(); // считываем описание
//
//        taskManager.addNewTask(name, description); // у менеджера задач вызываем метод добавление задачки
//    }
//
//    private static void createNewEpic() {
//        System.out.println("Введите имя эпика:");
//        String name = scanner.nextLine().trim(); // считываем имя
//        System.out.println("Введите описание эпика:");
//        String description = scanner.nextLine().trim(); // считываем описание
//
//        taskManager.addNewEpic(name, description); // у менеджера задач вызываем метод добавление задачки
//    }
//
//    private static void createNewSubTask() {
//        System.out.println("Введите id эпика для которого создать подзадачу:");
//        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
//            int epicId = Integer.parseInt(scanner.nextLine().trim()); // считываем id эпика
//            taskManager.addNewSubTask(epicId, scanner); // у менеджера задач вызываем метод добавление задачки
//        } catch (NumberFormatException e){ // ловим исключение
//            System.out.println("Введено не число");
//        }
//    }
//
//    private static void getTasksByCategory() { // метод получения задачек из категории
//        System.out.println("Введите категорию задач которые вы хотите распечатать: ");
//        System.out.println("1 - простые");
//        System.out.println("2 - эпики");
//        String category = scanner.nextLine().trim(); // считываем имя
//
//        switch (category) { // проверяем категорию
//            case "1":
//                taskManager.getAllTasks("простые");
//                break;
//            case "2":
//                taskManager.getAllTasks("эпики");
//                break;
//            default:
//                System.out.println("Такой категории нет :)");
//        }
//    }
//
//    private static void getSubTasks() {
//        System.out.println("Введите id эпика подзадачи которого вы хотите распечатать");
//
//        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
//            int epicId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
//            taskManager.getSubTasksByEpicId(epicId);
//        } catch(NumberFormatException e){ // ловим исключение
//            System.out.println("Введено не число");
//        }
//    }
//
//    private static void deleteAllTasks() { // метод удаления всех задачек
//        System.out.println("Уверены что хотите удалить все задачи");
//        System.out.println("1 - да");
//        System.out.println("Ели хотите отменить нажмите любую кнопку кроме 1");
//
//        String del = scanner.nextLine().trim(); // считываем ответ
//
//        switch (del) { // проверяем ответ
//            case "1":
//                taskManager.removeAllTasks(); // если да, удаляем
//                break;
//            default:
//                System.out.println("Отлично, отменяем удаление :)");
//        }
//    }
//
//    private static void getTask() { // метод получения задачки
//        System.out.println("Введите id задачки которую хотите получить");
//
//        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
//            int taskId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
//
//            System.out.println("Из какой категории вы хотите получить задачку?");
//            System.out.println("1 - обычные");
//            System.out.println("2 - эпики");
//            System.out.println("3 - подзадачки");
//            int category = Integer.parseInt(scanner.nextLine().trim());
//
//            Task task = taskManager.getTaskById(taskId, category); // записываем в переменную task обьект возврщенный из метода getTaskById
//            if (task != null) { // если вернулся не null
//                System.out.println(task.toString()); // вызываем переопределленый метод toString
//            }
//        } catch(NumberFormatException e){ // ловим исключение
//            System.out.println("Введено не число");
//        }
//    }
//
//    private static void updateTask() {
//        System.out.println("Введите id задачки которую хотите обновить");
//
//        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
//            int taskId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
//            taskManager.updateTaskById(scanner, taskId);
//        } catch(NumberFormatException e){ // ловим исключение
//            System.out.println("Введено не число");
//        }
//    }
//
//    private static void updateSubTask() {
//
//        System.out.println("Введите id епика который содержит подзадачу которую хотите обновить");
//
//        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
//            int epicId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
//            System.out.println("Введите id подзадачи которую хотите обновить");
//            int subTaskId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
//            taskManager.updateSubTaskById(scanner, subTaskId, epicId);
//        } catch(NumberFormatException e){ // ловим исключение
//            System.out.println("Введено не число");
//        }
//    }
//
//    private static void deleteTask() {
//        System.out.println("Введите id задачки которую хотите удалить");
//
//        try { // проверочка что пользовотель введет строку которую можно преобразовать в число
//            int taskId = Integer.parseInt(scanner.nextLine().trim()); // считываем ввод
//            taskManager.removeTaskById(taskId);
//        } catch(NumberFormatException e){ // ловим исключение
//            System.out.println("Введено не число");
//        }
//    }
}
