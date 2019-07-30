package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec4Test {
    private static final double ERROR_MARGIN = .001;

    private final Vec4 vecA = new Vec4(new double[]{1.0, 2.0, 3.0, 4.0});
    private final Vec4 vecB = new Vec4(new double[]{10.0, 10.0, 10.0, 10.0});

    @Test
    public void testAccessors() {
        assertEquals(1.0, vecA.x());
        assertEquals(2.0, vecA.y());
        assertEquals(3.0, vecA.z());
        assertEquals(4.0, vecA.w());
    }

    @Test
    public void testNormalize() {
        assertVectorsEqual(new Vec4(0.1825, 0.3651, 0.5477, 0.7302), vecA.normalize(),
                ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        assertVectorsEqual(new Vec4(-1.0, -2.0, -3.0, -4.0), vecA.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Vec4 vecSumAB = new Vec4(11.0, 12.0, 13.0, 14.0);
        assertVectorsEqual(vecSumAB, vecA.add(vecB), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Vec4 vecDiffAB = new Vec4(-9.0, -8.0, -7.0, -6.0);
        assertVectorsEqual(vecDiffAB, vecA.subtract(vecB), ERROR_MARGIN);
    }

    @Test
    public void testCross() {
        Vec3 vecCrossAB = new Vec3(-10.0, 20.0, -10.0);
        assertVectorsEqual(vecCrossAB, vecA.cross(vecB), ERROR_MARGIN);
    }
}
