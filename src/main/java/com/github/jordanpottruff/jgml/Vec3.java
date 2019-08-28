package com.github.jordanpottruff.jgml;

/**
 * A vector of dimension 3.
 */
public class Vec3 extends VecN implements CrossProductVec {

    /**
     * Constructs a Vec3 from the first three values of an array. The array must contain at least
     * three values. The order of the elements is preserved.
     *
     * @param array an array containing at least three elements.
     * @throws IllegalArgumentException if the array does not contain at least three elements.
     */
    public Vec3(double[] array) {
        super(array);
        Util.verifyMinimumDimension(array, 3);
    }

    /**
     * Constructs a Vec3 from the first three elements of a vector object. The vector must contain
     * at least three values.
     *
     * @param vec a vector containing at least three elements.
     * @throws IllegalArgumentException if the vector does not contain at least three elements.
     */
    public Vec3(Vec vec) {
        super(vec.toArray());
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
     * Returns the x and y elements as a new Vec2 object.
     *
     * @return the xy 2-dimensional vector.
     */
    public Vec2 xy() {
        return new Vec2(x(), y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 normalize() {
        return new Vec3(super.normalize().vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 scale(double scalar) {
        return new Vec3(super.scale(scalar).vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 invert() {
        return new Vec3(super.invert().vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 add(Vec vec) {
        return new Vec3(super.add(vec).vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 subtract(Vec vec) {
        return new Vec3(super.subtract(vec).vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec3 cross(CrossProductVec vec) {
        return new Vec3(Util.cross(vector, vec.toArray()));
    }

}
