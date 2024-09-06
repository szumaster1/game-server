package core.tools

import core.game.world.map.Location

/**
 * Vector 3D.
 */
class Vector3d {
    /**
     * The X.
     */
    var x: Double

    /**
     * The Y.
     */
    var y: Double

    /**
     * The Z.
     */
    var z: Double

    /**
     * Instantiates a new [Vector3d].
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    /**
     * Instantiates a new [Vector3d].
     *
     * @param t the t
     */
    constructor(t: DoubleArray) {
        this.x = t[0]
        this.y = t[1]
        this.z = t[2]
    }

    /**
     * Instantiates a new [Vector3d].
     *
     * @param v the v
     */
    constructor(v: Vector3d) {
        this.x = v.x
        this.y = v.y
        this.z = v.z
    }

    /**
     * Instantiates a new [Vector3d].
     *
     * @param l the l
     */
    constructor(l: Location) {
        this.x = l.x.toDouble()
        this.y = l.y.toDouble()
        this.z = l.z.toDouble()
    }

    /**
     * Instantiates a new [Vector3d].
     */
    constructor() {
        this.x = 0.0
        this.y = 0.0
        this.z = 0.0
    }

    /**
     * Returns a string representation of the [Vector3d] object.
     *
     * @return the string representation
     */
    override fun toString(): String {
        return "($x, $y, $z)"
    }

    /**
     * Adds two [Vector3d] objects and returns the result.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the sum of the two vectors
     */
    fun add(v1: Vector3d, v2: Vector3d): Vector3d {
        this.x = v1.x + v2.x
        this.y = v1.y + v2.y
        this.z = v1.z + v2.z
        return this
    }

    /**
     * Adds a [Vector3d] object to this vector.
     *
     * @param v1 the vector to be added
     * @return the updated vector
     */
    fun add(v1: Vector3d): Vector3d {
        this.x += v1.x
        this.y += v1.y
        this.z += v1.z
        return this
    }

    /**
     * Subtracts two [Vector3d] objects and returns the result.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the difference of the two vectors
     */
    fun sub(v1: Vector3d, v2: Vector3d): Vector3d {
        this.x = v1.x - v2.x
        this.y = v1.y - v2.y
        this.z = v1.z - v2.z
        return this
    }

    /**
     * Subtracts a [Vector3d] object from this vector.
     *
     * @param v1 the vector to be subtracted
     * @return the updated vector
     */
    fun sub(v1: Vector3d): Vector3d {
        this.x -= v1.x
        this.y -= v1.y
        this.z -= v1.z
        return this
    }

    /**
     * Negates the [Vector3d] object and returns the result.
     *
     * @param v1 the vector to be negated
     * @return the negated vector
     */
    fun negate(v1: Vector3d): Vector3d {
        this.x = -v1.x
        this.y = -v1.y
        this.z = -v1.z
        return this
    }

    /**
     * Negates the [Vector3d] object.
     *
     * @return the negated vector
     */
    fun negate(): Vector3d {
        this.x = -this.x
        this.y = -this.y
        this.z = -this.z
        return this
    }

    /**
     * Computes the cross product of this vector and another vector.
     *
     * @param v2 the other vector
     * @return the cross product vector
     */
    fun cross(v2: Vector3d): Vector3d {
        this.cross(Vector3d(this), v2)
        return this
    }

    /**
     * Computes the cross product of two vectors and stores the result in this vector.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the cross product vector
     */
    fun cross(v1: Vector3d, v2: Vector3d): Vector3d {
        this.x = v1.y * v2.z - v1.z * v2.y
        this.y = v1.z * v2.x - v1.x * v2.z
        this.z = v1.x * v2.y - v1.y * v2.x
        return this
    }

    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param v2 the other vector
     * @return the dot product
     */
    fun dot(v2: Vector3d): Double {
        return (this.x * v2.x + this.y * v2.y + this.z * v2.z)
    }

    /**
     * Computes the L2 norm (Euclidean length) of this vector.
     *
     * @return the L2 norm
     */
    fun l2norm(): Double {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z)
    }

    /**
     * Normalizes this vector.
     *
     * @return the normalized vector
     */
    fun normalize(): Vector3d {
        val norm = this.l2norm()
        this.x /= norm
        this.y /= norm
        this.z /= norm
        return this
    }

    companion object {
        /**
         * Computes the signed angle between two vectors.
         *
         * @param v1 the first vector
         * @param v2 the second vector
         * @param n  the normal vector
         * @return the signed angle
         */
        fun signedAngle(v1: Vector3d, v2: Vector3d, n: Vector3d): Double {
            return Math.atan2(Vector3d().cross(v1, v2).dot(n), v1.dot(v2))
        }
    }


    /**
     * Checks if this vector is equal to another vector.
     *
     * @param other the other vector
     * @return true if the vectors are equal, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vector3d) return false
        return (this.x == other.x && this.y == other.y && this.z == other.z)
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    /**
     * Checks if this vector is approximately equal to another vector within a given epsilon.
     *
     * @param v1      the other vector
     * @param epsilon the epsilon value
     * @return true if the vectors are approximately equal, false otherwise
     */
    fun epsilonEquals(v1: Vector3d, epsilon: Double): Boolean {
        var diff: Double

        diff = this.x - v1.x
        if (diff.isNaN()) return false
        if (Math.abs(diff) > epsilon) return false

        diff = this.y - v1.y
        if (diff.isNaN()) return false
        if (Math.abs(diff) > epsilon) return false

        diff = this.z - v1.z
        if (diff.isNaN()) return false
        if (Math.abs(diff) > epsilon) return false

        return true
    }
}
