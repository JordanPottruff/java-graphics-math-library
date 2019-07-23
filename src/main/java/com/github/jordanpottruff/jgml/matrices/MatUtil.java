package com.github.jordanpottruff.jgml.matrices;

class MatUtil {

    private MatUtil() {
        // Intentionally left blank; prevents instantiation.
    }

    /**
     * Returns a new matrix such that the components of the matrix are the negation of the input
     * matrix's components.
     */
    static double[][] invert(double[][] mat) {
        double[][] result = new double[mat.length][];

        for (int col = 0; col < mat.length; col++) {
            double[] invRow = new double[mat[col].length];

            for (int row = 0; row < invRow.length; row++) {
                invRow[row] = -mat[col][row];
            }

            result[col] = invRow;
        }

        return result;
    }

    /**
     * Returns a new matrix that is the sum of the two input matrices.
     */
    static double[][] add(double[][] matA, double[][] matB) {
        verifyEqualDimensions(matA, matB);
        int rows = matA[0].length;
        int cols = matA.length;

        double[][] result = new double[cols][rows];

        for (int ci = 0; ci < cols; ci++) {
            for (int ri = 0; ri < rows; ri++) {
                result[ci][ri] = matA[ci][ri] + matB[ci][ri];
            }
        }

        return result;
    }

    /**
     * Returns a new matrix that is the subtraction of matB from matA.
     */
    static double[][] subtract(double[][] matA, double[][] matB) {
        verifyEqualDimensions(matA, matB);
        return add(matA, invert(matB));
    }

    /**
     * Returns a new matrix that is the input matrix scaled by the input scalar.
     */
    static double[][] scale(double[][] mat, double scalar) {
        verifyUniformMatrix(mat);
        int rows = mat[0].length;
        int cols = mat.length;

        double[][] result = new double[cols][rows];

        for (int ci = 0; ci < cols; ci++) {
            for (int ri = 0; ri < rows; ri++) {
                result[ci][ri] = scalar * mat[ci][ri];
            }
        }

        return result;
    }

    /**
     * Returns a new vector that is the multiplication of the input matrix and the input vector. The
     * multiplication is performed in the order of 'mat' * 'vec'.
     */
    static double[] multiply(double[][] mat, double[] vec) {
        verifyOperableDimensions(mat, vec);
        int matCols = mat.length;
        int matRows = mat[0].length;

        double[] result = new double[matRows];

        for (int row = 0; row < matRows; row++) {
            for (int col = 0; col < matCols; col++) {
                result[row] += mat[col][row] * vec[col];
            }
        }

        return result;
    }

    /**
     * Returns a new matrix that is the multiplication of the two input matrices. The multiplication
     * is performed in the order of 'matA' * 'matB'.
     */
    static double[][] multiply(double[][] matA, double[][] matB) {
        verifyOperableDimensions(matA, matB);
        int matACols = matA.length;
        int matARows = matA[0].length;
        int matBCols = matB.length;

        double[][] result = new double[matARows][matBCols];

        for (int row = 0; row < matARows; row++) {
            for (int col = 0; col < matBCols; col++) {
                for (int i = 0; i < matACols; i++) {
                    result[col][row] += matA[i][row] * matB[col][i];
                }
            }
        }

        return result;
    }

    /**
     * Returns a submatrix formed by the removal of the given row and column.
     */
    static double[][] submatrix(double[][] mat, int row, int col) {
        verifyUniformMatrix(mat);
        verifyValidCoord(mat, row, col);
        double[][] sub = new double[mat.length - 1][mat[0].length - 1];

        int cSub = 0;
        for (int ci = 0; ci < mat.length; ci++) {
            if (ci == col) continue;

            int rSub = 0;
            for (int ri = 0; ri < mat[0].length; ri++) {
                if (ri == row) continue;
                sub[cSub][rSub] = mat[ci][ri];
                rSub++;
            }
            cSub++;
        }
        return sub;
    }

    /**
     * Returns the minor of the matrix given a selected row and column. The minor of a matrix is the
     * determinant of the submatrix formed by removing the given row and column.
     */
    private static double minor(double[][] mat, int row, int col) {
        verifySquareMatrix(mat);
        verifyValidCoord(mat, row, col);

        double[][] sub = submatrix(mat, row, col);
        return determinant(sub);
    }

    /**
     * Returns the cofactor of a matrix at (row,col). The cofactor of a matrix at (row,col) is the
     * value of the matrix at (row,col) multiplied by the minor of the matrix at (row,col).
     */
    private static double cofactor(double[][] mat, int row, int col) {
        verifySquareMatrix(mat);
        verifyValidCoord(mat, row, col);

        return (double) Math.pow(-1, row + col + 2) * minor(mat, row, col);
    }

