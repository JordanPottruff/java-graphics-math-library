package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.MatMNTest.assertMatricesEqual;
import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Mat2Test {
    private static final double ERROR_MARGIN = .001;

    private static final Mat2 MAT_A = new Mat2(new double[][]{{1.0, 2.0}, {5.0, 6.0}});
    private static final Mat2 MAT_B = new Mat2(new double[][]{{5.0, 10.0}, {25.0, 30.0}});
    private static final Mat2 MAT_C = new Mat2(new double[][]{{5.0, 1.0}, {1.0, 5.0}});

    private static final Vec2 VEC_A = new Vec2(new double[]{1.0, 2.0});

    @Test
    public void testInvert() {
        Mat2 inverted = new Mat2(new double[][]{{-1.0, -2.0}, {-5.0, -6.0}});
        assertMatricesEqual(inverted, MAT_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Mat2 sum = new Mat2(new double[][]{{6.0, 12.0}, {30.0, 36.0}});
        assertMatricesEqual(sum, MAT_A.add(MAT_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Mat2 diff = new Mat2(new double[][]{{4.0, 8.0}, {20.0, 24.0}});
        assertMatricesEqual(diff, MAT_B.subtract(MAT_A), ERROR_MARGIN);
    }

    @Test
    public void testScale() {
        assertMatricesEqual(MAT_B, MAT_A.scale(5.0), ERROR_MARGIN);
    }
    
    @Test
    public void testMultiply_mat() {
        Mat2 product = new Mat2(new double[][]{{55.0, 70.0}, {175.0, 230.0}});
        assertMatricesEqual(product, MAT_A.multiply(MAT_B), ERROR_MARGIN);
    }

    @Test
    public void testMultiply_vec() {
        Vec2 product = new Vec2(new double[]{11.0, 14.0});
        System.out.println(MAT_A.multiply(VEC_A));
        assertVectorsEqual(product, MAT_A.multiply(VEC_A), ERROR_MARGIN);
    }

    @Test
    public void testDeterminant() {
        assertEquals(-4.0, MAT_A.determinant(), ERROR_MARGIN);
    }

    @Test
    public void testInverse() {
        Mat2 inverse = new Mat2(new double[][]{{0.2083, -0.0417}, {-0.0417, 0.2083}});
        assertMatricesEqual(inverse, MAT_C.inverse(), ERROR_MARGIN);
    }

    @Test
    public void testBuilder_scale() {
        Mat2 scaleMat = new Mat2.TransformBuilder().scale(5.0, 3.0).build();
        Mat2 expected = new Mat2(new double[][]{{5.0, 0.0}, {0.0, 3.0}});
        assertMatricesEqual(expected, scaleMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_rotate() {
        Mat2 rotateXMat = new Mat2.TransformBuilder().rotate(Math.PI / 2).build();
        Mat2 expected = new Mat2(new double[][]{{0.0, 1.0}, {-1.0, 0.0}});
        assertMatricesEqual(expected, rotateXMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearX() {
        Mat2 shearXMat = new Mat2.TransformBuilder().shearX(1.0).build();
        Mat2 expected = new Mat2(new double[][]{{1.0, 0.0}, {1.0, 1.0}});
        assertMatricesEqual(expected, shearXMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearY() {
        Mat2 shearYMat = new Mat2.TransformBuilder().shearY(1.0).build();
        Mat2 expected = new Mat2(new double[][]{{1.0, 1.0}, {0.0, 1.0}});
        assertMatricesEqual(expected, shearYMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_chainOperations() {
        // Variety of operations
        Mat2 transformation =
                new Mat2.TransformBuilder().rotate(Math.PI / 4).scale(4.0, 2.0).build();
        Mat2 expected = new Mat2(new double[][]{{2.8284, 1.4142}, {-2.8284, 1.4142}});
        assertMatricesEqual(expected, transformation, ERROR_MARGIN);

        // Reverse operations
        Mat2 reverse =
                new Mat2.TransformBuilder().rotate(Math.PI / 4).scale(5.0, 1.0).scale(1.0 / 5.0,
                        1.0).rotate(-Math.PI / 4).build();
        assertMatricesEqual(Mat2.createIdentityMatrix(), reverse, ERROR_MARGIN);
    }
}
