package kanban.model;

public class Node<E> {
    E data;
    Node<E> prev;
    Node<E> next;


    public Node(Node<E> prev, E data, Node<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
