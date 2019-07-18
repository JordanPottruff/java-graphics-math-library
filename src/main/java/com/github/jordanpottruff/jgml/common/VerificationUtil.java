package com.github.jordanpottruff.jgml.common;

public class VerificationUtil {

    private VerificationUtil() {
        // Intentionally left blank to prevent instantiation.
    }

    /**
     * Throws a IllegalArgumentException at runtime if the two vectors are of different dimensions.
     *
     * @param vecA first vector operand.
     * @param vecB second vector operand.
     */
    public static void verifyEqualDimensions(double[] vecA, double[] vecB) {
        if (vecA.length != vecB.length) throw ExceptionsUtil.unequalVectorDimensions(vecA, vecB);
    }

    /**
     * Throws an IllegalArgumentException at runtime if the vector is not longer than the minimum
     * required length.
     */
    public static void verifyMinimumLength(double[] vec, int minLength) {
        if(vec.length < minLength) throw ExceptionsUtil.belowMinimumVectorDimension(vec, minLength);
    }

    /**
     * Throws a IllegalArgumentException at runtime if the two matrices are of different dimensions.
     *
     * @param matA first matrix operand.
     * @param matB second matrix operand.
     */
    public static void verifyEqualDimensions(double[][] matA, double[][] matB) {
        verifyUniformMatrix(matA);
        verifyUniformMatrix(matB);
        if (matA.length != matB.length || matA[0].length != matB[0].length) {
            throw ExceptionsUtil.unequalMatrixDimensions(matA, matB);
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the matrix and vector are of dimensions that are not compatible
     * for an operation like multiplication, where the number of columns in the matrix must equal the number of rows in
     * the vector.
     *
     * @param mat first operand, a matrix.
     * @param vec second operand, a vector.
     */
    public static void verifyOperableDimensions(double[][] mat, double[] vec) {
        verifyUniformMatrix(mat);

        int matCols = mat.length;
        int vecRows = vec.length;

        if(matCols != vecRows) {
            throw ExceptionsUtil.inoperableMatrixVectorDimensions(mat, vec);
        }

    }

    /**
     * Throws a IllegalArgumentException at runtime if the two matrices are of dimensions that are not compatible for
     * an operation like matrix multiplication, where the number of columns in the first operand must equal the
     * number of rows in the second operand.
     *
     * @param matA first matrix operand.
     * @param matB second matrix operand.
     */
    public static void verifyOperableDimensions(double[][] matA, double[][] matB) {
        verifyUniformMatrix(matA);
        verifyUniformMatrix(matB);

        int matACols = matA.length;
        int matBRows = matB[0].length;

        if(matACols != matBRows) {
            throw ExceptionsUtil.inoperableMatrixDimensions(matA, matB);
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the matrix has column vectors with different lengths. The
     * expected length is set as the length of the first (left-most) column vector, and the first column vector that
     * differs in length triggers the exception.
     *
     * This verification is for handling the drawbacks of using 2D arrays, which are implemented as arrays of arrays
     * in Java. This allows arrays of different sizes to be put inside a 2D array, which breaks constraints that are
     * generally assumed for all operations.
     *
     * @param mat the matrix to be verified.
     */
    public static void verifyUniformMatrix(double[][] mat) {
        if (mat.length == 0) return;
        int colLength = mat[0].length;
        for (int i = 1; i < mat.length; i++) {
            if (mat[i].length != colLength) throw ExceptionsUtil.expectedUniformMatrix(mat);
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the matrix is not square, i.e. the number of rows differs
     * from the number of columns. Also verifies that the matrix is uniform, which is a precondition for it to be
     * square.
     * @param mat the matrix to be verified.
     */
    public static void verifySquareMatrix(double[][] mat) {
        verifyUniformMatrix(mat);
        if (mat.length == 0) return;
        if (mat.length != mat[0].length) throw ExceptionsUtil.expectedSquareMatrix(mat);
    }

    /**
     * Throws an IllegalArgumentException at runtime if the given row coordinate is out of bounds of the
     * vector.
     *
     * @param vec the vector to be accessed.
     * @param row the row portion of the coordinate to be verified.
     */
    public static void verifyValidCoord(double[] vec, int row) {
        try {
            double num = vec[row];
        } catch (IndexOutOfBoundsException e) {
            throw ExceptionsUtil.invalidVectorCoordinate(row, vec);
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the given (row, col) coordinate is out of bounds of the
     * matrix.
     *
     * @param mat the matrix to be accessed.
     * @param row the row portion of the coordinate to be verified.
     * @param col the col portion of the coordinate to be verified.
     */
    public static void verifyValidCoord(double[][] mat, int row, int col) {
        // Verification can just be done by translating an index out of bound exception to our exception.
        try {
            double num = mat[col][row];
        } catch (IndexOutOfBoundsException e) {
            throw ExceptionsUtil.invalidMatrixCoordinates(row, col, mat);
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the given column is out of bounds of the matrix.
     *
     * @param mat the matrix to be accessed.
     * @param col the column to be verified.
     */
    public static void verifyValidColumn(double[][] mat, int col) {
        try {
            double[] column = mat[col];
        } catch (IndexOutOfBoundsException e) {
            throw ExceptionsUtil.invalidMatrixColumn(col, mat);
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the vector is not of the required dimension.
     *
     * @param vec the vector to be verified.
     * @param rows the number of rows the vector must have.
     */
    public static void verifyExactDimension(double[] vec, int rows) {
        if(vec.length != rows) {
            throw ExceptionsUtil.invalidDimensions(vec, rows);
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the matrix is not of the required dimension.
     *
     * @param mat the matrix to be verified.
     * @param rows the number of rows the matrix must have.
     * @param cols the number of columns the matrix must have.
     */
    public static void verifyExactDimension(double[][] mat, int rows, int cols) {
        // Verify the matrix is uniform (all columns are the same length)
        try {
            verifyUniformMatrix(mat);
        } catch (Exception e) {
            throw ExceptionsUtil.invalidDimensions(mat, rows, cols);
        }

        // Verify that the number of columns and rows match what is expected (given).
        if(mat.length != cols || mat[0].length != rows) {
            throw ExceptionsUtil.invalidDimensions(mat, rows, cols);
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if
     */

    /**
     * Throws an InvalidInputException at runtime if the given matrix is not invertible.
     *
     * @param mat the matrix to be inverted.
     */
    public static void verifyInvertibleMatrix(double[][] mat) {
        if(OperationsUtil.determinant(mat) == 0.0f) {
            throw ExceptionsUtil.notInvertible(mat);
        }
    }
}
