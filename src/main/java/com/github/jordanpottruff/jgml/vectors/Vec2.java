package com.github.jordanpottruff.jgml.vectors;

/**
 * A vector of dimension 2.
 */
public class Vec2 extends VecN {

    /**
     * Constructs a Vec2 from an array of elements. The array must have exactly two elements or an
     * IllegalArgumentException will be thrown.
     *
     * @param array an array of elements.
     */
    public Vec2(double[] array) {
        super(array);
        VecUtil.verifyExactDimension(array, 2);
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
     * Returns the normalized form of the vector.
     *
     * @return the normalized form.
     */
    @Override
    public Vec2 normalize() {
        return new Vec2(super.normalize().toArray());
    }

    /**
     * Returns the vector formed by inverting the sign of all the elements.
     *
     * @return the inverted vector.
     */
    @Override
    public Vec2 invert() {
        return new Vec2(super.invert().toArray());
    }

    /**
     * Calculates the sum of this vector with the passed vector.
     *
     * @param vec the vector being added.
     * @return the sum.
     */
    @Override
    public Vec2 add(Vec vec) {
        return new Vec2(super.add(vec).toArray());
    }

    /**
     * Calculates the difference of this vector with the passed vector.
     *
     * @param vec the vector being subtracted.
     * @return the difference.
     */
    @Override
    public Vec2 subtract(Vec vec) {
        return new Vec2(super.subtract(vec).toArray());
    }

}
