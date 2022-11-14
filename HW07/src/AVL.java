import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Thomas Le
 * @version 1.0
 * @userid thomas30
 * @GTID 903696568
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: Piazza test files
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("There is null data in collection");
            }
            this.add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        root = addAtNode(data, root);
    }

    /**
     * Adds searchData based on root target node
     *
     * @param searchData the data to add to the tree
     * @param node the target root node
     * @return root node
     */
    private AVLNode<T> addAtNode(T searchData, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<T>(searchData);
        } else if (searchData.compareTo(node.getData()) > 0) {
            //data > nodeData
            node.setRight(addAtNode(searchData, node.getRight()));
        } else if (searchData.compareTo(node.getData()) < 0) {
            //data < nodeData
            node.setLeft(addAtNode(searchData, node.getLeft()));
        } else {
            return node;
        }
        //Update height
        updateHeightBalance(node);

        if (node.getBalanceFactor() > 1) {
            //Left heavy
            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            AVLNode rotate = rightRotate(node);
            return rotate;
        } else if (node.getBalanceFactor() < -1) {
            //Right heavy
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotate(node.getRight()));
            }
            AVLNode rotate = leftRotate(node);
            return rotate;
        }
        return node;
    }

    /**
     * Updates height and balanceFactor of node
     *
     * @param node the target node
     * @return root node
     */
    private AVLNode<T> updateHeightBalance(AVLNode<T> node) {
        if (node != null) {
            node.setHeight(1 + Math.max(heightOfNode(node.getRight()), heightOfNode(node.getLeft())));
            node.setBalanceFactor(node == null ? 0 : heightOfNode(node.getLeft()) - heightOfNode(node.getRight()));
        }
        return node;
    }

    /**
     * Returns height of given node
     *
     * @param node the target root node
     * @return height of node
     */
    private int heightOfNode(AVLNode node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * Right rotates tree around node
     *
     * @return new target root node
     * @param x target root node of tree
     */
    private AVLNode<T> rightRotate(AVLNode<T> x) {
        //x is former target root, y is left child of target
        AVLNode<T> y = x.getLeft();

        x.setLeft(y.getRight());
        y.setRight(x);

        //Recalculate heights
        updateHeightBalance(x);
        updateHeightBalance(y);

        //Return the new target root
        return y;
    }

    /**
     * Left rotates tree around node
     *
     * @return new target root node
     * @param x target root node of tree
     */
    private AVLNode<T> leftRotate(AVLNode<T> x) {
        //x is former target root, y is right child of target
        AVLNode<T> y = x.getRight();

        x.setRight(y.getLeft());
        y.setLeft(x);

        //Recalculate heights
        updateHeightBalance(x);
        updateHeightBalance(y);

        //Return the new target root
        return y;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        //Temp node to hold return data
        AVLNode<T> temp = new AVLNode<T>(null);

        //Remove
        root = removeAtNode(data, root, temp);
        size--;

        return temp.getData();
    }

    /**
     * Removes node with matching data using recursion
     *
     * @param searchData the search data to be removed
     * @param node the node to be removed
     * @param temp the node that holds the data returned in main remove method
     * @return new root
     */
    private AVLNode<T> removeAtNode(T searchData, AVLNode<T> node, AVLNode<T> temp) {
        if (node == null) {
            throw new NoSuchElementException("Element not in tree");
        }
        if (node.getData().compareTo(searchData) > 0) {
            //Traverse left
            node.setLeft(removeAtNode(searchData, node.getLeft(), temp));
        } else if (node.getData().compareTo(searchData) < 0) {
            //Traverse right
            node.setRight(removeAtNode(searchData, node.getRight(), temp));
        } else {
            //Node to be remove is found, remove node
            temp.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                //Node has no children
                return null;
            } else if (node.getLeft() == null || node.getRight() == null) {
                //Node has one child
                if (node.getLeft() == null) {
                    //Replace with right child
                    return node.getRight();
                } else {
                    //Replace with left child
                    return node.getLeft();
                }
            } else {
                // Node has 2 children
                AVLNode<T> temp1 = new AVLNode<>(null);
                AVLNode<T> predecessorNode = node.getLeft();
                while (predecessorNode.getRight() != null) {
                    predecessorNode = predecessorNode.getRight();
                }
                //Replace with predecessor and recursively remove
                node.setData(predecessorNode.getData());
                node.setLeft(removeAtNode(predecessorNode.getData(), node.getLeft(), temp1));
            }
        }
        updateHeightBalance(node);


        if (node.getBalanceFactor() > 1) {
            //Left heavy
            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            AVLNode rotate = rightRotate(node);
            return rotate;
        } else if (node.getBalanceFactor() < -1) {
            //Right heavy
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rightRotate(node.getRight()));
            }
            AVLNode rotate = leftRotate(node);
            return rotate;
        }
        return node;
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("Element is not in tree");
        }
        return getAtNode(data, root);
    }

    /**
     * Obtains data in tree matching search data
     *
     * @param searchData data of target node
     * @param node root node of tree containing target node
     * @return data matching searchData or null is data does not exist
     */
    public T getAtNode(T searchData, AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        if (node.getData().compareTo(searchData) > 0) {
            //Traverse left
            return getAtNode(searchData, node.getLeft());
        } else if (node.getData().compareTo(searchData) < 0) {
            //Traverse right
            return getAtNode(searchData, node.getRight());
        } else {
            //Reached node
            return node.getData();
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
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
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightOfNode(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxDeepestRecurse(root);
    }

    /**
     * Recurses through to find max deepest node
     * @param node target root node
     * @return max deepest node value
     */

    private T maxDeepestRecurse(AVLNode<T> node) {
        if (node == null) {
            //Empty tree
            return null;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            //Deepest node with no children
            return node.getData();
        }
        //Look for deeper side;
        if (node.getBalanceFactor() > 0) {
            //Traverse left side
            return maxDeepestRecurse(node.getLeft());
        } else {
            //Traverse right side
            return maxDeepestRecurse(node.getRight());
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        AVLNode<T> temp = new AVLNode<>(null);
        return successorRecurse(data, root, temp, false);
    }

    /**
     * Recurses through tree to find successor
     *
     * @param node target root node
     * @param searchData searchData that the successor is to be found of
     * @param temp temp node containing value of data to be returned
     * @param isFound whether the searchData node has been found
     * @return successor
     */
    private T successorRecurse(T searchData, AVLNode<T> node, AVLNode<T> temp, boolean isFound) {
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (isFound) {
            //Found
            if (node.getLeft() == null) {
                //Reached leftmost node
                return temp.getData();
            } else {
                //Continue traversing the left nodes
                temp.setData(node.getLeft().getData());
                return successorRecurse(searchData, node.getLeft(), temp, true);
            }
        } else {
            //Not found
            if (node.getData().compareTo(searchData) > 0) {
                //Traversing until searchData is found in the tree
                //Traverse left
                temp.setData(node.getData());
                return successorRecurse(searchData, node.getLeft(), temp, false);
            } else if (node.getData().compareTo(searchData) < 0) {
                //Traverse right
                return successorRecurse(searchData, node.getRight(), temp, false);
            } else {
                //Found node
                isFound = true;
                if (node.getRight() == null) {
                    //Nothing to the right, successor from left
                    return temp.getData();
                }
                //Traverse right side of searchData containing node
                temp.setData(node.getRight().getData());
                return successorRecurse(searchData, node.getRight(), temp, true);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
