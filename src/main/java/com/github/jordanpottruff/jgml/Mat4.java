package com.github.jordanpottruff.jgml;

/**
 * A matrix of dimensions 4x4.
 */
public class Mat4 extends MatN {

    /**
     * Constructs a Mat4 from a two-dimensional array of elements. The outer-array must contain four
     * inner-arrays, with each being of length four. The order of the 2D array is preserved.
     *
     * @param array the 2D array of elements.
     * @throws IllegalArgumentException if the outer-array does not contain four inner-arrays.
     * @throws IllegalArgumentException if the inner-arrays are not all of length four.
     */
    public Mat4(double[][] array) {
        super(array);
        Util.verifyExactDimension(array, 4, 4);
    }

    /**
     * Constructs a Mat4 from a matrix object. The matrix must contain four rows and four columns.
     * The order of the elements will be preserved.
     *
     * @param mat the matrix object.
     * @throws IllegalArgumentException if the matrix object is not of dimension 4x4.
     */
    public Mat4(Mat mat) {
        super(mat);
        Util.verifyExactDimension(mat.toArray(), 4, 4);
    }

    /**
     * Constructs a Mat4 from four Vec4 objects. The vectors will create the column vectors of the
     * new matrix.
     *
     * @param vec1 the first column vector.
     * @param vec2 the second column vector.
     * @param vec3 the third column vector.
     * @param vec4 the fourth column vector.
     */
    public Mat4(Vec4 vec1, Vec4 vec2, Vec4 vec3, Vec4 vec4) {
        super(new double[][]{vec1.toArray(), vec2.toArray(), vec3.toArray(), vec4.toArray()});
    }

    /**
     * Creates a 4x4 identity matrix.
     *
     * @return the identity matrix.
     */
    public static Mat4 createIdentityMatrix() {
        return new Mat4(Util.identity(4));
    }

