package com.github.jordanpottruff.jgml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The root of the hierarchy of concrete matrix implementations. A MatMN is a generalized matrix of
 * any dimensions M x N where M and N are both greater than or equal to two.
 */
public class MatMN implements Mat {

    final double[][] matrix;

    /**
     * Constructs a MatMN from a two-dimensional array of elements. The outer-array must contain two
     * or more inner-arrays, with each being the same length (uniform) and containing at least two
     * elements. The inner-arrays will be the column vectors of the new MatMN object. The order of
     * the 2D array is preserved.
     *
     * @param array the 2D array of elements.
     * @throws IllegalArgumentException if the inner-arrays are not of equal length (non-uniform).
     * @throws IllegalArgumentException if the outer-array is not of length two or greater or if the
     *                                  inner-arrays are not of length two or greater (MatMN must
     *                                  be of dimension 2x2 or larger).
     */
    public MatMN(double[][] array) {
        Util.verifyUniformMatrix(array);
        Util.verifyMinimumDimensions(array, 2, 2);
        matrix = arrayCopy(array);
    }

    /**
     * Constructs a MatMN from a matrix object. The order of the elements will be preserved. The
     * matrix must be of dimension 2x2 or larger.
     *
     * @param mat the matrix object.
     * @throws IllegalArgumentException if the matrix object is not of dimension 2x2 or larger.
     */
    public MatMN(Mat mat) {
        this(mat.toArray());
    }

    /**
     * Creates a MatMN from an iterable of vectors. The iterable must contain at least two vectors,
     * each of the same dimension and containing at least two elements. These vectors will be the
     * column vectors of the new MatMN object. The order of the column vectors will be based on the
     * order provided by the iterable. The order of the elements within the column vectors will be
     * preserved.
     *
     * @param vectors the iterable of column vectors.
     * @return a new MatMN composed of the iterable's column vectors.
     * @throws IllegalArgumentException if the vectors are not of equal dimension (non-uniform).
     * @throws IllegalArgumentException if the iterable does not contain at least two vectors or if
     *                                  the vectors are not of dimension 2 or greater (MatMN must
     *                                  be of dimension 2x2 or greater).
     */
    public static MatMN createFrom(Iterable<Vec> vectors) {
        int n = 0;
        for (Vec vec : vectors) {
            n++;
        }

        double[][] matrix = new double[n][];

        int c = 0;
        for (Vec vec : vectors) {
            matrix[c++] = vec.toArray();
        }
        return new MatMN(matrix);
    }

    /**
     * Returns an iterator over the elements in this matrix. Columns are traversed first, then each
     * row within a column.
     *
     * @return an iterator over the elements in this matrix.
     */
    public Iterator<Double> iterator() {
        return Arrays.stream(matrix).flatMapToDouble(Arrays::stream).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int rows() {
        return matrix[0].length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int cols() {
        return matrix.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN getRow(int i) {
        Util.verifyValidRow(matrix, i);
        ArrayList<Double> row = new ArrayList<>();
        for (int c = 0; c < matrix.length; c++) {
            row.add(matrix[c][i]);
        }
        return VecN.createFrom(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN getCol(int i) {
        Util.verifyValidColumn(matrix, i);
        return new VecN(matrix[i]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double get(int row, int col) {
        Util.verifyValidCoord(matrix, row, col);
        return matrix[col][row];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatMN invert() {
        return new MatMN(Util.invert(matrix));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatMN add(Mat mat) {
        return new MatMN(Util.add(matrix, mat.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatMN subtract(Mat mat) {
        return new MatMN(Util.subtract(matrix, mat.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatMN scale(double scalar) {
        return new MatMN(Util.scale(matrix, scalar));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VecN multiply(Vec vec) {
        return new VecN(Util.multiply(matrix, vec.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatMN multiply(Mat mat) {
        return new MatMN(Util.multiply(matrix, mat.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[][] toArray() {
        return arrayCopy(matrix);
    }

    @Override
    public String toString() {
        return Util.stringify(matrix);
    }

    /**
     * Returns a string representation of the matrix where each element is limited to a specific
     * number of decimals.
     *
     * @param decimals the number of decimals each element should have.
     * @return the string representation.
     * @throws IllegalArgumentException if the specified decimal amount is less than zero.
     */
    public String toString(int decimals) {
        return Util.stringify(matrix, decimals);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MatMN mat = (MatMN) obj;
        // Matrices of different dimensions cannot be equal.
        if (cols() != mat.cols() || rows() != mat.rows()) {
            return false;
        }
        for (int c = 0; c < cols(); c++) {
            if (!Arrays.equals(matrix[c], mat.matrix[c])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj, double error) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MatMN mat = (MatMN) obj;
        // Matrices of different dimensions cannot be equal.
        if (cols() != mat.cols() || rows() != mat.rows()) {
            return false;
        }
        for (int c = 0; c < cols(); c++) {
            for (int r = 0; r < rows(); r++) {
                if (Math.abs(matrix[c][r] - mat.matrix[c][r]) > error) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }

    private static double[][] arrayCopy(double[][] array) {
        int n = array.length;
        double[][] arrayCopy = new double[n][];
        for (int i = 0; i < n; i++) {
            arrayCopy[i] = array[i].clone();
        }
        return arrayCopy;
    }
}
