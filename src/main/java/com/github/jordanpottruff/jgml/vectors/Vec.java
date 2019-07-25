package com.github.jordanpottruff.jgml.vectors;

/**
 * The definition of the core functionality for a generalized vector.
 */
public interface Vec {

    /**
     * Returns the element at the specified 0-based index.
     *
     * @param i the index of the element.
     * @return the element at the given index.
     * @throws IllegalArgumentException if the index is out of range.
     */
    double get(int i);

    /**
     * Returns the dimension/length of the vector.
     *
     * @return the dimension.
     */
    int size();

    /**
     * Calculates the magnitude of the vector.
     *
     * @return the magnitude.
     */
    double magnitude();

    /**
     * Calculates the normalized form of the vector.
     *
     * @return the normalized form.
     */
    Vec normalize();

    /**
     * Calculates the vector formed by inverting the sign of all the elements.
     *
     * @return the inverted vector.
     */
    Vec invert();

    /**
     * Calculates the sum of this vector with the passed vector.
     *
     * @param vec the vector being added.
     * @return the sum.
     * @throws IllegalArgumentException if the vector is of a different dimension (size).
     */
    Vec add(Vec vec);

    /**
     * Calculates the difference of this vector with the passed vector.
     *
     * @param vec the vector being subtracted.
     * @return the difference.
     * @throws IllegalArgumentException if the vector is of a different dimension (size).
     */
    Vec subtract(Vec vec);

    /**
     * Calculates the dot product of this vector with the passed vector.
     *
     * @param vec the vector being multiplied.
     * @return the dot product.
     * @throws IllegalArgumentException if the vector is of a different dimension (size).
     */
    double dot(Vec vec);

    /**
     * Creates an array representation of the vector.
     *
     * @return the array.
     */
    double[] toArray();

}