    /**
     * Returns the determinant of a matrix, using a recursive Laplace cofactor expansion.
     */
    static double determinant(double[][] mat) {
        verifySquareMatrix(mat);
        int dim = mat.length;
        if (dim == 1) {
            return mat[0][0];
        } else if (dim == 2) {
            return mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1];
        } else {
            double det = 0;
            for (int ri = 0; ri < dim; ri++) {
                det += mat[0][ri] * cofactor(mat, ri, 0);
            }
            return det;
        }
    }

    /**
     * Returns the inverse of the input matrix. Uses cofactor/adjugate/determinant to calculate the
     * answer. Will throw an exception if the matrix is not invertible.
     */
    static double[][] inverse(double[][] mat) {
        verifySquareMatrix(mat);
        verifyInvertibleMatrix(mat);
        double[][] result = new double[mat.length][mat[0].length];

        for (int col = 0; col < mat.length; col++) {
            for (int row = 0; row < mat[0].length; row++) {
                // Creating adjugate by transposing cofactors
                result[col][row] = cofactor(mat, col, row);
            }
        }

        return scale(result, 1 / determinant(mat));
    }

    /**
     * Converts a double-array vector into a string representation. Each component of the vector is
     * surrounded by brackets and put onto a newline.
     */
    static String stringify(double[] vec, int decimals) {
        if (vec.length == 0) {
            return "[]";
        }
        String result = "";

        // Find the length of the longest string representation of the components of the vector.
        int maxStrLen = Integer.MIN_VALUE;
        for (double component : vec) {
            maxStrLen = Math.max(maxStrLen,
                    String.format("%." + decimals + "f", component).length());
        }

        // Build the result string, justifying each component appropriately given the length of
        // the longest one.
        for (double component : vec) {
            result += String.format("[%" + maxStrLen + "." + decimals + "f]\n", component);
        }
        return result.substring(0, result.length()-1);
    }

    /**
     * Converts a 2D double-array matrix into a string representation. Each column vector's
     * component is surrounded by brackets and put on a new line.
     */
    static String stringify(double[][] mat, int decimals) {
        if (mat.length == 0) {
            return "[]";
        }

        decimals = Math.max(decimals, 0);   // Eliminate negative integers.
        String result = "";
        // Store string values in temporary matrix:
        String[][] strMat = new String[mat.length][];
        // Setup the string matrix columns and record the length of the longest column to help
        // with traversal later.
        int maxColLen = Integer.MIN_VALUE;
        for (int col = 0; col < mat.length; col++) {
            maxColLen = Math.max(maxColLen, mat[col].length);
            strMat[col] = new String[mat[col].length];
        }

        // Assign values to the string matrix and record the length of the longest string
        // encountered.
        int maxStrLen = Integer.MIN_VALUE;
        for (int row = 0; row < maxColLen; row++) {
            for (int col = 0; col < mat.length; col++) {
                // Need to make sure that the current column is long enough for 'row' to be a
                // valid index.
                if (row < mat[col].length) {
                    String valueStr = String.format("%." + decimals + "f", mat[col][row]);
                    strMat[col][row] = valueStr;

                    maxStrLen = Math.max(maxStrLen, valueStr.length());
                }
            }
        }

        // Finally, traverse the string array and format the strings to be correctly justified
        // using the max string
        // length recorded above.
        for (int row = 0; row < maxColLen; row++) {
            for (int col = 0; col < mat.length; col++) {
                if (row < mat[col].length) {
                    result += String.format("[%" + maxStrLen + "s]", strMat[col][row]);
                } else {
                    result += String.format("[%" + maxStrLen + "s]", "");
                }
            }
            result += "\n";
        }

        return result.substring(0, result.length()-1);
    }

    /**
     * Throws an IllegalArgumentException at runtime if the matrix is not of the required dimension.
     */
    static void verifyExactDimension(double[][] mat, int rows, int cols) {
        verifyUniformMatrix(mat);
        if (mat.length != cols || mat[0].length != rows) {
            throw new IllegalArgumentException(String.format("Expected a uniform matrix with %d " +
                    "rows and %d cols, but received:\n%s", rows, cols, stringify(mat, 2)));
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the dimensions of the matrix are not at
     * least as large as the minimum required dimensions.
     */
    static void verifyMinimumDimensions(double[][] mat, int minRows, int minCols) {
        verifyUniformMatrix(mat);
        if (mat.length < minCols || mat[0].length < minRows) {
            throw new IllegalArgumentException(String.format("Expected matrix %s of dimensions " +
                            "%dx%d to have dimensions larger than %dx%d", stringify(mat, 2),
                    mat.length,
                    mat[0].length, minRows, minCols));
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the two matrices are of different dimensions.
     */
    static void verifyEqualDimensions(double[][] matA, double[][] matB) {
        verifyUniformMatrix(matA);
        verifyUniformMatrix(matB);
        if (matA.length != matB.length || matA[0].length != matB[0].length) {
            throw new IllegalArgumentException(String.format("Expected matrices with identical " +
                    "dimensions but received:\n%sand:\n%s", stringify(matA, 2), stringify(matB,
                    2)));
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the matrix and vector are of dimensions
     * that are not compatible for an operation like multiplication, where the number of columns in
     * the matrix must equal the number of rows in the vector.
     */
    static void verifyOperableDimensions(double[][] mat, double[] vec) {
        verifyUniformMatrix(mat);

        int matCols = mat.length;
        int vecRows = vec.length;

        if (matCols != vecRows) {
            throw new IllegalArgumentException(String.format("Expected the number of columns in a" +
                            " matrix to equal the number of rows in the vector but " +
                            "received:\n%sand:\n%s"
                    , stringify(mat, 2), stringify(vec, 2)));
        }

    }

    /**
     * Throws a IllegalArgumentException at runtime if the two matrices are of dimensions that
     * are not compatible for an operation like matrix multiplication, where the number of columns
     * in the first operand must equal the number of rows in the second operand.
     */
    static void verifyOperableDimensions(double[][] matA, double[][] matB) {
        verifyUniformMatrix(matA);
        verifyUniformMatrix(matB);

        int matACols = matA.length;
        int matBRows = matB[0].length;

        if (matACols != matBRows) {
            throw new IllegalArgumentException(String.format("Expected matrices with dimensions " +
                            "compatible for operations but received:\n%sand:\n%s", stringify(matA
                    , 2),
                    stringify(matB, 2)));
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the matrix has column vectors with different
     * lengths. The expected length is set as the length of the first (left-most) column vector, and
     * the first column vector that differs in length triggers the exception.
     * <p>
     * This verification is for handling the drawbacks of using 2D arrays, which are implemented
     * as arrays of arrays in Java. This allows arrays of different sizes to be put inside a 2D
     * array, which breaks constraints that are generally assumed for all operations.
     */
    static void verifyUniformMatrix(double[][] mat) {
        if (mat.length == 0) return;
        int colLength = mat[0].length;
        for (int i = 1; i < mat.length; i++) {
            if (mat[i].length != colLength)
                throw new IllegalArgumentException(String.format("Expected a matrix with columns " +
                        "of equal length but received:\n%s", stringify(mat, 2)));
        }
    }

    /**
     * Throws a IllegalArgumentException at runtime if the matrix is not square, i.e. the number of
     * rows differsfrom the number of columns. Also verifies that the matrix is uniform, which is a
     * precondition for it to be square.
     */
    static void verifySquareMatrix(double[][] mat) {
        verifyUniformMatrix(mat);
        if (mat.length == 0) return;
        if (mat.length != mat[0].length)
            throw new IllegalArgumentException(String.format("Expected a square matrix but " +
                    "received matrix:\n%s", stringify(mat, 2)));
    }

    /**
     * Throws an IllegalArgumentException at runtime if the given (row, col) coordinate is out of
     * bounds of the matrix.
     */
    static void verifyValidCoord(double[][] mat, int row, int col) {
        // Verification can just be done by translating an index out of bound exception to our
        // exception.
        try {
            double num = mat[col][row];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Position at row=%d, col=%d is out " +
                    "of bounds of the matrix:\n%s", row, col, stringify(mat, 2)));
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the given column is out of bounds of the
     * matrix.
     */
    static void verifyValidColumn(double[][] mat, int col) {
        try {
            double[] column = mat[col];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Column at position %d does not " +
                    "exist in the matrix:\n%s", col, stringify(mat, 2)));
        }
    }

    /**
     * Throws an IllegalArgumentException at runtime if the given row is out of bounds of the
     * matrix.
     */
    static void verifyValidRow(double[][] mat, int row) {
        try {
            double val = mat[0][row];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Row at position %d does not " +
                    "exist in the matrix:\n%s", row, stringify(mat, 2)));
        }
    }

    /**
     * Throws an InvalidInputException at runtime if the given matrix is not invertible.
     */
    static void verifyInvertibleMatrix(double[][] mat) {
        if (determinant(mat) == 0.0f) {
            throw new IllegalArgumentException(String.format("Expected an invertible matrix but " +
                    "received:\n%s", stringify(mat, 2)));
        }
    }
}
