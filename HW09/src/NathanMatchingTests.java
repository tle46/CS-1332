import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class NathanMatchingTests {
    
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final String CHAR_BANK = "abc";


    // ---- CHANGE THIS FOR A SPECIFIC SEED ----
    public static int seed = -1;

    @Test
    public void testMatches() {

        double bmgtToBmComparisionsSum = 0;
        seed = seed == -1 ? (int) (Math.random() * 100000) : seed;
        Random rand = new Random(seed);

        for (int i = 0; i < 1000; i++) {
            String charBank  = getCharBank(rand, 1, 10);
            String text = getRandomString(rand, 100, 501, CHAR_BANK, true);
            String pattern = getRandomString(rand, 2, 5, CHAR_BANK, false);

            List<Integer> matches = new LinkedList<>();

            // Brute force approach
            int index = 0;
            String bfTest = text;
            while (bfTest.length() >= pattern.length()) {
                if (bfTest.substring(0, pattern.length()).equals(pattern)) {
                    matches.add(index);
                }
                bfTest = bfTest.substring(1);
                index++;
            }
            System.out.println();

            System.out.println("Text: \"" + text + "\"");
            System.out.println("Pattern: \"" + pattern + "\"");
            System.out.println("Expected Matches:");
            printSet(matches);

            List<Integer> kmpMatches = PatternMatching.kmp(pattern, text, new CharacterComparator());

            System.out.println("Actual KMP Matches:");
            printSet(kmpMatches);
            for (int ii = 0; ii < matches.size(); ii++) {
                assertEquals(matches.get(ii), kmpMatches.get(ii));
            }

            TrackingComparator bmComparator = new TrackingComparator(text, pattern);

            List<Integer> bmMatches = PatternMatching.boyerMoore(pattern, text, bmComparator);

            System.out.println("Actual Boyer-Moore Matches:");
            printSet(bmMatches);
            for (int ii = 0; ii < matches.size(); ii++) {
                assertEquals(matches.get(ii), bmMatches.get(ii));
            }

            List<Integer> rkMatches = PatternMatching.rabinKarp(pattern, text, new CharacterComparator());

            System.out.println("Actual Rabin-Karp Matches:");
            printSet(rkMatches);
            for (int ii = 0; ii < matches.size(); ii++) {
                assertEquals(matches.get(ii), rkMatches.get(ii));
            }

            TrackingComparator bmgrComparator = new TrackingComparator(text, pattern);
            List<Integer> bmgrMatches = PatternMatching.boyerMooreGalilRule(pattern, text, bmgrComparator);

            if (bmgrMatches != null) {
                System.out.println("Actual Boyer-Moore-Galil-Rule Matches:");
                printSet(bmgrMatches);
                for (int ii = 0; ii < matches.size(); ii++) {
                    assertEquals(matches.get(ii), bmgrMatches.get(ii));
                }

                System.out.println();

                System.out.println("BM vs BMGR comparisons: ");
                System.out.println("BM did " + bmComparator.getComparisonCount() + " comparisons");
                printSet(bmComparator.getComparisons());
                System.out.println();
                System.out.println("BMGR did " + bmgrComparator.getComparisonCount() + " comparisons");
                printSet(bmgrComparator.getComparisons());
            }

            bmgtToBmComparisionsSum += (double) bmgrComparator.getComparisonCount() / bmComparator.getComparisonCount();
                
            System.out.println();
        }

        System.out.println("Should be <1: " + bmgtToBmComparisionsSum / 1000);
        System.out.println("---------------------------SEED: " + seed);
        System.out.println();
    }

    /**
     * Produces random set of elements contained in an arraylist
     *
     * @param rand seeded random
     * @param ub upper bound for size
     * @param lb lower bound for size
     * @return a randomly-generated ordered set of data
     */
    public String getCharBank(Random rand, int lb, int ub) {

        int size = rand.nextInt(ub - lb) + lb;

        String str = "";
        while (str.length() < size) {
            int addInt = (int) (rand.nextDouble() * ALPHABET.length());
            if (str.indexOf(ALPHABET.charAt(addInt)) == -1) {
                str += ALPHABET.charAt(addInt);
            }
        }
        return str;
    }

    /**
     * Produces random set of elements contained in an arraylist
     *
     * @param rand seeded random
     * @param lb lower bound for size
     * @param ub upper bound for size
     * @param charBank the characters to choose from
     * @param extraChars whether there should be chars from outside the charbank
     * @return a randomly-generated ordered set of data
     */
    public String getRandomString(Random rand, int lb, int ub, String charBank, boolean extraChars) {
        String str = "";
        while (str.length() < rand.nextInt(ub - lb) + lb) {
            if (extraChars && rand.nextDouble() < 0.05) {
                int addInt = (int) (rand.nextDouble() * ALPHABET.length());
                while (charBank.indexOf(ALPHABET.charAt(addInt)) != -1) {
                    addInt = (int) (rand.nextDouble() * ALPHABET.length());
                }
                str += ALPHABET.charAt(addInt);
            } else {
                int addInt = (int) (rand.nextDouble() * charBank.length());
                str += charBank.charAt(addInt);
            }
        }
        return str;
    }

    /**
     * Prints a set in the order Iterator iterates through it
     *
     * @param set the set to be printed
     * @param <T> generic
     */
    public <T> void printSet(List<T> set) {
        String printString  = "[ ";
        for (int i = 0; i < set.size(); i++) {
            printString += set.get(i) + ", ";
        }
        if (set.iterator().hasNext()) {
            printString = printString.substring(0, printString.length() - 2);
        }
        printString += " ]";
        System.out.println(printString);
    }


    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }

    private class TrackingComparator extends CharacterComparator {

        private List<Comparison> comparisons;
        private CharSequence text;
        private CharSequence pattern;

        /**
         * @param t text 
         * @param p pattern
         */
        public TrackingComparator(CharSequence t, CharSequence p) {
            comparisons = new LinkedList<>();
            text = t;
            pattern = p;
        }

        @Override
        public int compare(Character a, Character b) {
            comparisons.add(comparisons.size(), new Comparison(0, 0, a, b));
            return super.compare(a, b);
        }

        /**
         * @return list of comparisons
         */
        public List<Comparison> getComparisons() {
            return comparisons;
        }
        
        private class Comparison {
            public final int patternIndex;
            public final int textIndex;
            public final char patternChar;
            public final char textChar;
            public Comparison(int pi, int ti, char pc, char tc) {
                patternIndex = pi;
                textIndex = ti;
                patternChar = pc;
                textChar = tc;
            }

            @Override
            public String toString() {
                return "<" + textChar + ", " + patternChar + ">";
            }
        }
    }
}
