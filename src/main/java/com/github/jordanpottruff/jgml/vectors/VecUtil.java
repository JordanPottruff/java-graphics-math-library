package com.github.jordanpottruff.jgml.vectors;

class VecUtil {

    private VecUtil() {
        // Intentionally left blank; prevents instantiation.
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
     * Returns a new vector that is the subtraction of vecB from vecA.
     */
    static double[] subtract(double[] vecA, double[] vecB) {
        // Use add() for op and for compatibility checking.
        return add(vecA, invert(vecB));
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

    /**
     * Converts a double-array vector into a string representation. Each component of the vector
     * is surrounded by
     * brackets and put onto a newline.
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
     * Throws an IllegalArgumentException at runtime if the vector is not of the required dimension.
     */
    static void verifyExactDimension(double[] vec, int rows) {
        if (vec.length != rows) {
            throw new IllegalArgumentException(String.format("Expected a vector with %d rows, but" +
                    " received: \n%s", rows, stringify(vec, 2)));
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


}
