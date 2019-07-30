package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.MatMNTest.assertMatricesEqual;
import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Mat4Test {
    private static final double ERROR_MARGIN = .001;

    private static final Mat4 MAT_A = new Mat4(new double[][]{{1.0, 2.0, 3.0, 4.0}, {5.0, 6.0,
            7.0, 8.0}, {9.0, 10.0, 11.0, 12.0}, {13.0, 14.0, 15.0, 16.0}});
    private static final Mat4 MAT_B = new Mat4(new double[][]{{5.0, 10.0, 15.0, 20.0}, {25.0,
            30.0, 35.0, 40.0}, {45.0, 50.0, 55.0, 60.0}, {65.0, 70.0, 75.0, 80.0}});
    private static final Mat4 MAT_C = new Mat4(new double[][]{{5.0, 1.0, 1.0, 1.0}, {1.0, 5.0,
            1.0, 1.0}, {1.0, 1.0, 5.0, 1.0}, {1.0, 1.0, 1.0, 5.0}});

    private static final Vec4 VEC_A = new Vec4(new double[]{1.0, 2.0, 3.0, 4.0});

    @Test
    public void testInvert() {
        Mat4 inverted = new Mat4(new double[][]{{-1.0, -2.0, -3.0, -4.0}, {-5.0, -6.0, -7.0,
                -8.0}, {-9.0, -10.0, -11.0, -12.0}, {-13.0, -14.0, -15.0, -16.0}});
        assertMatricesEqual(inverted, MAT_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Mat4 sum = new Mat4(new double[][]{{6.0, 12.0, 18.0, 24.0}, {30.0, 36.0, 42.0, 48.0},
                {54.0, 60.0, 66.0, 72.0}, {78.0, 84.0, 90.0, 96.0}});
        assertMatricesEqual(sum, MAT_A.add(MAT_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Mat4 diff = new Mat4(new double[][]{{4.0, 8.0, 12.0, 16.0}, {20.0, 24.0, 28.0, 32.0},
                {36.0, 40.0, 44.0, 48.0}, {52.0, 56.0, 60.0, 64.0}});
        assertMatricesEqual(diff, MAT_B.subtract(MAT_A), ERROR_MARGIN);
    }

    @Test
    public void testScale() {
        assertMatricesEqual(MAT_B, MAT_A.scale(5.0), ERROR_MARGIN);
    }

    @Test
    public void testMultiply_mat() {
        Mat4 product = new Mat4(new double[][]{{450.0, 500.0, 550.0, 600.0}, {1010.0, 1140.0,
                1270.0, 1400.0}, {1570.0, 1780.0, 1990.0, 2200.0}, {2130.0, 2420.0, 2710.0,
                3000.0}});
        assertMatricesEqual(product, MAT_A.multiply(MAT_B), ERROR_MARGIN);
    }

    @Test
    public void testMultiply_vec() {
        Vec4 product = new Vec4(new double[]{90.0, 100.0, 110.0, 120.0});
        assertVectorsEqual(product, MAT_A.multiply(VEC_A), ERROR_MARGIN);
    }

    @Test
    public void testDeterminant() {
        assertEquals(0.0, MAT_A.determinant(), ERROR_MARGIN);
    }

    @Test
    public void testInverse() {
        Mat4 inverse = new Mat4(new double[][]{{0.2188, -0.0313, -0.0313, -0.0313}, {-0.0313,
                0.2188, -0.0313, -0.0313}, {-0.0313, -0.0313, 0.2188, -0.0313}, {-0.0313, -0.0313
                , -0.0313, 0.2188}});
        assertMatricesEqual(inverse, MAT_C.inverse(), ERROR_MARGIN);
    }

    @Test
    public void testBuilder_scale() {
        Mat4 scaleMat = new Mat4.TransformBuilder().scale(5.0, 3.0, 2.0).build();
        Mat4 expected = new Mat4(new double[][]{{5.0, 0.0, 0.0, 0.0}, {0.0, 3.0, 0.0, 0.0}, {0.0,
                0.0, 2.0, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, scaleMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_translate() {
        Mat4 translateMat = new Mat4.TransformBuilder().translate(10.0, -10.0, 25.0).build();
        Mat4 expected = new Mat4(new double[][]{{1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0,
                0.0, 1.0, 0.0}, {10.0, -10.0, 25.0, 1.0}});
        assertMatricesEqual(expected, translateMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_rotateX() {
        Mat4 rotateXMat = new Mat4.TransformBuilder().rotateX(Math.PI / 2).build();
        Mat4 expected = new Mat4(new double[][]{{1.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0,
                -1.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, rotateXMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_rotateY() {
        Mat4 rotateYMat = new Mat4.TransformBuilder().rotateY(Math.PI / 4).build();
        Mat4 expected = new Mat4(new double[][]{{0.7071, 0.0, -0.7071, 0.0}, {0.0, 1.0, 0.0, 0.0}
        , {0.7071, 0.0, 0.7071, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, rotateYMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_rotateZ() {
        Mat4 rotateZMat = new Mat4.TransformBuilder().rotateZ(-Math.PI / 2).build();
        Mat4 expected = new Mat4(new double[][]{{0.0, -1.0, 0.0, 0.0}, {1.0, 0.0, 0.0, 0.0}, {0.0
                , 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, rotateZMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearX() {
        Mat4 shearXMat = new Mat4.TransformBuilder().shearX(1.0, 2.0).build();
        Mat4 expected = new Mat4(new double[][]{{1.0, 0.0, 0.0, 0.0}, {1.0, 1.0, 0.0, 0.0}, {2.0,
                0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, shearXMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearY() {
        Mat4 shearYMat = new Mat4.TransformBuilder().shearY(1.0, 2.0).build();
        Mat4 expected = new Mat4(new double[][]{{1.0, 1.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0,
                2.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, shearYMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_shearZ() {
        Mat4 shearZMat = new Mat4.TransformBuilder().shearZ(1.0, 2.0).build();
        Mat4 expected = new Mat4(new double[][]{{1.0, 0.0, 1.0, 0.0}, {0.0, 1.0, 2.0, 0.0}, {0.0,
                0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}});
        assertMatricesEqual(expected, shearZMat, ERROR_MARGIN);
    }

    @Test
    public void testBuilder_chainOperations() {
        // Variety of operations
        Mat4 transformation = new Mat4.TransformBuilder().rotateX(Math.PI / 2).scale(4.0, 2.0,
                0.0).translate(10.0, -100.0, 0.0).build();
        Mat4 expected = new Mat4(new double[][]{{4.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0,
                -2.0, 0.0, 0.0}, {10.0, -100.0, 0.0, 1.0}});
        assertMatricesEqual(expected, transformation, ERROR_MARGIN);

        // Reverse operations
        Mat4 reverse =
                new Mat4.TransformBuilder().rotateX(Math.PI / 4).scale(5.0, 1.0, 1.0).scale(1.0 / 5.0, 1.0, 1.0).rotateX(-Math.PI / 4).build();
        assertMatricesEqual(Mat4.createIdentityMatrix(), reverse, ERROR_MARGIN);
    }

}
