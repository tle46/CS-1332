import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class HambyDuggalTestHW7 {

    /* Set debug to true to enable debug print statements, which will provide
     * more information but cause the tests to take longer.
     */
    private static boolean debug = false;

    public static final int TIMEOUT = 400;

    private AVL<Integer> avl;

    /**
     * Gets the preorder traversal of an AVL
     * @param <T> Comparable type
     * @param root Root of the subtree
     * @return Preorder traversal in LinkedList
     */
    private <T extends Comparable<? super T>> List<T> preorder(AVLNode<T> root) {
        List<T> preorderList = new LinkedList<>();
        preorderHelper(root, preorderList);
        return preorderList;
    }

    /**
     * Recursive helper for {@link #preorder() preorder} method two child case
     * @param <T> Comparable type
     * @param node Current node
     * @param list Preorder traversal list
     */
    private <T extends Comparable<? super T>> void preorderHelper(
            AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        preorderHelper(node.getLeft(), list);
        preorderHelper(node.getRight(), list);
    }

    /**
     * Gets the inorder traversal of an AVL
     * @param <T> Comparable generic
     * @param root Root of the subtree
     * @return Preorder traversal in LinkedList
     */
    private <T extends Comparable<? super T>> List<T> inorder(AVLNode<T> root) {
        List<T> inorderList = new LinkedList<>();
        inorderHelper(root, inorderList);
        return inorderList;
    }

    /**
     * Recursive helper for {@link #inorder() inorder} method two child case
     * @param <T> Comparable type
     * @param node Current node
     * @param list Preorder traversal list
     */
    private <T extends Comparable<? super T>> void inorderHelper(
            AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        inorderHelper(node.getLeft(), list);
        list.add(node.getData());
        inorderHelper(node.getRight(), list);
    }

    /**
     * Asserts the validity of a given avl
     * @param <T> Comparable type
     * @param avl Tree to validate
     */
    private <T extends Comparable<? super T>> void assertInOrder(AVL<T> avl) {
        Object[] inorder = inorder(avl.getRoot()).toArray();
        Object[] actuallyInorder = Arrays.copyOf(inorder, inorder.length);
        Arrays.sort(actuallyInorder);
        assertArrayEquals(actuallyInorder, inorder);
    }

    /**
     * Creates a tree from a preorder list
     * @param preorderList - a preorder list representing a tree
     * @return the root of the tree represented by the preorder list
     * @param <T> the type of elements in the tree
     */
    private <T extends Comparable<? super T>> AVLNode<T> buildTree(List<T> preorderList) {
        Stack<AVLNode<T>> parents = new Stack<>();
        AVLNode<T> root = null;
        for (T i : preorderList) {
            AVLNode<T> node = new AVLNode<>(i);
            if (root == null) {
                root = node;
            } else if (i.compareTo(parents.peek().getData()) < 0) {
                parents.peek().setLeft(node);
            } else {
                AVLNode<T> current = parents.pop();
                while (!parents.isEmpty()
                        && i.compareTo(parents.peek().getData()) > 0) {
                    current = parents.pop();
                }
                current.setRight(node);
            }
            parents.push(node);
        }
        return root;
    }

    /**
     * Prints debug information
     * @param testName - the name of the test where this method is called
     * @param desired - the preorder of the desired state of the tree
     * @param <T> - the type of elements in the tree
     */
    private <T extends Comparable<? super T>> void debugPrint(String testName, T[] desired) {
        if (debug) {
            System.out.println("\n" + testName + ": \n");
            System.out.println("Desired: ");
            AVLPrinter.printNode(buildTree(Arrays.asList(desired)));
            System.out.println("\nYour Tree: ");
            AVLPrinter.printNode(avl.getRoot());
        }
    }

    /**
     * Prints debug information
     * @param testName - the name of the test where this method is called
     * @param <T> - the type of elements in the tree
     */
    private <T extends Comparable<? super T>> void debugPrint(String testName) {
        if (debug) {
            System.out.println("\n" + testName + ": \n");
            System.out.println("Your Tree: ");
            AVLPrinter.printNode(avl.getRoot());
        }
    }

    @Before
    public void setUp() {
        avl = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, avl.size());
        assertNull(avl.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAlternateInitialization() {


        Integer[] tree = {50, 25, 75, 60, 100, 80};

        avl = new AVL<>(Arrays.asList(tree));

        Integer[] desired = new Integer[]{75, 50, 25, 60, 100, 80};

        debugPrint("testAlternateInitialization", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullConstruct() {
        AVL<Integer> test = new AVL<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullInCollection() {
        Integer[] ints = {1, 2, 3, null, 4, 5};
        AVL<Integer> test = new AVL<Integer>(Arrays.asList(ints));
    }

    @Test(timeout = TIMEOUT)
    public void testAddBasic() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }
        Integer[] desired = {50, 25, 20, 32, 75, 62, 100};

        debugPrint("testAddBasic", desired);

        assertEquals(7, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);

    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotate() {
        avl.add(10);
        avl.add(12);
        avl.add(5);
        avl.add(11);
        avl.add(13);

        assertEquals(5, avl.size());
        assertArrayEquals(new Integer[]{10, 5, 12, 11, 13},
                preorder(avl.getRoot()).toArray());

        avl.add(14);

        Integer[] desired = new Integer[]{12, 10, 5, 11, 13, 14};

        debugPrint("testAddLeftRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);

    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotate() {
        avl.add(50);
        avl.add(25);
        avl.add(60);
        avl.add(7);
        avl.add(30);

        assertEquals(5, avl.size());
        assertArrayEquals(new Integer[]{50, 25, 7, 30, 60},
                preorder(avl.getRoot()).toArray());

        avl.add(8);

        Integer[] desired = new Integer[]{25, 7, 8, 50, 30, 60};

        debugPrint("testAddRightRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotate() {
        Integer[] tree = {50, 7, 60, 55, 65};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(5, avl.size());
        assertArrayEquals(new Integer[]{50, 7, 60, 55, 65},
                preorder(avl.getRoot()).toArray());

        avl.add(54);

        Integer[] desired = new Integer[]{55, 50, 7, 54, 60, 65};

        debugPrint("testAddRightLeftRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotate() {
        Integer[] tree = {50, 30, 60, 7, 40};

        for (Integer i : tree) {
            avl.add(i);
        }
        assertEquals(5, avl.size());
        assertArrayEquals(new Integer[]{50, 30, 7, 40, 60},
                preorder(avl.getRoot()).toArray());

        avl.add(35);

        Integer[] desired = new Integer[]{40, 30, 7, 35, 50, 60};

        debugPrint("testAddLeftRightRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAlreadyPresent() {
        Integer[] tree = {50, 30, 60, 7, 40};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(5, avl.size());
        assertArrayEquals(new Integer[]{50, 30, 7, 40, 60},
                preorder(avl.getRoot()).toArray());

        avl.add(60);

        Integer[] desired = new Integer[]{50, 30, 7, 40, 60};

        debugPrint("testAddAlreadyPresent", desired);

        assertEquals(5, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        avl.add(1);
        avl.add(null);
    }

    @Test(timeout = TIMEOUT)
    public void testAddPropagationOfImbalance() {
        Integer[] tree = {100, 50, 150, 25, 75, 125, 175, 12, 37, 62, 87};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(11, avl.size());

        Integer[] desired = new Integer[]{100, 50, 25, 12, 37,
            75, 62, 87, 150, 125, 175};

        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        avl.add(65);
        assertEquals(12, avl.size());
        desired = new Integer[]{75, 50, 25, 12, 37, 62, 65, 100, 87,
            150, 125, 175};

        debugPrint("testAddPropagationOfImbalance", desired);


        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(3, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveBasic() {
        Integer[] tree = {50, 25, 75, 37, 51};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(5, avl.size());
        assertArrayEquals(new Integer[]{50, 25, 37, 75, 51},
                preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(25), avl.remove(25));
        assertEquals(Integer.valueOf(75), avl.remove(75));

        Integer[] desired = new Integer[]{50, 37, 51};

        debugPrint("testRemoveBasic Part 1", desired);

        assertEquals(3, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(37), avl.remove(37));
        assertEquals(Integer.valueOf(51), avl.remove(51));

        desired = new Integer[]{50};

        debugPrint("testRemoveBasic Part 2", desired);

        assertEquals(1, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(50), avl.remove(50));

        assertEquals(0, avl.size());
        assertNull(avl.getRoot());

        assertInOrder(avl);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChildren() {
        Integer[] tree = {100, 50, 150, 30, 60, 110, 170, 10, 55, 70, 105, 113,
            160, 112};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(14, avl.size());
        Integer[] desired = {100, 50, 30, 10, 60, 55, 70, 150, 110, 105, 113,
            112, 170, 160};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(150), avl.remove(150));
        desired = new Integer[]{100, 50, 30, 10, 60, 55, 70, 113, 110, 105, 112,
            170, 160};

        debugPrint("testRemoveTwoChildren Part 1", desired);

        assertEquals(13, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(60), avl.remove(60));
        desired = new Integer[]{100, 50, 30, 10, 55, 70, 113, 110, 105, 112,
            170, 160};

        debugPrint("testRemoveTwoChildren Part 2", desired);
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(3, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemovePredecessorRightRotate() {
        Integer[] tree = {100, 50, 150, 40, 60, 200, 35};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(7, avl.size());
        Integer[] desired = {100, 50, 40, 35, 60, 150, 200};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(100), avl.remove(100));


        desired = new Integer[]{60, 40, 35, 50, 150, 200};

        debugPrint("testRemovePredecessorRightRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemovePredecessorLeftRightRotate() {
        Integer[] tree = {100, 50, 150, 40, 60, 200, 45};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(7, avl.size());
        Integer[] desired = {100, 50, 40, 45, 60, 150, 200};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(100), avl.remove(100));

        desired = new Integer[]{60, 45, 40, 50, 150, 200};

        debugPrint("testRemovePredecessorLeftRightRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRotate() {
        Integer[] tree = {50, 25, 75, 30, 60, 100, 110};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(7, avl.size());
        Integer[] desired = {50, 25, 30, 75, 60, 100, 110};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(25), avl.remove(25));
        desired = new Integer[]{75, 50, 30, 60, 100, 110};

        debugPrint("testRemoveLeftRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRightRotate() {
        Integer[] tree = {50, 25, 75, 20, 30, 60, 10};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(7, avl.size());
        Integer[] desired = {50, 25, 20, 10, 30, 75, 60};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(75), avl.remove(75));
        desired = new Integer[]{25, 20, 10, 50, 30, 60};

        debugPrint("testRemoveRightRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRightLeftRotate() {
        Integer[] tree = {50, 25, 75, 30, 60, 100, 61};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(7, avl.size());
        Integer[] desired = {50, 25, 30, 75, 60, 61, 100};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(25), avl.remove(25));
        desired = new Integer[]{60, 50, 30, 75, 61, 100};

        debugPrint("testRemoveRightLeftRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRightRotate() {
        Integer[] tree = {50, 25, 75, 20, 30, 60, 40};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(7, avl.size());
        Integer[] desired = {50, 25, 20, 30, 40, 75, 60};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(75), avl.remove(75));
        desired = new Integer[]{30, 25, 20, 50, 40, 60};

        debugPrint("testRemoveLeftRightRotate", desired);

        assertEquals(6, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(2, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotPresent() {
        Integer[] tree = {50, 10, 90, 30, 70};

        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testRemoveNotPresent");

        avl.remove(39);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        avl.remove(1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        avl.add(1);
        avl.remove(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemovePropagationOfImbalance() {
        Integer[] tree = {100, 50, 150, 25, 75, 125, 175,
            12, 37, 62, 87, 160, 65};

        for (Integer i : tree) {
            avl.add(i);
        }

        assertEquals(tree.length, avl.size());
        Integer[] desired = {100, 50, 25, 12, 37, 75, 62, 65, 87, 150, 125,
            175, 160};
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        avl.remove(160);


        desired = new Integer[]{75, 50, 25, 12, 37, 62, 65, 100, 87, 150,
            125, 175};

        debugPrint("testRemovePropagationOfImbalance", desired);

        assertEquals(tree.length - 1, avl.size());
        assertArrayEquals(desired, preorder(avl.getRoot()).toArray());

        assertEquals(3, avl.getRoot().getHeight());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNotReferenceEqual() {
        AVL<String> avl = new AVL<>();
        avl.add(new String("a"));
        String remove = new String("a");
        String removedValue = avl.remove(remove);
        assertEquals(remove, removedValue);
        assertNotSame(remove, removedValue);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveSameLevelLeftRotate() {
        Integer[] tree = {4, 2, 10, 11};
        for (Integer i : tree) {
            avl.add(i);
        }
        assertEquals(4, avl.size());
        assertArrayEquals(new Integer[]{4, 2, 10, 11},
                preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(4), avl.remove(4));

        Integer[] desired = new Integer[]{10, 2, 11};

        debugPrint("testRemoveSameLevelLeftRotate", desired);

        assertEquals(3, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveSameLevelRightLeftRotate() {
        Integer[] tree = {4, 2, 10, 9};
        for (Integer i : tree) {
            avl.add(i);
        }
        assertEquals(4, avl.size());
        assertArrayEquals(new Integer[]{4, 2, 10, 9},
                preorder(avl.getRoot()).toArray());

        assertEquals(Integer.valueOf(4), avl.remove(4));

        Integer[] desired = new Integer[]{9, 2, 10};

        debugPrint("testRemoveSameLevelRightLeftRotate", desired);

        assertEquals(3, avl.size());
        assertArrayEquals(desired,
                preorder(avl.getRoot()).toArray());

        assertInOrder(avl);
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testGet");

        Integer getValue = new Integer(50);
        Integer gottenValue = avl.get(getValue);
        assertEquals(getValue, gottenValue);
        assertNotSame(getValue, gottenValue);

        for (Integer i : tree) {
            assertEquals(i, avl.get(i));
        }

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNull() {
        avl.add(1);
        avl.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotPresent() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testGetNotPresent");

        avl.get(31);
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        assertFalse(avl.contains(1));

        Integer[] tree = {50, 30, 60, 7, 40, 35};

        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testContains");

        assertTrue(avl.contains(40));
        assertTrue(avl.contains(30));
        assertTrue(avl.contains(60));
        assertFalse(avl.contains(1));
        assertFalse(avl.contains(100));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNull() {
        avl.contains(null);
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        assertEquals(-1, avl.height());

        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testHeight Part 1");

        assertEquals(2, avl.height());

        avl.clear();

        tree = new Integer[]{100, 50, 150, 25, 75, 125, 175, 12, 37, 62, 87, 65};
        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testHeight Part 2");

        assertEquals(3, avl.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        avl.clear();
        assertEquals(0, avl.size());
        assertNull(avl.getRoot());

        Integer[] tree = {100, 50, 150, 25, 75, 125, 175, 12, 37, 62, 87, 65};
        for (Integer i : tree) {
            avl.add(i);
        }
        assertEquals(tree.length, avl.size());

        avl.clear();
        assertEquals(0, avl.size());
        assertNull(avl.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testMaxDeepestNode() {
        assertNull(avl.maxDeepestNode());

        avl.add(50);
        debugPrint("testMaxDeepestNode Part 1");
        assertEquals(Integer.valueOf(50), avl.maxDeepestNode());

        avl.add(60);
        avl.add(10);
        avl.add(25);
        debugPrint("testMaxDeepestNode Part 2");
        assertEquals(Integer.valueOf(25), avl.maxDeepestNode());

        avl.add(75);
        debugPrint("testMaxDeepestNode Part 3");
        assertEquals(Integer.valueOf(75), avl.maxDeepestNode());

        avl.add(55);
        debugPrint("testMaxDeepestNode Part 4");
        assertEquals(Integer.valueOf(75), avl.maxDeepestNode());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testSuccessorEmpty() {
        avl.successor(0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testSuccessorDoesNotContain() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testSuccessorDoesNotContain");

        avl.successor(46);
    }


    @Test(timeout = TIMEOUT)
    public void testSuccessorLargest() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }

        debugPrint("testSuccessorLargest");

        assertNull(avl.successor(100));
    }

    @Test(timeout = TIMEOUT)
    public void testSuccessorIsRoot() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }
        debugPrint("testSuccessorIsRoot");
        assertEquals((Integer) 50, avl.successor(32));
    }

    @Test(timeout = TIMEOUT)
    public void testSuccessorIsChild() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }
        debugPrint("testSuccessorIsChild");
        assertEquals((Integer) 62, avl.successor(50));
    }

    @Test(timeout = TIMEOUT)
    public void testSuccessorIsDirectParent() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100};
        for (Integer i : tree) {
            avl.add(i);
        }
        debugPrint("testSuccessorIsDirectParent");
        assertEquals((Integer) 75, avl.successor(62));
    }

    @Test(timeout = TIMEOUT)
    public void testSuccessorIsAncestor() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100, 37, 23, 10, 31};
        for (Integer i : tree) {
            avl.add(i);
        }
        debugPrint("testSuccessorIsAncestor");
        assertEquals((Integer) 25, avl.successor(23));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testSuccessorNull() {
        Integer[] tree = {50, 25, 75, 32, 62, 20, 100, 37, 23, 10, 31};
        for (Integer i : tree) {
            avl.add(i);
        }
        avl.successor(null);
    }

    /*
     *
     * ///////////////////////
     * PROCEDURAL TESTS BELOW
     * //////////////////////
     *
     */

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
    public void buildTest() {
        for (int test = 1; test <= 50; test++) {

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

            avl = new AVL<>(testSet);

            if (debug) {
                AVLPrinter.printNode(avl.getRoot());
            }

            Collections.sort(testSet);

            List<Integer> compareList = inorder(avl.getRoot());

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

        for (int test = 1; test <= 50 * multiplier; test++) {

            if (debug) {
                System.out.println(
                        "----------------------\n"
                                + " Test #" + test + " start\n"
                                + "----------------------\n"
                );
            }

            ArrayList<Integer> testSet = getRandomCollection((int) (Math.random() * 20 * multiplier));
            avl = new AVL<>(testSet);

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

                assertSame(avl.remove(toRemove), removed);

                if (debug) {
                    System.out.println("avl:");
                    AVLPrinter.printNode(avl.getRoot());

                    System.out.println();
                }

                List<Integer> compareList = inorder(avl.getRoot());

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
                assertNull(avl.getRoot());

                System.out.println("Test #" + test + " end\n");
            }
        }

        System.out.println("All removal tests passed! :D");
    }

    @Test
    public void getTest() {
        for (int test = 1; test <= 50; test++) {

            if (debug) {
                System.out.println(
                        "----------------------\n"
                                + " Test #" + test + " start\n"
                                + "----------------------\n"
                );
            }

            ArrayList<Integer> testSet = getRandomCollection((int) (Math.random() * 20));

            avl = new AVL<>(testSet);

            Collections.sort(testSet);

            int oriSize = testSet.size();

            for (int i = 0; i < oriSize; i++) {

                int testIndex = (int) (Math.random() * testSet.size());
                Integer testVal = testSet.get(testIndex);

                if (debug) {
                    System.out.println("***Getting " + testVal + ": ***\n");
                    printSet(testSet);
                    System.out.println();
                    AVLPrinter.printNode(avl.getRoot());
                    System.out.println();
                }

                assertEquals(testVal, avl.get(new Integer(testVal)));

                avl.remove(testSet.remove(testIndex));

                boolean thrown = false;
                try {
                    avl.get(testVal);
                } catch (Exception e) {
                    assertTrue(e instanceof NoSuchElementException);
                    thrown = true;
                }
                assertTrue(thrown);
            }
        }

        System.out.println("All get tests passed! :D");
    }


    @Test
    public void successorTest() {
        for (int test = 1; test <= 50; test++) {

            if (debug) {
                System.out.println(
                        "----------------------\n"
                                + " Test #" + test + " start\n"
                                + "----------------------\n"
                );
            }

            ArrayList<Integer> testSet = getRandomCollection((int) (Math.random() * 20));

            avl = new AVL<>(testSet);

            Collections.sort(testSet);

            int oriSize = testSet.size();

            for (int i = 0; i < oriSize; i++) {

                if (debug) {
                    System.out.println("***Successor " + i + ": ***\n");
                    printSet(testSet);
                    System.out.println();
                    AVLPrinter.printNode(avl.getRoot());
                    System.out.println();
                }

                int testIndex = (int) (Math.random() * testSet.size());
                Integer testVal = testSet.get(testIndex);

                try {
                    if (debug) {
                        System.out.println("Successor of "
                                + testSet.get(testIndex) + " should be "
                                + (testIndex + 1 == testSet.size() ? null : testSet.get(testIndex + 1) + "\n"));
                    }
                    assertEquals(testSet.get(testIndex + 1), avl.successor(testVal));
                } catch (IndexOutOfBoundsException e) {
                    if (testSet.size() == 0) {
                        boolean thrown = false;
                        try {
                            avl.successor(testVal);
                        } catch (Exception e2) {
                            assertTrue(e2 instanceof NoSuchElementException);
                            thrown = true;
                        }
                        assertTrue(thrown);
                    } else {
                        assertNull(avl.successor(testVal));
                    }
                }
                avl.remove(testSet.remove(testIndex));
            }
        }

        System.out.println("All successor tests passed! :D");
    }

    /*
     *
     * BIG HELPER CLASS I GOT FROM STACKOVERFLOW
     *
     */
    static class AVLPrinter {

        /**
         * Initiates the avl printing
         * @param <T> comparable generic
         * @param root the root of the avl to be printed
         */
        public static <T extends Comparable<? super T>> void printNode(AVLNode<T> root) {
            int maxLevel = AVLPrinter.maxLevel(root);

            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        /**
         *
         * @param <T> comparable generic
         * @param nodes list containing the nodes to traverse
         * @param level the current level of the avl
         * @param maxLevel the last level of the avl
         */
        private static <T extends Comparable<? super T>> void printNodeInternal(List<AVLNode<T>> nodes,
                                                                                int level, int maxLevel) {
            if (nodes.isEmpty() || AVLPrinter.isAllElementsNull(nodes)) {
                return;
            }

            int floor = maxLevel - level + 1;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            AVLPrinter.printWhitespaces(firstSpaces);

            List<AVLNode<T>> newNodes = new ArrayList<AVLNode<T>>();
            for (AVLNode<T> node : nodes) {
                int used = 0;
                if (node != null) {
                    System.out.print(node.getData());
                    used += node.getData().toString().length() - 1;
                    newNodes.add(node.getLeft());
                    newNodes.add(node.getRight());
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                AVLPrinter.printWhitespaces(betweenSpaces - used);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    AVLPrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        AVLPrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).getLeft() != null) {
                        System.out.print("/");
                    } else {
                        AVLPrinter.printWhitespaces(1);
                    }

                    AVLPrinter.printWhitespaces(i + i - 1);

                    if (nodes.get(j).getRight() != null) {
                        System.out.print("\\");
                    } else {
                        AVLPrinter.printWhitespaces(1);
                    }

                    AVLPrinter.printWhitespaces(endgeLines + endgeLines - i);
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
         * @param <T> comparable generic
         * @param node the root node
         * @return the max level
         */
        private static <T extends Comparable<? super T>> int maxLevel(AVLNode<T> node) {
            if (node == null) {
                return 0;
            }

            return Math.max(AVLPrinter.maxLevel(node.getLeft()), AVLPrinter.maxLevel(node.getRight())) + 1;
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