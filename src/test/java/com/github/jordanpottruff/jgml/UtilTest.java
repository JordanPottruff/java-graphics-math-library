package com.github.jordanpottruff.jgml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.github.jordanpottruff.jgml.Util.*;

public class UtilTest {
    // For comparing floating point values
    private static final double EPSILON = 0.001f;

    // General vectors
    private static final double[] VEC2_A = {-2.3f, 3.2f};
    private static final double[] VEC2_B = {1.0f, 2.0f};
    private static final double[] VEC3_A = {1.5f, 3.5f, 2.5f};
    private static final double[] VEC3_B = {-3.2f, 0.0f, 14.1f};
    private static final double[] VEC4_A = {-10.2f, 5.4f, 98.3f, 53.2f};
    private static final double[] VEC4_B = {432f, 3.3f, -2.2f, 5.6f};

    // Special vectors
    private static final double[] VEC4_ZERO = {0.0f, 0.0f, 0.0f, 0.0f};

    // General matrices
    private static final double[][] MAT2X2_A = {{1.0f, 2.3f}, {-1.5f, 0.5f}};
    private static final double[][] MAT2X2_B = {{3.2f, -1.1f}, {6.5f, -2.3f}};
    private static final double[][] MAT2X3_A = {{1.0f, 2.0f}, {3.0f, 4.0f}, {5.0f, 6.0f}};
    private static final double[][] MAT3X2_A = {{1.0f, 3.3f, 1.2f}, {-3.3f, 2.3f, 1.4f}};
    private static final double[][] MAT3X2_B = {{-1.0f, -2.0f, 3.0f}, {10.0f, 1.0f, 5.0f}};
    private static final double[][] MAT3X3_A = {{-3.7f, 8.2f, 2.9f}, {6.1f, 9.9f, -2.0f}, {4.5f,
            -3.3f, 3.5f}};

    // Special matrices
    private static final double[][] MAT2X2_IDENTITY = {{1f, 0f}, {0f, 1f}};
    private static final double[][] MAT_3X3_IDENTITY = {{1f, 0f, 0f}, {0f, 1f, 0f}, {0f, 0f, 1f}};
    private static final double[][] MAT2X2_NON_INVERTIBLE = {{1.0f, 2.0f}, {2.0f, 4.0f}};

    private boolean vecsEqual(double[] vecA, double[] vecB) {
        if (vecA.length != vecB.length) return false;
        for (int i = 0; i < vecA.length; i++) {
            if (Math.abs(vecA[i] - vecB[i]) > EPSILON) return false;
        }
        return true;
    }

    private boolean matsEqual(double[][] matA, double[][] matB) {
        if (matA.length != matB.length) return false;
        for (int i = 0; i < matA.length; i++) {
            if (!vecsEqual(matA[i], matB[i])) return false;
        }
        return true;
    }

    @Test
    public void testVecsEqual() {
        assertTrue(vecsEqual(VEC3_A, VEC3_A));
        assertFalse(vecsEqual(VEC3_A, VEC3_B)); // Different values.
        assertFalse(vecsEqual(VEC3_A, VEC4_A)); // Different dimensions.
    }

    @Test
    public void testMatsEqual() {
        assertTrue(matsEqual(MAT2X2_A, MAT2X2_A));
        assertFalse(matsEqual(MAT2X2_A, MAT2X2_B)); // Different values.
        assertFalse(matsEqual(MAT2X2_A, MAT3X2_A)); // Different dimensions.
    }

    @Test
    public void testMagnitude() {
        assertEquals(magnitude(VEC3_A), 4.555f, EPSILON);
        assertEquals(magnitude(VEC4_ZERO), 0.0f, EPSILON);
    }

    @Test
    public void testNormalize() {
        assertTrue(vecsEqual(normalize(VEC3_A), new double[]{0.329f, 0.768f, 0.548f}));
    }

    @Test
    public void testScale() {
        assertTrue(matsEqual(scale(MAT2X2_A, 3.0f), new double[][]{{3.0f, 6.9f}, {-4.5f, 1.5f}}));
    }

    @Test
    public void testDeterminant() {
        assertEquals(3.95f, determinant(MAT2X2_A), EPSILON);
        assertEquals(-540.226f, determinant(MAT3X3_A), EPSILON);
        assertThrows(IllegalArgumentException.class, () -> determinant(MAT3X2_A));
    }

    @Test
    public void testInverse() {
        assertTrue(matsEqual(inverse(MAT2X2_A), new double[][]{{0.126f, -0.582f}, {0.379f,
                0.253f}}));
        assertTrue(matsEqual(multiply(inverse(MAT2X2_A), MAT2X2_A), MAT2X2_IDENTITY));
        assertTrue(matsEqual(inverse(MAT_3X3_IDENTITY), MAT_3X3_IDENTITY));
        assertThrows(IllegalArgumentException.class, () -> inverse(MAT2X2_NON_INVERTIBLE));
    }

