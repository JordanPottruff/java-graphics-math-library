package com.github.jordanpottruff.jgml;

/**
 * A matrix of dimensions 3x3.
 */
public class Mat3 extends MatN {

    /**
     * Constructs a Mat3 from a two-dimensional array of elements. The outer-array must contain
     * three inner-arrays, with each being of length three. The order of the 2D array is preserved.
     *
     * @param array the 2D array of elements.
     * @throws IllegalArgumentException if the outer-array does not contain three inner-arrays.
     * @throws IllegalArgumentException if the inner-arrays are not all of length three.
     */
    public Mat3(double[][] array) {
        super(array);
        Util.verifyExactDimension(array, 3, 3);
    }

    /**
     * Constructs a Mat3 from a matrix object. The matrix must contain three rows and three columns.
     * The order of the elements will be preserved.
     *
     * @param mat the matrix object.
     * @throws IllegalArgumentException if the matrix object is not of dimension 3x3.
     */
    public Mat3(Mat mat) {
        super(mat);
        Util.verifyExactDimension(mat.toArray(), 3, 3);
    }

    /**
     * Constructs a Mat3 from three Vec3 objects. The vectors will create the column vectors of the
     * new matrix.
     *
     * @param vec1 the first column vector.
     * @param vec2 the second column vector.
     * @param vec3 the third column vector.
     */
    public Mat3(Vec3 vec1, Vec3 vec2, Vec3 vec3) {
        super(new double[][]{vec1.toArray(), vec2.toArray(), vec3.toArray()});
    }

    /**
     * Creates a 3x3 identity matrix.
     *
     * @return the identity matrix.
     */
    public static Mat3 createIdentityMatrix() {
        return new Mat3(Util.identity(3));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat3 invert() {
        return new Mat3(super.invert());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat3 add(Mat mat) {
        return new Mat3(super.add(mat));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat3 subtract(Mat mat) {
        return new Mat3(super.subtract(mat));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat3 scale(double scalar) {
        return new Mat3(super.scale(scalar));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat3 multiply(MatN mat) {
        return new Mat3(super.multiply(mat));
    }

    /**
     * Calculates the multiplication of this 3x3 matrix with the passed 3-dimensional vector.
     *
     * @param vec the 3-dimensional vector to multiply by.
     * @return the product, always a 3-dimensional vector.
     */
    public Vec3 multiply(Vec3 vec) {
        return new Vec3(super.multiply(vec));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat3 inverse() {
        return new Mat3(super.inverse());
    }

    /**
     * A Mat3 builder that provides methods to construct an affine transformation. The order of
     * execution of operations follows the order that the methods were called on the builder object.
     */
    public static class TransformBuilder {

        private Mat3 matrix;

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
         * Translates in both axes.
         *
         * @param x how much to translate along the x-axis.
         * @param y how much to translate along the y-axis.
         * @return the transformed builder.
         */
        public TransformBuilder translate(double x, double y) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[2][0] = x;
            operation[2][1] = y;
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
        public Mat3 build() {
            return new Mat3(matrix);
        }

        private void applyOperation(double[][] operation) {
            Mat3 operationMat = new Mat3(operation);
            matrix = operationMat.multiply(matrix);
        }

    }
}
