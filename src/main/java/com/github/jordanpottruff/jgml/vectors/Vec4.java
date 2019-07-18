package com.github.jordanpottruff.jgml.vectors;

import com.github.jordanpottruff.jgml.common.OperationsUtil;
import com.github.jordanpottruff.jgml.common.VerificationUtil;

/**
 * A vector of dimension 4.
 */
public class Vec4 extends VecN implements CrossProductVec {

    /**
     * Constructs a Vec4 from an array of elements. The array must have exactly four elements or an
     * IllegalArgumentException will be thrown.
     *
     * @param array an array of elements.
     */
    public Vec4(double[] array) {
        super(array);
        VerificationUtil.verifyExactDimension(array, 4);
    }

    /**
     * Constructs a Vec4 from four elements.
     *
     * @param x the x element.
     * @param y the y element.
     * @param z the z element.
     * @param w the w element.
     */
    public Vec4(double x, double y, double z, double w) {
        this(new double[]{x, y, z, w});
    }

    /**
     * Constructs a Vec4 from a Vec3 and a fourth element, w. The Vec3 represents the x, y, and z
     * elements for the vector.
     *
     * @param vec3 the x, y, and z elements.
     * @param w the w element.
     */
    public Vec4(Vec3 vec3, double w) {
        this(new double[]{vec3.x(), vec3.y(), vec3.z(), w});
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
     * Returns the w element.
     *
     * @return the w element.
     */
    public double w() {
        return get(3);
    }

    /**
     * Returns the normalized form of the vector.
     *
     * @return the normalized form.
     */
    @Override
    public Vec4 normalize() {
        return new Vec4(super.normalize().toArray());
    }

    /**
     * Returns the vector formed by inverting the sign of all the elements.
     *
     * @return the inverted vector.
     */
    @Override
    public Vec4 invert() {
        return new Vec4(super.invert().toArray());
    }

    /**
     * Calculates the sum of this vector with the passed vector.
     *
     * @param vec the vector being added.
     * @return the sum.
     */
    @Override
    public Vec4 add(Vec vec) {
        return new Vec4(super.add(vec).toArray());
    }

    /**
     * Calculates the difference of this vector with the passed vector.
     *
     * @param vec the vector being subtracted.
     * @return the difference.
     */
    @Override
    public Vec4 subtract(Vec vec) {
        return new Vec4(super.add(vec).toArray());
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
