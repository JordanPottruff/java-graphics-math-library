package com.github.jordanpottruff.jgml;

/**
 * A matrix whose dimensions are equal.
 */
public interface SquareMat extends Mat {

    /**
     * Calculates the determinant of the matrix.
     *
     * @return the determinant.
     */
    double determinant();

    /**
     * Calculates the inverse matrix.
     *
     * @return the inverse matrix.
     */
    SquareMat inverse();
}
