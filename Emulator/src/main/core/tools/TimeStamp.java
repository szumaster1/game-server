package core.tools;

/**
 * Time stamp.
 */
public final class TimeStamp {

    private long start; // Variable to store the start time

    private long interval; // Variable to store the interval time

    /**
     * Instantiates a new Time stamp and initializes start and interval.
     */
    public TimeStamp() {
        start = System.currentTimeMillis(); // Set the start time to the current system time
        interval = start; // Set the interval time to the start time
    }

    /**
     * Calculates the interval time.
     *
     * @return the interval time in milliseconds
     */
    public long interval() {
        return interval(true, ""); // Calls the interval method with debug set to true and empty info
    }

    /**
     * Calculates the interval time with debug option.
     *
     * @param debug the debug flag
     * @return the interval time in milliseconds
     */
    public long interval(boolean debug) {
        return interval(debug, ""); // Calls the interval method with provided debug flag and empty info
    }

    /**
     * Calculates the interval time with debug and info.
     *
     * @param debug the debug flag
     * @param info  the additional information
     * @return the interval time in milliseconds
     */
    public long interval(boolean debug, String info) {
        long current = System.currentTimeMillis(); // Get the current system time
        long difference = current - interval; // Calculate the time difference
        if (debug || difference > 100) {
            // Perform debug operations if debug is true or difference is greater than 100 milliseconds
        }
        interval = current; // Update the interval time
        return difference; // Return the calculated time difference
    }

    /**
     * Calculates the duration time from start.
     *
     * @param debug the debug flag
     * @param info  the additional information
     * @return the duration time in milliseconds
     */
    public long duration(boolean debug, String info) {
        long current = System.currentTimeMillis(); // Get the current system time
        long difference = current - start; // Calculate the duration from the start time
        if (debug) {
            // Perform debug operations if debug is true
        }
        return difference; // Return the calculated duration time
    }

}
