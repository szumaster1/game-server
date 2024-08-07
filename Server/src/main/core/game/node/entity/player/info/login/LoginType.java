package core.game.node.entity.player.info.login;

/**
 * The enum Login type.
 */
public enum LoginType {

    /**
     * Normal login login type.
     */
    NORMAL_LOGIN(16),

    /**
     * Reconnect type login type.
     */
    RECONNECT_TYPE(18);

    private int type;

    LoginType(int type) {
        this.setType(type);
    }

    /**
     * From type login type.
     *
     * @param type the type
     * @return the login type
     */
    public static LoginType fromType(int type) {
        for (LoginType login : LoginType.values()) {
            if (login.getType() == type) {
                return login;
            }
        }
        return null;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }
}
