package core.game.node.entity.player.info.login;

/**
 * The enum Response.
 */
public enum Response {

    /**
     * Unexpected response response.
     */
    UNEXPECTED_RESPONSE(0),

    /**
     * Could not display ad response.
     */
    COULD_NOT_DISPLAY_AD(1),

    /**
     * Successful response.
     */
    SUCCESSFUL(2),

    /**
     * Invalid credentials response.
     */
    INVALID_CREDENTIALS(3),

    /**
     * Account disabled response.
     */
    ACCOUNT_DISABLED(4),

    /**
     * Already online response.
     */
    ALREADY_ONLINE(5),

    /**
     * Updated response.
     */
    UPDATED(6),

    /**
     * Full world response.
     */
    FULL_WORLD(7),

    /**
     * Login server offline response.
     */
    LOGIN_SERVER_OFFLINE(8),

    /**
     * Login limit exceeded response.
     */
    LOGIN_LIMIT_EXCEEDED(9),

    /**
     * Bad session id response.
     */
    BAD_SESSION_ID(10),

    /**
     * Weak password response.
     */
    WEAK_PASSWORD(11),

    /**
     * Members world response.
     */
    MEMBERS_WORLD(12),

    /**
     * Could not login response.
     */
    COULD_NOT_LOGIN(13),

    /**
     * Updating response.
     */
    UPDATING(14),

    /**
     * Too many incorrect logins response.
     */
    TOO_MANY_INCORRECT_LOGINS(16),

    /**
     * Standing in member response.
     */
    STANDING_IN_MEMBER(17),

    /**
     * Locked response.
     */
    LOCKED(18),

    /**
     * Closed beta response.
     */
    CLOSED_BETA(19),

    /**
     * Invalid login server response.
     */
    INVALID_LOGIN_SERVER(20),

    /**
     * Moving world response.
     */
    MOVING_WORLD(21),

    /**
     * Error loading profile response.
     */
    ERROR_LOADING_PROFILE(24),

    /**
     * Banned response.
     */
    BANNED(26);

    private final int opcode;

    Response(int opcode) {
        this.opcode = opcode;
    }

    /**
     * Get response.
     *
     * @param opcode the opcode
     * @return the response
     */
    public static Response get(int opcode) {
        for (Response r : values()) {
            if (r.opcode == opcode) {
                return r;
            }
        }
        return null;
    }

    /**
     * Opcode int.
     *
     * @return the int
     */
    public int opcode() {
        return opcode;
    }
}