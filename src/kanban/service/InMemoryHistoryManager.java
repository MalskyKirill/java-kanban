package kanban.service;

import kanban.model.Node;
import kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> historyTasksList = new ArrayList<>(); // список для формирования истории
    private Map<Integer, Node<Task>> handeMadeLinkedHashMap = new HashMap<>(); // мапа для реализации двусвязного списка

    private Node<Task> head; // голова списка
    private Node<Task> tail; // хвост списка

    @Override
    public void addTask(Task task) {

        int tasId = task.getId();
        setNewTail(task); // делаем новую задачку хвостом связного списка
        handeMadeLinkedHashMap.put(tasId, tail); // добавляем айдиник и новый хвост в мапу
    }

    private void setNewTail(Task task) {
        final Node<Task> oldTail = tail; // создаем переменную старый хвост и записываем в нее текущий хвост
        final Node<Task> newNode = new Node<>(oldTail, task, null); // создаем новую ноду для хвоста

        tail = newNode; // записываем в текущий хвост новую ноду

        if (oldTail == null) { // проверяем если в старом хвосте был null (его не было)
            head = newNode; // голова будет тоже ссылаться на новую ноду
        } else { // если в списке что то было
            oldTail.setNext(newNode); // записываем в поле старого хвоста ссылку на новый
        }
    }

    public void getTasks() { // формируем историю
        Node<Task> node = head; // записываем в ноду голову

        while (node != null) { // пока нода не null
            historyTasksList.add(node.getData()); // добавляем в список данные из ноды
            node = node.getNext(); // переприсваеваем ноде ссылку на следующую ноду
        }
    }

    @Override
    public List<Task> getHistory() { // возвращаем историю
        getTasks();
        return new ArrayList<>(historyTasksList);
    }

    @Override
    public void remove(int id) {

    }
}
