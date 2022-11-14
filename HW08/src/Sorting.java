import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author THOMAS LE
 * @version 1.0
 * @userid THOMAS30
 * @GTID 903696568
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        T sortData;
        int sort;
        for (int i = 1; i < arr.length; i++) {
            sortData = arr[i];
            sort = i - 1;

            while (sort >= 0 && comparator.compare(arr[sort], sortData) > 0) {
                arr[sort + 1] = arr[sort];
                sort--;
            }
            arr[sort + 1] = sortData;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        boolean swapped = true;
        int start = 0;
        int end = arr.length - 1;
        int block;

        while (swapped && start < end) {
            swapped = false;
            block = end - 1;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    block = i;
                    swapped = true;
                }
            }
            end = block;

            if (swapped) {
                swapped = false;
                block = start + 1;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        block = i;
                        swapped = true;
                    }
                }
                start = block;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int middle = arr.length / 2;

        //Divide left and right
        T[] left = (T[]) new Object[middle];
        T[] right = (T[]) new Object[length - middle];
        for (int i = 0; i < middle; i++) {
            left[i] = arr[i];
        }
        for (int i = middle; i < length; i++) {
            right[i - middle] = arr[i];
        }

        //Recursively mergesort left and right
        mergeSort(left, comparator);
        mergeSort(right, comparator);

        //Merging left and right
        int leftI = 0;
        int rightI = 0;
        while (leftI < left.length && rightI < right.length) {
            if (comparator.compare(left[leftI], right[rightI]) <= 0) {
                arr[leftI + rightI] = left[leftI];
                leftI++;
            } else {
                arr[leftI + rightI] = right[rightI];
                rightI++;
            }
        }
        while (leftI < left.length) {
            arr[leftI + rightI] = left[leftI];
            leftI++;
        }
        while (rightI < right.length) {
            arr[leftI + rightI] = right[rightI];
            rightI++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random is null");
        }
        quickSorter(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted
     * @param start      start of array to sort
     * @param end        end of array to sort
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     */
    private static <T> void quickSorter(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (start >= end) {
            return;
        }
        int i = start + 1;
        int j = end;

        //Generate pivot
        int pivot = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivot];

        //Switch pivot with first element
        arr[pivot] = arr[start];
        arr[start] = pivotVal;

        T temp;

        //Swap elements below pivot from left with elements above partition on right
        while (i <= j) {
            //Find element below pivot from left
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            //Find element above pivot from right
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            //Swap
            if (i <= j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        //Put the pivot where it belongs
        temp = arr[start];
        arr[start] = arr[j];
        arr[j] = temp;

        //Recursively sort left and right of partition
        quickSorter(arr, start, j - 1, comparator, rand);
        quickSorter(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (arr.length <= 1) {
            return;
        }

        //Find maximum digits
        int max = arr[0];
        int digits = 1;
        for (int element: arr) {
            if (element == Integer.MIN_VALUE) {
                max = Integer.MAX_VALUE;
            } else if (Math.abs(element) > max) {
                max = Math.abs(element);
            }
        }
        while (max >= 10) {
            digits++;
            max = max / 10;
        }

        //Initialize buckets
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int factor = 1;
        //Index through digits, re-sorting buckets by digit each time
        for (int i = 0; i < digits; i++) {
            //Placing elements into buckets
            for (int element: arr) {
                //Account for negatives
                int digit = (element / factor) % 10;
                buckets[digit + 9].addLast(element);
            }
            factor = factor * 10;

            //Placing elements from bucket to array
            int index = 0;
            for (LinkedList<Integer> bucket: buckets) {
                for (Integer num: bucket) {
                    arr[index] = num;
                    index++;
                }
                bucket.clear();
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(data);
        int[] sorted = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            sorted[i] = pq.remove();
        }

        return sorted;
    }
}
