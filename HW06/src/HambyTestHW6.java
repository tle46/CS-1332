import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class HambyTestHW6 {

    private static final int TIMEOUT = 200;

    private LinearProbingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY],
                map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testAlternateInitialization() {
        map = new LinearProbingHashMap<>(2);
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[2], map.getTable());
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testPutBasic() {
        assertNull(map.put(1, "B"));
        assertNull(map.put(0, "A"));
        assertEquals(2, map.size());

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        desired[0] = new LinearProbingMapEntry<>(0, "A");
        desired[1] = new LinearProbingMapEntry<>(1, "B");

        assertArrayEquals(desired, map.getTable());

        assertEquals("A", map.put(0, "C"));
        desired[0] = new LinearProbingMapEntry<>(0, "C");
        assertEquals(2, map.size());
        assertArrayEquals(desired, map.getTable());

        map.put(13, "D");
        desired[2] = new LinearProbingMapEntry<>(13, "D");
        assertEquals(3, map.size());
        assertArrayEquals(desired, map.getTable());
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testPutResize() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(13, "C");
        map.put(3, "D");
        map.put(5, "F");
        map.put(4, "E");
        map.put(6, "G");
        map.put(27, "H");

        assertEquals(8, map.size());

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        desired[0] = new LinearProbingMapEntry<>(0, "A");
        desired[1] = new LinearProbingMapEntry<>(1, "B");
        desired[2] = new LinearProbingMapEntry<>(13, "C");
        desired[3] = new LinearProbingMapEntry<>(3, "D");
        desired[4] = new LinearProbingMapEntry<>(4, "E");
        desired[5] = new LinearProbingMapEntry<>(5, "F");
        desired[6] = new LinearProbingMapEntry<>(6, "G");
        desired[7] = new LinearProbingMapEntry<>(27, "H");

        assertArrayEquals(desired, map.getTable());

        map.put(9, "T");
        assertEquals(9, map.size());

        desired = new LinearProbingMapEntry[27];
        desired[0] = new LinearProbingMapEntry<>(0, "A");
        desired[1] = new LinearProbingMapEntry<>(1, "B");
        desired[13] = new LinearProbingMapEntry<>(13, "C");
        desired[3] = new LinearProbingMapEntry<>(3, "D");
        desired[4] = new LinearProbingMapEntry<>(4, "E");
        desired[5] = new LinearProbingMapEntry<>(5, "F");
        desired[6] = new LinearProbingMapEntry<>(6, "G");
        desired[2] = new LinearProbingMapEntry<>(27, "H");
        desired[9] = new LinearProbingMapEntry<>(9, "T");

        assertArrayEquals(desired, map.getTable());

    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testPutRemovedItems() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(13, "C");
        assertEquals(3, map.size());

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        desired[0] = new LinearProbingMapEntry<>(0, "A");
        desired[1] = new LinearProbingMapEntry<>(1, "B");
        desired[2] = new LinearProbingMapEntry<>(13, "C");

        assertArrayEquals(desired, map.getTable());

        map.remove(1);
        desired[1].setRemoved(true);

        map.put(13, "Y");
        desired[2].setValue("Y");
        assertEquals(2, map.size());
        assertArrayEquals(desired, map.getTable());

        assertNull(map.put(1, "Q"));
        desired[1] = new LinearProbingMapEntry<>(1, "Q");
        assertEquals(3, map.size());
        assertArrayEquals(desired, map.getTable());

        map.remove(1);
        desired[1].setRemoved(true);

        map.put(14, "L");
        desired[1] = new LinearProbingMapEntry<>(14, "L");
        assertEquals(3, map.size());
        assertArrayEquals(desired, map.getTable());

        map.put(1, "G");
        desired[3] = new LinearProbingMapEntry<>(1, "G");
        assertEquals(4, map.size());
        assertArrayEquals(desired, map.getTable());

        map.remove(14);
        desired[1].setRemoved(true);
        map.put(1, "K");
        desired[3].setValue("K");
        assertEquals(3, map.size());
        assertArrayEquals(desired, map.getTable());

        map.remove(1);
        desired[3].setRemoved(true);
        assertNull(map.put(1, "K"));
        desired[1] = new LinearProbingMapEntry<>(1, "K");
        assertEquals(3, map.size());
        assertArrayEquals(desired, map.getTable());
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testPutDelLoop() {
        for (int i = 0; i < LinearProbingHashMap.INITIAL_CAPACITY; i++) {
            map.put(i, "A");
            map.remove(i);
        }

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];

        for (int i = 0; i < desired.length; i++) {
            desired[i] = new LinearProbingMapEntry<>(i, "A");
            desired[i].setRemoved(true);
        }

        assertEquals(0, map.size());
        assertArrayEquals(desired, map.getTable());

        map.put(0, "B");
        map.put(13, "C");

        desired[0] = new LinearProbingMapEntry<>(0, "B");
        desired[1] = new LinearProbingMapEntry<>(13, "C");

        assertEquals(2, map.size());
        assertArrayEquals(desired, map.getTable());
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testPutWrapAround() {
        map.put(12, "A");
        map.put(25, "B");

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];

        desired[12] = new LinearProbingMapEntry<>(12, "A");
        desired[0] = new LinearProbingMapEntry<>(25, "B");

        assertEquals(2, map.size());
        assertArrayEquals(desired, map.getTable());

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        map.put(1, "2");
        map.put(null, "hello");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        map.put(1, "2");
        map.put(2, null);
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testRemove() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(13, "D");
        map.put(27, "E");
        assertEquals(5, map.size());

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        desired[0] = new LinearProbingMapEntry<>(0, "A");
        desired[1] = new LinearProbingMapEntry<>(1, "B");
        desired[2] = new LinearProbingMapEntry<>(2, "C");
        desired[3] = new LinearProbingMapEntry<>(13, "D");
        desired[4] = new LinearProbingMapEntry<>(27, "E");
        assertArrayEquals(desired, map.getTable());

        assertEquals("B", map.remove(1));
        assertEquals("D", map.remove(13));
        assertEquals("E", map.remove(27));
        assertEquals(2, map.size());

        desired[1].setRemoved(true);
        desired[3].setRemoved(true);
        desired[4].setRemoved(true);

        assertArrayEquals(desired, map.getTable());

    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testRemoveWrapAround() {
        map.put(12, "A");
        map.put(25, "B");
        map.remove(25);

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];

        desired[12] = new LinearProbingMapEntry<>(12, "A");
        desired[0] = new LinearProbingMapEntry<>(25, "B");
        desired[0].setRemoved(true);

        assertEquals(1, map.size());
        assertArrayEquals(desired, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullKey() {
        map.put(1, "1");
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveKeyNotPresentEmpty() {
        map.remove(1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveKeyNotPresentCollision() {
        map.put(0, "A");
        map.put(1, "B");
        map.remove(13);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveKeyNotPresentAllDel() {
        for (int i = 0; i < LinearProbingHashMap.INITIAL_CAPACITY; i++) {
            map.put(i, "A");
            map.remove(i);
        }

        map.remove(15);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveKeyNotPresentAlreadyGone() {
        map.put(0, "A");
        map.put(1, "B");
        map.remove(1);
        map.remove(1);
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(13, "D");
        map.put(14, "E");

        assertEquals("B", map.get(1));
        assertEquals("D", map.get(13));

        map.remove(1);
        assertEquals("D", map.get(13));
        assertEquals("E", map.get(14));

    }

    @Test(timeout = TIMEOUT)
    public void testGetWrapAround() {
        map.put(12, "A");
        map.put(25, "B");

        assertEquals("B", map.get(25));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        map.put(1, "A");
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetKeyNotPresentEmpty() {
        map.get(1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetKeyNotPresentCollision() {
        map.put(0, "A");
        map.put(1, "B");
        map.get(13);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetKeyNotPresentAllDel() {
        for (int i = 0; i < LinearProbingHashMap.INITIAL_CAPACITY; i++) {
            map.put(i, "A");
            map.remove(i);
        }

        map.get(15);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetKeyNotPresentAlreadyGone() {
        map.put(0, "A");
        map.put(1, "B");
        map.remove(1);
        map.get(1);
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(13, "D");
        map.put(14, "E");

        assertTrue(map.containsKey(0));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsKey(13));
        assertTrue(map.containsKey(14));

        assertFalse(map.containsKey(3));
        assertFalse(map.containsKey(4));

        map.remove(1);

        assertFalse(map.containsKey(1));
        assertTrue(map.containsKey(13));
        assertTrue(map.containsKey(14));

        map.remove(13);

        assertFalse(map.containsKey(13));
        assertTrue(map.containsKey(14));

        map.remove(0);
        assertFalse(map.containsKey(13));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKeyWrapAround() {
        map.put(12, "A");
        map.put(25, "B");

        assertTrue(map.containsKey(25));
        assertFalse(map.containsKey(38));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsKeyNullKey() {
        map.put(1, "");
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(13, "D");
        map.put(14, "E");
        map.put(9, "F");

        map.remove(1);

        Set<Integer> keySet = map.keySet();

        assertEquals(5, keySet.size());
        assertTrue(keySet.contains(0));
        assertTrue(keySet.contains(2));
        assertTrue(keySet.contains(13));
        assertTrue(keySet.contains(14));
        assertTrue(keySet.contains(9));
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(13, "D");
        map.put(14, "E");
        map.put(9, "F");

        map.remove(1);

        List<String> values = map.values();

        assertEquals(5, values.size());
        String[] desired = {"A", "C", "D", "E", "F"};
        assertArrayEquals(desired, values.toArray());
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testResizeBackingTable() {
        map.put(12, "A");
        map.put(13, "B");
        map.put(30, "C");
        map.put(18, "D");
        map.put(4, "E");
        map.remove(4);

        map.resizeBackingTable(5);

        assertEquals(4, map.size());

        LinearProbingMapEntry<Integer, String>[] desired =
                new LinearProbingMapEntry[5];

        desired[0] = new LinearProbingMapEntry<>(30, "C");
        desired[2] = new LinearProbingMapEntry<>(12, "A");
        desired[3] = new LinearProbingMapEntry<>(13, "B");
        desired[4] = new LinearProbingMapEntry<>(18, "D");

        assertArrayEquals(desired, map.getTable());

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testResizeBackingTableTooSmall() {
        map.put(0, "A");
        map.put(1, "B");
        map.resizeBackingTable(1);
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        map.put(0, "A");
        map.put(5, "B");

        assertEquals(2, map.size());

        map.clear();

        assertEquals(0, map.size());
        assertArrayEquals(
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY],
                map.getTable()
        );

    }

    @Test(timeout = TIMEOUT)
    public void testNoAbsError() {
        map.put(Integer.MIN_VALUE, "A");
        assertEquals(1, map.size());
    }
}