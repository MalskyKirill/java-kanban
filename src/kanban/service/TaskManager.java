package kanban.service;

import kanban.model.Task;

import java.util.HashMap;

public class TaskManager {

    //создал хеш-маппу для хранения тасочек
    private HashMap<Integer, Task> tasksList = new HashMap<>();

    public void addNewTask(Task task) { // метод добавления задачки в хешмапу
        int id = task.getId(); // получаем id задачки
        for (int taskId : tasksList.keySet()){ // пробегаемся по ключам-айдишникам
            if (id == taskId) { // если задача с таким айдишником существует
                System.out.println("Задача с таким именем и описанием уже существует");
                return; // вылетаем
            }
        }

        tasksList.put(id, task);
    }

    public void getAllTasks(String category) { // печатаем все задачки из категории
        if (category.equals("простые")) { // проверяем категорию
            if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
                System.out.println("Простых задачек пока нет");
                return; // вылетаем
            }

            for (Task task : tasksList.values()) { // провегаемся по значениям хешмапы и печатаем
                System.out.println("Задача -" + task.getName()); // имя
                System.out.println("Описание - " + task.getDescription()); // описание
                System.out.println("Статус - " + task.getStatus()); // статус
                System.out.println("Id - " + task.getId()); // получаем айдишник
                System.out.println("");

            }
        }
    }

    public void removeAllTasks() { // метод удаления всех задачек
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("Пока нечего удалять :)");
            return; // вылетаем
        }
        tasksList.clear(); // очищаем мапу
    }

    public Task getTaskById(int id) {
        if (tasksList.isEmpty()) { // проверяем что хешмапа не пустая
            System.out.println("задачек пока нет");
            return null; // возвращаем null
        }

        if (tasksList.containsKey(id)) { // проверяем что в мапе есть такой ключик-id
            Task task = tasksList.get(id); // если ок, достаем из мапы задачку по айдишнику
            return task; // возвращаем обьектик
        } else { // ежели нет
            System.out.println("задачи с таким id нет");
            return null; // возвращаем null
        }
    }

    @Override
    public String toString() {
        String taskName = "";
        for(Task task : tasksList.values()) {
            taskName += task.getName();
        }

        return "TaskManager{" +
            "tasksList=" + taskName +
            '}';
    }
}
