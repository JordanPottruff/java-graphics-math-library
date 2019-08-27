package com.github.jordanpottruff.jgml;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VecNTest {
    private static final double ERROR_MARGIN = .001;

    private static final VecN VEC3_A = new VecN(new double[]{1.0, 2.0, 3.0});
    private static final VecN VEC3_B = new VecN(new double[]{-5.5, 10.0, -23.494});
    private static final VecN VEC4_A = new VecN(new double[]{10.0, 100.0, 1000.0, 10000.0});
    private static final VecN VEC4_B = new VecN(new double[]{-10000.0, -1000.0, -100.0, -10.0});

    static void assertVectorsEqual(VecN expected, VecN actual, double error) {
        assertTrue(expected.equals(actual, error));
    }

    @Test
    public void testCreateFrom() {
        double[] components = {1.0, -2.0, 101.35, 61303.3214};
        ArrayList<Double> arrayList = new ArrayList<>();
        for (double component : components) {
            arrayList.add(component);
        }

        VecN fromArray = new VecN(components);
        VecN fromIterable = VecN.createFrom(arrayList);

        assertEquals(fromArray, fromIterable);
    }

    @Test
    public void testIterator() {
        List<Double> expected = new ArrayList<>();
        expected.add(1.0);
        expected.add(2.0);
        expected.add(3.0);

        List<Double> actual = new ArrayList<>();
        for(double elem: VEC3_A) {
            actual.add(elem);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testGet() {
        assertEquals(1.0, VEC3_A.get(0), ERROR_MARGIN);
        assertEquals(-10.0, VEC4_B.get(3), ERROR_MARGIN);

        assertThrows(IllegalArgumentException.class, () -> VEC3_A.get(-1));
        assertThrows(IllegalArgumentException.class, () -> VEC3_A.get(4));
    }

    @Test
    public void testSize() {
        assertEquals(3, VEC3_A.size());
        assertEquals(4, VEC4_A.size());
    }

    @Test
    public void testMagnitude() {
        assertEquals(3.7416, VEC3_A.magnitude(), ERROR_MARGIN);
        assertEquals(10050.3781, VEC4_A.magnitude(), ERROR_MARGIN);
    }

    @Test
    public void testNormalize() {
        VecN vec3ANorm = new VecN(new double[]{0.2672, 0.5345, 0.8017});
        VecN vec4ANorm = new VecN(new double[]{0.0009, 0.0099, 0.0994, 0.9949});

        assertVectorsEqual(vec3ANorm, VEC3_A.normalize(), ERROR_MARGIN);
        assertVectorsEqual(vec4ANorm, VEC4_A.normalize(), ERROR_MARGIN);
    }

    @Test
    public void testInvert() {
        VecN vec3AInv = new VecN(new double[]{-1.0, -2.0, -3.0});
        VecN vec4AInv = new VecN(new double[]{-10.0, -100.0, -1000.0, -10000.0});

        assertVectorsEqual(vec3AInv, VEC3_A.invert(), ERROR_MARGIN);
        assertVectorsEqual(vec4AInv, VEC4_A.invert(), ERROR_MARGIN);
    }

    @Test
    public void testScale() {
        VecN vec3AScaleByTwo = new VecN(new double[]{2.0, 4.0, 6.0});
        VecN vec4AScaleByTen = new VecN(new double[]{100.0, 1000.0, 10000.0, 100000.0});

        assertVectorsEqual(vec3AScaleByTwo, VEC3_A.scale(2.0), ERROR_MARGIN);
        assertVectorsEqual(vec4AScaleByTen, VEC4_A.scale(10.0), ERROR_MARGIN);
    }

    @Test
    public void testAdd() {
        VecN vec3Sum = new VecN(new double[]{-4.5, 12.0, -20.494});
        VecN vec4Sum = new VecN(new double[]{-9990.0, -900.0, 900.0, 9990.0});

        assertVectorsEqual(vec3Sum, VEC3_A.add(VEC3_B), ERROR_MARGIN);
        assertVectorsEqual(vec3Sum, VEC3_B.add(VEC3_A), ERROR_MARGIN);
        assertVectorsEqual(vec4Sum, VEC4_A.add(VEC4_B), ERROR_MARGIN);
        assertVectorsEqual(vec4Sum, VEC4_B.add(VEC4_A), ERROR_MARGIN);
    }

    @Test
    public void testSubtract() {
        VecN vec3Diff = new VecN(new double[]{6.5, -8.0, 26.494});
        VecN vec4Diff = new VecN(new double[]{10010.0, 1100.0, 1100.0, 10010.0});

        assertVectorsEqual(vec3Diff, VEC3_A.subtract(VEC3_B), ERROR_MARGIN);
        assertVectorsEqual(vec4Diff, VEC4_A.subtract(VEC4_B), ERROR_MARGIN);
    }

    @Test
    public void testDot() {
        assertEquals(-55.982, VEC3_A.dot(VEC3_B), ERROR_MARGIN);
        assertEquals(-400000.0, VEC4_A.dot(VEC4_B), ERROR_MARGIN);
    }

    @Test
    public void testToString() {
        String expected = "[1.000000]\n[2.000000]\n[3.000000]";
        assertEquals(expected, VEC3_A.toString());
    }

    @Test
    public void testToString_decimals() {
        String expected = "[1.00]\n[2.00]\n[3.00]";
        assertEquals(expected, VEC3_A.toString(2));
    }
}
