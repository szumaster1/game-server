package core.game.node.entity.player.info.login;

public enum LoginType {

    NORMAL_LOGIN(16),

    RECONNECT_TYPE(18);

    private int type;

    LoginType(int type) {
        this.setType(type);
    }

    public static LoginType fromType(int type) {
        for (LoginType login : LoginType.values()) {
            if (login.getType() == type) {
                return login;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }
}
