package com.github.jordanpottruff.jgml.vectors;

import org.junit.jupiter.api.Test;

import static com.github.jordanpottruff.jgml.vectors.VecUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VecUtilTest {
    // For comparing floating point values
    private static final int PRECISION = 1000;
    private static final double EPSILON = 1.0/PRECISION;

    // General vectors
    private static final double[] VEC2_A = {-2.3f, 3.2f};
    private static final double[] VEC2_B = {1.0f, 2.0f};
    private static final double[] VEC3_A = {1.5f, 3.5f, 2.5f};
    private static final double[] VEC3_B = {-3.2f, 0.0f, 14.1f};
    private static final double[] VEC4_A = {-10.2f, 5.4f, 98.3f, 53.2f};
    private static final double[] VEC4_B = {432f, 3.3f, -2.2f, 5.6f};
    // Special vectors
    private static final double[] VEC4_ZERO = {0.0f, 0.0f, 0.0f, 0.0f};

    private boolean vecsEqual(double[] vecA, double[] vecB) {
        if(vecA.length != vecB.length) return false;
        for(int i=0; i<vecA.length; i++) {
            if(Math.abs(vecA[i] - vecB[i]) > EPSILON) return false;
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
    public void testMagnitude() {
        assertEquals(magnitude(VEC3_A), 4.555f, EPSILON);
        assertEquals(magnitude(VEC4_ZERO), 0.0f, EPSILON);
    }

    @Test
    public void testNormalize() {
        assertTrue(vecsEqual(normalize(VEC3_A), new double[]{0.329f, 0.768f, 0.548f}));
    }

    @Test
    public void testInvert() {
        assertTrue(vecsEqual(invert(VEC3_A), new double[]{-1.5f, -3.5f, -2.5f}));
    }

    @Test
    public void testAdd() {
        assertTrue(vecsEqual(add(VEC3_A, VEC3_B), new double[]{-1.7f, 3.5f, 16.6f}));
        assertTrue(vecsEqual(add(VEC4_A, VEC4_B), new double[]{421.8f, 8.7f, 96.1f, 58.8f}));
        assertThrows(IllegalArgumentException.class, () -> add(VEC3_A, VEC4_A));
    }

    @Test
    public void testSubtract() {
        assertTrue(vecsEqual(subtract(VEC3_A, VEC3_B), new double[]{4.7f, 3.5f, -11.6f}));
        assertTrue(vecsEqual(subtract(VEC4_A, VEC4_B), new double[]{-442.2f, 2.1f, 100.5f, 47.6f}));
        assertThrows(IllegalArgumentException.class, () -> subtract(VEC3_B, VEC4_B));
    }

    @Test
    public void testMultiply() {
        assertEquals(30.45f, multiply(VEC3_A, VEC3_B), EPSILON);
        assertEquals( -4306.92f, multiply(VEC4_A, VEC4_B), EPSILON);
        assertThrows(IllegalArgumentException.class, () -> multiply(VEC3_B, VEC4_B));
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
    public void testVerifyExactDimension() {
        verifyExactDimension(VEC2_A, 2);
        verifyExactDimension(VEC3_A, 3);
        verifyExactDimension(VEC4_A, 4);

        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(VEC2_A, 3));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(VEC3_A, 4));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(VEC4_A, 100));
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
    public void testVerifyEqualDimensions() {
        // No exception expected -- test fails if one occurs.
        verifyEqualDimensions(VEC3_A, VEC3_B);
        verifyEqualDimensions(VEC4_A, VEC4_B);

        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(VEC3_A, VEC4_B));
        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(VEC2_A, VEC3_B));
    }

    @Test
    public void testVerifyValidCoord() {
        // No exception expected -- test fails if one occurs.
        verifyValidCoord(VEC2_A, 0);
        verifyValidCoord(VEC3_A, 2);
        verifyValidCoord(VEC4_A, 3);

        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC2_A, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC3_A, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC4_A, 10000));
    }
}
