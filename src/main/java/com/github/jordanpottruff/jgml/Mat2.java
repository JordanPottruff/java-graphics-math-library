package com.github.jordanpottruff.jgml;

/**
 * A matrix of dimensions 2x2.
 */
public class Mat2 extends MatN {

    /**
     * Constructs a Mat2 from a two-dimensional array of elements. The outer-array must contain two
     * inner-arrays, with each being of length two. The order of the 2D array is preserved.
     *
     * @param array the 2D array of elements.
     * @throws IllegalArgumentException if the outer-array does not contain two inner-arrays.
     * @throws IllegalArgumentException if the inner-arrays are not all of length two.
     */
    public Mat2(double[][] array) {
        super(array);
        Util.verifyExactDimension(array, 2, 2);
    }

    /**
     * Constructs a Mat2 from a matrix object. The matrix must contain two rows and two columns. The
     * order of the elements will be preserved.
     *
     * @param mat the matrix object.
     * @throws IllegalArgumentException if the matrix object is not of dimension 2x2.
     */
    public Mat2(Mat mat) {
        super(mat);
        Util.verifyExactDimension(mat.toArray(), 2, 2);
    }

    /**
     * Constructs a Mat2 from three Vec2 objects. The vectors will create the column vectors of the
     * new matrix.
     *
     * @param vec1 the first column vector.
     * @param vec2 the second column vector.
     */
    public Mat2(Vec2 vec1, Vec2 vec2) {
        super(new double[][]{vec1.toArray(), vec2.toArray()});
    }

    /**
     * Creates a 2x2 identity matrix.
     *
     * @return the identity matrix.
     */
    public static Mat2 createIdentityMatrix() {
        return new Mat2(Util.identity(2));
    }

    /**
     * {@inheritDoc}
     */
    public Mat2 invert() {
        return new Mat2(super.invert());
    }

    /**
     * {@inheritDoc}
     */
    public Mat2 add(Mat mat) {
        return new Mat2(super.add(mat));
    }

    /**
     * {@inheritDoc}
     */
    public Mat2 subtract(Mat mat) {
        return new Mat2(super.subtract(mat));
    }

    /**
     * {@inheritDoc}
     */
    public Mat2 scale(double scalar) {
        return new Mat2(super.scale(scalar));
    }

    /**
     * {@inheritDoc}
     */
    public Mat2 multiply(MatN mat) {
        return new Mat2(super.multiply(mat));
    }

    /**
     * {@inheritDoc}
     */
    public Mat2 inverse() {
        return new Mat2(super.inverse());
    }

    /**
     * A Mat2 builder that provides methods to construct a transformation matrix. The order of
     * execution of operations follows the order that the methods were called on the builder object.
     */
    public static class TransformBuilder {

        private Mat2 matrix;

        /**
         * Creates a new transformation.
         */
        public TransformBuilder() {
            matrix = createIdentityMatrix();
        }

        /**
         * Scales in both axes.
         *
         * @param x how much to scale in the x-axis.
         * @param y how much to scale in the y-axis.
         * @return the transformed builder.
         */
        public TransformBuilder scale(double x, double y) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][0] = x;
            operation[1][1] = y;
            applyOperation(operation);
            return this;
        }

        /**
         * Rotates (along the invisible third axis perpendicular to the view plane).
         *
         * @param radians the amount to rotate by, in radians.
         * @return the transformed builder.
         */
        public TransformBuilder rotate(double radians) {
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][0] = cos;
            operation[1][0] = -sin;
            operation[0][1] = sin;
            operation[1][1] = cos;
            applyOperation(operation);
            return this;
        }

        /**
         * Shears the x-axis based on the y factor.
         *
         * @param y shear factor for y-axis.
         * @return the transformed builder.
         */
        public TransformBuilder shearX(double y) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[1][0] = y;
            applyOperation(operation);
            return this;
        }

        /**
         * Shears the y-axis based on x factor.
         *
         * @param x shear factor for x-axis.
         * @return the transformed builder.
         */
        public TransformBuilder shearY(double x) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][1] = x;
            applyOperation(operation);
            return this;
        }

        /**
         * Creates the transformation matrix.
         *
         * @return the transformation matrix.
         */
        public Mat2 build() {
            return new Mat2(matrix);
        }

        private void applyOperation(double[][] operation) {
            Mat2 operationMat = new Mat2(operation);
            matrix = operationMat.multiply(matrix);
        }

    }
}
