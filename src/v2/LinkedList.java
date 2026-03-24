package v2;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T value = head.data;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        size--;
        return value;
    }

    public T getFirst() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    public T getLast() {
        if (isEmpty()) {
            return null;
        }
        return tail.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}