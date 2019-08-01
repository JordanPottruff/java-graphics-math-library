package com.github.jordanpottruff.jgml;

/**
 * A vector of dimension 4.
 */
public class Vec4 extends VecN implements CrossProductVec {

    /**
     * Constructs a Vec4 from the first four values of an array. The array must contain at least
     * four values. The order of the elements is preserved.
     *
     * @param array an array containing at least four elements.
     * @throws IllegalArgumentException if the array does not contain at least four elements.
     */
    public Vec4(double[] array) {
        super(array);
        Util.verifyMinimumDimension(array, 4);
    }

    /**
     * Constructs a Vec4 from the first four elements of a vector object. The vector must contain
     * at least four values.
     *
     * @param vec a vector containing at least four elements.
     * @throws IllegalArgumentException if the vector does not contain at least four elements.
     */
    public Vec4(Vec vec) {
        super(vec.toArray());
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
     * Returns the x, y, and z elements as a new Vec3 object.
     *
     * @return the xyz 3-dimensional vector.
     */
    public Vec3 xyz() {
        return new Vec3(x(), y(), z());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec4 normalize() {
        return new Vec4(super.normalize().toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec4 invert() {
        return new Vec4(super.invert().vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec4 add(Vec vec) {
        return new Vec4(super.add(vec).vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec4 subtract(Vec vec) {
        return new Vec4(super.subtract(vec).vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 cross(CrossProductVec vec) {
        return new Vec3(Util.cross(vector, vec.toArray()));
    }

}
