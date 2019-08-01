package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec3Test {
    private static final double ERROR_MARGIN = .001;

    private final Vec3 VEC_A = new Vec3(1.0, 2.0, 3.0);
    private final Vec3 VEC_B = new Vec3(10.0, 10.0, 10.0);

    @Test
    public void testAccessors() {
        assertEquals(1.0, VEC_A.x());
        assertEquals(2.0, VEC_A.y());
        assertEquals(3.0, VEC_A.z());
    }

    @Test
    public void testNormalize() {
        assertVectorsEqual(new Vec3(0.2672, 0.5345, 0.8017), VEC_A.normalize(), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        assertVectorsEqual(new Vec3(-1.0, -2.0, -3.0), VEC_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Vec3 vecSumAB = new Vec3(11.0, 12.0, 13.0);
        assertVectorsEqual(vecSumAB, VEC_A.add(VEC_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Vec3 vecDiffAB = new Vec3(-9.0, -8.0, -7.0);
        assertVectorsEqual(vecDiffAB, VEC_A.subtract(VEC_B), ERROR_MARGIN);
    }

    @Test
    public void testCross() {
        Vec3 vecCrossAB = new Vec3(-10.0, 20.0, -10.0);
        assertVectorsEqual(vecCrossAB, VEC_A.cross(VEC_B), ERROR_MARGIN);
    }
}
