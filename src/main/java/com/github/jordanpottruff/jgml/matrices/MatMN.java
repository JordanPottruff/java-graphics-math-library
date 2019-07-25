package com.github.jordanpottruff.jgml.matrices;

import com.github.jordanpottruff.jgml.vectors.Vec;
import com.github.jordanpottruff.jgml.vectors.VecN;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The root of the hierarchy of concrete matrix implementations. A MatMN is a generalized matrix of
 * any dimensions M x N where M and N are both greater than or equal to two.
 */
public class MatMN implements Mat {

    private final double[][] matrix;

    /**
     * Constructs a MatMN from a two-dimensional array of elements. The outer-array must contain two
     * or more inner-arrays, with each being the same length (uniform) and containing at least two
     * elements. The inner-arrays will be the column vectors of the new MatMN object.
     *
     * @param array the 2D array of elements.
     * @throws IllegalArgumentException if the inner-arrays are not of equal length (non-uniform).
     * @throws IllegalArgumentException if the outer-array is not of length two or greater or if the
     *                                  inner-arrays are not of length two or greater (MatMN must
     *                                  be of dimension 2x2 or greater).
     */
    public MatMN(double[][] array) {
        MatUtil.verifyUniformMatrix(array);
        MatUtil.verifyMinimumDimensions(array, 2, 2);
        matrix = arrayCopy(array);
    }

    /**
     * Creates a MatMN from an iterable of vectors. The iterable must contain at least two vectors,
     * each of the same dimension and containing at least two elements. These vectors will be the
     * column vectors of the new MatMN object.
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
     * {@inheritDoc}
     */
    public int rows() {
        return matrix[0].length;
    }

    /**
     * {@inheritDoc}
     */
    public int cols() {
        return matrix.length;
    }

    /**
     * {@inheritDoc}
     */
    public VecN getRow(int i) {
        MatUtil.verifyValidRow(matrix, i);
        ArrayList<Double> row = new ArrayList<>();
        for (int c = 0; c < matrix.length; c++) {
            row.add(matrix[c][i]);
        }
        return VecN.createFrom(row);
    }

    /**
     * {@inheritDoc}
     */
    public VecN getCol(int i) {
        MatUtil.verifyValidColumn(matrix, i);
        return new VecN(matrix[i]);
    }

    /**
     * {@inheritDoc}
     */
    public double get(int row, int col) {
        MatUtil.verifyValidCoord(matrix, row, col);
        return matrix[col][row];
    }

    /**
     * {@inheritDoc}
     */
    public MatMN invert() {
        return new MatMN(MatUtil.invert(matrix));
    }

    /**
     * {@inheritDoc}
     */
    public MatMN add(Mat mat) {
        return new MatMN(MatUtil.add(matrix, mat.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    public MatMN subtract(Mat mat) {
        return new MatMN(MatUtil.subtract(matrix, mat.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    public MatMN scale(double scalar) {
        return new MatMN(MatUtil.scale(matrix, scalar));
    }

    /**
     * {@inheritDoc}
     */
    public VecN multiply(Vec vec) {
        return new VecN(MatUtil.multiply(matrix, vec.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    public MatMN multiply(Mat mat) {
        return new MatMN(MatUtil.multiply(matrix, mat.toArray()));
    }

    /**
     * {@inheritDoc}
     */
    public double[][] toArray() {
        return arrayCopy(matrix);
    }

    @Override
    public String toString() {
        return formatToString("%f");
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
        return formatToString("%." + decimals + "f");
    }

    private String formatToString(String format) {
        // This will store the longest string-representation for any value in each column.
        int[] maxColStrLen = new int[cols()];

        for (int c = 0; c < cols(); c++) {
            // Find the longest string-representation of a value in the current column.
            for (int r = 0; r < rows(); r++) {
                int curStrLen = String.format(format, matrix[c][r]).length();
                maxColStrLen[c] = Math.max(maxColStrLen[c], curStrLen);
            }
        }

        StringBuilder result = new StringBuilder();
        // To create the string correctly, we have to iterate through each row first.
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < cols(); c++) {
                result.append(String.format("[%" + maxColStrLen[c] + "s]", String.format(format,
                        matrix[c][r])));
            }
            if (r != rows() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
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
