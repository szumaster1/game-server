package core.tools

import kotlin.random.Random

/**
 * A utility class for generating random numbers.
 */
object RandomUtils {

    // The default random number generator.
    val random: Random = Random.Default

    /**
     * Generates a random integer between 0 (inclusive) and the specified value (inclusive).
     * @param random The random number generator to use.
     * @param i The upper bound for the random number (inclusive).
     * @return A random integer.
     */
    fun random(random: Random, i: Int): Int {
        return random.nextInt(i + 1)
    }

    /**
     * Generates a random integer between 0 (inclusive) and the specified value (inclusive) using the default random number generator.
     * @param i The upper bound for the random number (inclusive).
     * @return A random integer.
     */
    fun random(i: Int): Int {
        return random(random, i)
    }

    /**
     * Generates a random double between 0.0 (inclusive) and 1.0 (exclusive).
     * @param random The random number generator to use.
     * @return A random double.
     */
    fun randomDouble(random: Random): Double {
        return random.nextDouble()
    }

    /**
     * Generates a random double between 0.0 (inclusive) and 1.0 (exclusive) using the default random number generator.
     * @return A random double.
     */
    fun randomDouble(): Double {
        return randomDouble(random)
    }

}
