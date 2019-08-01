package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec4Test {
    private static final double ERROR_MARGIN = .001;

    private static final Vec4 VEC_A = new Vec4(new double[]{1.0, 2.0, 3.0, 4.0});
    private static final Vec4 VEC_B = new Vec4(new double[]{10.0, 10.0, 10.0, 10.0});

    @Test
    public void testAccessors() {
        assertEquals(1.0, VEC_A.x());
        assertEquals(2.0, VEC_A.y());
        assertEquals(3.0, VEC_A.z());
        assertEquals(4.0, VEC_A.w());
    }

    @Test
    public void testNormalize() {
        assertVectorsEqual(new Vec4(0.1825, 0.3651, 0.5477, 0.7302), VEC_A.normalize(),
                ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        assertVectorsEqual(new Vec4(-1.0, -2.0, -3.0, -4.0), VEC_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Vec4 vecSumAB = new Vec4(11.0, 12.0, 13.0, 14.0);
        assertVectorsEqual(vecSumAB, VEC_A.add(VEC_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Vec4 vecDiffAB = new Vec4(-9.0, -8.0, -7.0, -6.0);
        assertVectorsEqual(vecDiffAB, VEC_A.subtract(VEC_B), ERROR_MARGIN);
    }

    @Test
    public void testCross() {
        Vec3 vecCrossAB = new Vec3(-10.0, 20.0, -10.0);
        assertVectorsEqual(vecCrossAB, VEC_A.cross(VEC_B), ERROR_MARGIN);
    }
}
