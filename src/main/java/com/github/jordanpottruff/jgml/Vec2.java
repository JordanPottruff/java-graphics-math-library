package com.github.jordanpottruff.jgml;

/**
 * A vector of dimension 2.
 */
public class Vec2 extends VecN {

    /**
     * Constructs a Vec2 from the first two values of an array. The array must contain at least two
     * values. The order of the elements is preserved.
     *
     * @param array an array containing at least two elements.
     * @throws IllegalArgumentException if the array does not contain at least two elements.
     */
    public Vec2(double[] array) {
        super(array);
        VecUtil.verifyMinimumDimension(array, 2);
    }

    /**
     * Constructs a Vec2 from the first two elements of a vector object. The vector must contain at
     * least two values.
     *
     * @param vec a vector containing at least two elements.
     * @throws IllegalArgumentException if the vector does not contain at least two elements.
     */
    public Vec2(Vec vec) {
        super(vec.toArray());
    }

    /**
     * Constructs a Vec2 from two elements.
     *
     * @param x the x element.
     * @param y the y element.
     */
    public Vec2(double x, double y) {
        this(new double[]{x, y});
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
     * {@inheritDoc}
     */
    @Override
    public Vec2 normalize() {
        return new Vec2(super.normalize().toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec2 invert() {
        return new Vec2(super.invert().toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec2 add(Vec vec) {
        return new Vec2(super.add(vec).toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec2 subtract(Vec vec) {
        return new Vec2(super.subtract(vec).toArray());
    }

}
