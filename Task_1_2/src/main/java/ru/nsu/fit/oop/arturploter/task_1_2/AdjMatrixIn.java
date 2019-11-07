package ru.nsu.fit.oop.arturploter.task_1_2;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The {@code AdjMatrixIn} class represents a weighted directed graph with integer weights.
 * It only supports the {@code distTo} operation to find the shortest path from the given source to the destination
 * vertex.
 *
 * To compute a shortest paths tree, the Moore's 1957 algorithm is used. Now the algorithm is more commonly known
 * as the Bellman-Ford algorithm.
 *
 * The graph is represented by an adjacency matrix.
 *
 * @author  Artur Ploter
 */
public class AdjMatrixIn {
    private final MooreShortestPaths shortestPaths;
    private final int dest;
    private final int[][] adjMatrix;

    /**
     * Constructs a weighted directed graph with the edges and weights from the {@code adjMatrix} graph, and computes
     * a shortest paths tree from {@code source} to every other vertex in the graph.
     *
     * @param  source   the source vertex
     * @param  dest   the destination vertex
     * @param  adjMatrix   the weighted graph represented by an adjacency matrix
     */
    public AdjMatrixIn(int source, int dest, int[][] adjMatrix) {
        validateInput(source, dest, adjMatrix);

        int numOfVertices = adjMatrix.length;
        AdjListDigraph graph = new AdjListDigraph(numOfVertices);
        this.dest = dest - 1;
        this.adjMatrix = adjMatrix;

        IntStream.range(0, numOfVertices)
                .forEach(i -> IntStream.range(0, numOfVertices)
                .filter(j -> adjMatrix[i][j] != 0)
                .forEach(j -> graph.addEdge(i, j, adjMatrix[i][j])));

        shortestPaths = new MooreShortestPaths(graph, source - 1);
    }

    /**
     * Returns the length of the shortest path from {@code source} to {@code dest}.
     *
     * @return  the length of the shortest path from {@code source} to {@code dest}
     */
    public long distTo() {
        return shortestPaths.distTo(dest);
    }

    /**
     * Returns the length of the shortest path from {@code source} to {@code dest}.
     *
     * @param   dest  the destination vertex
     * @return  the length of the shortest path from {@code source} to {@code dest}
     */
    public long distTo(int dest) {
        validateDest(dest);
        return shortestPaths.distTo(dest - 1);
    }

    private void validateInput(int source, int dest, int[][] adjMatrix) {
        int numOfVertices = adjMatrix.length;

        if (adjMatrix.length == 0) {
            throw new IllegalArgumentException("The matrix is empty.");
        }

        if (source < 1 || source > numOfVertices
                || dest < 1 || dest > numOfVertices) {
            throw new IllegalArgumentException("Vertex is not in the graph.");
        }

        for (int[] matrix : adjMatrix) {
            if (adjMatrix.length != matrix.length) {
                throw new IllegalArgumentException(Arrays.deepToString(adjMatrix) + " is not an adjacency matrix.");
            }

//            for (int weight : matrix) {
//                if (weight == Integer.MAX_VALUE) {
//                    throw new IllegalArgumentException("Maximum allowed edge weight is "
//                            + Integer.sum(Integer.MAX_VALUE, -1));
//                }
//            }
        }
    }

    private void validateDest(int dest) {
        if (dest < 1 || dest > adjMatrix.length) {
            throw new IllegalArgumentException(dest + " is not in the graph.");
        }
    }
}