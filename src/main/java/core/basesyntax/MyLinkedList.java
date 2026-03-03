package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(tail, value, null);
        if (tail == null) {
            head = newNode;
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> oldHead = head;
            Node<T> newNode = new Node<T>(null, value, oldHead);
            head = newNode;
            if (oldHead != null) {
                oldHead.prev = newNode;
            } else {
                tail = newNode;
            }
            size++;
            return;
        }
        Node<T> right = findNodeByIndex(index);
        Node<T> left = findNodeByIndex(index - 1);
        Node<T> newNode = new Node<T>(left, value, right);

        left.next = newNode;
        right.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }

        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return returnElement(index);
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldNode = node.item;
        node.item = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        final T oldNode = node.item;
        Node<T> left = node.prev;
        Node<T> right = node.next;
        if (left == null) {
            head = right;
        } else {
            left.next = right;
        }
        if (right == null) {
            tail = left;
        } else {
            right.prev = left;
        }
        node.prev = null;
        node.next = null;
        size--;
        return oldNode;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (areEqual(object, current.item)) {
                Node<T> left = current.prev;
                Node<T> right = current.next;

                if (left == null) {
                    head = right;
                } else {
                    left.next = right;
                }

                if (right == null) {
                    tail = left;
                } else {
                    right.prev = left;
                }

                current.prev = null;
                current.next = null;

                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private boolean areEqual(T first, T second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    private T returnElement(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}

