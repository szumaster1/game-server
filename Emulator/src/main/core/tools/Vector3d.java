package core.tools;

import core.game.world.map.Location;

/**
 * Vector 3D.
 */
public class Vector3d {
    /**
     * The X.
     */
    public double x;
    /**
     * The Y.
     */
    public double y;
    /**
     * The Z.
     */
    public double z;

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param t the t
     */
    public Vector3d(double[] t) {
        this.x = t[0];
        this.y = t[1];
        this.z = t[2];
    }

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param v the v
     */
    public Vector3d(Vector3d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Instantiates a new Vector 3 d.
     *
     * @param l the l
     */
    public Vector3d(Location l) {
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
    }

    /**
     * Instantiates a new Vector 3 d.
     */
    public Vector3d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    /**
     * Returns a string representation of the Vector3d object.
     *
     * @return the string representation
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    /**
     * Adds two Vector3d objects and returns the result.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the sum of the two vectors
     */
    public final Vector3d add(Vector3d v1, Vector3d v2) {
        this.x = v1.x + v2.x;
        this.y = v1.y + v2.y;
        this.z = v1.z + v2.z;
        return this;
    }

    /**
     * Adds a Vector3d object to this vector.
     *
     * @param v1 the vector to be added
     * @return the updated vector
     */
    public final Vector3d add(Vector3d v1) {
        this.x += v1.x;
        this.y += v1.y;
        this.z += v1.z;
        return this;
    }

    /**
     * Subtracts two Vector3d objects and returns the result.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the difference of the two vectors
     */
    public final Vector3d sub(Vector3d v1, Vector3d v2) {
        this.x = v1.x - v2.x;
        this.y = v1.y - v2.y;
        this.z = v1.z - v2.z;
        return this;
    }

    /**
     * Subtracts a Vector3d object from this vector.
     *
     * @param v1 the vector to be subtracted
     * @return the updated vector
     */
    public final Vector3d sub(Vector3d v1) {
        this.x -= v1.x;
        this.y -= v1.y;
        this.z -= v1.z;
        return this;
    }

    /**
     * Negates the Vector3d object and returns the result.
     *
     * @param v1 the vector to be negated
     * @return the negated vector
     */
    public final Vector3d negate(Vector3d v1) {
        this.x = -v1.x;
        this.y = -v1.y;
        this.z = -v1.z;
        return this;
    }

    /**
     * Negates the Vector3d object.
     *
     * @return the negated vector
     */
    public final Vector3d negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    /**
     * Computes the cross product of this vector and another vector.
     *
     * @param v2 the other vector
     * @return the cross product vector
     */
    public final Vector3d cross(Vector3d v2) {
        this.cross(new Vector3d(this), v2);
        return this;
    }

    /**
     * Computes the cross product of two vectors and stores the result in this vector.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the cross product vector
     */
    public final Vector3d cross(Vector3d v1, Vector3d v2) {
        this.x = v1.y * v2.z - v1.z * v2.y;
        this.y = v1.z * v2.x - v1.x * v2.z;
        this.z = v1.x * v2.y - v1.y * v2.x;
        return this;
    }

    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param v2 the other vector
     * @return the dot product
     */
    public final double dot(Vector3d v2) {
        return (this.x * v2.x + this.y * v2.y + this.z * v2.z);
    }

    /**
     * Computes the L2 norm (Euclidean length) of this vector.
     *
     * @return the L2 norm
     */
    public final double l2norm() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Normalizes this vector.
     *
     * @return the normalized vector
     */
    public final Vector3d normalize() {
        double norm = this.l2norm();
        this.x /= norm;
        this.y /= norm;
        this.z /= norm;
        return this;
    }

    /**
     * Computes the signed angle between two vectors.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @param n  the normal vector
     * @return the signed angle
     */
    public static double signedAngle(final Vector3d v1, final Vector3d v2, final Vector3d n) {
        return Math.atan2(new Vector3d().cross(v1, v2).dot(n), v1.dot(v2));
    }

    /**
     * Checks if this vector is equal to another vector.
     *
     * @param v1 the other vector
     * @return true if the vectors are equal, false otherwise
     */
    public boolean equals(final Vector3d v1) {
        return (this.x == v1.x && this.y == v1.y && this.z == v1.z);
    }

    /**
     * Checks if this vector is approximately equal to another vector within a given epsilon.
     *
     * @param v1      the other vector
     * @param epsilon the epsilon value
     * @return true if the vectors are approximately equal, false otherwise
     */
    public boolean epsilonEquals(final Vector3d v1, double epsilon) {
        double diff;

        diff = this.x - v1.x;
        if (Double.isNaN(diff)) return false;
        if ((diff < 0 ? -diff : diff) > epsilon) return false;

        diff = this.y - v1.y;
        if (Double.isNaN(diff)) return false;
        if ((diff < 0 ? -diff : diff) > epsilon) return false;

        diff = this.z - v1.z;
        if (Double.isNaN(diff)) return false;
        if ((diff < 0 ? -diff : diff) > epsilon) return false;

        return true;
    }
}
