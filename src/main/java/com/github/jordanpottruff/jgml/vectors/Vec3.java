package com.github.jordanpottruff.jgml.vectors;

import com.github.jordanpottruff.jgml.common.OperationsUtil;
import com.github.jordanpottruff.jgml.common.VerificationUtil;

/**
 * A vector of dimension 3.
 */
public class Vec3 extends VecN implements CrossProductVec {

    /**
     * Constructs a Vec3 from an array of elements. The array must have exactly three elements or an
     * IllegalArgumentException will be thrown.
     *
     * @param array an array of elements.
     */
    public Vec3(double[] array) {
        super(array);
        VerificationUtil.verifyExactDimension(array, 3);
    }

    /**
     * Constructs a Vec3 from three elements.
     *
     * @param x the x element.
     * @param y the y element.
     * @param z the z element.
     */
    public Vec3(double x, double y, double z) {
        this(new double[]{x, y, z});
    }

    /**
     * Constructs a Vec3 from a Vec2 and a third element, z. The Vec2 represents the x and y
     * elements for the vector.
     *
     * @param vec2 the x, y, and z elements.
     * @param z the w element.
     */
    public Vec3(Vec2 vec2, double z) {
        this(new double[]{vec2.x(), vec2.y(), z});
    }

    /**
     * Returns the x element.
     *
     * @return the x element.
     */
    public double x() {
        return get(0);
    }

    /**
     * Returns the y element.
     *
     * @return the y element.
     */
    public double y() {
        return get(1);
    }

    /**
     * Returns the z element.
     *
     * @return the z element.
     */
    public double z() {
        return get(2);
    }

    /**
     * Returns the normalized form of the vector.
     *
     * @return the normalized form.
     */
    @Override
    public Vec3 normalize() {
        return new Vec3(super.normalize().toArray());
    }

    /**
     * Returns the vector formed by inverting the sign of all the elements.
     *
     * @return the inverted vector.
     */
    @Override
    public Vec3 invert() {
        return new Vec3(super.invert().toArray());
    }

    /**
     * Calculates the sum of this vector with the passed vector.
     *
     * @param vec the vector being added.
     * @return the sum.
     */
    @Override
    public Vec3 add(Vec vec) {
        return new Vec3(super.add(vec).toArray());
    }

    /**
     * Calculates the difference of this vector with the passed vector.
     *
     * @param vec the vector being subtracted.
     * @return the difference.
     */
    @Override
    public Vec3 subtract(Vec vec) {
        return new Vec3(super.subtract(vec).toArray());
    }

    /**
     * Calculates the cross product of this vector with the passed vector.
     *
     * @param vec the vector being multiplied.
     * @return the cross product.
     */
    @Override
    public Vec3 cross(CrossProductVec vec) {
        return new Vec3(OperationsUtil.cross(toArray(), vec.toArray()));
    }

}
