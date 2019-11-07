package ru.nsu.fit.oop.arturploter.task_1_2;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

public final class AdjMatrixInUnitTests {
    private AdjMatrixIn adjMatrix;

    @After
    public void tearDown() {
        adjMatrix = null;
    }

    @Test
    public void given_3x3matrix_When_CorrectInput_Then_ShortestPathReturned() {
        adjMatrix = new AdjMatrixIn(2, 1, new int[][]{
                {0, 1, 1},
                {4, 0, 1},
                {2, 1, 0}
        });

        assertThat(adjMatrix.distTo(), is(3L));
        assertThat(adjMatrix.distTo(2), is(0L));
        assertThat(adjMatrix.distTo(3), is(1L));
    }

    @Test
    public void given_9x9matrix_When_CorrectInput_Then_ShortestPathReturned() {
        adjMatrix = new AdjMatrixIn(1, 4, new int[][]{
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 0, 10, 0, 2, 0, 0},
                {0, 0, 0, 14, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        });

        assertThat(adjMatrix.distTo(), is(19L));
        assertThat(adjMatrix.distTo(1), is(0L));
        assertThat(adjMatrix.distTo(2), is(4L));
        assertThat(adjMatrix.distTo(3), is(12L));
        assertThat(adjMatrix.distTo(5), is(21L));
        assertThat(adjMatrix.distTo(6), is(11L));
        assertThat(adjMatrix.distTo(7), is(9L));
        assertThat(adjMatrix.distTo(8), is(8L));
        assertThat(adjMatrix.distTo(9), is(14L));
    }

    @Test
    public void given_3x3matrixWithMaxIntWeights_When_CorrectInput_Then_ShortestPathReturned() {
        adjMatrix = new AdjMatrixIn(2, 1, new int[][]{
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}
        });

        assertThat(adjMatrix.distTo(), is((long) Integer.MAX_VALUE));
        assertThat(adjMatrix.distTo(2), is(0L));
        assertThat(adjMatrix.distTo(3), is((long) Integer.MAX_VALUE));
    }

    @Test
    public void given_6x6matrix_When_OneOfTheWeightsIsIntMax_Then_ShortestPathReturned() {
        adjMatrix = new AdjMatrixIn(1, 3, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, Integer.MAX_VALUE},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });

        assertThat(adjMatrix.distTo(), is(-3L));
        assertThat(adjMatrix.distTo(1), is(0L));
        assertThat(adjMatrix.distTo(2), is(1L));
        assertThat(adjMatrix.distTo(6), is(0L));
    }

    @Test
    public void given_6x6matrixWithNegativeCycle_When_CycleIsUnreachableFromSource_Then_ShortestPathReturned() {
        adjMatrix = new AdjMatrixIn(1, 3, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, 7},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });

        assertThat(adjMatrix.distTo(), is(-3L));
        assertThat(adjMatrix.distTo(1), is(0L));
        assertThat(adjMatrix.distTo(2), is(1L));
        assertThat(adjMatrix.distTo(6), is(0L));
    }

    @Test
    public void given_1x1matrix_When_InputIsCorrect_Then_0Returned() {
        adjMatrix = new AdjMatrixIn(1, 1, new int[][]{
                {1}
        });

        assertThat(adjMatrix.distTo(), is(0L));
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void given_3x3matrixWithNegativeCycle_When_CycleIsReachableFromSource_Then_ExceptionThrown()
            throws UnsupportedOperationException {

        adjMatrix = new AdjMatrixIn(2, 1, new int[][]{
                {0, 1, 1},
                {4, 0, 1},
                {2, -2, 0}
        });

        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage(startsWith("Negative cycle"));
        adjMatrix.distTo();
    }

    @Test
    public void given_6x6matrixWithNegativeCycle_When_CycleIsReachableFromSource_Then_ExceptionThrown() {
        adjMatrix = new AdjMatrixIn(5, 5, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, 7},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });

        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage(startsWith("Negative cycle"));
        adjMatrix.distTo();
    }

    @Test
    public void given_6x6matrix_When_DestIsUnreachableFromSource_Then_ExceptionThrown() {
        int dest = 4;
        adjMatrix = new AdjMatrixIn(1, dest, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, 7},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });

        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage(startsWith(dest + " is unreachable"));
        adjMatrix.distTo();
    }

    @Test
    public void given_6x6matrix_When_InvalidSource_Then_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Vertex is not in the graph."));

        new AdjMatrixIn(0, 5, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, 7},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });
    }

    @Test
    public void given_6x6matrix_When_InvalidDestPassedInConstructor_Then_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Vertex is not in the graph."));

        new AdjMatrixIn(4, 7, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, 7},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });
    }

    @Test
    public void given_6x6matrix_When_InvalidDestPassedInMethod_Then_ExceptionThrown() {
        adjMatrix = new AdjMatrixIn(1, 3, new int[][]{
                {3, 1, -3, 0, 0, 0},
                {0, 0, 3, 0, 0, -1},
                {0, 0, 0, 0, 0, 7},
                {0, 0, 3, 0, -2, 0},
                {0, 0, 0, 0, -2, 0},
                {0, 0, 0, 0, 0, 0}
        });

        int dest = 7;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(dest + " is not in the graph.");
        adjMatrix.distTo(dest);
    }

//    @Test
//    public void given_6x6matrix_When_InvalidEdgeWeight_Then_ExceptionThrown() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(startsWith("Maximum allowed edge weight is"));
//
//        new AdjMatrixIn(1, 3, new int[][]{
//                {3, 1, -3, 0, 0, 0},
//                {0, 0, 3, 0, 0, -1},
//                {0, 0, 0, 0, 0, Integer.MAX_VALUE},
//                {0, 0, 3, 0, -2, 0},
//                {0, 0, 0, 0, -2, 0},
//                {0, 0, 0, 0, 0, 0}
//        });
//    }

    @Test
    public void given_EmptyMatrix_Then_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The matrix is empty.");

        new AdjMatrixIn(1, 1, new int[0][0]);
    }
}