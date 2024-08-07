package core.tools

// Constants for time conversion
const val tick = 600 //ms // Define the tick interval in milliseconds
const val second = 1000 //ms // Define the second interval in milliseconds
const val cycle = 20 //ms // Define the cycle interval in milliseconds

/**
 * Converts seconds to ticks based on the tick interval.
 * @param seconds the number of seconds to convert
 * @return the equivalent number of ticks
 */
fun secondsToTicks(seconds: Int): Int {
    val seconds = seconds * second // Convert seconds to milliseconds
    return seconds / tick
}

/**
 * Converts ticks to seconds based on the tick interval.
 * @param ticks the number of ticks to convert
 * @return the equivalent number of seconds
 */
fun ticksToSeconds(ticks: Int): Int {
    val ticksMs = ticks * tick
    return ticksMs / 1000
}

/**
 * Converts cycles to ticks based on the tick and cycle intervals.
 * @param cycles the number of cycles to convert
 * @return the equivalent number of ticks
 */
fun cyclesToTicks(cycles: Int): Int {
    val cyclesPerTick = tick / cycle
    return kotlin.math.ceil(cycles / cyclesPerTick.toDouble()).toInt()
}

/**
 * Converts ticks to cycles based on the tick and cycle intervals.
 * @param ticks the number of ticks to convert
 * @return the equivalent number of cycles
 */
fun ticksToCycles(ticks: Int): Int {
    return ticks * (tick / cycle)
}

/**
 * Converts minutes to ticks based on the tick interval.
 * @param minutes the number of minutes to convert
 * @return the equivalent number of ticks
 */
fun minutesToTicks(minutes: Int): Int {
    val minutesMs = minutes * 60 * 1000
    return minutesMs / tick
}

/**
 * Converts ticks to minutes based on the tick interval.
 * @param ticks the number of ticks to convert
 * @return the equivalent number of minutes
 */
fun ticksToMinutes(ticks: Int): Int {
    val ticksMs = ticks * tick
    return ticksMs / 1000 / 60
}

// Constants for tick conversion
const val ticksPerSecond = second / tick // Calculate the number of ticks per second
const val ticksPerMinute = 60 * ticksPerSecond // Calculate the number of ticks per minute
