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

    @Test
    public void testPointAsVec() {
        // Points (vertices) will set the homogeneous coordinate w=1.
        Vec4 point = new Vec4(5.0, 0.0, 0.0, 1.0);
        Mat4 translate = new Mat4.TransformBuilder().translate(10.0, -5.0, 50.0).build();
        Mat4 scale = new Mat4.TransformBuilder().scale(10.0, 1.0, 1.0).build();

        Vec3 actual = scale.multiply(translate.multiply(point)).xyz();
        assertVectorsEqual(new Vec3(150.0, -5.0, 50.0), actual, ERROR_MARGIN);
    }

    @Test
    public void testRotateAroundPoint() {
        // To rotate one point around another point:
        //  - translate by the negation of the components of that point.
        //  - rotate.
        //  - translate back.
        Vec4 pointToRotate = new Vec4(100.0, -50.0, 0.0, 1.0);
        Vec3 pointToRotateAround = new Vec3(10.0, -10.0 , 0.0);
        Mat4 translate = new Mat4.TransformBuilder().translate(pointToRotateAround).build();
        Mat4 rotateHalf = new Mat4.TransformBuilder().rotateZ(Math.PI).build();

        // Easier to read, less verbose with parentheses.
        Vec4 actual1 = Mat4.chain(translate, rotateHalf, translate.inverse()).multiply(pointToRotate);
        // Harder to read, more verbose.
        Vec4 actual2 = translate.multiply(rotateHalf.multiply(translate.inverse().multiply(pointToRotate)));
        assertVectorsEqual(new Vec4(-80.0, 30.0, 0.0, 1.0), actual1, ERROR_MARGIN);
        assertVectorsEqual(actual1, actual2, ERROR_MARGIN);
    }
}
