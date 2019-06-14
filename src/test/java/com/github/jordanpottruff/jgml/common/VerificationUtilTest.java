package com.github.jordanpottruff.jgml.common;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.common.VerificationUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class VerificationUtilTest {

    // General vectors
    private static final double[] VEC2_A = {-2.3f, 3.2f};
    private static final double[] VEC3_A = {1.5f, 3.5f, 2.5f};
    private static final double[] VEC3_B = {-3.2f, 0.0f, 14.1f};
    private static final double[] VEC4_A = {-10.2f, 5.4f, 98.3f, 53.2f};
    private static final double[] VEC4_B = {432f, 3.3f, -2.2f, 5.6f};
    // General matrices
    private static final double[][] MAT2X2_A = {{1.0f, 2.3f}, {-1.5f, 0.5f}};
    private static final double[][] MAT2X2_B = {{3.2f, -1.1f}, {6.5f, -2.3f}};
    private static final double[][] MAT2X3_A = {{1.0f, 2.0f},{3.0f, 4.0f}, {5.0f, 6.0f}};
    private static final double[][] MAT3X2_A = {{1.0f, 3.3f, 1.2f},{-3.3f, 2.3f, 1.4f}};
    private static final double[][] MAT3X2_B = {{-1.0f, -2.0f, 3.0f},{10.0f,1.0f,5.0f}};
    private static final double[][] MAT3X3_A = {{-3.7f, 8.2f, 2.9f}, {6.1f, 9.9f, -2.0f}, {4.5f, -3.3f, 3.5f}};


    @Test
    public void testVerifyEqualDimensions_vectors() {
        // No exception expected -- test fails if one occurs.
        verifyEqualDimensions(VEC3_A, VEC3_B);
        verifyEqualDimensions(VEC4_A, VEC4_B);

        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(VEC3_A, VEC4_B));
        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(VEC2_A, VEC3_B));
    }

    @Test
    public void testVerifyEqualDimensions_matrices() {
        // No exception expected -- test fails if one occurs.
        verifyEqualDimensions(MAT2X2_A, MAT2X2_B);
        verifyEqualDimensions(MAT3X2_A, MAT3X2_B);

        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(MAT2X2_A, MAT3X3_A));
        assertThrows(IllegalArgumentException.class, () -> verifyEqualDimensions(MAT2X2_B, MAT3X2_A));

    }

    @Test
    public void testVerifyOperableDimensions_vectorMatrix() {
        // No exception expected -- test fails if one occurs.
        verifyOperableDimensions(MAT2X2_A, VEC2_A);
        verifyOperableDimensions(MAT3X3_A, VEC3_A);
        verifyOperableDimensions(MAT3X2_B, VEC2_A);

        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT2X2_A, VEC3_A));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X3_A, VEC2_A));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X2_A, VEC3_A));
    }

    @Test
    public void testVerifyOperableDimensions_matrices() {
        // No exception expected -- test fails if one occurs.
        verifyOperableDimensions(MAT2X2_A, MAT2X2_B);
        verifyOperableDimensions(MAT2X3_A, MAT3X2_A);

        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X2_A, MAT3X2_B));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT2X2_A, MAT3X3_A));
        assertThrows(IllegalArgumentException.class, () -> verifyOperableDimensions(MAT3X2_A, MAT3X2_A));
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
    public void testVerifyValidCoord_vectors() {
        // No exception expected -- test fails if one occurs.
        verifyValidCoord(VEC2_A, 0);
        verifyValidCoord(VEC3_A, 2);
        verifyValidCoord(VEC4_A, 3);

        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC2_A, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC3_A, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(VEC4_A, 10000));
    }

    @Test
    public void testVerifyValidCoord_matrices() {
        // No exception expected -- test fails if one occurs.
        verifyValidCoord(MAT2X2_A, 0, 0);
        verifyValidCoord(MAT3X3_A, 2, 2);
        verifyValidCoord(MAT2X3_A, 1, 2);

        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(MAT2X2_A, 2, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(MAT3X2_A, 3, -1));
        assertThrows(IllegalArgumentException.class, () -> verifyValidCoord(MAT3X3_A, 10, 10000));
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
    public void testVerifyExactDimension() {
        // No exception expected -- test fails if one occurs.
        verifyExactDimension(MAT2X2_A, 2, 2);
        verifyExactDimension(MAT2X3_A, 2, 3);
        verifyExactDimension(MAT3X3_A, 3, 3);

        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(MAT2X2_A, 2, 3));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(MAT3X2_A, -1, 2));
        assertThrows(IllegalArgumentException.class, () -> verifyExactDimension(MAT3X3_A, 10000, 10000));
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
