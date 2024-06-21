package core.utilities;

import core.game.world.map.Location;

/**
 * The type Vector 3 d.
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

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    /**
     * Add vector 3 d.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the vector 3 d
     */
    public final Vector3d add(Vector3d v1, Vector3d v2) {
        this.x = v1.x + v2.x;
        this.y = v1.y + v2.y;
        this.z = v1.z + v2.z;
        return this;
    }

    /**
     * Add vector 3 d.
     *
     * @param v1 the v 1
     * @return the vector 3 d
     */
    public final Vector3d add(Vector3d v1) {
        this.x += v1.x;
        this.y += v1.y;
        this.z += v1.z;
        return this;
    }

    /**
     * Sub vector 3 d.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the vector 3 d
     */
    public final Vector3d sub(Vector3d v1, Vector3d v2) {
        this.x = v1.x - v2.x;
        this.y = v1.y - v2.y;
        this.z = v1.z - v2.z;
        return this;
    }

    /**
     * Sub vector 3 d.
     *
     * @param v1 the v 1
     * @return the vector 3 d
     */
    public final Vector3d sub(Vector3d v1) {
        this.x -= v1.x;
        this.y -= v1.y;
        this.z -= v1.z;
        return this;
    }

    /**
     * Negate vector 3 d.
     *
     * @param v1 the v 1
     * @return the vector 3 d
     */
    public final Vector3d negate(Vector3d v1) {
        this.x = -v1.x;
        this.y = -v1.y;
        this.z = -v1.z;
        return this;
    }

    /**
     * Negate vector 3 d.
     *
     * @return the vector 3 d
     */
    public final Vector3d negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    /**
     * Cross vector 3 d.
     *
     * @param v2 the v 2
     * @return the vector 3 d
     */
    public final Vector3d cross(Vector3d v2) {
        this.cross(new Vector3d(this), v2);
        return this;
    }

    /**
     * Cross vector 3 d.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the vector 3 d
     */
    public final Vector3d cross(Vector3d v1, Vector3d v2) {
        this.x = v1.y * v2.z - v1.z * v2.y;
        this.y = v1.z * v2.x - v1.x * v2.z;
        this.z = v1.x * v2.y - v1.y * v2.x;
        return this;
    }

    /**
     * Dot double.
     *
     * @param v2 the v 2
     * @return the double
     */
    public final double dot(Vector3d v2) {
        return (this.x * v2.x + this.y * v2.y + this.z * v2.z);
    }

    /**
     * L 2 norm double.
     *
     * @return the double
     */
    public final double l2norm() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Normalize vector 3 d.
     *
     * @return the vector 3 d
     */
    public final Vector3d normalize() {
        double norm = this.l2norm();
        this.x /= norm;
        this.y /= norm;
        this.z /= norm;
        return this;
    }

    /**
     * Signed angle double.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @param n  the n
     * @return the double
     */
    public static double signedAngle(final Vector3d v1, final Vector3d v2, final Vector3d n) {
        return Math.atan2(new Vector3d().cross(v1, v2).dot(n), v1.dot(v2));
    }

    /**
     * Equals boolean.
     *
     * @param v1 the v 1
     * @return the boolean
     */
    public boolean equals(final Vector3d v1) {
        return (this.x == v1.x && this.y == v1.y && this.z == v1.z);
    }

    /**
     * Epsilon equals boolean.
     *
     * @param v1      the v 1
     * @param epsilon the epsilon
     * @return the boolean
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
