package com.github.jordanpottruff.jgml.common;

public class ExceptionsUtil {

    private ExceptionsUtil() {
        // Intentionally left blank to prevent instantiation.
    }


    /**
     * Creates a IllegalArgumentException for two vectors that were expected to be the same length.
     *
     * @param vecA first vector.
     * @param vecB second vector.
     * @return the exception.
     */
    public static IllegalArgumentException unequalVectorDimensions(double[] vecA, double[] vecB) {
        return new IllegalArgumentException(String.format("Expected vectors with identical dimensions but received:\n%sand:\n%s", OperationsUtil.stringify(vecA, 2), OperationsUtil.stringify(vecB, 2)));
    }

    /**
     * Creates an IllegalArgumentException for a vector whose length is less than the minimum required.
     *
     * @param vec the vector being evaluated.
     * @param minLength the minimum length required of the vector.
     * @return the exception.
     */
    public static IllegalArgumentException belowMinimumVectorDimension(double[] vec, int minLength) {
        return new IllegalArgumentException(String.format("Expected vector %s of length %d to have a length longer than %d", OperationsUtil.stringify(vec, 2), vec.length, minLength));
    }

    /**
     * Creates a IllegalArgumentException for two matrices that were expected to be of the same dimensions.
     *
     * @param matA first matrix.
     * @param matB second matrix.
     * @return the exception.
     */
    public static IllegalArgumentException unequalMatrixDimensions(double[][] matA, double[][] matB) {
        return new IllegalArgumentException(String.format("Expected matrices with identical dimensions but received:\n%sand:\n%s", OperationsUtil.stringify(matA, 2), OperationsUtil.stringify(matB, 2)));
    }

    /**
     * Creates a IllegalArgumentException given a matrix and vector, where the number of columns in the matrix
     * was expected to match the number of rows in the vector.
     *
     * @param mat the matrix.
     * @param vec the vector.
     * @return the exception.
     */
    public static IllegalArgumentException inoperableMatrixVectorDimensions(double[][] mat, double[] vec) {
        return new IllegalArgumentException(String.format("Expected the number of columns in a matrix to equal the number of rows in the vector but received:\n%sand:\n%s", OperationsUtil.stringify(mat, 2), OperationsUtil.stringify(vec, 2)));
    }

    /**
     * Creates a IllegalArgumentException given two matrices, where the number of columns in the first matrix
     * was expected to match the number of rows in the second.
     *
     * @param matA first matrix.
     * @param matB second matrix.
     * @return the exception.
     */
    public static IllegalArgumentException inoperableMatrixDimensions(double[][] matA, double[][] matB) {
        return new IllegalArgumentException(String.format("Expected matrices with dimensions compatible for operations but received:\n%sand:\n%s", OperationsUtil.stringify(matA, 2), OperationsUtil.stringify(matB, 2)));
    }

    /**
     * Creates an IllegalArgumentException for a matrix that was expected to be square but was not.
     *
     * @param mat the non-square matrix.
     * @return the exception.
     */
    public static IllegalArgumentException expectedSquareMatrix(double[][] mat) {
        return new IllegalArgumentException(String.format("Expected a square matrix but received matrix:\n%s", OperationsUtil.stringify(mat, 2)));
    }

    /**
     * Creates an IllegalArgumentException for a matrix that does not have columns that are all the same length.
     *
     * @param mat the non-uniform matrix.
     * @return the exception.
     */
    public static IllegalArgumentException expectedUniformMatrix(double[][] mat) {
        return new IllegalArgumentException(String.format("Expected a matrix with columns of equal length but received:\n%s", mat));
    }

    /**
     * Creates an IllegalArgumentException caused by accessing the given matrix at the given position.
     *
     * @param row the row coordinate.
     * @param col the column coordinate.
     * @param mat the matrix being accessed.
     * @return the exception.
     */
    public static IllegalArgumentException invalidMatrixCoordinates(int row, int col, double[][] mat) {
        return new IllegalArgumentException(String.format("Position at row=%d, col=%d is out of bounds of the matrix:\n%s", row, col, OperationsUtil.stringify(mat, 2)));
    }

    /**
     * Creates an IllegalArgumentException caused by accessing the given matrix at the given column number.
     *
     * @param col the column coordinate.
     * @param mat the matrix being accessed.
     * @return the exception.
     */
    public static IllegalArgumentException invalidMatrixColumn(int col, double[][] mat) {
        return new IllegalArgumentException(String.format("Column at position %d does not exist in the matrix:\n%s", col, OperationsUtil.stringify(mat, 2)));
    }

    /**
     * Creates an IllegalArgumentException caused by accessing the given vector at the given position.
     *
     * @param row the row coordinate.
     * @param vec the vector being accessed.
     * @return the exception.
     */
    public static IllegalArgumentException invalidVectorCoordinate(int row, double[] vec) {
        return new IllegalArgumentException(String.format("Position at row=%d is out of bounds of the vector:\n%s", row, vec));
    }

    /**
     * Creates an IllegalArgumentException for a matrix that was expected to be invertible but was not.
     *
     * @param mat the matrix that is non-invertible.
     * @return the exception.
     */
    public static IllegalArgumentException notInvertible(double[][] mat) {
        return new IllegalArgumentException(String.format("Expected an invertible matrix but received:\n%s", OperationsUtil.stringify(mat, 2)));
    }

    /**
     * Creates an IllegalArgumentException for a vector that was expected to be of a certain dimension but was not.
     *
     * @param vec  the vector.
     * @param rows the expected number of rows.
     * @return the exception.
     */
    public static IllegalArgumentException invalidDimensions(double[] vec, int rows) {
        return new IllegalArgumentException(String.format("Expected a vector with %d rows, but received: \n%s", rows, vec));
    }

    /**
     * Creates an IllegalArgumentException for a matrix that was expected to be of a certain dimension but was not.
     *
     * @param mat  the matrix.
     * @param rows the expected number of rows.
     * @param cols the expected number of columns.
     * @return the exception.
     */
    public static IllegalArgumentException invalidDimensions(double[][] mat, int rows, int cols) {
        return new IllegalArgumentException(String.format("Expected a uniform matrix with %d rows and %d cols, but received:\n%s", rows, cols, OperationsUtil.stringify(mat, 2)));
    }
}
