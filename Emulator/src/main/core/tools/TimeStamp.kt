package core.tools

/**
 * Time stamp.
 */
class TimeStamp {

    private var start: Long = System.currentTimeMillis()
    private var interval: Long = start

    /**
     * Constructs a new Time stamp and initializes start and interval.
     */
    init {
        start = System.currentTimeMillis()
        interval = start
    }

    /**
     * Calculates the interval time.
     *
     * @return the interval time in milliseconds
     */
    fun interval(): Long {
        return interval(true, "")
    }

    /**
     * Calculates the interval time with debug option.
     *
     * @param debug the debug flag
     * @return the interval time in milliseconds
     */
    fun interval(debug: Boolean): Long {
        return interval(debug, "")
    }

    /**
     * Calculates the interval time with debug and info.
     *
     * @param debug the debug flag
     * @param info  the additional information
     * @return the interval time in milliseconds
     */
    fun interval(debug: Boolean, info: String): Long {
        val current = System.currentTimeMillis()
        val difference = current - interval
        if (debug || difference > 100) {
            // Perform debug operations if debug is true or difference is greater than 100 milliseconds
        }
        interval = current
        return difference
    }

    /**
     * Calculates the duration time from start.
     *
     * @param debug the debug flag
     * @param info  the additional information
     * @return the duration time in milliseconds
     */
    fun duration(debug: Boolean, info: String): Long {
        val current = System.currentTimeMillis()
        val difference = current - start
        if (debug) {
            // Perform debug operations if debug is true
        }
        return difference
    }
}