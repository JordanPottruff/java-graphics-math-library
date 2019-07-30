package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.MatMNTest.assertMatricesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Mat3Test {
    private static final double ERROR_MARGIN = .001;

    private static final Mat3 MAT_A = new Mat3(new double[][]{{1.0, 2.0, 3.00}, {5.0, 6.0, 7.0},
            {9.0, 10.0, 11.0}});
    private static final Mat3 MAT_B = new Mat3(new double[][]{{5.0, 10.0, 15.0}, {25.0, 30.0,
            35.0}, {45.0, 50.0, 55.0}});
    private static final Mat3 MAT_C = new Mat3(new double[][]{{5.0, 1.0, 1.0}, {1.0, 5.0, 1.0},
            {1.0, 1.0, 5.0}});

    @Test
    public void testInvert() {
        Mat3 inverted = new Mat3(new double[][]{{-1.0, -2.0, -3.0}, {-5.0, -6.0, -7.0}, {-9.0,
                -10.0, -11.0}});
        assertMatricesEqual(inverted, MAT_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Mat3 sum = new Mat3(new double[][]{{6.0, 12.0, 18.0}, {30.0, 36.0, 42.0}, {54.0, 60.0,
                66.0}});
        assertMatricesEqual(sum, MAT_A.add(MAT_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Mat3 diff = new Mat3(new double[][]{{4.0, 8.0, 12.0}, {20.0, 24.0, 28.0}, {36.0, 40.0,
                44.0}});
        assertMatricesEqual(diff, MAT_B.subtract(MAT_A), ERROR_MARGIN);
    }

    @Test
    public void testScale() {
        assertMatricesEqual(MAT_B, MAT_A.scale(5.0), ERROR_MARGIN);
    }

    @Test
    public void testDeterminant() {
        assertEquals(0.0, MAT_A.determinant(), ERROR_MARGIN);
    }

    @Test
    public void testInverse() {
        Mat3 inverse = new Mat3(new double[][]{{0.2143, -0.0357, -0.0357}, {-0.0357, 0.2143,
                -0.0357}, {-0.0357, -0.0357, 0.2143}});
        assertMatricesEqual(inverse, MAT_C.inverse(), ERROR_MARGIN);
    }

    @Test
    public void testBuilder_scale() {
        Mat3 scaleMat = new Mat3.TransformBuilder().scale(5.0, 3.0).build();
        Mat3 expected = new Mat3(new double[][]{{5.0, 0.0, 0.0}, {0.0, 3.0, 0.0}, {0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, scaleMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_translate() {
        Mat3 translateMat = new Mat3.TransformBuilder().translate(10.0, -10.0).build();
        Mat3 expected = new Mat3(new double[][]{{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {10.0, -10.0,
                1.0}});
        assertMatricesEqual(expected, translateMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_rotate() {
        Mat3 rotateXMat = new Mat3.TransformBuilder().rotate(Math.PI / 2).build();
        Mat3 expected = new Mat3(new double[][]{{0.0, 1.0, 0.0}, {-1.0, 0.0, 0.0}, {0.0, 0.0,
                1.0}});
        assertMatricesEqual(expected, rotateXMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearX() {
        Mat3 shearXMat = new Mat3.TransformBuilder().shearX(1.0).build();
        Mat3 expected = new Mat3(new double[][]{{1.0, 0.0, 0.0}, {1.0, 1.0, 0.0}, {0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, shearXMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearY() {
        Mat3 shearYMat = new Mat3.TransformBuilder().shearY(1.0).build();
        Mat3 expected = new Mat3(new double[][]{{1.0, 1.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, shearYMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_chainOperations() {
        // Variety of operations
        Mat3 transformation =
                new Mat3.TransformBuilder().rotate(Math.PI / 4).scale(4.0, 2.0).translate(10.0,
                        -100.0).build();
        Mat3 expected = new Mat3(new double[][]{{2.8284, 1.4142, 0.0}, {-2.8284, 1.4142, 0.0},
                {10.0, -100.0, 1.0}});
        assertMatricesEqual(expected, transformation, ERROR_MARGIN);

        // Reverse operations
        Mat3 reverse =
                new Mat3.TransformBuilder().rotate(Math.PI / 4).scale(5.0, 1.0).scale(1.0 / 5.0,
                        1.0).rotate(-Math.PI / 4).build();
        assertMatricesEqual(Mat3.createIdentityMatrix(), reverse, ERROR_MARGIN);
    }
}
