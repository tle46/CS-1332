import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoublyLinkedListTest {
    private DoublyLinkedList<String> list;

    /**
     * Initialize list to an empty DoublyLinkedList.
     */
    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    /**
     * Clear list and populate it with the sample values ["0", "1", "2", "3",
     * "4"].
     */
    private void fill() {
        list.clear();
        for (int i = 0; i < 5; ++i) {
            list.addToBack(String.valueOf(i));
        }
    }

    /**
     * Assert that the backing list of nodes equals the given list and is
     * internally consistent.
     */
    private void assertDLLEquals(List<String> oList) {
        assertEquals(list.size(), oList.size(), "List size is not as expected");
        if (oList.size() == 0) {
            assertNull(list.getHead(), "Head of empty list is not null");
            assertNull(list.getTail(), "Tail of empty list is not null");
            return;
        }

        DoublyLinkedListNode<String> curr = list.getHead();
        assertNotNull(curr, "Head of non-empty list is null");
        assertNull(curr.getPrevious(), "Head has non-null previous node");

        ListIterator<String> it = oList.listIterator();
        while (it.nextIndex() < oList.size() - 1) {
            assertEquals(curr.getData(), it.next(), "List data is not as "
                            + "expected");
            DoublyLinkedListNode<String> next = curr.getNext();
            assertNotNull(next, "List prematurely ends (null next node)");
            assertSame(next.getPrevious(), curr, "Node's previous pointer "
                    + "does not point to the previous element in the list");
            curr = next;
        }

        assertSame(curr, list.getTail(), "List does not end with tail");
        assertEquals(curr.getData(), it.next(), "List data is not as "
                + "expected");
        assertNull(curr.getNext(), "Tail has non-null next node");
    }

    /**
     * Test addAtIndex() for various positions in a list.
     */
    @Test
    public void addAtIndex() {
        fill();
        list.addAtIndex(0, "a");
        assertDLLEquals(List.of("a", "0", "1", "2", "3", "4"));
        list.addAtIndex(3, "b");
        assertDLLEquals(List.of("a", "0", "1", "b", "2", "3", "4"));
        list.addAtIndex(7, "c");
        assertDLLEquals(List.of("a", "0", "1", "b", "2", "3", "4", "c"));
    }

    /**
     * Test addAtIndex() for an empty list.
     */
    @Test
    public void addAtIndexEmpty() {
        list.addAtIndex(0, "a");
        assertDLLEquals(List.of("a"));
    }

    /**
     * Test addAtIndex() for a list of size 1.
     */
    @Test
    public void addAtIndexSize1() {
        list.addToBack("a");
        list.addAtIndex(0, "b");
        assertDLLEquals(List.of("b", "a"));
        list.clear();
        list.addToBack("a");
        list.addAtIndex(1, "b");
        assertDLLEquals(List.of("a", "b"));
    }

    /**
     * Test whether addAtIndex() throws IndexOutOfBoundsException only when
     * index is not in [0, size].
     */
    @Test
    public void addAtIndexThrowsIndexOutOfBoundsException() {
        fill();
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.addAtIndex(-1, ""), "Passing an index < 0 "
                        + "does not throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.addAtIndex(list.size() + 1, ""), "Passing an index"
                        + " > size does not throw IndexOutOfBoundsException");
        assertDoesNotThrow(() -> list.addAtIndex(0, ""), "Passing a valid "
                + "index (0) throws");
        assertDoesNotThrow(() -> list.addAtIndex(list.size(), ""), "Passing a"
                + " valid index (size) throws");
    }

    /**
     * Test whether addAtIndex() throws IllegalArgumentException only when
     * data is null.
     */
    @Test
    public void addAtIndexThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> list.addAtIndex(0,
                null), "Passing null data does not throw "
                + "IllegalArgumentException");
        assertDoesNotThrow(() -> list.addAtIndex(0, ""), "Passing valid "
                + "(non-null) data throws");
    }

    /**
     * Test addToFront() for a populated list.
     */
    @Test
    public void addToFront() {
        fill();
        list.addToFront("a");
        assertDLLEquals(List.of("a", "0", "1", "2", "3", "4"));
    }

    /**
     * Test addToFront() for an empty list.
     */
    @Test
    public void addToFrontEmpty() {
        list.addToFront("a");
        assertDLLEquals(List.of("a"));
    }

    /**
     * Test addToFront() for a list of size 1.
     */
    @Test
    public void addToFrontSize1() {
        list.addToBack("a");
        list.addToFront("b");
        assertDLLEquals(List.of("b", "a"));
    }

    /**
     * Test whether addToFront() throws IllegalArgumentException only when
     * data is null.
     */
    @Test
    public void addToFrontThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> list.addToFront(null), "Passing null data does not "
                        + "throw IllegalArgumentException");
        assertDoesNotThrow(() -> list.addToFront(""), "Passing valid "
                + "(non-null) data throws");
    }

    /**
     * Test addToBack() for a populated list.
     */
    @Test
    public void addToBack() {
        fill();
        list.addToBack("a");
        assertDLLEquals(List.of("0", "1", "2", "3", "4", "a"));
    }

    /**
     * Test addToBack() for an empty list.
     */
    @Test
    public void addToBackEmpty() {
        list.addToBack("a");
        assertDLLEquals(List.of("a"));
    }

    /**
     * Test addToBack() for a list of size 1.
     */
    @Test
    public void addToBackSize1() {
        list.addToBack("a");
        list.addToBack("b");
        assertDLLEquals(List.of("a", "b"));
    }

    /**
     * Test whether addToBack() throws IllegalArgumentException only when
     * data is null.
     */
    @Test
    public void addToBackThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> list.addToBack(null), "Passing null data does not "
                        + "throw IllegalArgumentException");
        assertDoesNotThrow(() -> list.addToBack(""), "Passing valid "
                + "(non-null) data throws");
    }

    /**
     * Test removeAtIndex() for various positions in a list.
     */
    @Test
    public void removeAtIndex() {
        fill();
        assertEquals(list.removeAtIndex(0), "0", "Removing from index 0 does "
                + "not return item at index 0");
        assertDLLEquals(List.of("1", "2", "3", "4"));
        assertEquals(list.removeAtIndex(1), "2", "Removing from index 1 does "
                + "not return item at index 1");
        assertDLLEquals(List.of("1", "3", "4"));
        assertEquals(list.removeAtIndex(2), "4", "Removing from index 2 does "
                + "not return item at index 2");
        assertDLLEquals(List.of("1", "3"));
    }

    /**
     * Test removeAtIndex() for a list of size 1.
     */
    @Test
    public void removeAtIndexSize1() {
        list.addToBack("a");
        assertEquals(list.removeAtIndex(0), "a", "Removing does not return "
                + "item");
        assertDLLEquals(List.of());
    }

    /**
     * Test whether removeAtIndex() throws IndexOutOfBoundsException only
     * when index is not in [0, size).
     */
    @Test
    public void removeAtIndexThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> list.removeAtIndex(0), "Removing from an empty list "
                    + "does not throw IndexOutOfBoundsException");
        fill();
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(-1), "Passing index < 0 does not "
                        + "throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(list.size()), "Passing index == size"
                        + " does not throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.removeAtIndex(list.size() + 1), "Passing index > "
                        + "size does not throw IndexOutOfBoundsException");
        assertDoesNotThrow(() -> list.removeAtIndex(0), "Passing index == 0 "
                + "throws");
        assertDoesNotThrow(() -> list.removeAtIndex(list.size() - 1),
                "Passing index < size throws");
    }

    /**
     * Test removeFromFront().
     */
    @Test
    public void removeFromFront() {
        fill();
        assertEquals(list.removeFromFront(), "0", "Removing does not return "
                + "item");
        assertDLLEquals(List.of("1", "2", "3", "4"));
    }

    /**
     * Test whether removeFromFront() throws NoSuchElementException only when
     * the list is empty.
     */
    @Test
    public void removeFromFrontThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> list.removeFromFront(), "Removing from an empty list "
                        + "does not throw NoSuchElementException");
        fill();
        assertDoesNotThrow(() -> list.removeFromFront(), "Removing from a "
                + "non-empty list throws");
    }

    /**
     * Test removeFromBack().
     */
    @Test
    public void removeFromBack() {
        fill();
        assertEquals(list.removeFromBack(), "4", "Removing does not return "
                + "item");
        assertDLLEquals(List.of("0", "1", "2", "3"));
    }

    /**
     * Test whether removeFromBack() throws NoSuchElementException only when
     * the list is empty.
     */
    @Test
    public void removeFromBackThrowsNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> list.removeFromBack(), "Removing from an empty list "
                        + "does not throw NoSuchElementException");
        fill();
        assertDoesNotThrow(() -> list.removeFromBack(), "Removing from a "
                + "non-empty list throws");
    }

    /**
     * Test get().
     */
    @Test
    public void get() {
        fill();
        for (int i = 0; i < list.size(); ++i) {
            assertEquals(list.get(i), String.valueOf(i));
        }
    }

    /**
     * Test whether get() throws IndexOutOfBoundsException only when index is
     * not in [0, size).
     */
    @Test
    public void getThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(0), "Getting from an empty list "
                        + "does not throw IndexOutOfBoundsException");
        fill();
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(-1), "Passing index < 0 does not "
                        + "throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(list.size()), "Passing index == size"
                        + " does not throw IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(list.size() + 1), "Passing index > "
                        + "size does not throw IndexOutOfBoundsException");
        assertDoesNotThrow(() -> list.get(0), "Passing index == 0 "
                + "throws");
        assertDoesNotThrow(() -> list.get(list.size() - 1),
                "Passing index < size throws");
    }

    /**
     * Test isEmpty().
     */
    @Test
    public void isEmpty() {
        assertTrue(list.isEmpty(), "Falsely reports empty list as non-empty");
        fill();
        assertFalse(list.isEmpty(), "Falsely reports non-empty list as empty");
        list.clear();
        assertTrue(list.isEmpty(), "Falsely reports empty list as non-empty");
    }

    /**
     * Test clear().
     */
    @Test
    public void clear() {
        fill();
        list.clear();
        assertDLLEquals(List.of());
    }

    /**
     * Test removeLastOccurrence().
     */
    @Test
    public void removeLastOccurrence() {
        for (String s : List.of("0", "1", "1", "2", "2", "1", "1", "0", "2")) {
            list.addToBack(s);
        }
        assertEquals(list.removeLastOccurrence("1"), "1", "Does not return "
                + "object equal to item to remove");
        assertDLLEquals(List.of("0", "1", "1", "2", "2", "1", "0", "2"));
    }

    /**
     * Test that removeLastOccurrence() returns the item that was actually in
     * the array.
     */
    @Test
    public void removeLastOccurrenceReturn() {
        String unique1 = new String("1");
        for (String s : List.of("0", "1", "1", "2", "2", "1", unique1, "0",
                "2")) {
            list.addToBack(s);
        }
        assertSame(list.removeLastOccurrence("1"), unique1, "Does not return "
                + "the same object as that was removed");
        assertDLLEquals(List.of("0", "1", "1", "2", "2", "1", "0", "2"));
    }

    /**
     * Test that removeLastOccurrence() throws IllegalArgumentException only
     * when data is null.
     */
    @Test
    public void removeLastOccurrenceThrowsIllegalArgumentException() {
        fill();
        assertThrows(IllegalArgumentException.class,
                () -> list.removeLastOccurrence(null), "Passing null data "
                        + "does not throw IllegalArgumentException");
        assertDoesNotThrow(() -> list.removeLastOccurrence("4"), "Passing "
                + "valid (non-null) data throws");
    }

    /**
     * Test that removeLastOccurrence() throws NoSuchElementException when
     * data isn't in the list.
     */
    @Test
    public void removeLastOccurrenceThrowsNoSuchElementException() {
        String unique1 = new String("1");
        for (String s : List.of("0", "2", "2", unique1, "0", "2")) {
            list.addToBack(s);
        }
        assertThrows(NoSuchElementException.class,
                () -> list.removeLastOccurrence(""), "Passing data not in the"
                        + " list does not throw NoSuchElementException");
        assertDoesNotThrow(() -> list.removeLastOccurrence("2"), "Passing "
                + "data found in the list throws");
        assertDoesNotThrow(() -> list.removeLastOccurrence("1"), "Passing "
                + "data which has an equivalent value in the list throws");
    }

    /**
     * Test toArray().
     */
    @Test
    public void toArray() {
        fill();
        assertArrayEquals(list.toArray(), new String[] {"0", "1", "2", "3",
                "4"});
    }

    /**
     * Test that toArray() returns an empty array for an empty list.
     */
    @Test
    public void toArrayEmpty() {
        assertArrayEquals(list.toArray(), new String[0]);
    }

    /**
     * Test whether the list still functions properly when adding and
     * removing lots of elements.
     */
    @Test
    public void addAndRemoveMany() {
        final int NUM_TO_ADD = 10000;
        List<String> equivList = new ArrayList<>(NUM_TO_ADD);
        for (int i = 0; i < NUM_TO_ADD; ++i) {
            String s = String.valueOf(i);
            list.addToBack(s);
            equivList.add(s);

            assertDLLEquals(equivList);
        }
        for (int i = NUM_TO_ADD - 1; i >= 0; --i) {
            assertEquals(list.removeFromBack(), String.valueOf(i));
            equivList.remove(i);

            assertDLLEquals(equivList);
        }
        assertTrue(list.isEmpty());
    }

    // Wrappers for JUnit 4 methods (I originally wrote this in JUnit 5 and
    // didn't feel like swapping the arguments around)
    private void assertEquals(Object expected, Object actual, String message) {
        org.junit.Assert.assertEquals(message, expected, actual);
    }
    private void assertEquals(int expected, int actual, String message) {
        org.junit.Assert.assertEquals(message, expected, actual);
    }
    private void assertEquals(Object expected, Object actual) {
        org.junit.Assert.assertEquals(expected, actual);
    }
    private void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        org.junit.Assert.assertArrayEquals(expecteds, actuals);
    }
    private void assertSame(Object expected, Object actual, String message) {
        org.junit.Assert.assertSame(message, expected, actual);
    }
    private void assertNull(Object object, String message) {
        org.junit.Assert.assertNull(message, object);
    }
    private void assertNotNull(Object object, String message) {
        org.junit.Assert.assertNotNull(message, object);
    }
    interface Function {
        void call();
    }
    private void assertThrows(Class<? extends Throwable> exceptionType,
                              Function function, String message) {
        try {
            function.call();
        } catch (Exception e) {
            org.junit.Assert.assertTrue(message, exceptionType.isInstance(e));
            return;
        }
        org.junit.Assert.assertTrue(message, false);
    }
    private void assertDoesNotThrow(Function function, String message) {
        try {
            function.call();
        } catch (Exception e) {
            org.junit.Assert.assertTrue(message, false);
        }
    }
    private void assertTrue(boolean condition, String message) {
        org.junit.Assert.assertTrue(message, condition);
    }
    private void assertTrue(boolean condition) {
        org.junit.Assert.assertTrue(condition);
    }
    private void assertFalse(boolean condition, String message) {
        org.junit.Assert.assertFalse(message, condition);
    }
}
