package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.MatMNTest.assertMatricesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatNTest {
    private static final double ERROR_MARGIN = .001;

    private static final MatN MAT2_A = new MatN(new double[][]{{1.0, 2.0}, {3.0, 4.0}});
    private static final MatN MAT2_B = new MatN(new double[][]{{2.0, 3.0}, {4.0, 5.0}});

    @Test
    public void testInvert() {
        MatN inverted = new MatN(new double[][]{{-1.0, -2.0}, {-3.0, -4.0}});
        assertMatricesEqual(inverted, MAT2_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        MatN sum = new MatN(new double[][]{{3.0, 5.0}, {7.0, 9.0}});
        assertMatricesEqual(sum, MAT2_A.add(MAT2_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        MatN diff = new MatN(new double[][]{{-1.0, -1.0}, {-1.0, -1.0}});
        assertMatricesEqual(diff, MAT2_A.subtract(MAT2_B), ERROR_MARGIN);
    }

    @Test
    public void testScale() {
        MatN scaled = new MatN(new double[][]{{5.0, 10.0}, {15.0, 20.0}});
        assertMatricesEqual(scaled, MAT2_A.scale(5.0), ERROR_MARGIN);
    }

    @Test
    public void testDeterminant() {
        assertEquals(-2.0, MAT2_A.determinant(), ERROR_MARGIN);
    }

    @Test
    public void testInverse() {
        MatN inverse = new MatN(new double[][]{{-2.0, 1.0}, {1.5, -0.5}});
        assertMatricesEqual(inverse, MAT2_A.inverse(), ERROR_MARGIN);
    }
}
