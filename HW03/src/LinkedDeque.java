import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Thomas Le
 * @version 1.0
 * @userid thomas30
 * @GTID 903696568
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            if (size == 0) {
                head = new LinkedNode(data, null, null);
                tail = head;
            } else {
                LinkedNode newNode = new LinkedNode(data, null, head);
                head.setPrevious(newNode);
                head = newNode;
            }
            size = size + 1;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            if (size == 0) {
                head = new LinkedNode(data, null, null);
                tail = head;
            } else {
                LinkedNode newNode = new LinkedNode(data, tail, null);
                tail.setNext(newNode);
                tail = newNode;
            }
            size = size + 1;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty");
        } else if (size == 1) {
            T removedData = head.getData();
            head = null;
            tail = null;
            size = size - 1;
            return removedData;
        } else {
            T removedData = head.getData();
            head.getNext().setPrevious(null);
            head = head.getNext();
            size = size - 1;

            return removedData;
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty");
        } else if (size == 1) {
            T removedData = head.getData();
            head = null;
            tail = null;
            size = size - 1;
            return removedData;
        } else {
            T removedData = tail.getData();
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size = size - 1;

            return removedData;
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty");
        } else {
            return tail.getData();
        }
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
