/**
 * Your implementation of an ArrayList.
 *
 * @author THOMAS LE
 * @version 1.0
 * @userid THOMAS30
 * @GTID 903696568
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {
    /**
     * The initial capacity of the ArrayList.
     * 
     */     
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns whether backingArray needs to be resized.
     *
     * @param size intended size of array.
     * @return whether or not the backingArray needs to be resized.
     */
    private boolean checkSize(int size) {
        return size > backingArray.length;
    }

    /**
     * Resizes backingArray, doubling the size of the array.
     */
    private void resize() {
        T[] newArray = (T[]) new Object[2 * backingArray.length];
        for (int i = 0; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
            backingArray = newArray;
        } 
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index <= size && index >= 0) {
            if (data != null) {
                if (checkSize(size + 1)) {
                    resize();
                }
                //Reassigns index i - 1 to i
                for (int i = size; i > index; i--) {
                    backingArray[i] = backingArray[i - 1];
                }
                backingArray[index] = data;
                size = size + 1;
            } else {
                throw new IllegalArgumentException("Null values can not be added.");
            }
        } else {
            throw new IndexOutOfBoundsException("The index must be within [0, array length].");
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        this.addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        this.addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < size && index >= 0) {
            T val = backingArray[index];
            //Variable that assigns to backingArray.
            T temp1;
            //Variable that holds old value of current backingArray
            T temp2 = backingArray[size - 1];
            //i represents index currently getting moved to i - 1
            for (int i = size - 1; i > index; i--) {
                temp1 = temp2;
                temp2 = backingArray[i - 1];
                backingArray[i - 1] = temp1;
            } 
            backingArray[size - 1] = null;
            size = size - 1;

            return val;
        } else {
            throw new IndexOutOfBoundsException("The index must be within in range [0, length of array).");
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
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
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < size && index >= 0) {
            return backingArray[index];
        } else {
            throw new IndexOutOfBoundsException("The index must be within in range [0, length of array).");
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
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
