package core.api.utils

import core.game.world.map.Direction
import core.game.world.map.Location
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Vector
 *
 * @property x The x-component of the vector
 * @property y The y-component of the vector
 * @constructor Initializes a Vector object with the given x and y components
 */
class Vector(val x: Double, val y: Double) {
    /**
     * Normalized
     *
     * @return A new Vector object that is the normalized version of the current vector
     */
    fun normalized(): Vector {
        val magnitude = magnitude()
        val xComponent = x / magnitude
        val yComponent = y / magnitude
        return Vector(xComponent, yComponent)
    }

    /**
     * Magnitude
     *
     * @return The magnitude of the vector
     */
    fun magnitude(): Double {
        return sqrt(x.pow(2.0) + y.pow(2.0))
    }

    /**
     * Unary minus
     *
     * @return A new Vector object that is the negation of the current vector
     */
    operator fun Vector.unaryMinus() = Vector(-x, -y)

    /**
     * Times
     *
     * @param other The scalar value to multiply the vector by
     * @return A new Vector object that is the result of multiplying the current vector by the scalar value
     */
    operator fun times(other: Double): Vector {
        return Vector(this.x * other, this.y * other)
    }

    /**
     * Times
     *
     * @param other The scalar value to multiply the vector by
     * @return A new Vector object that is the result of multiplying the current vector by the scalar value
     */
    operator fun times(other: Int): Vector {
        return Vector(this.x * other, this.y * other)
    }

    /**
     * Plus
     *
     * @param other The vector to add to the current vector
     * @return A new Vector object that is the result of adding the current vector and the other vector
     */
    operator fun plus(other: Vector): Vector {
        return Vector(this.x + other.x, this.y + other.y)
    }

    /**
     * Minus
     *
     * @param other The vector to subtract from the current vector
     * @return A new Vector object that is the result of subtracting the other vector from the current vector
     */
    operator fun minus(other: Vector): Vector {
        return Vector(this.x - other.x, this.y - other.y)
    }

    override fun toString(): String {
        return "{$x,$y}"
    }

    /**
     * Invert
     *
     * @return A new Vector object that is the negation of the current vector
     */
    fun invert(): Vector {
        return -this
    }

    /**
     * To location
     *
     * @param plane The plane of the location
     * @return A new Location object with the x and y components of the vector as its coordinates
     */
    fun toLocation(plane: Int = 0): Location {
        return Location.create(floor(x).toInt(), floor(y).toInt(), plane)
    }

    /**
     * To direction
     *
     * @return The Direction enum value that corresponds to the direction of the vector
     */
    fun toDirection(): Direction {
        val norm = normalized()

        if (norm.x >= 0.85) return Direction.EAST
        else if (norm.x <= -0.85) return Direction.WEST

        if (norm.y > 0) {
            if (norm.y >= 0.85) return Direction.NORTH
            return if (norm.x > 0) Direction.NORTH_EAST else Direction.NORTH_WEST
        } else {
            if (norm.y <= -0.85) return Direction.SOUTH
            return if (norm.x > 0) Direction.SOUTH_EAST else Direction.SOUTH_WEST
        }
    }

    companion object {
        /**
         * betweenLocs
         *
         * @param from The starting location
         * @param to The ending location
         * @return A new Vector object that represents the difference between the two locations
         */
        @JvmStatic
        fun betweenLocs(from: Location, to: Location): Vector {
            val xDiff = to.x - from.x
            val yDiff = to.y - from.y
            return Vector(xDiff.toDouble(), yDiff.toDouble())
        }

        /**
         * deriveWithEqualComponents
         *
         * @param magnitude The magnitude of the vector
         * @return A new Vector object with equal x and y components that has the given magnitude
         */
        @JvmStatic
        fun deriveWithEqualComponents(magnitude: Double): Vector {
            var sideLength = sqrt(magnitude.pow(2.0) / 2)
            return Vector(sideLength, sideLength)
        }
    }
}
