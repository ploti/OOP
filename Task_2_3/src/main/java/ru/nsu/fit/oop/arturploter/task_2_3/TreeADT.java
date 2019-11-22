package ru.nsu.fit.oop.arturploter.task_2_3;

import java.util.*;
import java.util.function.Consumer;

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
    private final List<T> treeNodesIndices;
    private final Map<T, TreeNode<T>> parentNodes;

    /**
     * Initializes an empty tree with root {@code root}.
     *
     * @param  root   the value of the root node.
     */
    public TreeADT(T root) {
        this.root = root;
        treeNodes = new HashMap<>();
        treeNodesIndices = new ArrayList<>();
        parentNodes = new HashMap<>();

        treeNodes.put(root, new TreeNode<>(root));
        treeNodesIndices.add(root);
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

        if (!treeNodesIndices.contains(parentVal)) {
            treeNodesIndices.add(parentVal);
        }

        if (!treeNodesIndices.contains(childVal)) {
            treeNodesIndices.add(childVal);
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
        treeNodesIndices.clear();
        parentNodes.clear();

        treeNodes.put(root, new TreeNode<>(root));
        treeNodesIndices.add(root);
    }

    /**
     * Returns the hash map containing all the nodes in the subtree with root {@code root},
     * where each tree node is mapped to the list of its children.
     *
     * @param   root   the root of the subtree to be returned.
     * @return  the hash map containing all the nodes in the subtree with root {@code root}.
     */
    public TreeADT<T> getSubtree(T root) {
        if (!treeNodes.containsKey(root)) {
            throw new NoSuchElementException("The node is not in the tree.");
        }

        TreeADT<T> newTree = new TreeADT<>(root);
        for (TreeNode<T> node : treeNodes.get(root)) {
            TreeNode<T> parentNode = node.getParent();

            if (!node.getVal().equals(root)) {
                newTree.append(parentNode.getVal(), node.getVal());
            }
        }

        return newTree;
    }

    /**
     * Does depth-first or breadth-first traversal of the rooted tree in the {@code TreeADT}
     * and performs the given action for each node in the tree until all nodes have been processed,
     * or the action throws an exception.
     *
     * @param  iteratorType   a type of an iterator that must be either 0 or 1,
     *                        0 for a depth-first iterator, 1 for a breadth-first iterator.
     * @param  action         an action to be performed on each node in the rooted tree.
     */
    public void forEach(int iteratorType, Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (iteratorType == 0) {
            for (T t : this) {
                action.accept(t);
            }
        } else if (iteratorType == 1) {
            Iterator<T> breadthFirstIterator = this.BreadthFirstIterator();
            while (breadthFirstIterator.hasNext()) {
                action.accept(breadthFirstIterator.next());
            }
        } else {
            throw new IllegalArgumentException("The iteratorType argument must be either 0 or 1.");
        }
    }

    /**
     * Does depth-first or breadth-first traversal of the tree with a root {@code root} in the {@code TreeADT}
     * and performs the given action for each node in the tree until all nodes have been processed,
     * or the action throws an exception.
     *
     * @param  iteratorType   a type of an iterator that must be either 0 or 1,
     *                        0 for a depth-first iterator, 1 for a breadth-first iterator.
     * @param  root           a root of the tree in the {@code TreeADT}.
     * @param  action         an action to be performed on each node in the tree with a root {@code root}.
     */
    public void forEach(int iteratorType, T root, Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (iteratorType == 0) {
            Iterator<T> depthFirstIterator = this.BreadthFirstIterator(root);
            while (depthFirstIterator.hasNext()) {
                action.accept(depthFirstIterator.next());
            }
        } else if (iteratorType == 1) {
            Iterator<T> breadthFirstIterator = this.BreadthFirstIterator(root);
            while (breadthFirstIterator.hasNext()) {
                action.accept(breadthFirstIterator.next());
            }
        } else {
            throw new IllegalArgumentException("The iteratorType argument must be either 0 or 1.");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }

        //noinspection unchecked
        final TreeADT<T> anotherTree = (TreeADT<T>) obj;
        List<T> anotherTreeNodesIndices = anotherTree.getTreeNodesIndices();

        for (int i = 0; i < treeNodesIndices.size(); i++) {
            if (i > anotherTreeNodesIndices.size()
                    || !treeNodesIndices.get(i).equals(anotherTreeNodesIndices.get(i))) {

                return false;
            }
        }

        return true;
    }

    private void unlinkParent(T child) {
        parentNodes.get(child).removeSubtree(treeNodes.get(child));
        parentNodes.remove(child);
    }

    private void traverseAndDelete(TreeNode<T> root) {
        for (TreeNode<T> node : root) {
            treeNodes.remove(node.getVal());
            treeNodesIndices.remove(node.getVal());
            parentNodes.remove(node.getVal());
        }
    }

    private List<T> getTreeNodesIndices() {
        return new ArrayList<>(treeNodesIndices);
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