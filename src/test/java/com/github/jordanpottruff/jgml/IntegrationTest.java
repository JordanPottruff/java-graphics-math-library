package com.github.jordanpottruff.jgml;

import org.junit.Test;

import static com.github.jordanpottruff.jgml.VecNTest.assertVectorsEqual;

public class IntegrationTest {
    private static final double ERROR_MARGIN = .001;

    @Test
    public void testRotateVec() {
        Vec4 ray = new Vec4(1.0, 0.0, 0.0, 0.0);
        Mat4 rotateQuarter = new Mat4.TransformBuilder().rotateZ(Math.PI/2.0).build();

        Vec4 rotateOnce = rotateQuarter.multiply(ray);
        Vec4 rotateTwice = rotateQuarter.multiply(rotateOnce);
        Vec4 rotateThrice = rotateQuarter.multiply(rotateTwice);
        Vec4 rotateFrice = rotateQuarter.multiply(rotateThrice);

        assertVectorsEqual(new Vec4(0.0, 1.0, 0.0, 0.0), rotateOnce, ERROR_MARGIN);
        assertVectorsEqual(new Vec4(-1.0, 0.0, 0.0, 0.0), rotateTwice, ERROR_MARGIN);
        assertVectorsEqual(new Vec4(0.0, -1.0, 0.0, 0.0), rotateThrice, ERROR_MARGIN);
        assertVectorsEqual(new Vec4(1.0, 0.0, 0.0, 0.0), rotateFrice, ERROR_MARGIN);
    }
}
