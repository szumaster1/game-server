package core.game.node.entity.player.info.login;

/**
 * The enum Login type.
 * This enum defines the different types of login methods available in the game.
 */
public enum LoginType {

    /**
     * Normal login type.
     * Represents a standard login method for users.
     */
    NORMAL_LOGIN(16),

    /**
     * Reconnect type login type.
     * Represents a login method for users who are reconnecting to the game.
     */
    RECONNECT_TYPE(18);

    private int type; // Variable to hold the type value associated with the login type.

    // Constructor to initialize the type variable with the provided value.
    LoginType(int type) {
        this.setType(type); // Calls the setter method to assign the type value.
    }

    /**
     * From type login type.
     * This method retrieves the LoginType based on the provided integer type.
     *
     * @param type the type - the integer value representing the login type.
     * @return returns the corresponding LoginType or null if not found.
     */
    public static LoginType fromType(int type) {
        // Iterates through all LoginType values to find a match.
        for (LoginType login : LoginType.values()) {
            if (login.getType() == type) { // Checks if the current login type matches the provided type.
                return login; // Returns the matching LoginType.
            }
        }
        return null; // Returns null if no matching LoginType is found.
    }

    /**
     * Gets type.
     * This method retrieves the type value of the LoginType.
     *
     * @return returns the integer value of the login type.
     */
    public int getType() {
        return type; // Returns the type value.
    }

    // Setter method to assign a value to the type variable.
    private void setType(int type) {
        this.type = type; // Assigns the provided value to the type variable.
    }
}