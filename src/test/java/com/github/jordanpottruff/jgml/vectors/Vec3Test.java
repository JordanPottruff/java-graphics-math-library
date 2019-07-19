package com.github.jordanpottruff.jgml.vectors;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.vectors.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec3Test {
    private static final double ERROR_MARGIN = .001;

    private final Vec3 vecA = new Vec3(1.0, 2.0, 3.0);
    private final Vec3 vecB = new Vec3(10.0, 10.0, 10.0);

    @Test
    public void testAccessors() {
        assertEquals(1.0, vecA.x());
        assertEquals(2.0, vecA.y());
        assertEquals(3.0, vecA.z());
    }

    @Test
    public void testNormalize() {
        assertVectorsEqual(new Vec3(0.2672, 0.5345, 0.8017), vecA.normalize(), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        assertVectorsEqual(new Vec3(-1.0, -2.0, -3.0), vecA.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Vec3 vecSumAB = new Vec3(11.0, 12.0, 13.0);
        assertVectorsEqual(vecSumAB, vecA.add(vecB), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Vec3 vecDiffAB = new Vec3(-9.0, -8.0, -7.0);
        assertVectorsEqual(vecDiffAB, vecA.subtract(vecB), ERROR_MARGIN);
    }

    @Test
    public void testCross() {
        Vec3 vecCrossAB = new Vec3(-10.0, 20.0, -10.0);
        assertVectorsEqual(vecCrossAB, vecA.cross(vecB), ERROR_MARGIN);
    }
}
