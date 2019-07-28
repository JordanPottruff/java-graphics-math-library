package com.github.jordanpottruff.jgml;

class Util {

    private Util() {
        // Intentionally blank to prevent instantiation.
    }

    /**
     * Returns the magnitude of the input vector.
     */
    static double magnitude(double[] vec) {
        double sum = 0;
        for (int i = 0; i < vec.length; i++) {
            sum += Math.pow(vec[i], 2.0f);
        }
        return (double) Math.sqrt(sum);
    }

    /**
     * Returns the normalized version of the input vector as a new vector.
     */
    static double[] normalize(double[] vec) {
        double[] newVec = new double[vec.length];
        double mag = magnitude(vec);
        for (int i = 0; i < vec.length; i++) {
            newVec[i] = vec[i] / mag;
        }
        return newVec;
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

        return Math.pow(-1, row + col + 2) * minor(mat, row, col);
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
     * Returns a new vector such that the components of the vector are the negation of the input
     * vector's components.
     */
    static double[] invert(double[] vec) {
        double[] newVec = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            newVec[i] = -vec[i];
        }
        return newVec;
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
     * Returns a new vector that is the sum of the two input vectors.
     */
    static double[] add(double[] vecA, double[] vecB) {
        verifyEqualDimensions(vecA, vecB);
        int n = vecA.length;
        double[] sum = new double[n];
        for (int i = 0; i < n; i++) {
            sum[i] = vecA[i] + vecB[i];
        }
        return sum;
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
     * Returns a new vector that is the subtraction of vecB from vecA.
     */
    static double[] subtract(double[] vecA, double[] vecB) {
        // Use add() for op and for compatibility checking.
        return add(vecA, invert(vecB));
    }

    /**
     * Returns a new matrix that is the subtraction of matB from matA.
     */
    static double[][] subtract(double[][] matA, double[][] matB) {
        verifyEqualDimensions(matA, matB);
        return add(matA, invert(matB));
    }

    /**
     * Returns a new vector that is the dot product of the two input vectors.
     */
    static double multiply(double[] vecA, double[] vecB) {
        verifyEqualDimensions(vecA, vecB);
        int n = vecA.length;
        double dot = 0;
        for (int i = 0; i < n; i++) {
            dot += vecA[i] * vecB[i];
        }
        return dot;
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
     * Returns the cross product of the two input vectors.
     */
    static double[] cross(double[] vecA, double[] vecB) {
        verifyMinimumDimension(vecA, 3);
        verifyMinimumDimension(vecB, 3);

        double[] crossProduct = new double[3];
        crossProduct[0] = vecA[1] * vecB[2] - vecA[2] * vecB[1];
        crossProduct[1] = vecA[2] * vecB[0] - vecA[0] * vecB[2];
        crossProduct[2] = vecA[0] * vecB[1] - vecA[1] * vecB[0];

        return crossProduct;
    }

    static String stringify(double[] vec) {
        return formatStringify("%f", vec);
    }

    static String stringify(double[] vec, int decimals) {
        return formatStringify("%."+decimals + "f", vec);
    }

    static String stringify(double[][] mat) {
        return formatStringify("%f", mat);
    }

    static String stringify(double[][] mat, int decimals) {
        return formatStringify("%."+decimals + "f", mat);
    }

    private static String formatStringify(String format, double[] vec) {
        int maxStrLen = 0;
        for (int i = 0; i < vec.length; i++) {
            int curStrLen = String.format(format, vec[i]).length();
            maxStrLen = Math.max(maxStrLen, curStrLen);
        }

        StringBuilder result = new StringBuilder();
        // To create the string correctly, we have to iterate through each row first.
        for (int i = 0; i < vec.length; i++) {
            result.append(String.format("[%" + maxStrLen + "s]", String.format(format, vec[i])));
            if (i != vec.length - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    private static String formatStringify(String format, double[][] mat) {
        // This will store the longest string-representation for any value in each column.
        int cols = mat.length;
        int[] maxColStrLen = new int[cols];
        int maxAmountOfRows = -1;
        for (int c = 0; c < cols; c++) {
            // Find the longest string-representation of a value in the current column.
            maxAmountOfRows = Math.max(maxAmountOfRows, mat[c].length);
            for (int r = 0; r < mat[c].length; r++) {
                int curStrLen = String.format(format, mat[c][r]).length();
                maxColStrLen[c] = Math.max(maxColStrLen[c], curStrLen);
            }
        }

        StringBuilder result = new StringBuilder();
        // To create the string correctly, we have to iterate through each row first.
        for (int r = 0; r < maxAmountOfRows; r++) {
            for (int c = 0; c < cols; c++) {
                String str = "";
                if(r < mat[c].length) {
                    str = String.format(format, mat[c][r]);
                }
                result.append(String.format("[%" + maxColStrLen[c] + "s]", str));
            }
            if (r != maxAmountOfRows - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Throws an IllegalArgumentException at runtime if the vector is not of the required dimension.
     */
    static void verifyExactDimension(double[] vec, int rows) {
        if (vec.length != rows) {
            throw new IllegalArgumentException(String.format("Expected a vector with %d rows, but" +
                    " received: \n%s", rows, stringify(vec, 2)));
        }
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
     * Throws an IllegalArgumentException at runtime if the vector is not longer than the minimum
     * required dimension.
     */
    static void verifyMinimumDimension(double[] vec, int minLength) {
        if (vec.length < minLength) {
            throw new IllegalArgumentException(String.format("Expected vector %s of dimension %d " +
                            "to have a dimension larger than %d", stringify(vec, 2), vec.length,
                    minLength));
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
     * Throws a IllegalArgumentException at runtime if the two vectors are of different dimensions.
     */
    static void verifyEqualDimensions(double[] vecA, double[] vecB) {
        if (vecA.length != vecB.length) {
            throw new IllegalArgumentException(String.format("Expected vectors with identical " +
                    "dimensions but received:\n%sand:\n%s", stringify(vecA, 2), stringify(vecB,
                    2)));
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
     * Throws an IllegalArgumentException at runtime if the given row coordinate is out of bounds
     * of the
     * vector.
     */
    static void verifyValidCoord(double[] vec, int row) {
        if (row < 0 || row >= vec.length) {
            throw new IllegalArgumentException(String.format("Position at row=%d is out of bounds" +
                    " of the vector:\n%s", row, stringify(vec, 2)));
        }
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
     * Throws an InvalidInputException at runtime if the given matrix is not invertible.
     */
    static void verifyInvertibleMatrix(double[][] mat) {
        if (determinant(mat) == 0.0f) {
            throw new IllegalArgumentException(String.format("Expected an invertible matrix but " +
                    "received:\n%s", stringify(mat, 2)));
        }
    }
}
