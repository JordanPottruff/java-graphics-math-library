package com.github.jordanpottruff.jgml;

/**
 * The definition of the core functionality for a generalized matrix.
 */
public interface Mat extends Iterable<Double> {

    /**
     * Returns the number of rows in the matrix.
     *
     * @return the number of rows.
     */
    int rows();

    /**
     * Returns the number of columns in the matrix.
     *
     * @return the number of columns.
     */
    int cols();

    /**
     * Returns the row at the specified 0-based index.
     *
     * @param i the index of the row.
     * @return the row at the given index.
     * @throws IllegalArgumentException if the index is out of range.
     */
    Vec getRow(int i);

    /**
     * Returns the column at the specified 0-based index.
     *
     * @param i the index of the column.
     * @return the column at the given index.
     * @throws IllegalArgumentException if the index is out of range.
     */
    Vec getCol(int i);

    /**
     * Returns the element at the specified 0-based coordinate.
     *
     * @param row the row index.
     * @param col the column index.
     * @return the element at (row,col)
     * @throws IllegalArgumentException if one of the indices is out of range.
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
     * @throws IllegalArgumentException if the matrix has different dimensions.
     */
    Mat add(Mat mat);

    /**
     * Calculates the difference of this matrix with the passed matrix.
     *
     * @param mat the matrix being subtracted.
     * @return the difference.
     * @throws IllegalArgumentException if the matrix has different dimensions.
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
     * @throws IllegalArgumentException if the dimension of the vector is not equal to the number
     * of columns in this matrix.
     */
    Vec multiply(Vec vec);

    /**
     * Calculates the multiplication of this matrix by the passed matrix.
     *
     * @param mat the matrix to be multiply by.
     * @return the product, always a matrix.
     * @throws IllegalArgumentException if the number of columns in this matrix is not equal to the
     * number of rows in the passed matrix.
     */
    Mat multiply(Mat mat);

    /**
     * Creates an array representation of the matrix.
     *
     * @return the matrix.
     */
    double[][] toArray();
}
