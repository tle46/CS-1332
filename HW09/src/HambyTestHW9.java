import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class HambyTestHW9 {

    public static final int TIMEOUT = 200;

    public ComparatorPlus comparator;

    @Before
    public void setUp() {
        comparator = new ComparatorPlus();
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable() {
        Map<Character, Integer> lastTable =
                PatternMatching.buildLastTable("abbbbcdefca");
        Map<Character, Integer> desired = new HashMap<>();
        desired.put('a', 10);
        desired.put('b', 4);
        desired.put('c', 9);
        desired.put('d', 6);
        desired.put('e', 7);
        desired.put('f', 8);

        assertEquals(desired, lastTable);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildLastTableNull() {
        PatternMatching.buildLastTable(null);
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore() {
        String pattern = "ababca";
        String text = "ababcdababcababcacabgababca";

        Integer[] desired = {6, 11, 21};
        assertEquals(Arrays.asList(desired),
                PatternMatching.boyerMoore(pattern, text, comparator));

        Character[] comparisons = {'a', 'd', 'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a',
            'b', 'b', 'a', 'a', 'a', 'b', 'a', 'b', 'a', 'a', 'c', 'c', 'b', 'b',
            'a', 'a', 'b', 'b', 'a', 'a', 'a', 'c', 'a', 'a', 'c', 'c', 'a', 'b',
            'a', 'b', 'a', 'a', 'c', 'g', 'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a',
            'b', 'b', 'a', 'a'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreNoMatches() {
        String pattern = "ababca";
        String text = "dbabcabcacbga";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.boyerMoore(pattern, text, comparator));

        Character[] comparisons = {'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'b',
            'a', 'd', 'a', 'b', 'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'c',
            'a', 'c', 'a', 'b', 'a', 'a', 'c', 'g'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreLongerPattern() {
        String pattern = "ababcaasdjf;lkajf";
        String text = "dbabcabcacbga";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.boyerMoore(pattern, text, comparator));

        Character[] comparisons = {};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreNullPattern() {
        PatternMatching.boyerMoore(null, "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreEmptyPattern() {
        PatternMatching.boyerMoore("", "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreNullText() {
        PatternMatching.boyerMoore("a", null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreNullComparator() {
        PatternMatching.boyerMoore("a", "a", null);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable() {
        String pattern = "ababcabababagab";
        int[] failureTable =
                PatternMatching.buildFailureTable(pattern, comparator);

        int[] desired = {0, 0, 1, 2, 0, 1, 2, 3, 4, 3, 4, 3, 0, 1, 2};

        assertArrayEquals(desired, failureTable);

        Character[] comparisons = {'a', 'b', 'a', 'a', 'b', 'b', 'a', 'c', 'a', 'c',
            'a', 'a', 'b', 'b', 'a', 'a', 'b', 'b', 'a', 'c', 'a', 'a', 'b', 'b',
            'a', 'c', 'a', 'a', 'b', 'g', 'b', 'g', 'a', 'g', 'a', 'a', 'b', 'b'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTableEmpty() {
        int[] failureTable =
                PatternMatching.buildFailureTable("", comparator);
        assertArrayEquals(new int[] {}, failureTable);
        assertEquals(new ArrayList<>(), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildFailureTableNullPattern() {
        PatternMatching.buildFailureTable(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildFailureTableNullComparator() {
        PatternMatching.buildFailureTable("a", null);
    }

    @Test(timeout = TIMEOUT)
    public void testKMP() {
        String pattern = "ababca";
        String text = "ababcdababcababcacabgabababca";

        Integer[] desired = {6, 11, 23};

        assertEquals(Arrays.asList(desired),
                PatternMatching.kmp(pattern, text, comparator));

        Character[] comparisons = {'a', 'b', 'a', 'a', 'b', 'b', 'a', 'c', 'a', 'c',
            'a', 'a',
            'a', 'a', 'b', 'b', 'a', 'a', 'b', 'b', 'c', 'c', 'a', 'd', 'a', 'd',
            'a', 'a', 'b', 'b', 'a', 'a', 'b', 'b', 'c', 'c', 'a', 'a',
            'b', 'b', 'a', 'a', 'b', 'b', 'c', 'c', 'a', 'a', 'b', 'c', 'a', 'c',
            'a', 'a', 'b', 'b', 'a', 'g', 'a', 'g', 'a', 'a', 'b', 'b', 'a', 'a',
            'b', 'b', 'a', 'c', 'a', 'a', 'b', 'b', 'c', 'c', 'a', 'a'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPNoMatches() {
        String pattern = "ababca";
        String text = "ababcdabacghsl";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.kmp(pattern, text, comparator));

        Character[] comparisons = {'a', 'b', 'a', 'a', 'b', 'b', 'a', 'c', 'a', 'c',
            'a', 'a',
            'a', 'a', 'b', 'b', 'a', 'a', 'b', 'b', 'c', 'c', 'a', 'd', 'a', 'd',
            'a', 'a', 'b', 'b', 'a', 'a', 'b', 'c', 'b', 'c'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPLongerPattern() {
        String pattern = "ababcaasdjf;lkajf";
        String text = "dbabcabcacbga";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.kmp(pattern, text, comparator));

        Character[] comparisons = {};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKMPNullPattern() {
        PatternMatching.kmp(null, "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKMPEmptyPattern() {
        PatternMatching.kmp("", "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKMPNullText() {
        PatternMatching.kmp("a", null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKMPNullComparator() {
        PatternMatching.kmp("a", "a", null);
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarp() {
        String pattern = "abaa";
        String text = "abadabaabaacabgababaa";

        Integer[] desired = {4, 7, 17};

        assertEquals(Arrays.asList(desired),
                PatternMatching.rabinKarp(pattern, text, comparator));

        Character[] comparisons = {'a', 'a', 'b', 'b', 'a', 'a',
            'a', 'a', 'a', 'a', 'b', 'b', 'a', 'a', 'a', 'a',
            'a', 'a', 'b', 'b', 'a', 'a', 'a', 'a'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpNoMatches() {
        String pattern = "abaa";
        String text = "ababcdabacghsl";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.rabinKarp(pattern, text, comparator));

        Character[] comparisons = {};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpSameHash() {
        char[] patternArray = {1, 2, 3};
        String pattern = new String(patternArray);
        char[] textArray = {0, 113, 229, 0, 1, 2, 3, 67};
        String text = new String(textArray);

        System.out.println();

        Integer[] desired = {4};

        assertEquals(Arrays.asList(desired),
                PatternMatching.rabinKarp(pattern, text, comparator));

        Character[] comparisons = {0, 1, 1, 1, 2, 2, 3, 3};
        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRabinKarpNullPattern() {
        PatternMatching.rabinKarp(null, "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRabinKarpEmptyPattern() {
        PatternMatching.rabinKarp("", "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRabinKarpNullText() {
        PatternMatching.rabinKarp("a", null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRabinKarpNullComparator() {
        PatternMatching.rabinKarp("a", "a", null);
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRule() {
        String pattern = "ababca";
        String text = "ababcdababcababcacabgababca";

        Integer[] desired = {6, 11, 21};
        assertEquals(Arrays.asList(desired),
                PatternMatching.boyerMooreGalilRule(pattern, text, comparator));

        Character[] comparisons = {'a', 'b', 'a', 'a', 'b', 'b', 'a', 'c', 'a', 'c',
            'a', 'a',
            'a', 'd', 'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'b', 'a', 'a',
            'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'b', 'a', 'a', 'c', 'g',
            'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'b', 'a', 'a'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuleNoMatches() {
        String pattern = "ababca";
        String text = "dbabcabcacbga";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.boyerMooreGalilRule(pattern, text, comparator));

        Character[] comparisons = {'a', 'b', 'a', 'a', 'b', 'b', 'a', 'c', 'a', 'c',
            'a', 'a',
            'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'b',
            'a', 'd', 'a', 'b', 'a', 'a', 'c', 'c', 'b', 'b', 'a', 'a', 'b', 'c',
            'a', 'c', 'a', 'b', 'a', 'a', 'c', 'g'};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreGalilRuleLongerPattern() {
        String pattern = "ababcaasdjf;lkajf";
        String text = "dbabcabcacbga";

        Integer[] desired = {};

        assertEquals(Arrays.asList(desired),
                PatternMatching.boyerMooreGalilRule(pattern, text, comparator));

        Character[] comparisons = {};

        assertEquals(Arrays.asList(comparisons), comparator.getCompared());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreGalilRuleNullPattern() {
        PatternMatching.boyerMooreGalilRule(null, "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreGalilRuleEmptyPattern() {
        PatternMatching.boyerMooreGalilRule("", "a", comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreGalilRuleNullText() {
        PatternMatching.boyerMooreGalilRule("a", null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBoyerMooreGalilRuleNullComparator() {
        PatternMatching.boyerMooreGalilRule("a", "a", null);
    }

    private static class ComparatorPlus extends CharacterComparator {
        private ArrayList<Character> compared = new ArrayList<>();
        private boolean inner = false;

        public ArrayList<Character> getCompared() {
            return compared;
        }

        public void addCompared(Character a, Character b) {
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

        public int compare(Character a, Character b) {
            addCompared(a, b);
            return a - b;
        }
    }
}