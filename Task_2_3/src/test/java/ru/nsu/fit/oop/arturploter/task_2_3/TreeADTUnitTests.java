package ru.nsu.fit.oop.arturploter.task_2_3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeADTUnitTests {

    @Nested
    class AppendRemoveDepthFirstSearchBreadthFirstSearchAndForEachTests {
        private final TreeADT<String> tree = new TreeADT<>("n1");
        private List<String> actual;
        Iterator<String> depthFirstIterator;
        Iterator<String> breadthFirstIterator;


        @BeforeEach
        void initActual() {
            actual = new ArrayList<>();
        }

        @Test
        void appendIteratorAndBreadthFirstIterator() {
            tree.append("n1", "n3");
            tree.append("n2", "n5");
            tree.append("n2", "n6");
            tree.append("n3", "n4");
            tree.append("n4", "n7");
            tree.append("n4", "n8");
            tree.append("n7", "n9");
            tree.append("n1", "n2");
            tree.append("n8", "n2");

            tree.forEach(0, actual::add);
            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n1", "n3", "n4", "n7", "n9", "n8", "n2", "n5", "n6")));

            actual.clear();

            tree.forEach(1, actual::add);
        }

        @Test
        void appendRemoveIteratorForEachAndBreadthFirstIterator() {
            tree.append("n1", "n3");
            tree.append("n2", "n5");
            tree.append("n2", "n6");
            tree.append("n3", "n4");
            tree.append("n4", "n7");
            tree.append("n4", "n8");
            tree.append("n7", "n9");
            tree.append("n1", "n2");
            tree.append("n8", "n2");

            tree.append("n222", "n3");
            tree.append("n1", "n2");
            tree.append("n7", "n3");
            tree.append("n1", "n2");
            tree.append("n6", "n1");
            tree.append("n222", "n333");
            tree.append("n333", "n1");
            tree.append("n333", "n222");
            tree.append("n5", "n9");
            tree.append("n6", "n4");
            tree.append("n4", "n777");

            tree.forEach(actual::add);
            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n1", "n2", "n5", "n9")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator();
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n1", "n2", "n5", "n9")));

            actual.clear();

            tree.forEach(0, "n7", actual::add);

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n7", "n3")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator("n7");
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n7", "n3")));

            actual.clear();

            depthFirstIterator = tree.DepthFirstIterator("n6");
            while (depthFirstIterator.hasNext()) {
                actual.add(depthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n6", "n4", "n8", "n777")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator("n6");
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n6", "n4", "n8", "n777")));

            actual.clear();

            depthFirstIterator = tree.DepthFirstIterator("n333");
            while (depthFirstIterator.hasNext()) {
                actual.add(depthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n333", "n1", "n2", "n5", "n9", "n222")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator("n333");
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n333", "n1", "n222", "n2", "n5", "n9")));
        }

        @Test
        void appendRemoveIteratorDepthFirstIteratorAndBreadthFirstIterator() {
            tree.append("n1", "n3");
            tree.append("n2", "n5");
            tree.append("n2", "n6");
            tree.append("n3", "n4");
            tree.append("n4", "n7");
            tree.append("n4", "n8");
            tree.append("n7", "n9");
            tree.append("n1", "n2");
            tree.append("n8", "n2");

            tree.append("n222", "n3");
            tree.append("n1", "n2");
            tree.append("n7", "n3");
            tree.append("n1", "n2");
            tree.append("n6", "n1");
            tree.append("n222", "n333");
            tree.append("n333", "n1");
            tree.append("n333", "n222");
            tree.append("n5", "n9");
            tree.append("n6", "n4");
            tree.append("n4", "n777");

            tree.remove("n5");

            tree.append("n777", "n5");
            tree.append("n6", "n222");
            tree.append("n1", "n9");
            tree.append("n2", "n1");

            tree.remove("n777");
            tree.remove("n3");

            tree.append("n333", "n999");

            tree.remove("n333");

            tree.forEach(actual::add);
            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n1", "n9")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator();
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n1", "n9")));

            actual.clear();

            depthFirstIterator = tree.DepthFirstIterator("n7");
            while (depthFirstIterator.hasNext()) {
                actual.add(depthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Collections.singletonList("n7")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator("n7");
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Collections.singletonList("n7")));

            actual.clear();

            depthFirstIterator = tree.DepthFirstIterator("n6");
            while (depthFirstIterator.hasNext()) {
                actual.add(depthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n6", "n4", "n8", "n222")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator("n6");
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n6", "n4", "n222", "n8")));

            actual.clear();

            depthFirstIterator = tree.DepthFirstIterator("n2");
            while (depthFirstIterator.hasNext()) {
                actual.add(depthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n2", "n1", "n9")));

            actual.clear();

            breadthFirstIterator = tree.BreadthFirstIterator("n2");
            while (breadthFirstIterator.hasNext()) {
                actual.add(breadthFirstIterator.next());
            }

            assertEquals(actual, new ArrayList<>(
                    Arrays.asList("n2", "n1", "n9")));
        }
    }

    @Nested
    class DeleteTreeAndGetSubtreeTests {
        private final TreeADT<Double> tree = new TreeADT<>(902.2);
        private final List<Double> nodes = new ArrayList<>(
                Arrays.asList(902.2, 1.2, 17.5, 5.5, 91.8, -21.8, 100.005, 251.1, 71.2, 123.2, 1041.2, 152.5, 63.2));

        @Test
        void deleteTreeAndGetSubtree1() {
            tree.append(nodes.get(0), nodes.get(1));
            tree.append(nodes.get(2), nodes.get(3));
            tree.append(nodes.get(4), nodes.get(5));
            tree.append(nodes.get(0), nodes.get(4));
            tree.append(nodes.get(1), nodes.get(2));

            TreeADT<Double> expectedTree = new TreeADT<>(nodes.get(0));
            expectedTree.append(nodes.get(0), nodes.get(1));
            expectedTree.append(nodes.get(2), nodes.get(3));
            expectedTree.append(nodes.get(4), nodes.get(5));
            expectedTree.append(nodes.get(0), nodes.get(4));
            expectedTree.append(nodes.get(1), nodes.get(2));

            assertEquals(tree.getSubtree(nodes.get(0)), expectedTree);

            tree.deleteTree(nodes.get(6));
            tree.append(nodes.get(6), nodes.get(0));

            expectedTree = new TreeADT<>(nodes.get(6));
            expectedTree.append(nodes.get(6), nodes.get(0));

            assertEquals(tree.getSubtree(nodes.get(6)), expectedTree);
        }

        @Test
        void deleteTreeAndGetSubtree2() {
            tree.append(nodes.get(7), nodes.get(8));
            tree.append(nodes.get(9), nodes.get(10));
            tree.append(nodes.get(9), nodes.get(7));
            tree.append(nodes.get(8),nodes.get(10));
            tree.append(nodes.get(9), nodes.get(11));

            tree.remove(nodes.get(8));

            tree.append(nodes.get(7), nodes.get(9));
            tree.append(nodes.get(11), nodes.get(7));
            tree.append(nodes.get(12), nodes.get(8));
            tree.append(nodes.get(6), nodes.get(11));

            TreeADT<Double> expectedTree = new TreeADT<>(nodes.get(6));
            expectedTree.append(nodes.get(11),nodes.get(7));
            expectedTree.append(nodes.get(6), nodes.get(11));
            expectedTree.append(nodes.get(7), nodes.get(9));

            assertEquals(tree.getSubtree(nodes.get(6)), expectedTree);

            expectedTree = new TreeADT<>(nodes.get(12));
            expectedTree.append(nodes.get(12), nodes.get(8));

            assertEquals(tree.getSubtree(nodes.get(12)), expectedTree);

            tree.deleteTree(nodes.get(7));
            tree.append(nodes.get(7), nodes.get(0));

            expectedTree = new TreeADT<>(nodes.get(7));
            expectedTree.append(nodes.get(7), nodes.get(0));

            assertEquals(tree.getSubtree(nodes.get(7)), expectedTree);
        }

        @Nested
        class ExceptionsTests {
            final Integer[] root = new Integer[] { 1, 2, 3 };
            private final TreeADT<Integer[]> tree = new TreeADT<>(root);

            @Test
            void append_NullWasPassedIntoAppend_IllegalArgumentExceptionIsThrown() {
                assertThrows(IllegalArgumentException.class, () -> tree.append(null, new Integer[] { 2, 0, 0, 1 }));
            }

            @Test
            void remove_NullWasPassedIntoRemove_IllegalArgumentExceptionIsThrown() {
                assertThrows(IllegalArgumentException.class, () -> tree.remove(null));
            }

            @Test
            void remove_NodeThatIsNotInTheTreeWasPassedIntoRemove_IllegalArgumentExceptionIsThrown() {
                assertThrows(IllegalArgumentException.class, () -> tree.remove(new Integer[] { 0, 2, 7 }));
            }

            @Test
            void remove_RootWasPassedIntoRemove_IllegalArgumentExceptionIsThrown() {
                assertThrows(IllegalArgumentException.class, () -> tree.remove(new Integer[] { 1, 2, 3 }));
            }

            @Test
            void getSubtree_NodeThatIsNotInTheTreeWasPassed_NoSuchElementExceptionIsThrown() {
                assertThrows(NoSuchElementException.class, () -> tree.getSubtree(new Integer[] { 1, 2, 3 }));
            }

            @Test
            void depthFirstIteratorNext_NextWasCalledOnAnEmptyTree_NoSuchElementExceptionIsThrown() {
                Integer[] node1 = new Integer[] { 7, 7, 7 };
                Integer[] node2 = new Integer[] { 2, 7, 5 };
                Integer[] node3 = new Integer[] { 3, 3, 3, 3 };
                Integer[] node4 = new Integer[] { 3, 2, 1, 0 };

                tree.append(node1, node2);
                tree.append(node2, node3);
                tree.append(node3, node4);

                assertThrows(NoSuchElementException.class, () -> {
                    Iterator<Integer[]> iterator = tree.DepthFirstIterator(node1);
                    while (iterator.hasNext()) {
                        Integer[] node = iterator.next();

                        if (Arrays.equals(node, node4)) {
                            iterator.next();
                            break;
                        }
                    }
                });
            }

            @Test
            void depthFirstIteratorRemove_RemoveWasCalled_UnsupportedOperationExceptionIsThrown() {
                Integer[] node1 = new Integer[] { 3, 3, 3, 3, 3 };
                Integer[] node2 = new Integer[] { 6, 1, 6 };

                tree.append(root, node1);
                tree.append(node1, node2);

                assertThrows(UnsupportedOperationException.class, () -> {
                    Iterator<Integer[]> iterator = tree.iterator();
                    while (iterator.hasNext()) {
                        Integer[] node = iterator.next();

                        if (Arrays.equals(node, node1)) {
                            iterator.remove();
                            break;
                        }
                    }
                });
            }

            @Test
            void breadthFirstIteratorNext_NextWasCalledOnAnEmptyTree_NoSuchElementExceptionIsThrown() {
                Integer[] node1 = new Integer[] { 7, 7, 7 };
                Integer[] node2 = new Integer[] { 2, 7, 5 };
                Integer[] node3 = new Integer[] { 3, 3, 3, 3 };
                Integer[] node4 = new Integer[] { 3, 2, 1, 0 };

                tree.append(root, node1);
                tree.append(node1, node2);
                tree.append(node2, node3);
                tree.append(node3, node4);

                assertThrows(NoSuchElementException.class, () -> {
                    Iterator<Integer[]> iterator = tree.BreadthFirstIterator(root);
                    while (iterator.hasNext()) {
                        Integer[] node = iterator.next();

                        if (Arrays.equals(node, node4)) {
                            iterator.next();
                            break;
                        }
                    }
                });
            }

            @Test
            void breadthFirstIteratorRemove_RemoveWasCalled_UnsupportedOperationExceptionIsThrown() {
                Integer[] node1 = new Integer[] { 4, 3, 4, 3, 4 };
                Integer[] node2 = new Integer[] { 6, 4, 6 };

                tree.append(node1, node2);

                assertThrows(UnsupportedOperationException.class, () -> {
                    Iterator<Integer[]> iterator = tree.BreadthFirstIterator(node1);
                    while (iterator.hasNext()) {
                        Integer[] node = iterator.next();

                        if (Arrays.equals(node, node1)) {
                            iterator.remove();
                            break;
                        }
                    }
                });
            }
        }
    }
}