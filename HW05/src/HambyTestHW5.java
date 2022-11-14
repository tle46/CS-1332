import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class HambyTestHW5 {

    private static final int TIMEOUT = 200;
    private MinHeap<Integer> heap;

    @Before
    public void setUp() throws Exception {
        heap = new MinHeap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, heap.size());
        assertArrayEquals(new Comparable[MinHeap.INITIAL_CAPACITY],
                heap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildHeap() {
        Integer[] input = {25, 51, 87, 14, 89, 43, 5, 26};
        ArrayList<Integer> in = new ArrayList<>(Arrays.asList(input));
        heap = new MinHeap<>(in);

        Comparable[] desired = new Comparable[17];
        desired[1] = 5;
        desired[2] = 14;
        desired[3] = 25;
        desired[4] = 26;
        desired[5] = 89;
        desired[6] = 43;
        desired[7] = 87;
        desired[8] = 51;

        assertEquals(8, heap.size());
        assertArrayEquals(desired, heap.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildHeapEmpty() {
        heap = new MinHeap<Integer>(new ArrayList<>());
        assertEquals(0, heap.size());
        assertArrayEquals(new Comparable[1], heap.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildHeapNull() {
        heap = new MinHeap<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildHeapNullElement() {
        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(null);
        input.add(2);
        heap = new MinHeap<>(input);
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        Integer[] input =
            {35, 85, 47, 88, 52, 68, 50, 77, 41, 87, 80, 71, 19, 100, 1};
        for (int i = 0; i < input.length; i++) {
            heap.add(input[i]);
        }
        assertEquals(15, heap.size());
        Integer[] desired = new Integer[26];
        desired[1] = 1;
        desired[2] = 41;
        desired[3] = 19;
        desired[4] = 52;
        desired[5] = 80;
        desired[6] = 47;
        desired[7] = 35;
        desired[8] = 88;
        desired[9] = 77;
        desired[10] = 87;
        desired[11] = 85;
        desired[12] = 71;
        desired[13] = 68;
        desired[14] = 100;
        desired[15] = 50;

        assertArrayEquals(desired, heap.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        heap.add(1);
        heap.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        Integer[] input =
            {35, 85, 47, 88, 52, 68, 50, 77, 41, 87, 80, 71, 19, 100, 1};
        for (int i = 0; i < input.length; i++) {
            heap.add(input[i]);
        }

        Arrays.sort(input);

        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], heap.remove());
        }

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        heap.add(1);
        heap.remove();
        heap.remove();
    }

    @Test(timeout = TIMEOUT)
    public void testGetMin() {
        Integer[] input = 
            {35, 85, 47, 88, 52, 68, 50, 77, 41, 87, 80, 71, 19, 100, 1};
        for (int i = 0; i < input.length; i++) {
            heap.add(input[i]);
        }

        Arrays.sort(input);

        for (int i = 0; i < input.length; i++) {
            assertEquals(input[i], heap.getMin());
            heap.remove();
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetMinEmpty() {
        heap.getMin();
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        assertTrue(heap.isEmpty());
        heap.add(1);
        assertFalse(heap.isEmpty());
        heap.remove();
        assertTrue(heap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        for (int i = 0; i < 100; i++) {
            heap.add(i);
        }
        assertFalse(heap.isEmpty());
        heap.clear();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        assertArrayEquals(new Comparable[MinHeap.INITIAL_CAPACITY],
                heap.getBackingArray());
    }
}