import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;


/**
 * Your implementation of a BST.
 *
 * @author Thomas Le
 * @version 1.0
 * @userid thomas30
 * @GTID 903696568
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection can not be null");
        }
        for (T eachData: data) {
            if (eachData == null) {
                throw new IllegalArgumentException("There is data in the collection that is null");
            } else {
                add(eachData);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        BSTNode newNode = new BSTNode(data);
        if (root == null) {
            //Tree is empty
            root = newNode;
            size++;
        } else {
            //Tree is not empty
            addAtNode(newNode, root);
            size++;
        }
    }

    /**
     * Adds data to tree with new node and root
     *
     * @param newNode new node to be added
     * @param node root node of tree
     */
    private void addAtNode(BSTNode newNode, BSTNode node) {
        if (newNode.getData().compareTo(node.getData()) > 0) {
            //data > nodeData
            if (node.getRight() == null) {
                node.setRight(newNode);
            } else {
                addAtNode(newNode, node.getRight());
            }
        } else {
            //data < nodeData
            if (node.getLeft() == null) {
                node.setLeft(newNode);
            } else {
                addAtNode(newNode, node.getLeft());
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data does not exist in the tree");
        }
        //Declares target node to be removed and its parent (both null if target is root (recursive)
        BSTNode parentOfTarget = getParentNodeWithData(data, root);
        BSTNode targetNode = getTargetWithParent(data, parentOfTarget);
        if (targetNode == null) {
            //Target returns null when it is root
            targetNode = root;
            if (targetNode.getLeft() == null && targetNode.getRight() == null) {
                //Case root is only node
                clear();
                return (T) targetNode.getData();
            } else if (targetNode.getLeft() == null || targetNode.getRight() == null) {
                //Case root has single child
                root = targetNode.getLeft() == null ? targetNode.getRight() : targetNode.getLeft();
                size--;
                return (T) targetNode.getData();
            }
            //Continues if root has two children (will always continue to following else)
        }

        if (targetNode.getLeft() == null && targetNode.getRight() == null) {
            //Case target has no children
            if (parentOfTarget.getLeft() == targetNode) {
                parentOfTarget.setLeft(null);
            } else {
                parentOfTarget.setRight(null);
            }
            size--;
            return (T) targetNode.getData();
        } else if (targetNode.getLeft() == null || targetNode.getRight() == null) {
            //Case target has one child
            BSTNode childOfTarget = targetNode.getLeft() == null ? targetNode.getRight() : targetNode.getLeft();
            if (parentOfTarget.getLeft() == targetNode) {
                parentOfTarget.setLeft(childOfTarget);
            } else {
                parentOfTarget.setRight(childOfTarget);
            }
            size--;
            return (T) targetNode.getData();
        } else {
            //Case target has two children
            //identify successor
            BSTNode successorNode = targetNode.getRight();
            while (successorNode.getLeft() != null) {
                successorNode = successorNode.getLeft();
            }
            //Recursively remove successor
            remove((T) successorNode.getData());
            //Replace successor where target currently is
            T returnData = (T) targetNode.getData();
            targetNode.setData(successorNode.getData());
            return returnData;
        }
    }

    /**
     * Obtains parent of target node with data of target recursively
     *
     * @param searchData data of target node
     * @param node root node of tree containing target node
     * @return parent node (null if target is root)
     */
    private BSTNode getParentNodeWithData(T searchData, BSTNode<T> node) {
        if (searchData.compareTo(node.getData()) == 0) {
            //Case root is target RETURNS NULL
            return null;
        } else if ((node.getLeft() != null && searchData.compareTo(node.getLeft().getData()) == 0)
                || (node.getRight() != null && searchData.compareTo(node.getRight().getData()) == 0)) {
            //Case node is parent
            return node;
        } else if (searchData.compareTo(node.getData()) > 0) {
            //Case parent is on right side
            return getParentNodeWithData(searchData, node.getRight());
        } else {
            //Case parent is on left side
            return getParentNodeWithData(searchData, node.getLeft());
        }
    }

    /**
     * Obtains target node using parent node
     *
     * @param searchData data of target node
     * @param node parent of target node
     * @return target node (null if target is root)
     */
    private BSTNode getTargetWithParent(T searchData, BSTNode<T> node) {
        if (node == null) {
            //Case target is root RETURN NULL
            return null;
        } else if (node.getRight() != null && searchData.compareTo(node.getRight().getData()) == 0) {
            //Case target is to right
            return node.getRight();
        } else if (searchData.compareTo(node.getLeft().getData()) == 0) {
            //Case target is to left
            return node.getLeft();
        } else {
            //Never reached
            return null;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        T result = getAtNode(data, root);
        if (result == null) {
            throw new NoSuchElementException("Data is not in tree");
        }
        return result;
    }

    /**
     * Obtains data in tree matching search data
     *
     * @param searchData data of target node
     * @param node root node of tree containing target node
     * @return data matching searchData or null is data does not exist
     */
    public T getAtNode(T searchData, BSTNode<T> node) {
        if (searchData.compareTo(node.getData()) == 0) {
            //Data is at node
            return node.getData();
        } else if (searchData.compareTo(node.getData()) > 0) {
            //Target node is to right of node
            if (node.getRight() == null) {
                //Data does not exist
                return null;
            }
            return getAtNode(searchData, node.getRight());
        } else {
            //Target node is to left of node
            if (node.getLeft() == null) {
                //Data does not exist
                return null;
            }
            return getAtNode(searchData, node.getLeft());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        T result = getAtNode(data, root);
        return result != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        return preorderListWithNode(new ArrayList<T>(), root);
    }

    /**
     * Obtains array list with preorder list
     *
     * @param list list to contain preorder
     * @param node root node
     * @return array list containing preorder list
     */
    private ArrayList<T> preorderListWithNode(ArrayList<T> list, BSTNode node) {
        if (node != null) {
            list.add((T) node.getData());
            preorderListWithNode(list, node.getLeft());
            preorderListWithNode(list, node.getRight());
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        return inorderListWithNode(new ArrayList<T>(), root);
    }

    /**
     * Obtains array list with inorder list
     *
     * @param list list to contain inorder
     * @param node root node
     * @return array list containing inorder list
     */
    private ArrayList<T> inorderListWithNode(ArrayList<T> list, BSTNode node) {
        if (node != null) {
            inorderListWithNode(list, node.getLeft());
            list.add((T) node.getData());
            inorderListWithNode(list, node.getRight());
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        return postorderListWithNode(new ArrayList<T>(), root);
    }

    /**
     * Obtains array list with postorder list
     *
     * @param list list to contain postorder
     * @param node root node
     * @return array list containing postorder list
     */
    private ArrayList<T> postorderListWithNode(ArrayList<T> list, BSTNode node) {
        if (node != null) {
            postorderListWithNode(list, node.getLeft());
            postorderListWithNode(list, node.getRight());
            list.add((T) node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList list = new ArrayList<T>();

        if (root == null) {
            //Case tree is empty
            return list;
        }

        //Declare queue initialized with root
        LinkedList<BSTNode> queue = new LinkedList<BSTNode>();
        queue.add(root);

        while (queue.size() != 0) {
            BSTNode targetNode = queue.removeFirst();

            //Add data from current node
            list.add(targetNode.getData());

            //Recurse on data from left and right child nodes
            if (targetNode.getLeft() != null) {
                queue.add(targetNode.getLeft());
            }

            if (targetNode.getRight() != null) {
                queue.add(targetNode.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return getHeightWithNode(root);
    }

    /**
     * Obtains height on tree under root node
     *
     * @param node root node
     * @return int representing height of tree
     */
    private int getHeightWithNode(BSTNode node) {
        if (node == null) {
            return -1;
        }
        return Math.max(getHeightWithNode(node.getLeft()), getHeightWithNode(node.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        return maxDataWithNode(new ArrayList<T>(), root, 0);
    }

    /**
     * Obtains array list with preorder list
     *
     * @param list list to contain preorder
     * @param node root node
     * @param height height of node in tree
     * @return array list containing preorder list
     */
    public List<T> maxDataWithNode(ArrayList<T> list, BSTNode node, int height) {
        if (node == null) {
            //Node doesn't exist
            return list;
        }
        if (height == list.size()) {
            //Height is increased at rightmost node
            list.add((T) node.getData());
        }

        //Recurse into upper heights
        maxDataWithNode(list, node.getRight(), height + 1);
        maxDataWithNode(list, node.getLeft(), height + 1);

        return list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
