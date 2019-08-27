package com.github.jordanpottruff.jgml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The root of the hierarchy of concrete vector implementations. A VecN is a generalized vector of
 * any dimension N greater or equal to two.
 */
public class VecN implements Vec {

    final double[] vector;

    /**
     * Constructs a VecN from an array of elements. The array must have at least two elements. The
     * order of the elements is preserved.
     *
     * @param array an array of elements.
     * @throws IllegalArgumentException if the array does not contain at least two elements.
     */
    public VecN(double[] array) {
        Util.verifyMinimumDimension(array, 2);
        this.vector = array.clone();
    }

    /**
     * Constructs a VecN from the elements of a vector object. The order of the elements is
     * preserved.
     *
     * @param vec a vector object.
     * @throws IllegalArgumentException if the vector does not contain at least two elements.
     */
    public VecN(Vec vec) {
        this(vec.toArray());
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
        for (double item : iterable) {
            items.add(item);
        }
        double[] vector = new double[items.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = items.get(i);
        }
        return new VecN(vector);
    }

    /**
     * Returns an iterator over the elements in this vector in proper sequence.
     *
     * @return an iterator over the elements in this vector.
     */
    public Iterator<Double> iterator() {
        return Arrays.stream(vector).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double get(int i) {
        Util.verifyValidCoord(vector, i);
        return vector[i];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return vector.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double magnitude() {
        return Util.magnitude(vector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN normalize() {
        return new VecN(Util.normalize(vector));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN invert() {
        return new VecN(Util.invert(vector));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN scale(double scalar) {
        return new VecN(Util.scale(vector, scalar));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN add(Vec vec) {
        return new VecN(Util.add(vector, vec.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN subtract(Vec vec) {
        return new VecN(Util.subtract(vector, vec.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double dot(Vec vec) {
        return Util.multiply(vector, vec.toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] toArray() {
        return vector.clone();
    }

    @Override
    public String toString() {
        return Util.stringify(vector);
    }

    /**
     * Returns a string representation of the vector where each element is limited to a specific
     * number of decimals.
     *
     * @param decimals the number of decimals each element should have.
     * @return the string representation.
     * @throws IllegalArgumentException if the specified decimal amount is less than zero.
     */
    public String toString(int decimals) {
        return Util.stringify(vector, decimals);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        VecN vec = (VecN) obj;
        return Arrays.equals(vector, vec.toArray());
    }

    public boolean equals(Object obj, double error) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        VecN vec = (VecN) obj;
        // Vectors of different dimensions cannot be equal.
        if (vec.size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            // Check if their corresponding components are equal within the given error.
            if (Math.abs(vec.vector[i] - vector[i]) > error) {
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
