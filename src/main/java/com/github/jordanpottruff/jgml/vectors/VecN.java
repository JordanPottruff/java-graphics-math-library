package com.github.jordanpottruff.jgml.vectors;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The root of the hierarchy of concrete vector implementations. A VecN is a generalized vector of
 * any dimension N greater or equal to two.
 */
public class VecN implements Vec {

    private final double[] vector;

    /**
     * Constructs a VecN from an array of elements. The array must have at least two elements. The
     * order of the elements is preserved.
     *
     * @param array an array of elements.
     * @throws IllegalArgumentException if the array does not contain at least two elements.
     */
    public VecN(double[] array) {
        VecUtil.verifyMinimumDimension(array, 2);
        this.vector = array.clone();
    }

    /**
     * Creates a VecN from an iterable of elements. The iterable must contain two or more elements.
     * The order of the elements will be based on the order provided by the iterable.
     *
     * @param iterable an iterable of elements.
     * @return a new VecN composed of the iterable's elements.
     * @throws IllegalArgumentException if the iterable does not contain at least two items.
     */
    public static VecN createFrom(Iterable<Double> iterable) {
        ArrayList<Double> items = new ArrayList<>();
        for(double item: iterable) {
            items.add(item);
        }
        double[] vector = new double[items.size()];
        for(int i=0; i<vector.length; i++) {
            vector[i] = items.get(i);
        }
        return new VecN(vector);
    }

    /**
     * {@inheritDoc}
     */
    public double get(int i) {
        VecUtil.verifyValidCoord(vector, i);
        return vector[i];
    }

    /**
     * {@inheritDoc}
     */
    public int size() {
        return vector.length;
    }

    /**
     * {@inheritDoc}
     */
    public double magnitude() {
        return VecUtil.magnitude(vector);
    }

    /**
     * {@inheritDoc}
     */
    public VecN normalize() {
        return new VecN(VecUtil.normalize(vector));
    }

    /**
     * {@inheritDoc}
     */
    public VecN invert() {
        return new VecN(VecUtil.invert(vector));
    }

    /**
     * {@inheritDoc}
     */
    public VecN add(Vec vec) {
        return new VecN(VecUtil.add(vector, vec.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    public VecN subtract(Vec vec) {
        return new VecN(VecUtil.subtract(vector, vec.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    public double dot(Vec vec) {
        return VecUtil.multiply(vector, vec.toArray());
    }

    /**
     * {@inheritDoc}
     */
    public double[] toArray() {
        return vector.clone();
    }

    @Override
    public String toString() {
        return VecUtil.stringify(vector, 4);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof VecN)) {
            return false;
        }
        VecN vec = (VecN) obj;
        return Arrays.equals(vector, vec.toArray());
    }

    public boolean equals(Object obj, double error) {
        if(!(obj instanceof VecN)) {
            return false;
        }
        VecN vec = (VecN) obj;
        // Vectors of different dimensions cannot be equal.
        if(vec.size() != size()) {
            return false;
        }
        for(int i=0; i<size(); i++) {
            // Check if their corresponding components are equal within the given error.
            if(Math.abs(vec.vector[i] - vector[i]) > error) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }

}