    @Test
    public void testInvert_vec() {
        assertTrue(vecsEqual(invert(VEC3_A), new double[]{-1.5f, -3.5f, -2.5f}));
    }

    @Test
    public void testInvert_mat() {
        assertTrue(matsEqual(invert(MAT2X2_A), new double[][]{{-1.0f, -2.3f}, {1.5f, -0.5f}}));
    }

    @Test
    public void testAdd_vec() {
        assertTrue(vecsEqual(add(VEC3_A, VEC3_B), new double[]{-1.7f, 3.5f, 16.6f}));
        assertTrue(vecsEqual(add(VEC4_A, VEC4_B), new double[]{421.8f, 8.7f, 96.1f, 58.8f}));
        assertThrows(IllegalArgumentException.class, () -> add(VEC3_A, VEC4_A));
    }

    @Test
    public void testAdd_mat() {
        assertTrue(matsEqual(add(MAT2X2_A, MAT2X2_B), new double[][]{{4.2f, 1.2f}, {5.0f, -1.8f}}));
        assertThrows(IllegalArgumentException.class, () -> add(MAT2X2_B, MAT3X2_A));
    }

    @Test
    public void testSubtract_vec() {
        assertTrue(vecsEqual(subtract(VEC3_A, VEC3_B), new double[]{4.7f, 3.5f, -11.6f}));
        assertTrue(vecsEqual(subtract(VEC4_A, VEC4_B), new double[]{-442.2f, 2.1f, 100.5f, 47.6f}));
        assertThrows(IllegalArgumentException.class, () -> subtract(VEC3_B, VEC4_B));
    }

    @Test
    public void testSubtract_mat() {
        assertTrue(matsEqual(subtract(MAT2X2_A, MAT2X2_B), new double[][]{{-2.2f, 3.4f}, {-8.0f,
                2.8f}}));
    }

    @Test
    public void testMultiply() {
        assertEquals(30.45f, multiply(VEC3_A, VEC3_B), EPSILON);
        assertEquals(-4306.92f, multiply(VEC4_A, VEC4_B), EPSILON);
        assertThrows(IllegalArgumentException.class, () -> multiply(VEC3_B, VEC4_B));
    }

    @Test
    public void testMultiply_vec() {
        assertTrue(vecsEqual(multiply(MAT2X2_A, VEC2_A), new double[]{-7.1f, -3.69f}));
    }

    @Test
    public void testMultiply_mat() {
        assertTrue(matsEqual(multiply(MAT2X2_A, MAT2X2_B), new double[][]{{4.85f, 6.81f}, {9.95f,
                13.8f}}));
    }

    @Test
    public void testCross() {
        assertTrue(vecsEqual(new double[]{49.35, -29.15, 11.2}, cross(VEC3_A, VEC3_B)));
        assertThrows(IllegalArgumentException.class, () -> cross(VEC2_A, VEC2_B));
    }

    @Test
    public void testStringify() {
        assertEquals("[-2.30]\n[ 3.20]", stringify(VEC2_A, 2));
        assertEquals("[1.500]\n[3.500]\n[2.500]", stringify(VEC3_A, 3));
    }

