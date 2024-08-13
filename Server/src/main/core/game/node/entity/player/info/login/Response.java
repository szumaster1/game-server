package core.game.node.entity.player.info.login;

/**
 * The enum Response represents various login responses.
 */
public enum Response {

    /**
     * Unexpected response response indicates an unanticipated login response.
     */
    UNEXPECTED_RESPONSE(0),

    /**
     * Could not display ad response indicates that an advertisement could not be shown.
     */
    COULD_NOT_DISPLAY_AD(1),

    /**
     * Successful response indicates a successful login attempt.
     */
    SUCCESSFUL(2),

    /**
     * Invalid credentials response indicates that the provided login credentials are incorrect.
     */
    INVALID_CREDENTIALS(3),

    /**
     * Account disabled response indicates that the user's account has been disabled.
     */
    ACCOUNT_DISABLED(4),

    /**
     * Already online response indicates that the user is already logged in.
     */
    ALREADY_ONLINE(5),

    /**
     * Updated response indicates that the user's information has been updated.
     */
    UPDATED(6),

    /**
     * Full world response indicates that the game world is currently full.
     */
    FULL_WORLD(7),

    /**
     * Login server offline response indicates that the login server is currently not operational.
     */
    LOGIN_SERVER_OFFLINE(8),

    /**
     * Login limit exceeded response indicates that the user has exceeded the allowed number of login attempts.
     */
    LOGIN_LIMIT_EXCEEDED(9),

    /**
     * Bad session id response indicates that the session ID provided is invalid.
     */
    BAD_SESSION_ID(10),

    /**
     * Weak password response indicates that the password provided does not meet security requirements.
     */
    WEAK_PASSWORD(11),

    /**
     * Members world response indicates that the user is attempting to access a members-only world.
     */
    MEMBERS_WORLD(12),

    /**
     * Could not login response indicates that the login attempt was unsuccessful for an unspecified reason.
     */
    COULD_NOT_LOGIN(13),

    /**
     * Updating response indicates that the game is currently being updated.
     */
    UPDATING(14),

    /**
     * Too many incorrect logins response indicates that the user has made too many unsuccessful login attempts.
     */
    TOO_MANY_INCORRECT_LOGINS(16),

    /**
     * Standing in member response indicates that the user is currently in a members-only area.
     */
    STANDING_IN_MEMBER(17),

    /**
     * Locked response indicates that the user's account is locked.
     */
    LOCKED(18),

    /**
     * Closed beta response indicates that the user is attempting to access a closed beta version of the game.
     */
    CLOSED_BETA(19),

    /**
     * Invalid login server response indicates that the login server specified is not valid.
     */
    INVALID_LOGIN_SERVER(20),

    /**
     * Moving world response indicates that the user is being moved to a different game world.
     */
    MOVING_WORLD(21),

    /**
     * Error loading profile response indicates that there was an error while loading the user's profile.
     */
    ERROR_LOADING_PROFILE(24),

    /**
     * Banned response indicates that the user's account has been banned.
     */
    BANNED(26);

    private final int opcode; // Holds the unique opcode for each response.

    /**
     * Constructor for Response enum that assigns the opcode.
     *
     * @param opcode the unique opcode for the response
     */
    Response(int opcode) {
        this.opcode = opcode; // Assigns the opcode to the instance variable.
    }

    /**
     * Get response based on the provided opcode.
     *
     * @param opcode the opcode to look up
     * @return the corresponding Response or null if not found
     */
    public static Response get(int opcode) {
        for (Response r : values()) { // Iterates through all Response values.
            if (r.opcode == opcode) { // Checks if the current response's opcode matches the provided opcode.
                return r; // Returns the matching Response.
            }
        }
        return null; // Returns null if no matching Response is found.
    }

    /**
     * Get the opcode associated with this response.
     *
     * @return the opcode as an integer
     */
    public int opcode() {
        return opcode; // Returns the opcode of the response.
    }
}