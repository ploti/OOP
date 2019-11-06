package ru.nsu.fit.oop.arturploter.task_2_3;

import java.util.*;

class TreeNode<T> implements Iterable<TreeNode<T>> {
    private final T val;
    private TreeNode<T> parent;
    private final List<TreeNode<T>> subtrees;

    TreeNode(T val) {
        this.val = val;
        parent = null;
        subtrees = new ArrayList<>();
    }

    void append(TreeNode<T> tree) {
        linkParent(tree, this);
        subtrees.add(tree);
    }

    void removeSubtree(TreeNode<T> tree) {
        subtrees.remove(tree);
        unlinkParent(tree);
    }

    T getVal() {
        return val;
    }

    TreeNode<T> getParent() {
        return parent;
    }

    int getSubtreesSize() {
        return subtrees.size();
    }

    T isDescendantOf(TreeNode<T> node) {
        TreeNode<T> parent = this.parent;

        while (parent != null) {
            if (parent.equals(node)) {
                return parent.val;
            }

            parent = parent.parent;
        }

        return null;
    }

    ArrayList<T> getSubtreesValues() {
        ArrayList<T> subtreesValues = new ArrayList<>();
        subtrees.forEach((subtree) -> subtreesValues.add(subtree.val));
        return subtreesValues;
    }

    public Iterator<TreeNode<T>> iterator() {
        return new DepthFirstIterator();
    }

    private void linkParent(TreeNode<T> node, TreeNode<T> parent) {
        node.parent = parent;
    }

    private void unlinkParent(TreeNode<T> node) {
        node.parent = null;
    }

    private class DepthFirstIterator implements Iterator<TreeNode<T>> {
        private final Stack<TreeNode<T>> stack;
        private final Set<TreeNode<T>> visited;
        private TreeNode<T> nextNode;
        private final Map<TreeNode<T>, Iterator<TreeNode<T>>> iterators;

        DepthFirstIterator() {
            stack = new Stack<>();
            visited = new HashSet<>();
            nextNode = TreeNode.this;
            iterators = new HashMap<>();

            stack.push(TreeNode.this);
            visited.add(TreeNode.this);
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public TreeNode<T> next() {
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
            TreeNode<T> treeNode = stack.peek();
            iterators.put(treeNode, treeNode.subtrees.iterator());

            do {
                while (!iterators.get(treeNode).hasNext()) {
                    stack.pop();

                    if (stack.isEmpty()) {
                        nextNode = null;
                        return;
                    }

                    treeNode = stack.peek();
                    iterators.putIfAbsent(treeNode, treeNode.subtrees.iterator());
                }

                if (treeNode.subtrees.size() > 0 && iterators.get(treeNode).hasNext()) {
                    nextNode = iterators.get(treeNode).next();
                } else {
                    nextNode = null;
                }
            } while (visited.contains(nextNode));

            stack.push(nextNode);
        }
    }
}