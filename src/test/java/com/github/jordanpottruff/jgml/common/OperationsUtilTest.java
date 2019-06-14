package com.github.jordanpottruff.jgml.common;

import org.junit.jupiter.api.Test;

import static com.github.jordanpottruff.jgml.common.OperationsUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperationsUtilTest {
    // For comparing doubleing point values
    private static final double EPSILON = 0.001f;

    // General vectors
    private static final double[] VEC2_A = {-2.3f, 3.2f};
    private static final double[] VEC3_A = {1.5f, 3.5f, 2.5f};
    private static final double[] VEC3_B = {-3.2f, 0.0f, 14.1f};
    private static final double[] VEC4_A = {-10.2f, 5.4f, 98.3f, 53.2f};
    private static final double[] VEC4_B = {432f, 3.3f, -2.2f, 5.6f};
    // Special vectors
    private static final double[] VEC4_ZERO = {0.0f, 0.0f, 0.0f, 0.0f};
    // General matrices
    private static final double[][] MAT2X2_A = {{1.0f, 2.3f}, {-1.5f, 0.5f}};
    private static final double[][] MAT2X2_B = {{3.2f, -1.1f}, {6.5f, -2.3f}};
    private static final double[][] MAT3X2_A = {{1.0f, 3.3f, 1.2f},{-3.3f, 2.3f, 1.4f}};
    private static final double[][] MAT3X3_A = {{-3.7f, 8.2f, 2.9f}, {6.1f, 9.9f, -2.0f}, {4.5f, -3.3f, 3.5f}};
    // Special matrices
    private static final double[][] MAT2X2_IDENTITY = {{1f, 0f}, {0f, 1f}};
    private static final double[][] MAT_3X3_IDENTITY = {{1f, 0f, 0f}, {0f, 1f, 0f}, {0f, 0f, 1f}};
    private static final double[][] MAT2X2_NON_INVERTIBLE = {{1.0f, 2.0f}, {2.0f, 4.0f}};


    private boolean vecsEqual(double[] vecA, double[] vecB) {
        if(vecA.length != vecB.length) return false;
        for(int i=0; i<vecA.length; i++) {
            if(Math.abs(vecA[i] - vecB[i]) > EPSILON) return false;
        }
        return true;
    }

    private boolean matsEqual(double[][] matA, double[][] matB) {
        if(matA.length != matB.length) return false;
        for(int i=0; i<matA.length; i++) {
            if(!vecsEqual(matA[i], matB[i])) return false;
        }
        return true;
    }

    // TESTING OF TEST METHODS

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

    // TESTING OF OPERATION METHODS

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
    public void testInvertVec() {
        assertTrue(vecsEqual(invert(VEC3_A), new double[]{-1.5f, -3.5f, -2.5f}));
    }

    @Test
    public void testAddVecs() {
        assertTrue(vecsEqual(add(VEC3_A, VEC3_B), new double[]{-1.7f, 3.5f, 16.6f}));
        assertTrue(vecsEqual(add(VEC4_A, VEC4_B), new double[]{421.8f, 8.7f, 96.1f, 58.8f}));
        assertThrows(IllegalArgumentException.class, () -> add(VEC3_A, VEC4_A));
    }

    @Test
    public void testSubtractVecs() {
        assertTrue(vecsEqual(subtract(VEC3_A, VEC3_B), new double[]{4.7f, 3.5f, -11.6f}));
        assertTrue(vecsEqual(subtract(VEC4_A, VEC4_B), new double[]{-442.2f, 2.1f, 100.5f, 47.6f}));
        assertThrows(IllegalArgumentException.class, () -> subtract(VEC3_B, VEC4_B));
    }

    @Test
    public void testMultiplyVecs() {
        assertEquals(multiply(VEC3_A, VEC3_B), 30.45f, EPSILON);
        assertEquals(multiply(VEC4_A, VEC4_B), -4306.92f, EPSILON);
        assertThrows(IllegalArgumentException.class, () -> multiply(VEC3_B, VEC4_B));
    }

    @Test
    public void testInvertMat() {
        assertTrue(matsEqual(invert(MAT2X2_A), new double[][]{{-1.0f, -2.3f}, {1.5f, -0.5f}}));
    }

    @Test
    public void testAddMats() {
        assertTrue(matsEqual(add(MAT2X2_A, MAT2X2_B), new double[][]{{4.2f, 1.2f}, {5.0f, -1.8f}}));
        assertThrows(IllegalArgumentException.class, () -> add(MAT2X2_B, MAT3X2_A));
    }

    @Test
    public void testSubtractMats() {
        assertTrue(matsEqual(subtract(MAT2X2_A, MAT2X2_B), new double[][]{{-2.2f, 3.4f}, {-8.0f, 2.8f}}));
    }

    @Test
    public void testScaleMat() {
        assertTrue(matsEqual(scale(MAT2X2_A, 3.0f), new double[][]{{3.0f, 6.9f}, {-4.5f, 1.5f}}));
    }

    @Test
    public void testMatVecMult() {
        assertTrue(vecsEqual(multiply(MAT2X2_A, VEC2_A), new double[]{-7.1f, -3.69f}));
    }

    @Test
    public void testMatMult() {
        assertTrue(matsEqual(multiply(MAT2X2_A, MAT2X2_B), new double[][]{{4.85f, 6.81f}, {9.95f, 13.8f}}));
    }

    @Test
    public void testMatSubmatrix() {
        assertTrue(matsEqual(submatrix(MAT3X2_A, 0, 0), new double[][]{{2.3f, 1.4f}}));
        assertTrue(matsEqual(submatrix(MAT3X2_A, 2, 1), new double[][]{{1.0f, 3.3f}}));

    }

    @Test
    public void testDeterminant() {
        assertEquals(determinant(MAT2X2_A), 3.95f, EPSILON);
        assertEquals(determinant(MAT3X3_A), -540.226f, EPSILON);
        assertThrows(IllegalArgumentException.class, () -> determinant(MAT3X2_A));
    }

    @Test
    public void testInverse() {
        assertTrue(matsEqual(inverse(MAT2X2_A), new double[][]{{0.126f, -0.582f}, {0.379f, 0.253f}}));
        assertTrue(matsEqual(multiply(inverse(MAT2X2_A), MAT2X2_A), MAT2X2_IDENTITY));
        assertTrue(matsEqual(inverse(MAT_3X3_IDENTITY), MAT_3X3_IDENTITY));
        assertThrows(IllegalArgumentException.class, () -> inverse(MAT2X2_NON_INVERTIBLE));
    }
}
