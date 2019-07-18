package com.github.jordanpottruff.jgml.vectors;

import com.github.jordanpottruff.jgml.common.OperationsUtil;
import com.github.jordanpottruff.jgml.common.VerificationUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The root of the hierarchy of concrete vector implementations. A VecN is a generalized vector of
 * any dimension N greater or equal to 2.
 */
public class VecN implements Vec {

    private final double[] vector;

    /**
     * Constructs a VecN from an array of elements. The array must have at least two elements or an
     * IllegalArgumentException will be thrown.
     *
     * @param array an array of elements.
     */
    public VecN(double[] array) {
        VerificationUtil.verifyMinimumLength(array, 2);
        this.vector = array;
    }

    /**
     * Creates a VecN from an iterable of elements. The iterable must contain two or more
     * elements or else an IllegalArgumentException will be thrown. The VecN's elements will follow
     * the order provided by the iterable.
     *
     * @param iterable an iterable of elements.
     * @return a new VecN composed of the iterable's elements.
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
     * Returns the element at the specified 0-based index.
     *
     * @param i the index of the element.
     * @return the element at the given index.
     */
    public double get(int i) {
        VerificationUtil.verifyValidCoord(vector, i);
        return vector[i];
    }

    /**
     * Returns the size/length/dimension of the vector.
     *
     * @return the vector's size/length/dimension.
     */
    public double size() {
        return vector.length;
    }

    /**
     * Returns the magnitude of the vector.
     *
     * @return the magnitude.
     */
    public double magnitude() {
        return OperationsUtil.magnitude(vector);
    }

    /**
     * Returns the normalized form of the vector.
     *
     * @return the normalized form.
     */
    public VecN normalize() {
        return new VecN(OperationsUtil.normalize(vector));
    }

    /**
     * Returns the vector formed by inverting the sign of all the elements.
     *
     * @return the inverted vector.
     */
    public VecN invert() {
        return new VecN(OperationsUtil.invert(vector));
    }

    /**
     * Calculates the sum of this vector with the passed vector.
     *
     * @param vec the vector being added.
     * @return the sum.
     */
    public VecN add(Vec vec) {
        return new VecN(OperationsUtil.add(vector, vec.toArray()));
    }

    /**
     * Calculates the difference of this vector with the passed vector.
     *
     * @param vec the vector being subtracted.
     * @return the difference.
     */
    public VecN subtract(Vec vec) {
        return new VecN(OperationsUtil.subtract(vector, vec.toArray()));
    }

    /**
     * Calculates the dot product of this vector with the passed vector.
     *
     * @param vec the vector being multiplied.
     * @return the dot product.
     */
    public double dot(Vec vec) {
        return OperationsUtil.multiply(vector, vec.toArray());
    }

    /**
     * Creates an array representation of the vector.
     *
     * @return the array.
     */
    public double[] toArray() {
        return vector.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(vector);
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
        // Vectors of different sizes cannot be equal.
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