    /**
     * Returns the product of multiplying a series of 4x4 matrices together. The order of the
     * arguments represents the order of multiplications such that A*B*C is equivalent to {@code
     * chain(A, B, C)}.
     *
     * @param mat the first matrix of the product chain.
     * @param matrices the rest of the matrices of the product chain.
     * @return the product of the series of matrices.
     */
    public static Mat4 chain(Mat4 mat, Mat4... matrices) {
        return new Mat4(MatMN.chain(mat, matrices));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat4 invert() {
        return new Mat4(super.invert());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat4 add(Mat mat) {
        return new Mat4(super.add(mat));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat4 subtract(Mat mat) {
        return new Mat4(super.subtract(mat));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat4 scale(double scalar) {
        return new Mat4(super.scale(scalar));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat4 multiply(MatN mat) {
        return new Mat4(super.multiply(mat));
    }

    /**
     * Calculates the multiplication of this 4x4 matrix with the passed 4-dimensional vector.
     *
     * @param vec the 4-dimensional vector to multiply by.
     * @return the product, always a 4-dimensional vector.
     */
    public Vec4 multiply(Vec4 vec) {
        return new Vec4(super.multiply(vec));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mat4 inverse() {
        return new Mat4(super.inverse());
    }

    /**
     * A Mat4 builder that provides methods to construct an affine transformation. The order of
     * execution of operations follows the order that the methods were called on the builder object.
     */
    public static class TransformBuilder {

        private Mat4 matrix;

        /**
         * Creates a new transformation.
         */
        public TransformBuilder() {
            matrix = createIdentityMatrix();
        }

        /**
         * Scales in all three axes.
         *
         * @param x how much to scale in the x-axis.
         * @param y how much to scale in the y-axis.
         * @param z how much to scale in the z-axis.
         * @return the transformed builder.
         */
        public TransformBuilder scale(double x, double y, double z) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][0] = x;
            operation[1][1] = y;
            operation[2][2] = z;
            applyOperation(operation);
            return this;
        }

        /**
         * Scales along the x-axis.
         *
         * @param factor how much to scale in the x-axis.
         * @return the transformed builder.
         */
        public TransformBuilder scaleX(double factor) {
            return scale(factor, 1.0, 1.0);
        }

        /**
         * Scales along the y-axis.
         *
         * @param factor how much to scale in the y-axis.
         * @return the transformed builder.
         */
        public TransformBuilder scaleY(double factor) {
            return scale(1.0, factor, 1.0);
        }

        /**
         * Scales along the z-axis.
         *
         * @param factor how much to scale in the z-axis.
         * @return the transformed builder.
         */
        public TransformBuilder scaleZ(double factor) {
            return scale(1.0, 1.0, factor);
        }

        /**
         * Translates in all three axes.
         *
         * @param x how much to translate along the x-axis.
         * @param y how much to translate along the y-axis.
         * @param z how much to translate along the z-axis.
         * @return the transformed builder.
         */
        public TransformBuilder translate(double x, double y, double z) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[3][0] = x;
            operation[3][1] = y;
            operation[3][2] = z;
            applyOperation(operation);
            return this;
        }

        /**
         * Translates in all three axes.
         *
         * @param amounts how much to translate along each axis, represented as a Vec3.
         * @return the transformed builder.
         */
        public TransformBuilder translate(Vec3 amounts) {
            return translate(amounts.x(), amounts.y(), amounts.z());
        }

        /**
         * Translates along the x-axis.
         *
         * @param factor how much to translate along the x-axis.
         * @return the transformed builder.
         */
        public TransformBuilder translateX(double factor) {
            return translate(factor, 0.0, 0.0);
        }

        /**
         * Translates along the y-axis.
         *
         * @param factor how much to translate along the y-axis.
         * @return the transformed builder.
         */
        public TransformBuilder translateY(double factor) {
            return translate(0.0, factor, 0.0);
        }

        /**
         * Translates along the z-axis.
         *
         * @param factor how much to translate along the z-axis.
         * @return the transformed builder.
         */
        public TransformBuilder translateZ(double factor) {
            return translate(0.0, 0.0, factor);
        }

        /**
         * Rotates along the x-axis.
         *
         * @param radians the amount to rotate by, in radians.
         * @return the transformed builder.
         */
        public TransformBuilder rotateX(double radians) {
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double[][] operation = createIdentityMatrix().toArray();
            operation[1][1] = cos;
            operation[2][1] = -sin;
            operation[1][2] = sin;
            operation[2][2] = cos;
            applyOperation(operation);
            return this;
        }

        /**
         * Rotates along the y-axis.
         *
         * @param radians the amount to rotate by, in radians.
         * @return the transformed builder.
         */
        public TransformBuilder rotateY(double radians) {
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][0] = cos;
            operation[2][0] = sin;
            operation[0][2] = -sin;
            operation[2][2] = cos;
            applyOperation(operation);
            return this;
        }

        /**
         * Rotates along the z-axis.
         *
         * @param radians the amount to rotate by, in radians.
         * @return the transformed builder.
         */
        public TransformBuilder rotateZ(double radians) {
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
         * Shears the x-axis based on the y and z shear factors.
         *
         * @param y shear factor for y-axis.
         * @param z shear factor for z-axis.
         * @return the transformed builder.
         */
        public TransformBuilder shearX(double y, double z) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[1][0] = y;
            operation[2][0] = z;
            applyOperation(operation);
            return this;
        }

        /**
         * Shears the y-axis based on the x and z shear factors.
         *
         * @param x shear factor for x-axis.
         * @param z shear factor for z-axis.
         * @return the transformed builder.
         */
        public TransformBuilder shearY(double x, double z) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][1] = x;
            operation[2][1] = z;
            applyOperation(operation);
            return this;
        }

        /**
         * Shears the z-axis based on the x and y shear factors.
         *
         * @param x shear factor for x-axis.
         * @param y shear factor for y-axis.
         * @return the transformed builder.
         */
        public TransformBuilder shearZ(double x, double y) {
            double[][] operation = createIdentityMatrix().toArray();
            operation[0][2] = x;
            operation[1][2] = y;
            applyOperation(operation);
            return this;
        }

        /**
         * Creates the transformation matrix.
         *
         * @return the transformation matrix.
         */
        public Mat4 build() {
            return new Mat4(matrix);
        }

        private void applyOperation(double[][] operation) {
            Mat4 operationMat = new Mat4(operation);
            matrix = operationMat.multiply(matrix);
        }

    }
}
