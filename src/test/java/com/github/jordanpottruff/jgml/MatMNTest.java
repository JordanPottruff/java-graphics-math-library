package com.github.jordanpottruff.jgml;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatMNTest {
    private static final double ERROR_MARGIN = .001;

    private static final MatMN MAT2X2_A = new MatMN(new double[][]{{1.0f, 2.3f}, {-1.5f, 0.5f}});
    private static final MatMN MAT2X2_B = new MatMN(new double[][]{{3.2f, -1.1f}, {6.5f, -2.3f}});
    private static final MatMN MAT2X3_A = new MatMN(new double[][]{{1.0f, 2.0f}, {3.0f, 4.0f},
            {5.0f, 6.0f}});
    private static final MatMN MAT3X2_A = new MatMN(new double[][]{{1.0f, 3.3f, 1.2f}, {-3.3f,
            2.3f, 1.4f}});

    private static final VecN VEC2_A = new VecN(new double[]{-2.3, 3.2});

    static void assertMatricesEqual(MatMN expected, MatMN actual, double error) {
        assertTrue(expected.equals(actual, error));
    }

    @Test
    public void testCreateFrom() {
        double[][] components = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};

        Vec vec1 = new VecN(new double[]{1.0, 2.0, 3.0});
        Vec vec2 = new VecN(new double[]{4.0, 5.0, 6.0});

        ArrayList<Vec> arrayList = new ArrayList<>();
        arrayList.add(vec1);
        arrayList.add(vec2);

        MatMN fromArray = new MatMN(components);
        MatMN fromIterable = MatMN.createFrom(arrayList);

        assertEquals(fromArray, fromIterable);
    }

    @Test
    public void testIterator() {
        double[][] components = {{1.0, 2.0}, {3.0, 4.0}};

        List<Double> expected = new ArrayList<>();
        expected.add(1.0);
        expected.add(2.0);
        expected.add(3.0);
        expected.add(4.0);

        List<Double> actual = new ArrayList<>();
        for(double elem: new MatMN(components)) {
            actual.add(elem);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testRows() {
        assertEquals(2, MAT2X2_A.rows());
        assertEquals(3, MAT3X2_A.rows());
    }

    @Test
    public void testCols() {
        assertEquals(2, MAT2X2_B.cols());
        assertEquals(3, MAT2X3_A.cols());
    }

    @Test
    public void testGetRow() {
        VecN row = new VecN(new double[]{1.0, 3.0, 5.0});
        assertVectorsEqual(row, MAT2X3_A.getRow(0), ERROR_MARGIN);
    }

    @Test
    public void testGetCol() {
        VecN col = new VecN(new double[]{1.0f, 2.0f});
        assertVectorsEqual(col, MAT2X3_A.getCol(0), ERROR_MARGIN);
    }

    @Test
    public void testGet() {
        assertEquals(3.3, MAT3X2_A.get(1, 0), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        MatMN inverted = new MatMN(new double[][]{{-1.0f, -2.3f}, {1.5f, -0.5f}});
        assertMatricesEqual(inverted, MAT2X2_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        MatMN sum = new MatMN(new double[][]{{4.2, 1.2}, {5.0, -1.8}});
        assertMatricesEqual(sum, MAT2X2_A.add(MAT2X2_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        MatMN diff = new MatMN(new double[][]{{-2.2, 3.4}, {-8.0, 2.8}});
        assertMatricesEqual(diff, MAT2X2_A.subtract(MAT2X2_B), ERROR_MARGIN);
    }

    @Test
    public void testScale() {
        MatMN scaled = new MatMN(new double[][]{{2.0, 4.6}, {-3.0, 1.0}});
        assertMatricesEqual(scaled, MAT2X2_A.scale(2.0), ERROR_MARGIN);
    }

    @Test
    public void testMultiply_vec() {
        VecN product = new VecN(new double[]{-7.1, -3.69});
        assertVectorsEqual(product, MAT2X2_A.multiply(VEC2_A), ERROR_MARGIN);
    }

    @Test
    public void testMultiply_mat() {
        MatMN product = new MatMN(new double[][]{{16.9, 22.4}, {10.6, 11}});
        assertMatricesEqual(product, MAT2X3_A.multiply(MAT3X2_A), ERROR_MARGIN);
    }

    @Test
    public void testToString() {
        String expected = "[1.000000][-1.500000]\n[2.300000][ 0.500000]";
        assertEquals(expected, MAT2X2_A.toString());
    }

    @Test
    public void testToString_decimals() {
        String expected = "[1.00][-1.50]\n[2.30][ 0.50]";
        assertEquals(expected, MAT2X2_A.toString(2));
    }
}
