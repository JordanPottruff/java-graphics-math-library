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
     * Constructs a MatMN from a 2D array of elements, representing an array of column vectors. The
     * array must contain at least two column vectors, all sharing the same dimension which must be
     * a minimum of two.
     *
     * @param array the 2D array of elements.
     */
    public MatMN(double[][] array) {
        MatUtil.verifyUniformMatrix(array);
        MatUtil.verifyMinimumDimensions(array, 2, 2);
        matrix = arrayCopy(array);
    }

    /**
     * Creates a MatMN from an iterable of vectors, representing column vectors of the matrix. The
     * iterable must contain at least two column vectors, all sharing the same dimension which must
     * be a minimum of two.
     *
     * @param vectors the iterable of column vectors.
     * @return a new MatMN composed of the iterable's column vectors.
     */
    public static MatMN createFrom(Iterable<Vec> vectors) {
        int n = 0;
        for(Vec vec: vectors) {
            n++;
        }

        double[][] matrix = new double[n][];

        int c=0;
        for(Vec vec: vectors) {
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
        for(int c=0; c<matrix.length; c++) {
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
        return MatUtil.stringify(matrix, 4);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MatMN)) {
            return false;
        }
        MatMN mat = (MatMN) obj;
        // Matrices of different dimensions cannot be equal.
        if(cols() != mat.cols() || rows() != mat.rows()) {
            return false;
        }
        for(int c=0; c<cols(); c++) {
            if(!Arrays.equals(matrix[c], mat.matrix[c])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj, double error) {
        if(!(obj instanceof MatMN)) {
            return false;
        }
        MatMN mat = (MatMN) obj;
        // Matrices of different dimensions cannot be equal.
        if(cols() != mat.cols() || rows() != mat.rows()) {
            return false;
        }
        for(int c=0; c<cols(); c++) {
            for(int r=0; r<rows(); r++) {
                if(Math.abs(matrix[c][r] - mat.matrix[c][r]) > error) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double[][] arrayCopy(double[][] array) {
        int n = array.length;
        double[][] arrayCopy = new double[n][];
        for(int i=0; i<n; i++) {
            arrayCopy[i] = array[i].clone();
        }
        return arrayCopy;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }
}
