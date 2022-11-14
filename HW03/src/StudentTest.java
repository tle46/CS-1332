import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Saad Mufti
 */
public class StudentTest {

    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    private static final String SINGLE_DATA = "1";

    @Before
    public void init() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    /**
     * Returns a properly casted instance of array.getBackingArray
     * @return array.getBackingArray()
     */
    @SuppressWarnings("RedundantCast")
    private Object[] backingArray() {
        return (Object[]) array.getBackingArray();
    }

    /**
     * <b>Verifies</b>
     * <ul>
     *     <li>Adding multiple elements to both array and linked result in the correct size</li>
     * </ul>
     */
    @Test
    public void multiFill() {
        String[] items = {"1", "2", "3", "4", "5", "6"};
        for (int i = items.length - 1; i >= 0; i--) {
            array.addFirst(items[i]);
            linked.addFirst(items[i]);
        }
        assertEquals(items.length, array.size());
        assertEquals(items.length, linked.size());
    }

    /**
     * Adds a single element to array and linked using addFirst()
     */
    private void singleFill() {
        array.addFirst(SINGLE_DATA);
        linked.addFirst(SINGLE_DATA);
    }


    /**
     * <b>Verifies:</b>
     * <ul>
     *     <li>addFirst() actually adds elements</li>
     *     <li>The ArrayDeque puts its first element in the back of the backingArray</li>
     *     <li>The size of array and linked are 1 after adding the single element</li>
     *     <li>getFirst() and getLast() both return the same data for a size 1 deque</li>
     *     <li>The head and tail of the LinkedDeque are the same LinkedNode that have a null next/last reference</li>
     * </ul>
     */
    @Test
    public void singleAddFirst() {
        array.addFirst(SINGLE_DATA);
        linked.addFirst(SINGLE_DATA);

        assertEquals(SINGLE_DATA, array.getFirst());
        assertEquals(SINGLE_DATA, array.getLast());
        assertEquals(1, array.size());
        assertEquals(SINGLE_DATA, backingArray()[ArrayDeque.INITIAL_CAPACITY - 1]);

        assertEquals(SINGLE_DATA, linked.getFirst());
        assertEquals(SINGLE_DATA, linked.getLast());
        assertEquals(1, linked.size());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getHead().getNext());
        assertNull(linked.getTail().getPrevious());
        assertNull(linked.getTail().getNext());
    }

    /**
     * <b>Verifies:</b>
     * <ul>
     *     <li>addLast() actually adds elements</li>
     *     <li>The ArrayDeque puts its last element in the front of the backingArray</li>
     *     <li>The size of array and linked are 1 after adding the single element</li>
     *     <li>getFirst() and getLast() both return the same data for a size 1 deque</li>
     *     <li>The head and tail of the LinkedDeque are the same LinkedNode that have a null next/last reference</li>
     * </ul>
     */
    @Test
    public void singleAddLast() {
        array.addLast(SINGLE_DATA);
        linked.addLast(SINGLE_DATA);

        assertEquals(SINGLE_DATA, array.getFirst());
        assertEquals(SINGLE_DATA, array.getLast());
        assertEquals(SINGLE_DATA, backingArray()[0]);

        assertEquals(SINGLE_DATA, linked.getFirst());
        assertEquals(SINGLE_DATA, linked.getLast());
        assertNull(linked.getHead().getPrevious());
        assertNull(linked.getHead().getNext());
        assertNull(linked.getTail().getPrevious());
        assertNull(linked.getTail().getNext());
    }

    /**
     * <b>Verifies:</b>
     * <ul>
     *     <li>Once full, adding to the ArrayDeque resizes its backingArray to twice of ArrayDeque.INITIAL_CAPACITY</li>
     *     <li>After adding when the backingArray was previously full, the size is still 1 + the previous size</li>
     * </ul>
     */
    @Test
    public void resizing() {
        String[] payload = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        for (int i = payload.length - 1; i >= 0; i--) {
            array.addFirst(payload[i]);
        }
        assertEquals(payload.length, array.size());

        array.addLast("a");
        assertEquals(ArrayDeque.INITIAL_CAPACITY * 2, backingArray().length);
        assertEquals(payload.length + 1, array.size());
    }

    /**
     * <b>Verifies:</b>
     * <ul>
     *     <li>addFirst() places new elements in the expected order in the backingArray</li>
     *     <li>removeLast() replaces the correct element in backingArray with null, and returns the correct value</li>
     *     <li>Adding an element in the back with addLast() is added in the correct position, including cases
     *     where the index must be wrapped to before front</li>
     *     <li>After adding elements with circular indexing, getLast() and getFirst() still work as expected</li>
     *     <li>backingArray can be correctly filled with addFirst()</li>
     *     <li>the backingArray is not resized preemptively</li>
     * </ul>
     */
    @Test
    public void wrapAround() {
        String[] vals = {"1", "2", "3"};
        for (String val : vals) {
            array.addFirst(val);
        }
        assertArrayEquals(new String[]{null, null, null, null, null, null, null, null, "3", "2", "1"}, backingArray());
        assertEquals("1", array.removeLast());
        assertNull(backingArray()[backingArray().length - 1]);

        array.addLast("1");
        assertEquals("1", backingArray()[backingArray().length - 1]);
        array.addLast("w");
        assertEquals("w", backingArray()[0]);
        for (int i = 0; i < 7; i++) {
            array.addLast("w" + i);
        }
        assertEquals("3", array.getFirst());
        assertEquals("w6", array.getLast());

        init();
        for (int i = 0; i < 11; i++) {
            array.addFirst(i + "");
        }
        assertEquals(ArrayDeque.INITIAL_CAPACITY, backingArray().length);
        assertArrayEquals(new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"}, backingArray());
        array.addFirst("first");
        assertEquals("first", backingArray()[0]);
        assertEquals("first", array.getFirst());
    }


    /**
     * <b>Verifies removing from a deque of size 1 works as expected:</b>
     * <ul>
     *     <li>The head and tail of a LinkedDeque are null</li>
     *     <li>The size of both deques are 0</li>
     *     <li>getFirst(), getLast(), removeLast(), and removeFirst() throw a NoSuchElementException
     *     for both deques after removal</li>
     *     <li>Each element of the ArrayDeque's backingArray is null</li>
     * </ul>
     */
    @Test
    public void singleRemoval() {
        singleFill();

        assertEquals(SINGLE_DATA, linked.removeFirst());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());

        assertThrows(NoSuchElementException.class, linked::getFirst);
        assertThrows(NoSuchElementException.class, linked::getLast);
        assertThrows(NoSuchElementException.class, linked::removeLast);
        assertThrows(NoSuchElementException.class, linked::removeFirst);


        assertEquals(SINGLE_DATA, array.removeFirst());
        assertEquals(0, array.size());
        for (Object o : backingArray()) {
            assertNull(o);
        }

        assertThrows(NoSuchElementException.class, array::getFirst);
        assertThrows(NoSuchElementException.class, array::getLast);
        assertThrows(NoSuchElementException.class, array::removeLast);
        assertThrows(NoSuchElementException.class, array::removeFirst);
    }

    /**
     * <b>Verifies:</b>
     * <ul>
     *     <li>Adding (both addFirst() and addLast()) a null value to array throws an IllegalArgumentException.</li>
     *     <li>Adding (both addFirst() and addLast()) a null value to linked throws an IllegalArgumentException.</li>
     * </ul>
     */
    @Test
    public void nullAdd() {
        assertThrows(IllegalArgumentException.class, () -> array.addLast(null));
        assertThrows(IllegalArgumentException.class, () -> array.addFirst(null));

        assertThrows(IllegalArgumentException.class, () -> linked.addLast(null));
        assertThrows(IllegalArgumentException.class, () -> linked.addFirst(null));
    }
}