package ru.nsu.fit.oop.arturploter.task_2_3;

import java.util.*;

/**
 * The {@code TreeADT} class represents a tree data structure.
 * It supports the following two primary operations: append the node/subtree to the specified node,
 * remove the node/subtree. It also provides the {@code deleteTree} and {@code getSubtree} operations.
 *
 * @author  Artur Ploter
 * @param   <T>   the generic type of an object in the tree.
 */
public class TreeADT<T> implements Iterable<T> {
    private T root;
    private final Map<T, TreeNode<T>> treeNodes;
    private final Map<T, TreeNode<T>> parentNodes;

    /**
     * Initializes an empty tree with root {@code root}.
     *
     * @param  root   the value of the root node.
     */
    public TreeADT(T root) {
        this.root = root;
        treeNodes = new HashMap<>();
        parentNodes = new HashMap<>();

        treeNodes.put(root, new TreeNode<>(root));
    }

    /**
     * Adds the node/subtree to the parent node with value {@code parentVal}.
     *
     * If the parent node is not in the tree, it creates such node.
     * If the child node/subtree is already in the tree, it detaches the node/subtree
     * from its parent if it has one, detaches the parent node from one of the nodes in the subtree
     * if the node with value {@code childVal} is an ancestor of the node with value {@code parentVal},
     * and attaches it to the specified parent.
     *
     * @param  parentVal   parent node's value.
     * @param  childVal    child node's value.
     */
    public void append(T parentVal, T childVal) {
        if (parentVal == null || childVal == null) {
            throw new IllegalArgumentException("The node value cannot be null.");
        }

        TreeNode<T> parent;
        TreeNode<T> child;
        if (treeNodes.containsKey(childVal)) {
            child = treeNodes.get(childVal);

            if (parentNodes.containsKey(childVal)) {
                unlinkParent(childVal);
            }

            if (treeNodes.containsKey(parentVal)) {
                parent = treeNodes.get(parentVal);

                T nodeVal = parent.isDescendantOf(child);
                if (nodeVal != null) {
                    parent.getParent().removeSubtree(parent);
                    parentNodes.remove(parentVal);

                    if (parentNodes.containsKey(nodeVal)) {
                        unlinkParent(nodeVal);
                    }
                } else if (parentNodes.containsKey(childVal)) {
                    unlinkParent(childVal);
                }
            }
        } else {
            child = new TreeNode<>(childVal);
            treeNodes.put(childVal, child);
        }

        if (treeNodes.containsKey(parentVal)) {
            parent = treeNodes.get(parentVal);
        } else {
            parent = new TreeNode<>(parentVal);
            treeNodes.put(parentVal, parent);
        }

        parent.append(child);
        parentNodes.put(childVal, parent);
    }

    /**
     * Removes the specified node/subtree with root {@code node}.
     *
     * @param  node   the value of the node/root of the subtree to be removed.
     */
    public void remove(T node) {
        if (node == null) {
            throw new IllegalArgumentException("The node or the tree to be removed cannot be null.");
        }

        if (!treeNodes.containsKey(node)) {
            throw new IllegalArgumentException("The node is not in the tree.");
        }

        if (node.equals(root)) {
            throw new IllegalArgumentException("Cannot delete the root of the tree. " +
                    "Use the deleteTree(newRoot) method.");
        }

        if (parentNodes.containsKey(node)) {
            parentNodes.get(node).removeSubtree(treeNodes.get(node));
        }

        traverseAndDelete(treeNodes.get(node));
    }

    /**
     * Deletes the tree and creates a new one with the root {@code newRoot}.
     *
     * @param  newRoot   the value of the new root.
     */
    public void deleteTree(T newRoot) {
        this.root = newRoot;
        treeNodes.clear();
        parentNodes.clear();

        treeNodes.put(root, new TreeNode<>(root));
    }

    /**
     * Returns the hash map containing all the nodes in the subtree with root {@code root},
     * where each tree node is mapped to the list of its children.
     *
     * @param   root   the root of the subtree to be returned.
     * @return  the hash map containing all the nodes in the subtree with root {@code root}.
     */
    public HashMap<T, ArrayList<T>> getSubtree(T root) {
        if (!treeNodes.containsKey(root)) {
            throw new NoSuchElementException("The node is not in the tree.");
        }

        HashMap<T, ArrayList<T>> subtree = new HashMap<>();
        for (TreeNode<T> node : treeNodes.get(root)) {
            subtree.put(node.getVal(), node.getSubtreesValues());
        }

        return subtree;
    }

