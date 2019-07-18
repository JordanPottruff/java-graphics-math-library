package com.github.jordanpottruff.jgml.common;

public class OperationsUtil {



    private OperationsUtil() {
        // Intentionally left blank to prevent instantiation.
    }

    /**
     * Returns the magnitude of the input vector.
     *
     * @param vec operand.
     * @return the magnitude of the operand as a doubleing point value.
     */
    public static double magnitude(double[] vec) {
        double sum = 0;
        for (int i = 0; i < vec.length; i++) {
            sum += Math.pow(vec[i], 2.0f);
        }
        return (double) Math.sqrt(sum);
    }

    /**
     * Returns the normalized version of the input vector as a new vector.
     *
     * @param vec operand.
     * @return the normalization of the operand represented by a doubleing point array.
     */
    public static double[] normalize(double[] vec) {
        double[] newVec = new double[vec.length];
        double mag = magnitude(vec);
        for (int i = 0; i < vec.length; i++) {
            newVec[i] = vec[i] / mag;
        }
        return newVec;
    }

    /**
     * Returns a new vector such that the components of the vector are the negation of the input vector's components.
     *
     * @param vec operand.
     * @return the inverse vector of the operand represented by a doubleing point array.
     */
    public static double[] invert(double[] vec) {
        double[] newVec = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            newVec[i] = -vec[i];
        }
        return newVec;
    }

    /**
     * Returns a new vector that is the sum of the two input vectors.
     *
     * @param vecA first operand.
     * @param vecB second operand.
     * @return the sum of the operands.
     */
    public static double[] add(double[] vecA, double[] vecB) {
        VerificationUtil.verifyEqualDimensions(vecA, vecB);
        int n = vecA.length;
        double[] sum = new double[n];
        for (int i = 0; i < n; i++) {
            sum[i] = vecA[i] + vecB[i];
        }
        return sum;
    }

    /**
     * Returns a new vector that is the subtraction of vecB from vecA.
     *
     * @param vecA operand to be subtracted from.
     * @param vecB operand to subtract.
     * @return the difference between vecA and vecB.
     */
    public static double[] subtract(double[] vecA, double[] vecB) {
        // Use add() for op and for compatibility checking.
        return add(vecA, invert(vecB));
    }

    /**
     * Returns a new vector that is the dot product of the two input vectors.
     *
     * @param vecA first operand.
     * @param vecB second operand.
     * @return the dot product of vecA and vecB.
     */
    public static double multiply(double[] vecA, double[] vecB) {
        VerificationUtil.verifyEqualDimensions(vecA, vecB);
        int n = vecA.length;
        double dot = 0;
        for (int i = 0; i < n; i++) {
            dot += vecA[i] * vecB[i];
        }
        return dot;
    }

    /**
     * Returns the cross product of the two input vectors.
     *
     * @param vecA first operand.
     * @param vecB second operand.
     * @return the cross product of VecA and VecB, an array of length 3.
     */
    public static double[] cross(double[] vecA, double[] vecB) {
        VerificationUtil.verifyMinimumLength(vecA, 3);
        VerificationUtil.verifyMinimumLength(vecB, 3);

        double[] crossProduct = new double[3];
        crossProduct[0] = vecA[1]*vecB[2] - vecA[2]*vecB[1];
        crossProduct[1] = vecA[2]*vecB[0] - vecA[0]*vecB[2];
        crossProduct[2] = vecA[0]*vecB[1] - vecA[1]*vecB[0];

        return crossProduct;
    }


    /**
     * Returns a new matrix such that the components of the matrix are the negation of the input matrix's components.
     *
     * @param mat matrix operand.
     * @return the inverse matrix of the operand represented by a 2D doubleing point array.
     */
    public static double[][] invert(double[][] mat) {
        double[][] result = new double[mat.length][];

        for(int col=0; col<mat.length; col++) {
            double[] invRow = new double[mat[col].length];

            for(int row=0; row<invRow.length; row++) {
                invRow[row] = -mat[col][row];
            }

            result[col] = invRow;
        }

        return result;
    }

    /**
     * Returns a new matrix that is the sum of the two input matrices.
     *
     * @param matA first operand.
     * @param matB second operand.
     * @return the sum of the operands.
     */
    public static double[][] add(double[][] matA, double[][] matB) {
        VerificationUtil.verifyEqualDimensions(matA, matB);
        int rows = matA[0].length;
        int cols = matA.length;

        double[][] result = new double[cols][rows];

        for(int ci=0; ci<cols; ci++) {
            for(int ri=0; ri<rows; ri++) {
                result[ci][ri] = matA[ci][ri] + matB[ci][ri];
            }
        }

        return result;
    }

    /**
     * Returns a new matrix that is the subtraction of matB from matA.
     *
     * @param matA operand to be subtracted from.
     * @param matB operand to subtract.
     * @return the difference between matA and matB.
     */
    public static double[][] subtract(double[][] matA, double[][] matB) {
        VerificationUtil.verifyEqualDimensions(matA, matB);
        return add(matA, invert(matB));
    }

    /**
     * Returns a new matrix that is the input matrix scaled by the input scalar.
     *
     * @param mat the matrix operand.
     * @param scalar the scalar operand.
     * @return the scaled matrix.
     */
    public static double[][] scale(double[][] mat, double scalar) {
        VerificationUtil.verifyUniformMatrix(mat);
        int rows = mat[0].length;
        int cols = mat.length;

        double[][] result = new double[cols][rows];

        for(int ci=0; ci<cols; ci++) {
            for(int ri=0; ri<rows; ri++) {
                result[ci][ri] = scalar*mat[ci][ri];
            }
        }

        return result;
    }

    /**
     * Returns a new vector that is the multiplication of the input matrix and the input vector. The multiplication is
     * performed in the order of 'mat' * 'vec'.
     *
     * @param mat the matrix operand.
     * @param vec the vector operand.
     * @return the product of the matrix and vector operands.
     */
    public static double[] multiply(double[][] mat, double[] vec) {
        VerificationUtil.verifyOperableDimensions(mat, vec);
        int matCols = mat.length;
        int matRows = mat[0].length;

        double[] result = new double[matRows];

        for(int row=0; row<matRows; row++) {
            for(int col=0; col<matCols; col++) {
                result[row] += mat[col][row] * vec[col];
            }
        }

        return result;
    }

    /**
     * Returns a new matrix that is the multiplication of the two input matrices. The multiplication is performed in the
     * order of 'matA' * 'matB'.
     *
     * @param matA the first matrix operand.
     * @param matB the second matrix operand.
     * @return the product of the two input matrices.
     */
    public static double[][] multiply(double[][] matA, double[][] matB) {
        VerificationUtil.verifyOperableDimensions(matA, matB);
        int matACols = matA.length;
        int matARows = matA[0].length;
        int matBCols = matB.length;
        int matBRows = matB[0].length;

        double[][] result = new double[matARows][matBCols];

        for(int row=0; row<matARows; row++) {
            for(int col=0; col<matBCols; col++) {
                for(int i=0; i<matACols; i++) {
                    result[col][row] += matA[i][row] * matB[col][i];
                }
            }
        }

        return result;
    }

    /**
     * Returns a submatrix formed by the removal of the given row and column.
     *
     * @param mat operand.
     * @param row index of row to be removed.
     * @param col index of column to be removed.
     * @return the operand matrix with the given row and column removed.
     */
    public static double[][] submatrix(double[][] mat, int row, int col) {
        VerificationUtil.verifyUniformMatrix(mat);
        VerificationUtil.verifyValidCoord(mat, row, col);
        double[][] sub = new double[mat.length-1][mat[0].length-1];

        int cSub = 0;
        for(int ci=0; ci<mat.length; ci++) {
            if(ci == col) continue;

            int rSub = 0;
            for(int ri=0; ri<mat[0].length; ri++) {
                if(ri == row) continue;
                sub[cSub][rSub] = mat[ci][ri];
                rSub++;
            }
            cSub++;
        }
        return sub;
    }

    /**
     * Returns the minor of the matrix given a selected row and column. The minor of a matrix is the determinant of the
     * submatrix formed by removing the given row and column.
     *
     * @param mat operand.
     * @param row row to be removed to compute minor.
     * @param col column to be removed to compute minor.
     * @return the minor of the operand matrix.
     */
    public static double minor(double[][] mat, int row, int col) {
        VerificationUtil.verifySquareMatrix(mat);
        VerificationUtil.verifyValidCoord(mat, row, col);

        double[][] sub = submatrix(mat, row, col);
        return determinant(sub);
    }

    /**
     * Returns the cofactor of a matrix at (row,col). The cofactor of a matrix at (row,col) is the value of the matrix
     * at (row,col) multiplied by the minor of the matrix at (row,col).
     *
     * @param mat operand.
     * @param row selected row for the cofactor.
     * @param col selected column for the cofactor.
     * @return the cofactor of the operand matrix at coordinate (row,col).
     */
    public static double cofactor(double[][] mat, int row, int col) {
        VerificationUtil.verifySquareMatrix(mat);
        VerificationUtil.verifyValidCoord(mat, row, col);

        return (double)Math.pow(-1, row+col+2) * minor(mat, row, col);
    }

    /**
     * Returns the determinant of a matrix, using a recursive Laplace cofactor expansion.
     *
     * @param mat operand.
     * @return the determinant of the operand matrix.
     */
    public static double determinant(double[][] mat) {
        VerificationUtil.verifySquareMatrix(mat);
        int dim = mat.length;
        if(dim == 1) {
            return mat[0][0];
        } else if(dim == 2) {
            return mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1];
        } else {
            double det = 0;
            for(int ri=0; ri<dim; ri++) {
                det += mat[0][ri] * cofactor(mat, ri, 0);
            }
            return det;
        }
    }

    /**
     * Returns the inverse of the input matrix. Uses cofactor/adjugate/determinant to calculate the answer. Will throw
     * an exception if the matrix is not invertible.
     *
     * @param mat the matrix to inverse.
     * @return the inverse of the matrix.
     */
    public static double[][] inverse(double[][] mat) {
        VerificationUtil.verifySquareMatrix(mat);
        VerificationUtil.verifyInvertibleMatrix(mat);
        double[][] result = new double[mat.length][mat[0].length];

        for(int col=0; col<mat.length; col++) {
            for(int row=0; row<mat[0].length; row++) {
                // Creating adjugate by transposing cofactors
                result[col][row] = cofactor(mat, col, row);
            }
        }

        return scale(result, 1/determinant(mat));
    }

    /**
     * Converts a double-array vector into a string representation. Each component of the vector is surrounded by
     * brackets and put onto a newline.
     *
     * @param vec the vector to be stringified.
     * @param decimals the number of decimals that each component should be formatted to.
     * @return a string representation of the vector.
     */
    public static String stringify(double[] vec, int decimals) {
        if(vec.length == 0) {
            return "[]";
        }
        String result = "";

        // Find the length of the longest string representation of the components of the vector.
        int maxStrLen = Integer.MIN_VALUE;
        for(double component: vec) {
            maxStrLen = Math.max(maxStrLen, String.format("%."+decimals+"f", component).length());
        }

        // Build the result string, justifying each component appropriately given the length of the longest one.
        for(double component: vec) {
            result += String.format("[%"+maxStrLen+"."+decimals+"f]\n", component);
        }
        return result;
    }

    /**
     * Converts a 2D double-array matrix into a string representation. Each column vector's component is surrounded by
     * brackets and put on a new line.
     *
     * @param mat the matrix to be stringified.
     * @param decimals the number of decimals that each component should be formatted to.
     * @return a string representation of the matrix.
     */
    public static String stringify(double[][] mat, int decimals) {
        if(mat.length == 0) {
            return "[]";
        }

        decimals = Math.max(decimals, 0);   // Eliminate negative integers.
        String result = "";
        // Store string values in temporary matrix:
        String[][] strMat = new String[mat.length][];
        // Setup the string matrix columns and record the length of the longest column to help with traversal later.
        int maxColLen = Integer.MIN_VALUE;
        for(int col=0; col<mat.length; col++) {
            maxColLen = Math.max(maxColLen, mat[col].length);
            strMat[col] = new String[mat[col].length];
        }

        // Assign values to the string matrix and record the length of the longest string encountered.
        int maxStrLen = Integer.MIN_VALUE;
        for(int row=0; row<maxColLen; row++) {
            for(int col=0; col<mat.length; col++) {
                // Need to make sure that the current column is long enough for 'row' to be a valid index.
                if(row < mat[col].length) {
                    String valueStr = String.format("%."+decimals+"f", mat[col][row]);
                    strMat[col][row] = valueStr;

                    maxStrLen = Math.max(maxStrLen, valueStr.length());
                }
            }
        }

        // Finally, traverse the string array and format the strings to be correctly justified using the max string
        // length recorded above.
        for(int row=0; row<maxColLen; row++) {
            for(int col=0; col<mat.length; col++) {
                if(row < mat[col].length) {
                    result += String.format("[%"+maxStrLen+"s]", strMat[col][row]);
                } else {
                    result += String.format("[%"+maxStrLen+"s]", "");
                }
            }
            result += "\n";
        }

        return result;
    }
}
