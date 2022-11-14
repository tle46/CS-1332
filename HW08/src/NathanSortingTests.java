import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

/**
 * This is a basic set of unit tests for Sorting.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author Nathan Duggal
 * @version 1.0
 */
public class NathanSortingTests {

    @Test
    public void testSorts() {
        for (int test = 0; test < 1000; test++) {
            ArrayList<Integer> list = getRandomCollection((int) (Math.random() * 15));

            Object[] listArr = list.toArray();
            Integer[] testArr = new Integer[list.size()];

            for (int i = 0; i < list.size(); i++) {
                testArr[i] = (Integer) listArr[i];
            }

            Collections.sort(list);
            listArr = list.toArray();

            Integer[] sortedArr = new Integer[list.size()];

            for (int i = 0; i < list.size(); i++) {
                sortedArr[i] = (Integer) listArr[i];
            }

            // Insertion
            System.out.println("----------Insertion----------");
            Integer[] insertArr = Arrays.copyOf(testArr, list.size());
            Sorting.insertionSort(insertArr, 
                (x, y) -> x.compareTo(y)
            );
            System.out.println("Expected:");
            printSet(Arrays.asList(sortedArr));
            System.out.println("Actual:");
            printSet(Arrays.asList(insertArr));
            assertArrayEquals(sortedArr, insertArr);

            // Cocktail
            System.out.println("----------Cocktail----------");
            Integer[] cocktailArr = Arrays.copyOf(testArr, list.size());
            Sorting.cocktailSort(cocktailArr, 
                (x, y) -> x.compareTo(y)
            );
            System.out.println("Expected:");
            printSet(Arrays.asList(sortedArr));
            System.out.println("Actual:");
            printSet(Arrays.asList(cocktailArr));
            assertArrayEquals(sortedArr, cocktailArr);

            // Merge
            System.out.println("----------Merge----------");
            Integer[] mergeArr = Arrays.copyOf(testArr, list.size());
            Sorting.mergeSort(mergeArr, 
                (x, y) -> x.compareTo(y)
            );
            System.out.println("Expected:");
            printSet(Arrays.asList(sortedArr));
            System.out.println("Actual:");
            printSet(Arrays.asList(mergeArr));
            assertArrayEquals(sortedArr, mergeArr);

            // Quick
            System.out.println("----------Quick----------");
            Integer[] quickArr = Arrays.copyOf(testArr, list.size());
            Sorting.quickSort(quickArr, 
                (x, y) -> x.compareTo(y), 
                new Random(234)
            );
            System.out.println("Expected:");
            printSet(Arrays.asList(sortedArr));
            System.out.println("Actual:");
            printSet(Arrays.asList(quickArr));
            assertArrayEquals(sortedArr, quickArr);
            
            // Radix
            System.out.println("----------Radix----------");
            int[] radixArr = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                radixArr[i] = testArr[i];
            }
            Sorting.lsdRadixSort(radixArr);
            System.out.println("Expected:");
            printSet(Arrays.asList(sortedArr));
            ArrayList<Integer> radixList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                radixList.add(radixArr[i]);
            }
            System.out.println("Actual:");
            printSet(radixList);
            Integer[] radixEnd = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                radixEnd[i] = radixArr[i];
            }
            assertArrayEquals(sortedArr, radixEnd);

            // Heap
            System.out.println("----------Heap----------");
            Integer[] heapArr = Arrays.copyOf(testArr, list.size());
            int[] heapList = Sorting.heapSort(Arrays.asList(heapArr));
            for (int i = 0; i < list.size(); i++) {
                heapArr[i] = heapList[i];
            }
            System.out.println("Expected:");
            printSet(Arrays.asList(sortedArr));
            System.out.println("Actual:");
            printSet(Arrays.asList(heapArr));
            assertArrayEquals(sortedArr, heapArr);
        }
    }

    /**
     * Produces random set of elements contained in an arraylist
     *
     * @param size the number of elements in the set
     * @return a randomly-generated ordered set of data
     */
    public ArrayList<Integer> getRandomCollection(int size) {
        ArrayList<Integer> set = new ArrayList<>();
        while (set.size() < size) {
            int addInt = (int) (Math.random() * size * 1.5 - size / 2);
            // if (!set.contains(addInt)) {
            
            // }
            if (Math.random() < 0.01) {
                set.add(Integer.MAX_VALUE);
            } else if (Math.random() < 0.01) {
                set.add(Integer.MIN_VALUE);
            } else {
                set.add(addInt);
            }
        }
        return set;
    }

    /**
     * Prints a set in the order Iterator iterates through it
     *
     * @param set the set to be printed
     */
    public void printSet(Iterable<Integer> set) {
        String printString  = "[ ";
        for (Integer i : set) {
            printString += i + ", ";
        }
        if (set.iterator().hasNext()) {
            printString = printString.substring(0, printString.length() - 2);
        }
        printString += " ]";
        System.out.println(printString);
    }
}