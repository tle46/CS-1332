import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

/**
 * This is a basic set of unit tests for BST.
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
public class BSTStudentTest {

    private static final int TIMEOUT = 200;
    private BST<Integer> tree;

    @Before
    public void setup() {
        tree = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        /*
              2
             /
            0
             \
              1
        */

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(0);
        data.add(1);
        tree = new BST<>(data);

        assertEquals(3, tree.size());

        assertEquals((Integer) 2, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 1, tree.getRoot().getLeft().getRight()
                .getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        /*
              1
             / \
            0   2
        */

        tree.add(1);
        tree.add(0);
        tree.add(2);

        assertEquals(3, tree.size());

        assertEquals((Integer) 1, tree.getRoot().getData());
        assertEquals((Integer) 0, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 2, tree.getRoot().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        Integer temp = 200;

        /*
              100
             / \
           10   200
                 \
                  300
                   \
                    400
            ->
              100
             / \
           10   300
                 \
                  400
        */

        tree.add(100);
        tree.add(10);
        tree.add(temp);
        tree.add(300);
        tree.add(400);
        assertEquals(5, tree.size());

        assertSame(temp, tree.remove(200));

        assertEquals(4, tree.size());

        assertEquals((Integer) 100, tree.getRoot().getData());
        assertEquals((Integer) 10, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 300, tree.getRoot().getRight().getData());
        assertEquals((Integer) 400, tree.getRoot().getRight()
                .getRight().getData());

        temp = 100;
        tree = new BST<>();

        /*
             100
             / \
           10   200
                 \
                  300
                   \
                    400
            ->
             200
             / \
           10   300
                 \
                  400
        */

        tree.add(temp);
        tree.add(10);
        tree.add(200);
        tree.add(300);
        tree.add(400);
        assertEquals(5, tree.size());

        assertSame(temp, tree.remove(100));

        assertEquals(4, tree.size());

        assertEquals((Integer) 200, tree.getRoot().getData());
        assertEquals((Integer) 10, tree.getRoot().getLeft().getData());
        assertEquals((Integer) 300, tree.getRoot().getRight().getData());
        assertEquals((Integer) 400, tree.getRoot().getRight()
                .getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        Integer temp300 = 300;
        Integer temp10 = 10;
        Integer temp100 = 100;
        Integer temp200 = 200;
        Integer temp600 = 600;
        Integer temp400 = 400;
        Integer temp500 = 500;

        /*
                300
             /     \
           10       600
             \     /
            100   400
               \   \
                200   500
        */

        tree.add(temp300);
        tree.add(temp10);
        tree.add(temp100);
        tree.add(temp200);
        tree.add(temp600);
        tree.add(temp400);
        tree.add(temp500);
        assertEquals(7, tree.size());

        // We want to make sure the data we retrieve is the one from the tree,
        // not the data that was passed in.
        assertSame(temp10, tree.get(10));
        assertSame(temp100, tree.get(100));
        assertSame(temp200, tree.get(200));
        assertSame(temp300, tree.get(300));
        assertSame(temp400, tree.get(400));
        assertSame(temp500, tree.get(500));
        assertSame(temp600, tree.get(600));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                3
             /     \
            0       6
             \     /
              1   4
               \   \
                2   5
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(6);
        tree.add(4);
        tree.add(5);
        assertEquals(7, tree.size());

        assertTrue(tree.contains(0));
        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(6));
    }

    @Test(timeout = TIMEOUT)
    public void testPreorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> preorder = new ArrayList<>();
        preorder.add(3);
        preorder.add(0);
        preorder.add(1);
        preorder.add(2);
        preorder.add(8);
        preorder.add(4);
        preorder.add(6);
        preorder.add(5);
        preorder.add(7);

        // Should be [3, 0, 1, 2, 8, 4, 6, 5, 7]
        assertEquals(preorder, tree.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void testInorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> inorder = new ArrayList<>();
        inorder.add(0);
        inorder.add(1);
        inorder.add(2);
        inorder.add(3);
        inorder.add(4);
        inorder.add(5);
        inorder.add(6);
        inorder.add(7);
        inorder.add(8);

        // Should be [0, 1, 2, 3, 4, 5, 6, 7, 8]
        assertEquals(inorder, tree.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void testPostorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> postorder = new ArrayList<>();
        postorder.add(2);
        postorder.add(1);
        postorder.add(0);
        postorder.add(5);
        postorder.add(7);
        postorder.add(6);
        postorder.add(4);
        postorder.add(8);
        postorder.add(3);

        // Should be [2, 1, 0, 5, 7, 6, 4, 8, 3]
        assertEquals(postorder, tree.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void testLevelorder() {
        /*
                3
             /     \
            0       8
             \     /
              1   4
               \   \
                2   6
                   / \
                  5   7
        */

        tree.add(3);
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(8);
        tree.add(4);
        tree.add(6);
        tree.add(5);
        tree.add(7);
        assertEquals(9, tree.size());

        List<Integer> levelorder = new ArrayList<>();
        levelorder.add(3);
        levelorder.add(0);
        levelorder.add(8);
        levelorder.add(1);
        levelorder.add(4);
        levelorder.add(2);
        levelorder.add(6);
        levelorder.add(5);
        levelorder.add(7);

        // Should be [3, 0, 8, 1, 4, 2, 6, 5, 7]
        assertEquals(levelorder, tree.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
              2
             /
            0
             \
              1
        */

        tree.add(2);
        tree.add(0);
        tree.add(1);
        assertEquals(3, tree.size());

        assertEquals(2, tree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        /*
              2
             /
            0
             \
              1
        */

        tree.add(2);
        tree.add(0);
        tree.add(1);
        assertEquals(3, tree.size());

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testGetMaxDataPerLevel() {
        /*
              3
             / \
            1   4
               /
              2  
        */

        tree.add(3);
        tree.add(4);
        tree.add(1);
        tree.add(2);

        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(4);
        expected.add(2);
        assertEquals(expected, tree.getMaxDataPerLevel());
    }
}