package com.github.jordanpottruff.jgml;

/**
 * A MatN is a generalized square matrix of any dimensions N x N where N is greater than or equal to
 * two.
 */
public class MatN extends MatMN implements SquareMat {

    /**
     * Constructs a MatN from a two-dimensional array of elements. The outer-array must contain two
     * or more inner-arrays, with each being the same length (uniform) and containing at least two
     * elements. To satisfy the requirements of a square matrix, the number of inner-arrays must
     * equal the length of the inner-arrays. The order of the 2D array is preserved.
     *
     * @param array the 2D array of elements.
     * @throws IllegalArgumentException if the inner-arrays are not of equal length (non-uniform).
     * @throws IllegalArgumentException if the outer-array is not of length two or greater or if the
     *                                  inner-arrays are not of length two or greater (MatN must be
     *                                  of dimension 2x2 or larger).
     * @throws IllegalArgumentException if the 2D array is not "square", or contains an equal number
     *                                  of rows and columns.
     */
    public MatN(double[][] array) {
        super(array);
        Util.verifySquareMatrix(array);
    }

    /**
     * Constructs a MatN from a matrix object. The matrix must contain the same number of rows and
     * columns, with dimensions of 2x2 or larger. The order of the elements will be preserved.
     *
     * @param mat the matrix object.
     * @throws IllegalArgumentException if the matrix object is not of dimension 2x2 or larger.
     * @throws IllegalArgumentException if the matrix object is not a square matrix (contains the
 *                                      same number of rows and columns).
     */
    public MatN(Mat mat) {
        super(mat);
        Util.verifySquareMatrix(mat.toArray());
    }

    /**
     * Creates a MatN from an iterable of vectors. The iterable must contain at least two vectors,
     * each of the same dimension and containing at least two elements. These vectors will be the
     * column vectors of the new MatN object. The length of these column vectors must be equal to
     * the number of column vectors in the iterable. The order of the column vectors will be based
     * on the order provided by the iterable. The order of the elements within the column vectors
     * will be preserved.
     *
     * @param vectors the iterable of column vectors.
     * @return a new MatN composed of the iterable's column vectors.
     * @throws IllegalArgumentException if the vectors are not of equal dimension (non-uniform).
     * @throws IllegalArgumentException if the iterable does not contain at least two vectors or if
     *                                  the vectors are not of dimension 2 or greater (MatN must be
     *                                  of dimension 2x2 or greater).
     * @throws IllegalArgumentException if iterable of column vectors does not form a square matrix
     *                                  (contains the same number of rows and columns).
     */
    public static MatN createFrom(Iterable<Vec> vectors) {
        MatMN mat = MatMN.createFrom(vectors);
        Util.verifySquareMatrix(mat.toArray());
        return new MatN(mat);
    }

    /**
     * {@inheritDoc}
     */
    public MatN invert() {
        return new MatN(super.invert());
    }

    /**
     * {@inheritDoc}
     */
    public MatN add(Mat mat) {
        return new MatN(super.add(mat));
    }

    /**
     * {@inheritDoc}
     */
    public MatN subtract(Mat mat) {
        return new MatN(super.subtract(mat));
    }

    /**
     * {@inheritDoc}
     */
    public MatN scale(double scalar) {
        return new MatN(super.scale(scalar));
    }

    /**
     * Calculates the multiplication of this square matrix by the passed square matrix.
     *
     * @param mat the square matrix to multiply by.
     * @return the product, always a square matrix.
     * @throws IllegalArgumentException if dimensions of these matrices differs.
     */
    public MatN multiply(MatN mat) {
        return new MatN(super.multiply(mat));
    }

    /**
     * {@inheritDoc}
     */
    public double determinant() {
        return Util.determinant(matrix);
    }

    /**
     * {@inheritDoc}
     */
    public MatN inverse() {
        return new MatN(Util.inverse(matrix));
    }
}
