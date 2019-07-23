package com.github.jordanpottruff.jgml.vectors;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VecNTest {
    private static final double ERROR_MARGIN = .001;

    private final VecN vec3A = new VecN(new double[]{1.0, 2.0, 3.0});
    private final VecN vec3B = new VecN(new double[]{-5.5, 10.0, -23.494});
    private final VecN vec4A = new VecN(new double[]{10.0, 100.0, 1000.0, 10000.0});
    private final VecN vec4B = new VecN(new double[]{-10000.0, -1000.0, -100.0, -10.0});

    static void assertVectorsEqual(VecN expected, VecN actual, double error) {
        assertTrue(expected.equals(actual, error));
    }

    @Test
    public void testCreateFrom() {
        double[] components = {1.0, -2.0, 101.35, 61303.3214};
        ArrayList<Double> arrayList = new ArrayList<>();
        for(double component: components) {
            arrayList.add(component);
        }

        VecN fromArray = new VecN(components);
        VecN fromIterable = VecN.createFrom(arrayList);

        assertEquals(fromArray, fromIterable);
    }

    @Test
    public void testGet() {
        assertEquals(1.0, vec3A.get(0), ERROR_MARGIN);
        assertEquals(-10.0, vec4B.get(3), ERROR_MARGIN);

        assertThrows(IllegalArgumentException.class, () -> vec3A.get(-1));
        assertThrows(IllegalArgumentException.class, () -> vec3A.get(4));
    }

    @Test
    public void testSize() {
        assertEquals(3, vec3A.size());
        assertEquals(4, vec4A.size());
    }

    @Test
    public void testMagnitude() {
        assertEquals(3.7416, vec3A.magnitude(), ERROR_MARGIN);
        assertEquals(10050.3781, vec4A.magnitude(), ERROR_MARGIN);
    }

    @Test
    public void testNormalize() {
        VecN vec3ANorm = new VecN(new double[]{0.2672, 0.5345, 0.8017});
        VecN vec4ANorm = new VecN(new double[]{0.0009, 0.0099, 0.0994, 0.9949});

        assertVectorsEqual(vec3ANorm, vec3A.normalize(), ERROR_MARGIN);
        assertVectorsEqual(vec4ANorm, vec4A.normalize(), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        VecN vec3AInv = new VecN(new double[]{-1.0, -2.0, -3.0});
        VecN vec4AInv = new VecN(new double[]{-10.0, -100.0, -1000.0, -10000.0});

        assertVectorsEqual(vec3AInv, vec3A.invert(), ERROR_MARGIN);
        assertVectorsEqual(vec4AInv, vec4A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        VecN vec3Sum = new VecN(new double[]{-4.5, 12.0, -20.494});
        VecN vec4Sum = new VecN(new double[]{-9990.0, -900.0, 900.0, 9990.0});

        assertVectorsEqual(vec3Sum, vec3A.add(vec3B), ERROR_MARGIN);
        assertVectorsEqual(vec3Sum, vec3B.add(vec3A), ERROR_MARGIN);
        assertVectorsEqual(vec4Sum, vec4A.add(vec4B), ERROR_MARGIN);
        assertVectorsEqual(vec4Sum, vec4B.add(vec4A), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        VecN vec3Diff = new VecN(new double[]{6.5, -8.0, 26.494});
        VecN vec4Diff = new VecN(new double[]{10010.0, 1100.0, 1100.0, 10010.0});

        assertVectorsEqual(vec3Diff, vec3A.subtract(vec3B), ERROR_MARGIN);
        assertVectorsEqual(vec4Diff, vec4A.subtract(vec4B), ERROR_MARGIN);
    }

    @Test
    public void testDot() {
        assertEquals(-55.982, vec3A.dot(vec3B), ERROR_MARGIN);
        assertEquals(-400000.0, vec4A.dot(vec4B), ERROR_MARGIN);
    }
}
