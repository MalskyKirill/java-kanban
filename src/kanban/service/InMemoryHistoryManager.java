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
//        if (task != null) { // проверка на null
//            if (historyTasksList.size() >= MAX_SIZE_HISTORY) { // если список равен или больше максимального размера
//                historyTasksList.remove(0); // удаляем первую в списке
//            }
//            historyTasksList.add(task); // добавляем в список задачку
//        }

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

    @Override
    public Map<Integer, Node<Task>> getMap() {
        return handeMadeLinkedHashMap;
    }


    @Override
    public List<Task> getHistory() {
        return  new ArrayList<>(historyTasksList);
    }

    @Override
    public void remove(int id) {

    }
}
