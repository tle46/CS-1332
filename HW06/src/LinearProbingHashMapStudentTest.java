import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for LinearProbingHashMap.
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
 * @author CS 1332 TAs
 * @version 1.0
 */
public class LinearProbingHashMapStudentTest {

    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();

    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }
}
