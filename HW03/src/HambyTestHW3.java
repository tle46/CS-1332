import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class HambyTestHW3 {

    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setUp() throws Exception {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayDeque.INITIAL_CAPACITY],
                array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedAddFirst() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst(String.valueOf(i));
        }
        LinkedNode<String> head = linked.getHead();
        for (int i = 999; i >= 0; i--) {
            assertEquals(head.getData(), String.valueOf(i));
            head = head.getNext();
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedAddFirstNull() {
        linked.addFirst("a");
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedAddLast() {
        for (int i = 0; i < 1000; i++) {
            linked.addLast(String.valueOf(i));
        }
        LinkedNode<String> head = linked.getHead();
        for (int i = 0; i < 1000; i++) {
            assertEquals(head.getData(), String.valueOf(i));
            head = head.getNext();
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedAddLastNull() {
        linked.addLast("a");
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedRemoveFirst() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst(String.valueOf(i));
        }
        for (int i = 999; i >= 0; i--) {
            assertEquals(linked.removeFirst(), String.valueOf(i));
        }

        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedRemoveFirstEmpty() {
        linked.addFirst("a");
        linked.removeFirst();
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedRemoveLast() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst(String.valueOf(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(linked.removeLast(), String.valueOf(i));
        }

        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedRemoveLastEmpty() {
        linked.addFirst("a");
        linked.removeLast();
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedGetFirst() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst(String.valueOf(i));
        }
        for (int i = 999; i >= 0; i--) {
            assertEquals(linked.getFirst(), String.valueOf(i));
            linked.removeFirst();
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedGetFirstEmpty() {
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedGetLast() {
        for (int i = 0; i < 1000; i++) {
            linked.addFirst(String.valueOf(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(linked.getLast(), String.valueOf(i));
            linked.removeLast();
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedGetLastEmpty() {
        linked.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayAddFirst() {

        array.addFirst("a");
        array.addFirst("b");
        array.addFirst("c");
        array.addFirst("d");

        Object[] desired = new Object[ArrayDeque.INITIAL_CAPACITY];
        desired[7] = "d";
        desired[8] = "c";
        desired[9] = "b";
        desired[10] = "a";

        assertArrayEquals(desired, array.getBackingArray());

        array = new ArrayDeque<>();

        for (int i = 0; i < 1000; i++) {
            array.addFirst(String.valueOf(i));
        }

        assertEquals(array.size(), 1000);

        for (int i = 0; i < 1000; i++) {
            assertEquals(array.removeLast(), String.valueOf(i));
        }

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayAddFirstNull() {
        array.addFirst("a");
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayAddLast() {
        for (int i = 0; i < 1000; i++) {
            array.addLast(String.valueOf(i));
        }

        Object[] test = array.getBackingArray();
        for (int i = 0; i < 1000; i++) {
            assertEquals(test[i], String.valueOf(i));
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayAddLastNull() {
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayRemoveFront() {
        for (int i = 0; i < 1000; i++) {
            array.addLast(String.valueOf(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(array.removeFirst(), String.valueOf(i));
        }
        assertEquals(array.size(), 0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayRemoveFrontEmpty() {
        array.addLast("a");
        array.removeFirst();
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayRemoveLast() {
        for (int i = 0; i < 1000; i++) {
            array.addFirst(String.valueOf(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(array.removeLast(), String.valueOf(i));
        }
        assertEquals(array.size(), 0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayRemoveLastEmpty() {
        array.addLast("a");
        array.removeLast();
        array.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayGetFirst() {
        for (int i = 0; i < 1000; i++) {
            array.addFirst(String.valueOf(i));
        }
        for (int i = 999; i >= 0; i--) {
            assertEquals(array.getFirst(), String.valueOf(i));
            array.removeFirst();
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayGetFirstEmpty() {
        array.getFirst();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayGetLast() {
        for (int i = 0; i < 1000; i++) {
            array.addFirst(String.valueOf(i));
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(array.getLast(), String.valueOf(i));
            array.removeLast();
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayGetLastEmpty() {
        array.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayWrapAround() {
        array.addFirst("a");
        for(int i = 0; i < 1000; i++) {
            array.addFirst("a");
            array.removeLast();
        }
        assertEquals(array.size(), 1);
    }

}