    @Test
    public void testVerifyExactDimension_vec() {
        verifyExactDimension(VEC2_A, 2);
        verifyExactDimension(VEC3_A, 3);
        verifyExactDimension(VEC4_A, 4);

        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(VEC2_A, 3));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(VEC3_A, 4));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(VEC4_A, 100));
    }

    @Test
    public void testVerifyExactDimension_mat() {
        // No exception expected -- test fails if one occurs.
        verifyExactDimension(MAT2X2_A, 2, 2);
        verifyExactDimension(MAT2X3_A, 2, 3);
        verifyExactDimension(MAT3X3_A, 3, 3);

        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(MAT2X2_A, 2, 3));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(MAT3X2_A, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(MAT3X3_A, 10000,
                10000));
    }

    @Test
    public void testMinimumDimension() {
        verifyMinimumDimension(VEC2_A, 2);
        verifyMinimumDimension(VEC3_A, 3);
        verifyMinimumDimension(VEC4_A, 1);

        assertThrows(IllegalArgumentException.class, () -> verifyMinimumDimension(VEC2_A, 3));
        assertThrows(IllegalArgumentException.class, () -> verifyMinimumDimension(VEC3_A, 10));
        assertThrows(IllegalArgumentException.class, () -> verifyMinimumDimension(VEC4_A, 5));
    }

    @Test
    public void testVerifyMinimumDimensions() {
        verifyMinimumDimensions(MAT2X2_A, 1, 1);
        verifyMinimumDimensions(MAT2X2_A, 2, 2);
        verifyMinimumDimensions(MAT3X2_A, 3, 2);

        assertThrows(IllegalArgumentException.class, () -> verifyMinimumDimensions(MAT2X2_A, 3, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyMinimumDimensions(MAT3X3_A, 4, 4));
        assertThrows(IllegalArgumentException.class, () -> verifyMinimumDimensions(MAT3X2_B, 3, 3));
    }

    @Test
    public void testVerifyEqualDimensions_vec() {
        // No exception expected -- test fails if one occurs.
        verifyEqualDimensions(VEC3_A, VEC3_B);
        verifyEqualDimensions(VEC4_A, VEC4_B);

        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(VEC3_A, VEC4_B));
        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(VEC2_A, VEC3_B));
    }

    @Test
    public void testVerifyEqualDimensions_mat() {
        // No exception expected -- test fails if one occurs.
        verifyEqualDimensions(MAT2X2_A, MAT2X2_B);
        verifyEqualDimensions(MAT3X2_A, MAT3X2_B);

        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(MAT2X2_A,
                MAT3X3_A));
        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(MAT2X2_B,
                MAT3X2_A));
    }

    @Test
    public void testVerifyValidCoord_vec() {
        // No exception expected -- test fails if one occurs.
        verifyValidCoord(VEC2_A, 0);
        verifyValidCoord(VEC3_A, 2);
        verifyValidCoord(VEC4_A, 3);

        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC2_A, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC3_A, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC4_A, 10000));
    }

    @Test
    public void testVerifyValidCoord_mat() {
        // No exception expected -- test fails if one occurs.
        verifyValidCoord(MAT2X2_A, 0, 0);
        verifyValidCoord(MAT3X3_A, 2, 2);
        verifyValidCoord(MAT2X3_A, 1, 2);

        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(MAT2X2_A, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(MAT3X2_A, 3, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(MAT3X3_A, 10, 10000));
    }

    @Test
    public void testVerifyOperableDimensions_vecMat() {
        // No exception expected -- test fails if one occurs.
        verifyOperableDimensions(MAT2X2_A, VEC2_A);
        verifyOperableDimensions(MAT3X3_A, VEC3_A);
        verifyOperableDimensions(MAT3X2_B, VEC2_A);

        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT2X2_A,
                VEC3_A));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X3_A,
                VEC2_A));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X2_A,
                VEC3_A));
    }

    @Test
    public void testVerifyOperableDimensions_mats() {
        // No exception expected -- test fails if one occurs.
        verifyOperableDimensions(MAT2X2_A, MAT2X2_B);
        verifyOperableDimensions(MAT2X3_A, MAT3X2_A);

        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X2_A,
                MAT3X2_B));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT2X2_A,
                MAT3X3_A));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X2_A,
                MAT3X2_A));
    }

    @Test
    public void testVerifyUniformMatrix() {
        // No exception expected -- test fails if one occurs.
        verifyUniformMatrix(MAT2X2_A);
        verifyUniformMatrix(MAT2X3_A);
        verifyUniformMatrix(MAT3X3_A);

        double[][] nonUniformMatrix = {{1.0f, 2.5f}, {3.0f, 4.0f, 5.0f}};
        assertThrows(IllegalArgumentException.class, () -> verifyUniformMatrix(nonUniformMatrix));
    }

    @Test
    public void testVerifySquareMatrix() {
        // No exception expected -- test fails if one occurs.
        verifySquareMatrix(MAT2X2_A);
        verifySquareMatrix(MAT3X3_A);

        assertThrows(IllegalArgumentException.class, () -> verifySquareMatrix(MAT2X3_A));
        assertThrows(IllegalArgumentException.class, () -> verifySquareMatrix(MAT3X2_A));
    }

    @Test
    public void testVerifyValidColumn() {
        // No exception expected -- test fails if one occurs.
        verifyValidColumn(MAT2X2_A, 0);
        verifyValidColumn(MAT3X3_A, 2);
        verifyValidColumn(MAT3X2_A, 1);

        assertThrows(IllegalArgumentException.class, () -> verifyValidColumn(MAT2X2_A, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidColumn(MAT3X2_A, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyValidColumn(MAT3X3_A, 10000));
    }

    @Test
    public void testVerifyValidRow() {
        // No exception expected -- test fails if one occurs.
        verifyValidRow(MAT2X2_A, 0);
        verifyValidRow(MAT3X3_A, 2);
        verifyValidRow(MAT3X2_A, 2);

        assertThrows(IllegalArgumentException.class, () -> verifyValidRow(MAT2X2_A, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidRow(MAT3X2_A, 3));
        assertThrows(IllegalArgumentException.class, () -> verifyValidRow(MAT3X3_A, 10000));
    }

    @Test
    public void testVerifyInvertibleMatrix() {
        double[][] invertibleMatrix = {{1.0f, 3.0f}, {-1.0f, 2.0f}};
        double[][] nonInvertibleMatrix = {{1.0f, 2.0f}, {2.0f, 4.0f}};

        // No exception expected -- test fails if one occurs.
        verifyInvertibleMatrix(invertibleMatrix);

        assertThrows(IllegalArgumentException.class, () -> verifyInvertibleMatrix(nonInvertibleMatrix));
    }

}
