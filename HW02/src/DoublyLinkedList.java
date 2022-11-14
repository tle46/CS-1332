import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Thomas Le
 * @version 1.0
 * @userid thomas30
 * @GTID 903696568
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * - Piazza JUNIT tests
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        //Check for index out of bounds
        if (index <= size && index >= 0) {
            //Check for null argument
            if (data != null) {
                //Perform adding
                if (index == 0) {
                    //Adding to front of list
                    DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, null, head);
                    if (size != 0) {
                        head.setPrevious(newNode);
                    } else {
                        tail = newNode;
                    }
                    head = newNode;
                    size = size + 1;
                } else if (index == size) {
                    //Adding to back of list
                    DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, tail, null);
                    if (size != 0) {
                        tail.setNext(newNode);
                    } else {
                        head = newNode;
                    }
                    tail = newNode;
                    size = size + 1;
                } else if (index == 1) {
                    //Adding at index 1
                    DoublyLinkedListNode<T> counterNode = head;
                    DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data,
                            head, counterNode.getNext());
                    head.setNext(newNode);
                    newNode.getNext().setPrevious(newNode);
                    size = 1 + size;
                } else {
                    //Base case
                    DoublyLinkedListNode<T> counterNode;
                    if (index < size / 2) {
                        //Increment from beginning to right below index
                        counterNode = head;
                        for (int i = 0; i < index - 1; i++) {
                            counterNode = counterNode.getNext();
                        }
                    } else {
                        //Increment from end to right below index
                        counterNode = tail;
                        for (int i = size; i > index; i--) {
                            counterNode = counterNode.getPrevious();
                        }
                    }
                    //Perform adding
                    DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data,
                            counterNode.getPrevious().getNext(), counterNode.getNext());
                    newNode.getPrevious().setNext(newNode);
                    newNode.getNext().setPrevious(newNode);
                    size = size + 1;
                }
            } else {
                throw new IllegalArgumentException("Data can not be null");
            }
        } else {
            throw new IndexOutOfBoundsException("Index must be in [0, array size]");
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        //Checking for index out of bounds
        if (index >= 0 && index < size) {
            //Remove operations
            if (size == 1) {
                //When size is one (make list empty)
                T data = head.getData();
                head = null;
                tail = null;
                size = 0;
                return data;
            } else if (index == 0) {
                //Remove from front
                T data = head.getData();
                head.getNext().setPrevious(null);
                head = head.getNext();
                size = size - 1;
                return data;
            } else if (index == size - 1) {
                //Remove form back
                T data = tail.getData();
                tail.getPrevious().setNext(null);
                tail = tail.getPrevious();
                size = size - 1;
                return data;
            } else {
                //Base case
                DoublyLinkedListNode<T> counterNode;
                if (index < size / 2) {
                    //Increment from beginning to index to index to be removed
                    counterNode = head;
                    for (int i = 0; i < index; i++) {
                        counterNode = counterNode.getNext();
                    }
                } else {
                    //Increment from end to index to be removed
                    counterNode = tail;
                    for (int i = size - 1; i > index; i--) {
                        counterNode = counterNode.getPrevious();
                    }
                }
                //Get data and remove
                T data = counterNode.getData();
                counterNode.getPrevious().setNext(counterNode.getNext());
                counterNode.getNext().setPrevious(counterNode.getPrevious());
                size = size - 1;
                return data;
            }
        } else {
            throw new IndexOutOfBoundsException("Index must be in [0, array size)");
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        } else {
            return removeAtIndex(0);
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < size && index >= 0) {
            if (index == 0) {
                return head.getData();
            } else if (index == size - 1) {
                return tail.getData();
            } else {
                DoublyLinkedListNode<T> counterNode;
                if (index < size / 2) {
                    //Increment from beginning to index
                    counterNode = head;
                    for (int i = 0; i < index; i++) {
                        counterNode = counterNode.getNext();
                    }
                } else {
                    //Increment from end to index
                    counterNode = tail;
                    for (int i = size - 1; i > index; i--) {
                        counterNode = counterNode.getPrevious();
                    }
                }
                return counterNode.getData();
            }
        } else {
            throw new IndexOutOfBoundsException("Index must be in [0, array size]");
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            DoublyLinkedListNode<T> counterNode = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (counterNode.getData().equals(data)) {
                    return removeAtIndex(i);
                }
                counterNode = counterNode.getPrevious();
            }
            throw new NoSuchElementException("There is no such element with that data");
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] newArray = (T[]) new Object[size];
        DoublyLinkedListNode<T> counterNode = head;
        for (int i = 0; i < size; i++) {
            newArray[i] = counterNode.getData();
            counterNode = counterNode.getNext();
        }
        return newArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
