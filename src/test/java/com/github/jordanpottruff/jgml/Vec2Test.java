package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec2Test {
    private static final double ERROR_MARGIN = .001;

    private final Vec2 VEC_A = new Vec2(1.0, 2.0);
    private final Vec2 VEC_B = new Vec2(10.0, 10.0);

    @Test
    public void testAccessors() {
        assertEquals(1.0, VEC_A.x());
        assertEquals(2.0, VEC_A.y());
    }

    @Test
    public void testNormalize() {
        assertVectorsEqual(new Vec2(0.4472, 0.8944), VEC_A.normalize(), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        assertVectorsEqual(new Vec2(-1.0, -2.0), VEC_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Vec2 vecSumAB = new Vec2(11.0, 12.0);
        assertVectorsEqual(vecSumAB, VEC_A.add(VEC_B), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Vec2 vecDiffAB = new Vec2(-9.0, -8.0);
        assertVectorsEqual(vecDiffAB, VEC_A.subtract(VEC_B), ERROR_MARGIN);
    }
}