    /**
     * Returns a depth-first iterator that iterates over the nodes in the tree
     * with the root specified in the constructor, or in the {@code deleteTree} operation if it was used.
     *
     * @return  a depth-first iterator that iterates over the nodes.
     */
    public Iterator<T> iterator() {
        return new DepthFirstIterator(root);
    }

    /**
     * Returns a depth-first iterator that iterates over the nodes in the tree with root {@code root}.
     *
     * @param   root   the root of the tree to be traversed.
     * @return  a depth-first iterator that iterates over the nodes.
     */
    public Iterator<T> DepthFirstIterator(T root) {
        return new DepthFirstIterator(root);
    }

    /**
     * Returns a breadth-first iterator that iterates over the nodes in the tree
     * with the root specified in the constructor, or in the {@code deleteTree} operation if it was used.
     *
     * @return  a breadth-first iterator that iterates over the nodes.
     */
    public Iterator<T> BreadthFirstIterator() {
        return new BreadthFirstIterator(root);
    }

    /**
     * Returns a breadth-first iterator that iterates over the nodes in the tree with root {@code root}.
     *
     * @param   root   the root of the tree to be traversed.
     * @return  a breadth-first iterator that iterates over the nodes.
     */
    public Iterator<T> BreadthFirstIterator(T root) {
        return new BreadthFirstIterator(root);
    }

    private void unlinkParent(T child) {
        parentNodes.get(child).removeSubtree(treeNodes.get(child));
        parentNodes.remove(child);
    }

    private void traverseAndDelete(TreeNode<T> root) {
        for (TreeNode<T> node : root) {
            treeNodes.remove(node.getVal());
            parentNodes.remove(node.getVal());
        }
    }

    private class DepthFirstIterator implements Iterator<T> {
        private final Stack<T> stack;
        private final Set<T> visited;
        private T nextNode;
        private final Map<T, Iterator<T>> iterators;

        DepthFirstIterator(T root) {
            stack = new Stack<>();
            visited = new HashSet<>();
            nextNode = root;
            iterators = new HashMap<>();

            stack.push(root);
            visited.add(root);
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The tree is empty.");
            }

            try {
                visited.add(nextNode);
                return nextNode;
            } finally {
                proceed();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported.");
        }

        private void proceed() {
            T nodeVal = stack.peek();
            TreeNode<T> node = treeNodes.get(nodeVal);
            iterators.put(nodeVal, node.getSubtreesValues().iterator());

            do {
                while (!iterators.get(nodeVal).hasNext()) {
                    stack.pop();

                    if (stack.isEmpty()) {
                        nextNode = null;
                        return;
                    }

                    nodeVal = stack.peek();
                    node = treeNodes.get(nodeVal);
                    iterators.putIfAbsent(nodeVal, node.getSubtreesValues().iterator());
                }

                if (node.getSubtreesSize() > 0 && iterators.get(nodeVal).hasNext()) {
                    nextNode = iterators.get(nodeVal).next();
                } else {
                    nextNode = null;
                }
            } while (visited.contains(nextNode));

            stack.push(nextNode);
        }
    }

    private class BreadthFirstIterator implements Iterator<T> {
        private final Queue<T> queue;
        private final Set<T> visited;

        BreadthFirstIterator(T root) {
            queue = new LinkedList<>();
            visited = new HashSet<>();

            queue.add(root);
            visited.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The tree is empty.");
            }

            T treeNode = queue.remove();

            TreeNode<T> node = treeNodes.get(treeNode);
            for (int i = 0; i < node.getSubtreesSize(); i++) {
                if (!visited.contains(node.getSubtreesValues().get(i))) {
                    queue.add(node.getSubtreesValues().get(i));
                    visited.add(node.getSubtreesValues().get(i));
                }
            }

            return treeNode;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("The remove operation is not supported.");
        }
    }
}