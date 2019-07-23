package com.github.jordanpottruff.jgml.matrices;

import com.github.jordanpottruff.jgml.vectors.Vec;

/**
 * The definition of the core functionality for a generalized matrix.
 */
public interface Mat {

    /**
     * Returns the row at the specified 0-based index.
     *
     * @param i the index of the row.
     * @return the row at the given index.
     */
    Vec getRow(int i);

    /**
     * Returns the column at the specified 0-based index.
     *
     * @param i the index of the column.
     * @return the column at the given index.
     */
    Vec getCol(int i);

    /**
     * Returns the element at the specified 0-based coordinate.
     *
     * @param row the row index.
     * @param col the column index.
     * @return the element at (row,col)
     */
    double get(int row, int col);

    /**
     * Calculates the matrix formed by inverting the sign of all the elements.
     *
     * @return the inverted matrix.
     */
    Mat invert();

    /**
     * Calculates the sum of this matrix with the passed matrix.
     *
     * @param mat the matrix being added.
     * @return the sum.
     */
    Mat add(Mat mat);

    /**
     * Calculates the difference of this matrix with the passed matrix.
     *
     * @param mat the matrix being subtracted.
     * @return the difference.
     */
    Mat subtract(Mat mat);

    /**
     * Calculates the matrix formed by scaling all the elements of this matrix by the scalar.
     *
     * @param scalar the multiplier to scale by.
     * @return the scaled matrix.
     */
    Mat scale(double scalar);

    /**
     * Calculates the multiplication of this matrix by the passed vector.
     *
     * @param vec the vector to multiply by.
     * @return the product, always a vector.
     */
    Vec multiply(Vec vec);

    /**
     * Calculates the multiplication of this matrix by the passed matrix.
     *
     * @param mat the matrix to be multiply by.
     * @return the product, always a matrix.
     */
    Mat multiply(Mat mat);
}
