package core;

import java.util.Calendar;
import java.util.Date;

/**
 * Util class contains utility methods for
 * string manipulation and date/time operations.
 */
public class Util {

    /**
     * Capitalize the first letter of the string.
     *
     * @param name the name to be capitalized
     * @return Capitalized string
     */
    public static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]); // Capitalize the first character
            return new String(chars);
        } else {
            return name;
        }
    }

    /**
     * Convert a string to an enum format.
     *
     * @param name the string to be converted
     * @return Enum formatted string
     */
    public static String strToEnum(String name) {
        name = name.toUpperCase(); // Convert the string to uppercase
        return name.replaceAll(" ", "_"); // Replace spaces with underscores
    }

    /**
     * Convert an enum formatted string to a readable string.
     *
     * @param name the enum formatted string
     * @return Readable string
     */
    public static String enumToString(String name) {
        name = name.toLowerCase(); // Convert the string to lowercase
        name = name.replaceAll("_", " "); // Replace underscores with spaces
        return capitalize(name); // Capitalize the first letter
    }

    /**
     * Clamp a double value within a specified range.
     *
     * @param input the value to be clamped
     * @param min   the minimum value
     * @param max   the maximum value
     * @return Clamped value
     */
    public static double clamp(double input, double min, double max) {
        return Math.max(Math.min(input, max), min); // Ensure the value is within the specified range
    }

    /**
     * Clamp an integer value within a specified range.
     *
     * @param input the value to be clamped
     * @param min   the minimum value
     * @param max   the maximum value
     * @return Clamped value
     */
    public static int clamp(int input, int min, int max) {
        return Math.max(Math.min(input, max), min); // Ensure the value is within the specified range
    }

    /**
     * Get the timestamp for the next midnight from the given time.
     *
     * @param currentTime the current time in milliseconds
     * @return Timestamp for the next midnight
     */
    public static long nextMidnight(long currentTime) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        date.setTime(currentTime);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0); // Set the hour to midnight
        cal.set(Calendar.MINUTE, 0); // Set the minute to 0
        cal.set(Calendar.SECOND, 0); // Set the second to 0
        cal.set(Calendar.MILLISECOND, 0); // Set the millisecond to 0
        cal.add(Calendar.HOUR_OF_DAY, 24); // Add 24 hours to reach the next midnight

        return cal.getTime().getTime(); // Return the timestamp for the next midnight
    }
}
