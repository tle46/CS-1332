import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        //Initialize backingArray with size 2n + 1
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        //Copy data into backingArray starting at index 1
        for (int i = 1; i <= data.size(); i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("There is data in the arrayList that is null");
            }
            size = size + 1;
            backingArray[i] = data.get(i - 1);
        }
        //Down heap tree
        for (int i = (int) Math.floor(size / 2); i >= 1; i--) {
            downHeap(i);
        }
    }

    /**
     * Sorts all children of target recursively
     *
     * @param index the index of target
     */
    private void downHeap(int index) {
        int changeIndex = index;
        //Check if has left child
        if (index <= (size / 2)) {
            //Determine lower child if right child exists or if right child does not
            if (index <= (size - 1) / 2 && backingArray[2 * index].compareTo(backingArray[2 * index + 1]) > 0) {
                changeIndex = 2 * index + 1;
            } else {
                changeIndex = 2 * index;
            }
        }
        //Swap if necessary
        if (backingArray[index].compareTo(backingArray[changeIndex]) > 0) {
            swap(index, changeIndex);
            downHeap(changeIndex);
        }
    }

    /**
     * Swaps two elements in array
     *
     * @param index1 index of element 1
     * @param index2 index of element 2
     */
    private void swap(int index1, int index2) {
        T temp = backingArray[index1];
        backingArray[index1] = backingArray[index2];
        backingArray[index2] = temp;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        //Check if backing array is full
        if (size == backingArray.length - 1) {
            resize();
        }
        //Add
        backingArray[size + 1] = data;
        size++;
        //Resort tree (through parent heaps)
        if (size > 1) {
            int current = size;
            int parent = (int) current / 2;
            while (parent >= 1 && backingArray[current].compareTo(backingArray[parent]) < 0) {
                swap(current, parent);
                current = parent;
                parent = (int) current / 2;
            }
        }
    }

    /**
     * Resizes the array to double its current capacity
     */
    private void resize() {
        T[] newArray = (T[]) new Comparable[backingArray.length * 2];
        for (int i = 1; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        T removedData = backingArray[1];
        if (size == 1) {
            //Case clearing tree
            clear();
        } else {
            //Default case
            //Replace min with last element and declare excess element null
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            //Re-sort
            downHeap(1);
        }
        return removedData;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
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
     * Returns the size of the heap.
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
