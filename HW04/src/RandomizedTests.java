import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains one simple remove test and two more rigorous, procedural tests
 * that test the BSTs add/constructor and remove methods. The tests also
 * depend on correct inorder traversal implementation.
 * 
 * *****************************************************************************
 * IMPORTANT: A TON OF INFORMATION IS PRINTED TO THE DEBUG CONSOLE!
 * CHANGE THE DEBUG BOOLEAN BELOW FOR EASIER TESTING BUT LESS DEBUG INFORMATION!
 * *****************************************************************************
 * 
 * @author Nathan Duggal & Pranav Tadepalli
 * @version 1.0
 */
public class RandomizedTests {

    // **************************************
    // CHANGE TO FAlSE FOR BETTER PERFORMANCE
    // **************************************
    private boolean debug = true;

    private BST<Integer> tree;

    @Before
    public void setup() {
        tree = new BST<>();
    }

    @Test
    public void testSmallTree() {
        Integer temp = 10;
        tree.add(temp);
        tree.add(5);

        //     10
        //     /
        //    5

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, tree.getRoot().getData());
        assertEquals((Integer) 5, tree.getRoot().getLeft().getData());

        assertSame(temp, tree.remove(10));

        assertEquals(1, tree.size());
        assertEquals((Integer) 5, tree.getRoot().getData());
    }

    /**
     * Produces random set of elements contained in an arraylist
     * 
     * @param size the number of elements in the set
     * @return a randomly-generated ordered set of data
     */
    public ArrayList<Integer> getRandomCollection(int size) { 
        ArrayList<Integer> set = new ArrayList<>();
        while (set.size() < size) {
            int addInt = (int) (Math.random() * size * 1.5);
            if (!set.contains(addInt)) {
                set.add(addInt);
            }
        }
        return set;
    }

    /**
     * Prints a set in the order Iterator iterates through it
     * 
     * @param set the set to be printed
     */
    public void printSet(Iterable<Integer> set) {
        String printString  = "[ ";
        for (Integer i : set) {
            printString += i + ", ";
        }
        if (set.iterator().hasNext()) {
            printString = printString.substring(0, printString.length() - 2);
        }
        printString += " ]";
        System.out.println(printString);
    }

    @Test
    public void durabilityTest() { 
        for (int test = 1; test <= 300; test++) {

            if (debug) {
                System.out.println(
                    "----------------------\n"
                    + " Test #" + test + " start\n"
                    + "----------------------\n"
                );
            }

            ArrayList<Integer> testSet = getRandomCollection((int) (Math.random() * 20));

            if (debug) {
                printSet(testSet);
                System.out.println();
            }

            tree = new BST<>(testSet);
            
            if (debug) {
                BTreePrinter.printNode(tree.getRoot());
            }

            Collections.sort(testSet);

            List<Integer> compareList = tree.inorder();

            int listIndex = 0;
            for (Integer i : testSet) {
                assertEquals(
                    "Failed test " + test,
                    i, 
                    compareList.get(listIndex)
                );
                listIndex++;
            }   
        }

        System.out.println("\nAll build tests passed! :D");
    }

    @Test
    public void removeTest() { 

        int multiplier = debug ? 1 : 10;

        for (int test = 1; test <= 200 * multiplier; test++) {

            if (debug) {
                System.out.println(
                    "----------------------\n"
                    + " Test #" + test + " start\n"
                    + "----------------------\n"
                );
            }

            ArrayList<Integer> testSet = getRandomCollection((int) (Math.random() * 20 * multiplier));
            tree = new BST<>(testSet);

            Collections.sort(testSet);

            int removalCounter = 1;
            while (testSet.size() > 0) {

                if (debug) {
                    System.out.println("***Removal " + removalCounter + ": ***\n");
                }

                int removeIndex = (int) (Math.random() * testSet.size());
                Integer toRemove = testSet.get(removeIndex);
                Integer removed = testSet.remove(removeIndex);

                if (debug) {
                    System.out.println("Removing element \"" + removed + "\"...\n");

                    System.out.println("Set:");
                    printSet(testSet);

                    System.out.println();
                }

                assertSame(tree.remove(toRemove), removed);

                if (debug) {
                    System.out.println("Tree:");
                    BTreePrinter.printNode(tree.getRoot());

                    System.out.println(); 
                }  

                List<Integer> compareList = tree.inorder();

                int listIndex = 0;
                for (Integer i : testSet) {
                    assertEquals(
                        "Failed test " + test 
                        + " removal " + removalCounter
                        + " attemping to remove item \"" + removed + "\"."
                        + " Check debug console for more details.", 
                        i, 
                        compareList.get(listIndex)
                    );
                    listIndex++;
                }

                removalCounter++;
            }

            if (debug) {
                System.out.println("Checking empty...\n");
            }
            assertNull(tree.getRoot());

            System.out.println("Test #" + test + " end\n");
        }

        System.out.println("All removal tests passed! :D");
    }

    /*
     * 
     * BIG HELPER CLASS I GOT FROM STACKOVERFLOW
     * 
     */
    static class BTreePrinter {

        /**
         * Initiates the tree printing
         * @param <T> comparable generic
         * @param root the root of the tree to be printed
         */
        public static <T extends Comparable<? super T>> void printNode(BSTNode<T> root) {
            int maxLevel = BTreePrinter.maxLevel(root);
    
            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }
    
        /**
         * 
         * @param <T> comparable generic
         * @param nodes list containing the nodes to traverse
         * @param level the current level of the tree
         * @param maxLevel the last level of the tree
         */
        private static <T extends Comparable<? super T>> void printNodeInternal(List<BSTNode<T>> nodes, 
            int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes)) {
                return;
            }
    
            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
    
            BTreePrinter.printWhitespaces(firstSpaces);
    
            List<BSTNode<T>> newNodes = new ArrayList<BSTNode<T>>();
            for (BSTNode<T> node : nodes) {
                if (node != null) {
                    System.out.print(node.getData());
                    newNodes.add(node.getLeft());
                    newNodes.add(node.getRight());
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }
    
                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println("");
    
            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }
    
                    if (nodes.get(j).getLeft() != null) {
                        System.out.print("/");
                    } else {
                        BTreePrinter.printWhitespaces(1);
                    }
    
                    BTreePrinter.printWhitespaces(i + i - 1);
    
                    if (nodes.get(j).getRight() != null) {
                        System.out.print("\\");
                    } else {
                        BTreePrinter.printWhitespaces(1);
                    }
    
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }
    
                System.out.println("");
            }
    
            printNodeInternal(newNodes, level + 1, maxLevel);
        }
    
        /**
         * Prints the specified number of spaces to the terminal
         * @param count the number of spaces to print
         */
        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++) {
                System.out.print(" ");
            }
        }
    
        /**
         * 
         * @param <T> comparable generic
         * @param node the node currently being assessed
         * @return the current level
         */
        private static <T extends Comparable<? super T>> int maxLevel(BSTNode<T> node) {
            if (node == null) {
                return 0;
            }
    
            return Math.max(BTreePrinter.maxLevel(node.getLeft()), BTreePrinter.maxLevel(node.getRight())) + 1;
        }
    
        /**
         * 
         * @param <T> comparable generic
         * @param list the list to search for non-null elements
         * @return whether or not the list is all null
         */
        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null) {
                    return false;
                }
            }
    
            return true;
        }
    
    }
}
