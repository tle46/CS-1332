import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class HambyTestHW8 {

    public static final int TIMEOUT = 200;

    public Data[] start;
    public Data[] sorted;
    public ComparatorPlus<Data> comparator;

    @SuppressWarnings("unchecked")
    private void assertCompareOrder(int[] order) {
        Data[] compared = comparator.getCompared().toArray(new Data[0]);
        int[] comparedInts = new int[compared.length];
        for (int i = 0; i < compared.length; i++) {
            comparedInts[i] = compared[i].getData();
        }
        assertArrayEquals(order, comparedInts);
    }

    private void assertPairsMatch(int[] pairs) {
        ArrayList<Pair> present = new ArrayList<>();
        ArrayList<Pair> desired = new ArrayList<>();
        for (int i = 0; i < comparator.getCompared().size(); i += 2) {
            present.add(new Pair(comparator.getCompared().get(i).getData(),
                    comparator.getCompared().get(i + 1).getData()));
        }
        for (int i = 0; i < pairs.length; i += 2) {
            desired.add(new Pair(pairs[i], pairs[i + 1]));
        }

        desired.sort(Comparator.comparing(Pair::getA).thenComparing(Pair::getB));
        present.sort(Comparator.comparing(Pair::getA).thenComparing(Pair::getB));

        assertEquals(desired, present);
    }

    @Before
    public void setUp() {
        comparator = Data.getComparator();
        start = new Data[11];
        start[0] = new Data(68, 0);
        start[1] = new Data(93, 0);
        start[2] = new Data(18, 0);
        start[3] = new Data(11, 0);
        start[4] = new Data(-67, 0);
        start[5] = new Data(72, 0);
        start[6] = new Data(-14, 0);
        start[7] = new Data(18, 1);
        start[8] = new Data(-68, 0);
        start[9] = new Data(64, 0);
        start[10] = new Data(-58, 0);

        sorted = new Data[11];
        sorted[0] = start[8];
        sorted[1] = start[4];
        sorted[2] = start[10];
        sorted[3] = start[6];
        sorted[4] = start[3];
        sorted[5] = start[2];
        sorted[6] = start[7];
        sorted[7] = start[9];
        sorted[8] = start[0];
        sorted[9] = start[5];
        sorted[10] = start[1];
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(start, comparator);
        assertArrayEquals(sorted, start);
        assertEquals(0, start[5].getOrder());
        assertEquals(1, start[6].getOrder());


        // This array stores comparison made during the sort
        // For example, the first comparison made is between 68 and 93,
        // The second is between 18 and 93
        // And the last comparison made is between -67 and -58
        int[] compareOrder = {68, 93, 18, 93, 18, 68, 11, 93, 11, 68, 11, 18,
            -67, 93, -67, 68, -67, 18, -67, 11, 72, 93, 68, 72, -14, 93, -14, 72,
            -14, 68, -14, 18, -14, 11, -67, -14, 18, 93, 18, 72, 18, 68, 18, 18,
            -68, 93, -68, 72, -68, 68, -68, 18, -68, 18, -68, 11, -68, -14,
            -68, -67, 64, 93, 64, 72, 64, 68, 18, 64, -58, 93, -58, 72, -58, 68,
            -58, 64, -58, 18, -58, 18, -58, 11, -58, -14, -67, -58};
        assertCompareOrder(compareOrder);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertionSortNullArray() {
        Sorting.insertionSort(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertionSortNullComparator() {
        Sorting.insertionSort(start, null);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(start, comparator);
        assertArrayEquals(sorted, start);
        assertEquals(0, start[5].getOrder());
        assertEquals(1, start[6].getOrder());


        // This array stores comparison made during the sort
        // For example, the first comparison made is between 68 and 93,
        // The second is between 18 and 93
        // And the last comparison made is between 18 and 64
        int[] compareOrder = {68, 93, 18, 93, 11, 93, -67, 93, 72, 93, -14, 93,
            18, 93, -68, 93, 64, 93, -58, 93, -58, 64, -68, -58, -68, 18,
            -68, -14, -68, 72, -68, -67, -68, 11, -68, 18, -68, 68, 18, 68,
            11, 68, -67, 68, 68, 72, -14, 72, 18, 72, -58, 72, 64, 72, -58, 64,
            -58, 18, -58, -14, -58, 68, -67, -58, -67, 11, -67, 18, 11, 18,
            -58, 18, 18, 68, -14, 68, 18, 68, 64, 68, 18, 64, -14, 18, -14, 18,
            -58, -14, -58, 11, -14, 11, 11, 18, 18, 18, 18, 64};
        assertCompareOrder(compareOrder);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortFrontOptimization() {
        start = new Data[5];
        start[0] = new Data(93, 0);
        start[1] = new Data(1, 0);
        start[2] = new Data(2, 0);
        start[3] = new Data(4, 0);
        start[4] = new Data(3, 0);

        sorted = new Data[5];
        sorted[0] = start[1];
        sorted[1] = start[2];
        sorted[2] = start[4];
        sorted[3] = start[3];
        sorted[4] = start[0];

        Sorting.cocktailSort(start, comparator);
        assertArrayEquals(sorted, start);

        int[] compareOrder = {1, 93, 2, 93, 4, 93, 3, 93, 3, 4, 2, 3, 1, 2};
        assertCompareOrder(compareOrder);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailSortNullArray() {
        Sorting.cocktailSort(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailSortNullComparator() {
        Sorting.cocktailSort(start, null);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(start, comparator);
        assertArrayEquals(sorted, start);
        assertEquals(0, start[5].getOrder());
        assertEquals(1, start[6].getOrder());

        int[] pairs = {-67, 11, -14, 18, -58, 64, 68, 93, -67, 18, 11, 18,
            -14, 72, 18, 72, -68, -58, -67, 68, 11, 68, 18, 68, -68, -14,
            -58, -14, -14, 64, 18, 64, 64, 72, -68, -67, -67, -58, -58, 11,
            -14, 11, 11, 18, 18, 18, 18, 68, 64, 68, 68, 72, 72, 93};
        assertPairsMatch(pairs);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeSortNullArray() {
        Sorting.mergeSort(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeSortNullComparator() {
        Sorting.mergeSort(start, null);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Random r = new Random(182);
        Sorting.quickSort(start, comparator, r);
        assertArrayEquals(sorted, start);

        int[] pairs = {72, 93, -58, 72, 18, 72, 11, 72, -67, 72, 68, 72,
            -14, 72, 18, 72, -68, 72, 64, 72, -58, 11, 11, 18, -68, 11, 11, 64,
            11, 18, -14, 11, -67, 11, 11, 68, 11, 68, -68, -58, -68, -14,
            -68, -67, -68, -58, -58, -14, -58, -14, -67, -58, 18, 64, 18, 68,
            18, 18, 18, 64, 18, 64, 64, 68, 64, 68};
        assertPairsMatch(pairs);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortNullArray() {
        Sorting.quickSort(null, comparator, new Random(0));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortNullComparator() {
        Sorting.quickSort(start, null, new Random(0));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickSortNullRandom() {
        Sorting.quickSort(start, comparator, null);
    }

    @Test(timeout = TIMEOUT)
    public void testRadixSort() {
        int[] start = {68, 93, 18, 11, -67, 72, -14, 18, -68, 64, Integer.MIN_VALUE,
            -58};

        int[] sorted = {Integer.MIN_VALUE, -68, -67, -58, -14, 11, 18, 18, 64,
            68, 72, 93};

        Sorting.lsdRadixSort(start);
        assertArrayEquals(sorted, start);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRadixSortNull() {
        Sorting.lsdRadixSort(null);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        Integer[] start = {68, 93, 18, 11, -67, 72, -14, 18, -68, 64,
            Integer.MIN_VALUE, -58};

        int[] sorted = {Integer.MIN_VALUE, -68, -67, -58, -14, 11, 18, 18, 64,
            68, 72, 93};

        int[] out = Sorting.heapSort(Arrays.asList(start));
        assertArrayEquals(sorted, out);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testHeapSortNull() {
        Sorting.heapSort(null);
    }


    private static class Data {
        private int data;
        private int order;

        public Data(int data, int order) {
            this.data = data;
            this.order = order;
        }

        public int getData() {
            return data;
        }

        public int getOrder() {
            return order;
        }

        public boolean equals(Object other) {
            if (other == null) return false;
            if (other == this) return true;
            return (other instanceof Data && ((Data) other).getData() == data);
        }

        public String toString() {
            return "(" + data + "," + order + ")";
        }

        public static ComparatorPlus<Data> getComparator() {
            return new ComparatorPlus<Data>() {
                @Override
                public int compare(Data a, Data b) {
                    addCompared(a, b);
                    return a.getData() - b.getData();
                }
            };
        }
    }

    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private ArrayList<T> compared = new ArrayList<>();
        private boolean inner = false;

        public ArrayList<T> getCompared() {
            return compared;
        }

        public void addCompared(T a, T b) {
            if (inner) return;
            inner = true;
            if (compare(a, b) <= 0) {
                compared.add(a);
                compared.add(b);
            } else {
                compared.add(b);
                compared.add(a);
            }
            inner = false;
        }
    }

    private static class Pair {
        private int a;
        private int b;
        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        public boolean equals(Object other) {
            if (other == null) return false;
            if (other == this) return true;
            return other instanceof Object && ((Pair) other).a == a
                    && ((Pair) other).b == b;
        }

        public String toString() {
            return "(" + a + "," + b + ")";
        }
    }
}