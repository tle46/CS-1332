import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class HeapTests {

    private static final int TIMEOUT = 200;
    private MinHeap<Integer> minHeap;

    @Before
    public void setUp() {
        minHeap = new MinHeap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, minHeap.size());
        assertArrayEquals(new Comparable[MinHeap.INITIAL_CAPACITY],
                minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildHeap() {
        /*
                 6
               /   \
              8     4
             / \
            2   1

            ->

                 1
               /   \
              2     4
             / \
            6   8
        */
        ArrayList<Integer> data = new ArrayList<>();
        data.add(6);
        data.add(8);
        data.add(4);
        data.add(2);
        data.add(1);
        minHeap = new MinHeap<>(data);

        assertEquals(5, minHeap.size());
        Integer[] expected = new Integer[11];
        expected[1] = 1;
        expected[2] = 2;
        expected[3] = 4;
        expected[4] = 6;
        expected[5] = 8;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        /*
                 1
               /   \
              2     6
             / \
            4   8
        */
        minHeap.add(4);
        minHeap.add(2);
        minHeap.add(6);
        minHeap.add(1);
        minHeap.add(8);

        assertEquals(5, minHeap.size());
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        expected[1] = 1;
        expected[2] = 2;
        expected[3] = 6;
        expected[4] = 4;
        expected[5] = 8;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        /*
                 1
               /   \
              2     4
             / \
            8   6

            ->

              6
             /
            8
        */
        minHeap.add(4);
        minHeap.add(8);
        minHeap.add(2);
        minHeap.add(6);
        minHeap.add(1);
        assertEquals((Integer) 1, minHeap.remove());
        assertEquals((Integer) 2, minHeap.remove());
        assertEquals((Integer) 4, minHeap.remove());

        assertEquals(2, minHeap.size());
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        expected[1] = 6;
        expected[2] = 8;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testGetMin() {
        Integer temp = 1;

        /*
                 1
               /   \
              2     3
             / \
            4   5
         */
        minHeap.add(temp);
        for (int i = 2; i < 6; i++) {
            minHeap.add(i);
        }

        assertSame(temp, minHeap.getMin());
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyAndClear() {
        // Should be empty at initialization
        assertTrue(minHeap.isEmpty());

        // Should not be empty after adding elements
        /*
                 1
               /   \
              2     3
             / \
            4   5
         */
        for (int i = 1; i < 6; i++) {
            minHeap.add(i);
        }
        assertFalse(minHeap.isEmpty());

        // Clearing the list should empty the array and reset size
        minHeap.clear();
        assertTrue(minHeap.isEmpty());
        assertEquals(0, minHeap.size());
        assertArrayEquals(new Comparable[MinHeap.INITIAL_CAPACITY],
                minHeap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testHeapInitialization() {
        MinHeap<Integer> testHeap = new MinHeap<>();
        Comparable[] heapArray = testHeap.getBackingArray();
        Integer[] expected = new Integer[13];
        assertArrayEquals(heapArray, expected);
        assertEquals(heapArray.length, 13);
        assertEquals(testHeap.size(), 0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructionWithNullList() {
        MinHeap<Integer> testHeap = new MinHeap<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructionWithNullsInList() {
        ArrayList<Integer> testList = new ArrayList<>(4);
        testList.add(1);
        testList.add(2);
        testList.add(null);
        MinHeap<Integer> testHeap = new MinHeap<>(testList);
    }


    /*
     * We create an ArrayList: [1,2,3,4]
     * Expected Heap:
     *          1
     *        /   \
     *       2    3
     *      /
     *     4
     */
    @Test(timeout = TIMEOUT)
    public void testConstructionWithList() {
        ArrayList<Integer> testList = new ArrayList<>(4);
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);

        MinHeap<Integer> testHeap = new MinHeap<Integer>(testList);
        // Capacity should be 9
        Integer[] expected = new Integer[9];
        expected[0] = null;
        expected[1] = 1;
        expected[2] = 2;
        expected[3] = 3;
        expected[4] = 4;
        assertArrayEquals(expected, testHeap.getBackingArray());
        assertEquals(testHeap.size(), 4);


        /*  We create an ArrayList: [4,3,2,1]
         * Expected Heap:
         *          1
         *        /   \
         *       3    2
         *      /
         *     4
         */
        ArrayList<Integer> testList2 = new ArrayList<>(4);
        testList2.add(4);
        testList2.add(3);
        testList2.add(2);
        testList2.add(1);

        MinHeap<Integer> testHeap2 = new MinHeap<Integer>(testList2);
        // Capacity should be 9
        expected = new Integer[9];
        expected[0] = null;
        expected[1] = 1;
        expected[2] = 3;
        expected[3] = 2;
        expected[4] = 4;
        assertArrayEquals(expected, testHeap2.getBackingArray());
        assertEquals(testHeap2.size(), 4);

    }

    @Test(timeout = TIMEOUT)
    public void testConstructionWithList2() {
        ArrayList<Integer> testList = new ArrayList<>(20);
        testList.add(10);
        testList.add(9);
        testList.add(8);
        testList.add(7);
        testList.add(6);
        testList.add(5);
        testList.add(4);
        testList.add(3);
        testList.add(2);
        testList.add(1);
        testList.add(-10);
        testList.add(-17);
        testList.add(-23);
        testList.add(45);
        testList.add(66);
        testList.add(-35);
        testList.add(-125);
        testList.add(98);
        testList.add(100);
        testList.add(-36);
        /*  We create an ArrayList: [10,9,8,7,6,5,4,3,2,1,-10,-17,-23,45,66,-35,-125,98,100,-36]
         * Expected Heap:
         *                  -125
         *             /           \
         *           -35            -23
         *       /       \         /     \
         *      2         -35     -17      4
         *    /  \        /  \    /  \   /   \
         *   3   10      1  -10  8   5  45   66
         *  / \  / \    /
         * 7  9 98 100  6
         */
        MinHeap<Integer> testHeap = new MinHeap<Integer>(testList);
        // Capacity should be 9
        Integer[] expected = new Integer[41];
        expected[0] = null;
        expected[1] = -125;
        expected[2] = -36;
        expected[3] = -23;
        expected[4] = -35;
        expected[5] = -10;
        expected[6] = -17;
        expected[7] = 4;
        expected[8] = 3;
        expected[9] = 2;
        expected[10] = 1;
        expected[11] = 10;
        expected[12] = 8;
        expected[13] = 5;
        expected[14] = 45;
        expected[15] = 66;
        expected[16] = 7;
        expected[17] = 9;
        expected[18] = 98;
        expected[19] = 100;
        expected[20] = 6;


        assertArrayEquals(expected, testHeap.getBackingArray());
        assertEquals(testHeap.size(), 20);
    }

    @Test(timeout = TIMEOUT)
    public void constructWithEmptyList() {
        MinHeap<Integer> testHeap = new MinHeap<>(new ArrayList<Integer>());
        assertEquals(testHeap.size(), 0);
        assertArrayEquals(testHeap.getBackingArray(), new Integer[]{null});
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addNull() {
        minHeap.add(null);
    }


    @Test(timeout = TIMEOUT)
    public void checkResizeWhileAdd() {
        Comparable[] heapArray = minHeap.getBackingArray();
        assertEquals(13, heapArray.length);
        for (int numberToAdd = 12; numberToAdd >= 1; numberToAdd -= 1) {
            minHeap.add(numberToAdd);
        }
        heapArray = minHeap.getBackingArray();
        assertEquals(13, heapArray.length);
        minHeap.add(13);
        heapArray = minHeap.getBackingArray();
        assertEquals(26, heapArray.length);
        for (int numberToAdd = 25; numberToAdd >= 14; numberToAdd -= 1) {
            minHeap.add(numberToAdd);
        }
        heapArray = minHeap.getBackingArray();
        assertEquals(26, heapArray.length);
        minHeap.add(26);
        heapArray = minHeap.getBackingArray();
        assertEquals(52, heapArray.length);
    }

    @Test(timeout = TIMEOUT)
    public void AddTest1() {
        for (int numberToAdd = 26; numberToAdd >= 1; numberToAdd -= 1) {
            minHeap.add(numberToAdd);
        }
        Integer[] expected = new Integer[52];
        expected[0] = null;
        expected[1] = 1;
        expected[2] = 5;
        expected[3] = 2;
        expected[4] = 10;
        expected[5] = 6;
        expected[6] = 3;
        expected[7] = 14;
        expected[8] = 17;
        expected[9] = 11;
        expected[10] = 9;
        expected[11] = 7;
        expected[12] = 13;
        expected[13] = 4;
        expected[14] = 22;
        expected[15] = 15;
        expected[16] = 26;
        expected[17] = 20;
        expected[18] = 23;
        expected[19] = 12;
        expected[20] = 24;
        expected[21] = 18;
        expected[22] = 19;
        expected[23] = 8;
        expected[24] = 25;
        expected[25] = 16;
        expected[26] = 21;
        assertArrayEquals(expected, minHeap.getBackingArray());
        assertEquals(26, minHeap.size());
    }

    @Test(timeout = TIMEOUT)
    public void AddTest2() {
        minHeap.add(10);
        minHeap.add(9);
        minHeap.add(8);
        minHeap.add(11);
        minHeap.add(5);
        minHeap.add(4);
        minHeap.add(7);
        minHeap.add(6);
        Integer[] expected = new Integer[13];
        expected[1] = 4;
        expected[2] = 6;
        expected[3] = 5;
        expected[4] = 8;
        expected[5] = 10;
        expected[6] = 9;
        expected[7] = 7;
        expected[8] = 11;
        assertArrayEquals(expected, minHeap.getBackingArray());
        assertEquals(8, minHeap.size());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeEmpty() {
        minHeap.remove();
    }

    @Test(timeout = TIMEOUT)
    public void removeSingle() {
        minHeap.add(1);
        assertEquals(1, minHeap.size());
        Integer removed = minHeap.remove();
        assertTrue(removed == 1);
        assertEquals(0, minHeap.size());
        Comparable[] heapArray = minHeap.getBackingArray();
        for (int index = 0; index < 13; index++) {
            assertNull(heapArray[index]);
        }
    }


    @Test(timeout = TIMEOUT)
    public void removeTest1() {
        ArrayList<Integer> testList = new ArrayList<>(20);
        testList.add(10);
        testList.add(9);
        testList.add(8);
        testList.add(7);
        testList.add(6);
        testList.add(5);
        testList.add(4);
        testList.add(3);
        testList.add(2);
        testList.add(1);
        testList.add(-10);
        testList.add(-17);
        testList.add(-23);
        testList.add(45);
        testList.add(66);
        testList.add(-35);
        testList.add(-125);
        testList.add(98);
        testList.add(100);
        testList.add(-36);
        Integer[] expected = new Integer[41];
        expected[0] = null;
        expected[1] = -125;
        expected[2] = -36;
        expected[3] = -23;
        expected[4] = -35;
        expected[5] = -10;
        expected[6] = -17;
        expected[7] = 4;
        expected[8] = 3;
        expected[9] = 2;
        expected[10] = 1;
        expected[11] = 10;
        expected[12] = 8;
        expected[13] = 5;
        expected[14] = 45;
        expected[15] = 66;
        expected[16] = 7;
        expected[17] = 9;
        expected[18] = 98;
        expected[19] = 100;
        expected[20] = 6;
        MinHeap<Integer> testHeap = new MinHeap<>(testList);
        Integer removedElement;
        removedElement = testHeap.remove();
        assertTrue(removedElement == -125);
        assertEquals(19, testHeap.size());
        expected[20] = null;
        expected[1] = -36;
        expected[2] = -35;
        expected[4] = 2;
        expected[9] = 6;
        assertArrayEquals(expected, testHeap.getBackingArray());
    }



}