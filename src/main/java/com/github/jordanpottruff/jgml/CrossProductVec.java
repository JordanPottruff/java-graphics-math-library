package com.github.jordanpottruff.jgml;

/**
 * A vector capable of computing a cross product in three-dimensional space.
 */
public interface CrossProductVec extends Vec {

    /**
     * Calculates the cross product of this vector with the passed vector.
     *
     * @param vec the vector being multiplied.
     * @return the cross product.
     */
    CrossProductVec cross(CrossProductVec vec);
}
