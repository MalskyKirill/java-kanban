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
        int taskId = task.getId(); // получаем id задачки
        remove(taskId); // каждый раз проверяем, если задачка с таким айдишником уже есть в мапе удалим ее

        setNewTail(task); // делаем новую задачку хвостом связного списка
        handeMadeLinkedHashMap.put(taskId, tail); // добавляем айдиник и новый хвост в мапу
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

    private void removeNode(Node node) { // удаляем ноду из двусвязного списка
        Node nextNode = node.getNext(); // получаем ссылку на следующую ноду
        Node prevNode = node.getPrev(); // получаем ссылку на предыдущую ноду

        if (prevNode == null) { // если ссылка на предыдущую ноду null, значит мы удаляем первую ноду
            head = nextNode; // перезаписываем ссылку head и теперь голова ссылается на nextNode
        } else { // ежели нет, значит мы удаляем ноду из середины
            prevNode.setNext(nextNode); // записываем в ссылку предыдущей ноды ссылку на nextNode
        }

        if (nextNode == null) { //если ссылка на следующую ноду null, значит мы удаляем последнюю ноду
            tail = prevNode; // перезаписываем ссылку tail и теперь хвост ссылается на prevNode
        } else { // ежели нет
            nextNode.setPrev(prevNode); // записываем в ссылку следующей ноды ссылку на prevNode
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
        getTasks(); // заполняем список задачками
        return new ArrayList<>(historyTasksList);
    }

    @Override
    public void remove(int id) { // удаляем задачку
        if (handeMadeLinkedHashMap.containsKey(id)) { // проверяем если такой айдишник уже есть в мапе
            removeNode(handeMadeLinkedHashMap.get(id)); // по айдишнику находим ноду и удаляем ее из двусвязного списка
            handeMadeLinkedHashMap.remove(id); // удаляем ноду из мапы
        }
    }
}
