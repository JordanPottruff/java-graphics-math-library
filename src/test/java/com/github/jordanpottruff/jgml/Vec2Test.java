package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vec2Test {
    private static final double ERROR_MARGIN = .001;

    private final Vec2 vecA = new Vec2(1.0, 2.0);
    private final Vec2 vecB = new Vec2(10.0, 10.0);

    @Test
    public void testAccessors() {
        assertEquals(1.0, vecA.x());
        assertEquals(2.0, vecA.y());
    }

    @Test
    public void testNormalize() {
        assertVectorsEqual(new Vec2(0.4472, 0.8944), vecA.normalize(), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        assertVectorsEqual(new Vec2(-1.0, -2.0), vecA.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        Vec2 vecSumAB = new Vec2(11.0, 12.0);
        assertVectorsEqual(vecSumAB, vecA.add(vecB), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        Vec2 vecDiffAB = new Vec2(-9.0, -8.0);
        assertVectorsEqual(vecDiffAB, vecA.subtract(vecB), ERROR_MARGIN);
    }
}
