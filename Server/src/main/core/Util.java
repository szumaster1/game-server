package core;

import core.game.node.item.Item;
import core.game.world.map.Location;
import core.tools.RandomFunction;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Util class contains utility methods for
 * string manipulation and date/time operations.
 */
public class Util {
    /**
     * The constant for x delta direction.
     */
    public static final byte[] DIRECTION_DELTA_X = new byte[]{-1, 0, 1, -1, 1, -1, 0, 1};
    /**
     * The constant for y delta direction.
     */
    public static final byte[] DIRECTION_DELTA_Y = new byte[]{-1, -1, -1, 0, 0, 1, 1, 1};

    /**
     * The constant for x delta npc direction.
     */
    public static final byte[] NPC_DIRECTION_DELTA_X = new byte[]{-1, 0, 1, -1, 1, -1, 0, 1};
    /**
     * The constant for y delta npc direction.
     */
    public static final byte[] NPC_DIRECTION_DELTA_Y = new byte[]{1, 1, 1, 0, 0, -1, -1, -1};

    private static final String[] tens_names = {"", " ten", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety"};

    /**
     * number to names strings.
     */
    public static final String[] number_names = {"", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen", " " + "fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};

    private static final Object ALGORITHM_LOCK = new Object();

    /**
     * The constant secure random.
     */
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * The constant random.
     */
    private static final Random r = new Random();

    /**
     * Generates a random integer between 0 and the specified upper bound (inclusive).
     *
     * @param random the Random instance used for generating random numbers
     * @param i      the upper bound for the random number generation
     * @return the generated random integer
     */
    public static int random(final Random random, final int i) {
        return random.nextInt(i + 1); // Returns a random integer from 0 to i
    }

    /**
     * Generates a random integer using a default Random instance.
     *
     * @param i the upper bound for the random number generation
     * @return the generated random integer
     */
    public static int random(final int i) {
        return random(r, i); // Calls the overloaded method with the default Random instance
    }

    /**
     * Rolls a die with a specified number of sides and checks if the roll is successful based on the given chance.
     *
     * @param sides  the number of sides on the die
     * @param chance the chance of a successful roll
     * @return true if the roll is successful, false otherwise
     */
    public static boolean rollDie(int sides, int chance) {
        return random(1, sides) <= chance; // Compares the random roll to the chance
    }

    /**
     * Generates a random double value between 0.0 and 1.0.
     *
     * @param random the Random instance used for generating random numbers
     * @return the generated random double
     */
    public static final double randomDouble(final Random random) {
        return random.nextDouble(); // Returns a random double between 0.0 and 1.0
    }

    /**
     * Generates a random double using a default Random instance.
     *
     * @return the generated random double
     */
    public static final double randomDouble() {
        return randomDouble(r); // Calls the overloaded method with the default Random instance
    }

    /**
     * Interpolates success based on the current level and defined chances.
     *
     * @param minChance    the minimum chance of success
     * @param maxChance    the maximum chance of success
     * @param minLevel     the minimum level for interpolation
     * @param maxLevel     the maximum level for interpolation
     * @param currentLevel the current level to evaluate
     * @return true if the interpolation indicates success, false otherwise
     */
    public static final boolean interpolateSuccess(final int minChance, final int maxChance, final int minLevel, final int maxLevel, final int currentLevel) {
        return Util.random(255) <= ((minChance * (maxLevel - currentLevel) + maxChance * (currentLevel - minLevel)) / (maxLevel - minLevel)); // Evaluates success based on interpolated chance
    }

    /**
     * Capitalizes the first letter of the provided string.
     *
     * @param name the name to be capitalized
     * @return Capitalized string or original if null or empty
     */
    public static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray(); // Converts the string to a character array
            chars[0] = Character.toUpperCase(chars[0]); // Capitalizes the first character
            return new String(chars); // Returns the new capitalized string
        } else {
            return name; // Returns the original name if null or empty
        }
    }

    /**
     * Converts a string to an enum format by uppercasing and replacing spaces with underscores.
     *
     * @param name the string to be converted
     * @return Enum formatted string
     */
    public static String strToEnum(String name) {
        name = name.toUpperCase(); // Converts the string to uppercase
        return name.replaceAll(" ", "_"); // Replaces spaces with underscores
    }

    /**
     * Converts an enum formatted string back to a readable string.
     *
     * @param name the enum formatted string
     * @return Readable string
     */
    public static String enumToString(String name) {
        name = name.toLowerCase(); // Converts the string to lowercase
        name = name.replaceAll("_", " "); // Replaces underscores with spaces
        return capitalize(name); // Capitalizes the first letter of the resulting string
    }

    /**
     * Clamps a double value within a specified range.
     *
     * @param input the value to be clamped
     * @param min   the minimum value
     * @param max   the maximum value
     * @return Clamped value within the specified range
     */
    public static double clamp(double input, double min, double max) {
        return Math.max(Math.min(input, max), min); // Ensures the input is within the min and max range
    }

    /**
     * Clamps an integer value within a specified range.
     *
     * @param input the value to be clamped
     * @param min   the minimum value
     * @param max   the maximum value
     * @return Clamped value within the specified range
     */
    public static int clamp(int input, int min, int max) {
        return Math.max(Math.min(input, max), min); // Ensures the input is within the min and max range
    }

    /**
     * Gets the timestamp for the next midnight from the given time.
     *
     * @param currentTime the current time in milliseconds
     * @return Timestamp for the next midnight
     */
    public static long nextMidnight(long currentTime) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        date.setTime(currentTime);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.HOUR_OF_DAY, 24);

        return cal.getTime().getTime();
    }

    /**
     * Returns a random element from the provided array of elements.
     *
     * @param <T>      the type parameter representing the type of elements
     * @param elements the elements from which a random element will be selected
     * @return a randomly selected element of type T
     */
    @SafeVarargs
    public static final <T> T randomElement(final T... elements) {
        if (elements.length == 0) {
            throw new IllegalStateException("Array cannot be empty!");
        }
        return elements[random(elements.length - 1)];
    }

    /**
     * Converts the provided elements into an ArrayList.
     *
     * @param <T>      the type parameter representing the type of elements
     * @param elements the elements to be converted into an ArrayList
     * @return an ArrayList containing the provided elements
     */
    public static <T> ArrayList<T> getArrayList(final T... elements) {
        // Create and return a new ArrayList from the provided elements
        return new ArrayList<>(Arrays.asList(elements));
    }

    /**
     * Finds the first element in the array that matches the given predicate.
     *
     * @param <T>       the type parameter representing the type of elements
     * @param array     the array to search through
     * @param predicate the condition to match elements against
     * @return the first matching element of type T, or null if none found
     */
    public static final <T> T findMatching(final T[] array, final Predicate<T> predicate) {
        // Delegate to the overloaded method with a default value of null
        return findMatching(array, predicate, null);
    }

    /**
     * Finds the first element in the array that matches the given predicate, returning a default value if none is found.
     *
     * @param <T>          the type parameter representing the type of elements
     * @param array        the array to search through
     * @param predicate    the condition to match elements against
     * @param defaultValue the value to return if no match is found
     * @return the first matching element of type T, or the default value if none found
     */
    public static final <T> T findMatching(final T[] array, final Predicate<T> predicate, final T defaultValue) {
        // Iterate through the array to find a matching element
        for (int i = 0; i < array.length; i++) {
            var object = array[i]; // Get the current element
            // Check if the current element matches the predicate
            if (predicate.test(object)) {
                return object; // Return the matching element
            }
        }
        return defaultValue; // Return the default value if no match is found
    }

    /**
     * Finds the first element in the collection that matches the given predicate.
     *
     * @param <T>       the type parameter representing the type of elements
     * @param list      the collection to search through
     * @param predicate the condition to match elements against
     * @return the first matching element of type T, or null if none found
     */
    public static final <T> T findMatching(final Collection<T> list, final Predicate<T> predicate) {
        // Delegate to the overloaded method with a default value of null
        return findMatching(list, predicate, null);
    }

    /**
     * Finds the first element in the collection that matches the given predicate, returning a default value if none is found.
     *
     * @param <T>          the type parameter representing the type of elements
     * @param list         the collection to search through
     * @param predicate    the condition to match elements against
     * @param defaultValue the value to return if no match is found
     * @return the first matching element of type T, or the default value if none found
     */
    public static final <T> T findMatching(final Collection<T> list, final Predicate<T> predicate, final T defaultValue) {
        // Iterate through the collection to find a matching element
        for (final T object : list) {
            // Check if the current element matches the predicate
            if (predicate.test(object)) {
                return object; // Return the matching element
            }
        }
        return defaultValue; // Return the default value if no match is found
    }

    /**
     * Finds the first element in the set that matches the given predicate.
     *
     * @param <T>       the type parameter representing the type of elements
     * @param list      the set to search through
     * @param predicate the condition to match elements against
     * @return the first matching element of type T, or null if none found
     */
    public static final <T> T findMatching(final Set<T> list, final Predicate<T> predicate) {
        // Delegate to the overloaded method with a default value of null
        return findMatching(list, predicate, null);
    }

    /**
     * Finds the first element in the set that matches the given predicate, returning a default value if none is found.
     *
     * @param <T>          the type parameter representing the type of elements
     * @param list         the set to search through
     * @param predicate    the condition to match elements against
     * @param defaultValue the value to return if no match is found
     * @return the first matching element of type T, or the default value if none found
     */
    public static final <T> T findMatching(final Set<T> list, final Predicate<T> predicate, final T defaultValue) {
        // Iterate through the set to find a matching element
        for (var object : list) {
            // Check if the current element matches the predicate
            if (predicate.test(object)) {
                return object; // Return the matching element
            }
        }
        return defaultValue; // Return the default value if no match is found
    }

    /**
     * Returns "s" if the value is not equal to 1, otherwise returns an empty string.
     *
     * @param value the integer value to check
     * @return "s" if value is not 1, otherwise an empty string
     */
    public static final String plural(final int value) {
        // Return "s" for pluralization based on the value
        return value == 1 ? "" : "s";
    }

    /**
     * Gets a random element from the provided collection.
     *
     * @param <E> the type parameter representing the type of elements
     * @param e   the collection from which a random element will be selected
     * @return a randomly selected element of type E
     */
    public static <E> E getRandomCollectionElement(final Collection<E> e) {
        var size = e.size(); // Get the size of the collection
        // Check if the collection is empty
        if (size == 0) {
            // Throw an exception if the collection is empty
            throw new RuntimeException("Collection cannot be empty.");
        }
        var random = RandomFunction.random(e.size() - 1); // Get a random index
        int i = 0; // Initialize index counter
        // Iterate through the collection to find the random element
        for (final E value : e) {
            // Check if the current index matches the random index
            if (i == random) {
                return value; // Return the randomly selected element
            }
            i++; // Increment the index counter
        }
        // Throw an exception if concurrent modification is detected
        throw new RuntimeException("Concurrent modification performed on the collection.");
    }

    /**
     * Returns the primary value if it is not null; otherwise, returns the default value.
     *
     * @param <T>          the type parameter representing the type of values
     * @param primary      the primary value to check
     * @param defaultValue the default value to return if primary is null
     * @return the primary value or the default value if primary is null
     */
    public static final <T> T getOrDefault(final T primary, final T defaultValue) {
        // Return the default value if primary is null, otherwise return primary
        return primary == null ? defaultValue : primary;
    }

    /**
     * Computes a value if the primary value is absent (null).
     *
     * @param <T>          the type parameter representing the type of values
     * @param primary      the primary value to check
     * @param defaultValue the function to compute the default value if primary is null
     * @return the primary value or the computed default value if primary is null
     */
    public static final <T> T computeIfAbsent(final T primary, final Function<Void, T> defaultValue) {
        // Return the computed default value if primary is null, otherwise return primary
        return primary == null ? defaultValue.apply(null) : primary;
    }

    /**
     * Determines if a random event occurs based on the given chance.
     *
     * @param chance the percentage chance of the event occurring
     * @return true if the event occurs, false otherwise
     */
    public static final boolean percentage(final int chance) {
        // Return true if a random number is less than the chance
        return random(99) < chance;
    }

    /**
     * Gets a random double value between 0 and the specified maximum value.
     *
     * @param maxValue the maximum value for the random double
     * @return a random double value between 0 and maxValue
     */
    public static final double getRandomDouble(final double maxValue) {
        // Return a random double scaled by the maximum value
        return (randomDouble() * maxValue);
    }

    /**
     * Gets a random double value between the specified minimum and maximum values.
     *
     * @param minValue the minimum value for the random double
     * @param maxValue the maximum value for the random double
     * @return a random double value between minValue and maxValue
     */
    public static final double getRandomDouble(final int minValue, final int maxValue) {
        final int random = random(minValue, maxValue - 1); // Get a random integer within the range
        final double randomDouble = r.nextDouble(); // Get a random double
        return random + randomDouble; // Return the sum of the random integer and double
    }

    /**
     * Gets a random double value between the specified minimum and maximum values, ensuring it stays within bounds.
     *
     * @param minValue the minimum value for the random double
     * @param maxValue the maximum value for the random double
     * @return a random double value between minValue and maxValue
     */
    public static final double getRandomDouble(final double minValue, final double maxValue) {
        final int random = random((int) minValue, (int) maxValue - 1); // Get a random integer within the range
        final double randomDouble = r.nextDouble(); // Get a random double
        double value = random + randomDouble; // Calculate the final value
        // Ensure the value does not exceed the maximum
        if (value > maxValue) value = maxValue;
        // Ensure the value does not fall below the minimum
        if (value < minValue) value = minValue;
        return value; // Return the bounded random double
    }

    /**
     * Generates a random integer between the specified minimum and maximum values.
     *
     * @param random the Random instance used for generating random numbers
     * @param min    the minimum value for the random integer
     * @param max    the maximum value for the random integer
     * @return a random integer between min and max
     */
    public static final int random(final Random random, final int min, final int max) {
        final int n = Math.abs(max - min); // Calculate the range
        // Return a random integer within the specified range
        return Math.min(min, max) + (n == 0 ? 0 : random(random, n));
    }

    /**
     * Generates a random integer between the specified minimum and maximum values.
     *
     * @param min the minimum value for the random integer
     * @param max the maximum value for the random integer
     * @return a random integer between min and max
     */
    public static final int random(final int min, final int max) {
        // Delegate to the overloaded method with the Random instance
        return random(r, min, max);
    }
    /**
     * Gets the Random instance used for generating random numbers.
     *
     * @return the Random instance
     */
    public static final Random getRandom() {
        // Return the Random instance
        return r; // Returns the static Random instance 'r' for random number generation
    }

    /**
     * Checks if the input string is plural and returns a formatted string.
     *
     * @param input the input string to check
     * @return a formatted string indicating quantity
     */
    public static final String checkPlural(final String input) {
        // Return a formatted string based on whether the input is plural
        return (input.endsWith("s") && !input.toLowerCase().equals("bass") ? "some " : getAOrAn(input) + " ") + input; // Checks if the input is plural and formats accordingly
    }

    /**
     * Gets a shifted value based on the total and the specified id.
     *
     * @param total the total value to shift
     * @param id    the id indicating the bit position to shift
     * @return the shifted value
     */
    public static final int getShiftedValue(final int total, final int id) {
        final int val = ((total >> id) & 1); // Get the bit at the specified position
        // Check if the bit is set
        if (val == 1) {
            // Clear the bit at the specified position
            return total & (1 << id ^ -1); // Clears the bit if it is set
        }
        // Set the bit at the specified position
        return total | 1 << id; // Sets the bit if it is not set
    }

    /**
     * Suffix ordinal string.
     *
     * @param i the integer to convert to an ordinal string
     * @return the ordinal string representation of the integer
     */
    public static String suffixOrdinal(final int i) {
        final String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"}; // Array of suffixes for ordinal numbers
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th"; // Special case for 11th, 12th, and 13th
            default:
                return i + sufixes[i % 10]; // Returns the ordinal string based on the last digit
        }
    }

    /**
     * Gets a or an.
     *
     * @param s the string to evaluate
     * @return "a" or "an" based on the first letter of the string
     */
    public static String getAOrAn(final String s) {
        if (s == null) {
            return null; // Returns null if the input string is null
        }
        if (s.length() == 0) {
            return ""; // Returns an empty string if the input string is empty
        }
        final char fc = Character.toLowerCase(s.charAt(0)); // Gets the first character in lowercase
        if (fc == 'a' || fc == 'e' || fc == 'i' || fc == 'u' || fc == 'o') {
            return "an"; // Returns "an" for vowels
        } else {
            final char sc = (s.length() > 1) ? s.charAt(1) : '\0'; // Gets the second character if it exists
            if (fc == 'x' && !(sc == 'a' || sc == 'e' || sc == 'i' || sc == 'o' || sc == 'u')) {
                return "an"; // Special case for "x" followed by a consonant
            } else {
                return "a"; // Returns "a" for consonants
            }
        }
    }

    /**
     * Gets shifted value.
     *
     * @param total   the total value to shift
     * @param id      the bit position to check
     * @param toggled the toggle state
     * @return the shifted value based on the toggle state
     */
    public static final int getShiftedValue(final int total, final int id, final boolean toggled) {
        final int val = ((total >> id) & 1); // Get the bit at the specified position
        if (val == 1 && !toggled) {
            return total & (1 << id ^ -1); // Clears the bit if it is set and toggled is false
        } else if (val == 0 && toggled) {
            return total | 1 << id; // Sets the bit if it is not set and toggled is true
        }
        return total; // Returns the total if no changes are made
    }

    /**
     * Gets shifted boolean.
     *
     * @param total the total value to check
     * @param id    the bit position to check
     * @return true if the bit at the specified position is set, false otherwise
     */
    public static final boolean getShiftedBoolean(final int total, final int id) {
        return ((total >> id) & 1) == 1; // Returns true if the bit at the specified position is set
    }

    /**
     * Gets distance.
     *
     * @param coordX1 the x-coordinate of the first point
     * @param coordY1 the y-coordinate of the first point
     * @param coordX2 the x-coordinate of the second point
     * @param coordY2 the y-coordinate of the second point
     * @return the Euclidean distance between the two points
     */
    public static final int getDistance(final int coordX1, final int coordY1, final int coordX2, final int coordY2) {
        final int deltaX = coordX2 - coordX1; // Calculate the difference in x-coordinates
        final int deltaY = coordY2 - coordY1; // Calculate the difference in y-coordinates
        return ((int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2))); // Returns the Euclidean distance
    }

    /**
     * Gets face direction.
     *
     * @param destination the target location
     * @param location    the current location
     * @return the direction to face towards the destination
     */
    public static final int getFaceDirection(final Location destination, final Location location) {
        return ((int) ((Math.atan2(-(destination.getX() - location.getX()), -(destination.getY() - location.getY())) * 325.949D)) & 0x7ff); // Calculates the direction to face based on the destination and current location
    }

    /**
     * Gets face direction.
     *
     * @param xOffset the x offset from the current position
     * @param yOffset the y offset from the current position
     * @return the direction to face based on the offsets
     */
    public static final int getFaceDirection(final double xOffset, final double yOffset) {
        return ((int) ((Math.atan2(-xOffset, -yOffset) * 325.949D)) & 0x7ff); // Calculates the direction to face based on x and y offsets
    }

    /**
     * Gets map archive id.
     *
     * @param regionX the x-coordinate of the region
     * @param regionY the y-coordinate of the region
     * @return the unique map archive identifier
     */
    public static int getMapArchiveId(final int regionX, final int regionY) {
        return regionX | regionY << 7; // Combines region coordinates to create a unique identifier
    }

    /**
     * Gets a move direction.
     *
     * @param xOffset the x offset from the current position
     * @param yOffset the y offset from the current position
     * @return the direction of movement based on the offsets
     */
    public static final int getMoveDirection(final int xOffset, final int yOffset) {
        // Determine the movement direction based on x and y offsets
        if (xOffset < 0) {
            if (yOffset < 0) {
                return 0; // Move Northwest
            } else if (yOffset > 0) {
                return 5; // Move Southwest
            } else {
                return 3; // Move West
            }
        } else if (xOffset > 0) {
            if (yOffset < 0) {
                return 2; // Move Northeast
            } else if (yOffset > 0) {
                return 7; // Move Southeast
            } else {
                return 4; // Move East
            }
        } else {
            if (yOffset < 0) {
                return 1; // Move North
            } else if (yOffset > 0) {
                return 6; // Move South
            } else {
                return -1; // No movement
            }
        }
    }

    /**
     * Gets npc walking direction.
     *
     * @param dir the direction index
     * @return the NPC walking direction based on the direction index
     */
    public static final int getNPCWalkingDirection(final int dir) {
        final int dx = Util.NPC_DIRECTION_DELTA_X[dir]; // Gets the x delta based on direction index
        final int dy = Util.NPC_DIRECTION_DELTA_Y[dir]; // Gets the y delta based on direction index
        if (dx == 0 && dy == 1) {
            return 6; // Move South
        } else if (dx == 1 && dy == 1) {
            return 7; // Move Southeast
        } else if (dx == -1 && dy == 0) {
            return 3; // Move West
        } else if (dx == 1 && dy == 0) {
            return 4; // Move East
        } else if (dx == -1 && dy == -1) {
            return 0; // Move Northwest
        } else if (dx == 0 && dy == -1) {
            return 1; // Move North
        } else if (dx == 1 && dy == -1) {
            return 2; // Move Northeast
        } else {
            return 5; // No movement
        }
    }

    /**
     * Gets player running direction.
     *
     * @param dx the dx
     * @param dy the dy
     * @return the player running direction
     */
    public static int getPlayerRunningDirection(final int dx, final int dy) {
        // Check if the player is moving diagonally up-left
        if (dx == -2 && dy == -2) {
            return 0; // Return direction 0 for up-left
        }
        // Check if the player is moving diagonally up
        if (dx == -1 && dy == -2) {
            return 1; // Return direction 1 for up
        }
        // Check if the player is moving straight up
        if (dx == 0 && dy == -2) {
            return 2; // Return direction 2 for straight up
        }
        // Check if the player is moving diagonally up-right
        if (dx == 1 && dy == -2) {
            return 3; // Return direction 3 for up-right
        }
        // Check if the player is moving diagonally up-right
        if (dx == 2 && dy == -2) {
            return 4; // Return direction 4 for up-right
        }
        // Check if the player is moving diagonally left-up
        if (dx == -2 && dy == -1) {
            return 5; // Return direction 5 for left-up
        }
        // Check if the player is moving diagonally right-up
        if (dx == 2 && dy == -1) {
            return 6; // Return direction 6 for right-up
        }
        // Check if the player is moving left
        if (dx == -2 && dy == 0) {
            return 7; // Return direction 7 for left
        }
        // Check if the player is moving right
        if (dx == 2 && dy == 0) {
            return 8; // Return direction 8 for right
        }
        // Check if the player is moving diagonally left-down
        if (dx == -2 && dy == 1) {
            return 9; // Return direction 9 for left-down
        }
        // Check if the player is moving diagonally right-down
        if (dx == 2 && dy == 1) {
            return 10; // Return direction 10 for right-down
        }
        // Check if the player is moving diagonally down-left
        if (dx == -2 && dy == 2) {
            return 11; // Return direction 11 for down-left
        }
        // Check if the player is moving diagonally down
        if (dx == -1 && dy == 2) {
            return 12; // Return direction 12 for down
        }
        // Check if the player is moving straight down
        if (dx == 0 && dy == 2) {
            return 13; // Return direction 13 for straight down
        }
        // Check if the player is moving diagonally down-right
        if (dx == 1 && dy == 2) {
            return 14; // Return direction 14 for down-right
        }
        // Check if the player is moving diagonally down-right
        if (dx == 2 && dy == 2) {
            return 15; // Return direction 15 for down-right
        }
        return -1; // Return -1 if no valid direction is found
    }

    /**
     * Crypt rsa byte..
     *
     * @param data     the data
     * @param exponent the exponent
     * @param modulus  the modulus
     * @return the byte.
     */
    public static byte[] cryptRSA(final byte[] data, final BigInteger exponent, final BigInteger modulus) {
        /*
         * Convert the byte array to a BigInteger, perform modular exponentiation,
         * and return the result as a byte array
         */
        return new BigInteger(data).modPow(exponent, modulus).toByteArray();
    }

    /**
     * Gets player walking direction.
     *
     * @param dx the dx
     * @param dy the dy
     * @return the player walking direction
     */
    public static int getPlayerWalkingDirection(final int dx, final int dy) {
        // Check if the player is moving diagonally down-left
        if (dx == -1 && dy == -1) {
            return 0; // Return direction 0 for down-left
        }
        // Check if the player is moving straight down
        if (dx == 0 && dy == -1) {
            return 1; // Return direction 1 for straight down
        }
        // Check if the player is moving diagonally down-right
        if (dx == 1 && dy == -1) {
            return 2; // Return direction 2 for down-right
        }
        // Check if the player is moving left
        if (dx == -1 && dy == 0) {
            return 3; // Return direction 3 for left
        }
        // Check if the player is moving right
        if (dx == 1 && dy == 0) {
            return 4; // Return direction 4 for right
        }
        // Check if the player is moving diagonally up-left
        if (dx == -1 && dy == 1) {
            return 5; // Return direction 5 for up-left
        }
        // Check if the player is moving straight up
        if (dx == 0 && dy == 1) {
            return 6; // Return direction 6 for straight up
        }
        // Check if the player is moving diagonally up-right
        if (dx == 1 && dy == 1) {
            return 7; // Return direction 7 for up-right
        }
        return -1; // Return -1 if no valid direction is found
    }

    /**
     * Converts a number less than one thousand into its word representation.
     *
     * @param number the number to convert
     * @return the word number.
     */
    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = number_names[number % 100];
            number /= 100;
        } else {
            soFar = number_names[number % 10];
            number /= 10;

            soFar = tens_names[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) {
            return soFar;
        }
        return number_names[number] + " hundred" + soFar;
    }

    /**
     * Converts a number into its word representation.
     *
     * @param number the number to convert
     * @return the word representation of the number
     */
    public static String convert(final int number) {
        if (number == 0) {
            return "zero";
        }

        String snumber = Long.toString(number);

        final String mask = "000000000000";
        final DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        final int billions = Integer.parseInt(snumber.substring(0, 3));
        final int millions = Integer.parseInt(snumber.substring(3, 6));
        final int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        final int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * Checks if two objects are within a specified range.
     *
     * @param x1          the x coordinate of the first object
     * @param y1          the y coordinate of the first object
     * @param size1       the size of the first object
     * @param x2          the x coordinate of the second object
     * @param y2          the y coordinate of the second object
     * @param size2       the size of the second object
     * @param maxDistance the maximum distance for the range check
     * @return true if the objects are within range, false otherwise
     */
    public static final boolean isOnRange(final int x1, final int y1, final int size1, final int x2, final int y2, final int size2, final int maxDistance) {
        final int distanceX = x1 - x2;
        final int distanceY = y1 - y2;
        return distanceX <= size2 + maxDistance && distanceX >= -size1 - maxDistance && distanceY <= size2 + maxDistance && distanceY >= -size1 - maxDistance;
    }

    /**
     * Checks if two objects are positioned diagonally relative to each other.
     *
     * @param x1    the x coordinate of the first object
     * @param y1    the y coordinate of the first object
     * @param size1 size of the first object
     * @param x2    the x coordinate of the second object
     * @param y2    the y coordinate of the second object
     * @param size2 size of the second object
     * @return true if the objects are diagonal to each other, false otherwise
     */
    public static final boolean isDiagonal(final int x1, final int y1, final int size1, final int x2, final int y2, final int size2) {
        return x1 == (x2 - size1) && y1 == (y2 - size1) || x1 == (x2 + size2) && y1 == (y2 - size1) || x1 == (x2 + size2) && y1 == (y2 + size2) || x1 == (x2 - size1) && y1 == (y2 + size2);
    }

    /**
     * Checks if two objects are within a specified range, excluding diagonal positioning.
     *
     * @param x1          the x coordinate of the first object
     * @param y1          the y coordinate of the first object
     * @param size1       the size of the first object
     * @param x2          the x coordinate of the second object
     * @param y2          the y coordinate of the second object
     * @param size2       the size of the second object
     * @param maxDistance the maximum distance for the range check
     * @return true if the objects are within range excluding diagonal, false otherwise
     */
    public static final boolean isOnRangeExcludingDiagonal(final int x1, final int y1, final int size1, final int x2, final int y2, final int size2, final int maxDistance) {
        final int distanceX = x1 - x2;
        final int distanceY = y1 - y2;
        return distanceX < size2 + maxDistance && distanceX > -size1 - maxDistance && distanceY < size2 + maxDistance && distanceY > -size1 - maxDistance;
    }

    /**
     * Retrieves all classes from a specified package.
     *
     * @param packageName the name of the package to search
     * @return an array of classes found in the package
     * @throws ClassNotFoundException if a class cannot be found
     * @throws IOException            if an I/O error occurs
     */
    public static Class<?>[] getClasses(final String packageName) throws ClassNotFoundException, IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String path = packageName.replace('.', '/');
        final Enumeration<URL> resources = classLoader.getResources(path);
        final List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            dirs.add(new File(resources.nextElement().getFile().replaceAll("%20", " ")));
        }
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        for (int i = dirs.size() - 1; i >= 0; i--) {
            final File directory = dirs.get(i);
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Find classes list.
     *
     * @param directory   the directory
     * @param packageName the package name
     * @return the list
     */
    public static final List<Class<?>> findClasses(final File directory, final String packageName) {
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        final File[] files = directory.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            final File file = files[i];
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
                continue;
            }
            final String name = file.getName();
            if (name.endsWith(".class")) {
                try {
                    classes.add(Class.forName(packageName + '.' + name.substring(0, name.length() - 6)));
                } catch (final ClassNotFoundException e) {

                }
            }
        }
        return classes;
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".###");

    /**
     * Converts nanoseconds to milliseconds.
     *
     * @param nanoseconds the nanoseconds to convert
     * @return the converted time in milliseconds as a double
     */
    public static final double nanoToMilli(final long nanoseconds) {
        final double num = nanoseconds / 1000000F;
        return Double.parseDouble(DECIMAL_FORMAT.format(num));
    }

    /**
     * Format username string to a standardized format.
     *
     * @param username the username to format
     * @return the formatted username string
     */
    public static String formatUsername(final String username) {
        return username.toLowerCase().replace(" ", "_");
    }

    /**
     * Format a string by capitalizing the first letter and handling underscores.
     *
     * @param str the string to format
     * @return the formatted string
     */
    public static String formatString(String str) {
        if (str == null || str.isEmpty()) return str;
        char[] array = str.toLowerCase().toCharArray();

        array[0] = Character.toUpperCase(array[0]);
        if (array[0] == '_') {
            array[0] = ' ';
        }


        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            } else if (array[i - 1] == '_') {
                array[i - 1] = ' ';
            }
        }

        return new String(array);
    }

    /**
     * Convert a string to a name format by filtering out unwanted characters.
     *
     * @param string the string to convert
     * @return the filtered string
     */
    public static final String convertToNameFormat(final String string) {
        var builder = new StringBuilder();
        for (var c : string.toCharArray()) {
            if (Character.isLetter(c) || Character.isDigit(c) || c == '_' || c == ' ') {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * Calculate the angle based on x and y offsets.
     *
     * @param xOffset the x offset
     * @param yOffset the y offset
     * @return the calculated angle in degrees
     */
    public static final int getAngle(final int xOffset, final int yOffset) {
        return ((int) (Math.atan2(-xOffset, -yOffset) * 2607.5945876176133)) & 0x3fff;
    }

    /**
     * Calculate the angle between two locations.
     *
     * @param from   the starting location
     * @param target the target location
     * @return the angle in degrees
     */
    public static final int getAngle(final Location from, final Location target) {
        double angle = Math.toDegrees(Math.atan2(target.getY() - from.getY(), -(target.getX() - from.getX())));
        if (angle < 0) {
            angle += 360;
        }
        return (int) angle;
    }

    /**
     * Check if two objects collide based on their coordinates and sizes.
     *
     * @param x1    the x coordinate of the first object
     * @param y1    the y coordinate of the first object
     * @param size1 the size of the first object
     * @param x2    the x coordinate of the second object
     * @param y2    the y coordinate of the second object
     * @param size2 the size of the second object
     * @return true if the objects collide, false otherwise
     */
    public static final boolean collides(final int x1, final int y1, final int size1, final int x2, final int y2, final int size2) {
        final int distanceX = x1 - x2;
        final int distanceY = y1 - y2;
        return distanceX < size2 && distanceX > -size1 && distanceY < size2 && distanceY > -size1;
    }

    /**
     * Get coordinate offsets based on a given size.
     *
     * @param size the size to base offsets on
     * @return an array of coordinate offsets
     */
    public static final int[][] getCoordOffsetsNear(final int size) {
        final int[] xs = new int[4 + (4 * size)];
        final int[] xy = new int[xs.length];
        xs[0] = -size;
        xy[0] = 1;
        xs[1] = 1;
        xy[1] = 1;
        xs[2] = -size;
        xy[2] = -size;
        xs[3] = 1;
        xy[2] = -size;
        for (int fakeSize = size; fakeSize > 0; fakeSize--) {
            xs[(4 + ((size - fakeSize) * 4))] = -fakeSize + 1;
            xy[(4 + ((size - fakeSize) * 4))] = 1;
            xs[(4 + ((size - fakeSize) * 4)) + 1] = -size;
            xy[(4 + ((size - fakeSize) * 4)) + 1] = -fakeSize + 1;
            xs[(4 + ((size - fakeSize) * 4)) + 2] = 1;
            xy[(4 + ((size - fakeSize) * 4)) + 2] = -fakeSize + 1;
            xs[(4 + ((size - fakeSize) * 4)) + 3] = -fakeSize + 1;
            xy[(4 + ((size - fakeSize) * 4)) + 3] = -size;
        }
        return new int[][]{xs, xy};
    }

    /**
     * Shuffle an integer array in place.
     *
     * @param array the array to shuffle
     */
    public static final void shuffleIntegerArray(final int[] array) {
        final Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            final int index = rnd.nextInt(i + 1);
            final int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    /**
     * Get a formatted number as a string with a specified separator.
     *
     * @param amount    the amount to format
     * @param seperator the character to use as a separator
     * @return the formatted number as a string
     */
    public static String getFormattedNumber(final double amount, final char seperator) {
        final String str = new DecimalFormat("#,###,###").format(amount);
        final char[] rebuff = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                rebuff[i] = c;
            } else {
                rebuff[i] = seperator;
            }
        }
        return new String(rebuff);
    }

    /**
     * Format an integer as a string.
     *
     * @param number the number to format
     * @return the formatted string
     */
    public static String format(final int number) {
        return NumberFormat.getNumberInstance().format(number);
    }

    /**
     * Format a long integer as a string.
     *
     * @param number the number to format
     * @return the formatted string
     */
    public static String format(final long number) {
        return NumberFormat.getNumberInstance().format(number);
    }

    /**
     * Format a float as a string.
     *
     * @param number the number to format
     * @return the formatted string
     */
    public static String format(final float number) {
        return NumberFormat.getNumberInstance().format(number);
    }

    /**
     * Concatenate multiple arrays of items into a single array.
     *
     * @param arrays the arrays to concatenate
     * @return the concatenated array of items
     */
    public static Item[] concatenate(final Item[]... arrays) {
        var list = new ArrayList<Item>();
        for (var array : arrays) {
            Arrays.stream(array).filter(Objects::nonNull).forEach(list::add);
        }
        final Item[] items = new Item[list.size()];
        return list.toArray(items);
    }

    /**
     * Concatenate a variable number of elements into a collection.
     *
     * @param <T>      the type parameter
     * @param elements the elements to concatenate
     * @return the collection of elements
     */
    public static final <T> Collection<T> concatenate(final T... elements) {
        return new LinkedList<T>(Arrays.asList(elements));
    }

    /**
     * Concatenate two collections of items into a single array.
     *
     * @param arg0 the first collection of items
     * @param arg1 the second collection of items
     * @return the concatenated array of items
     */
    public static Item[] concatenate(final Collection<Item> arg0, final Collection<Item> arg1) {
        final ArrayList<Item> list = new ArrayList<Item>();
        for (final Item item : arg0) {
            list.add(item);
        }
        for (final Item item : arg1) {
            list.add(item);
        }
        final Item[] items = new Item[list.size()];
        return list.toArray(items);
    }

    /**
     * Check if two objects are equal.
     *
     * @param a the first object
     * @param b the second object
     * @return true if the objects are equal, false otherwise
     */
    public static boolean equals(final Object a, final Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    /**
     * Round a double value to a specified number of decimal places.
     *
     * @param value  the value to round
     * @param places the number of decimal places to round to
     * @return the rounded value
     */
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        try {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } catch (final NumberFormatException e) {
            return -1;
        }
    }
}