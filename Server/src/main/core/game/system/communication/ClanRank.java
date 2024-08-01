package core.game.system.communication;

public enum ClanRank {
    NONE(-1, "Anyone"),
    FRIEND(0, "Any friends"),
    RECRUIT(1, "Recruit+"),
    CORPORAL(2, "Corporal+"),
    SERGEANT(3, "Sergeant+"),
    LIEUTENANT(4, "Lieutenant+"),
    CAPTAIN(5, "Captain+"),
    GENERAL(6, "General+"),
    OWNER(7, "Only me"),
    ADMINISTRATOR(127, "No-one");

    private final int value;

    private final String info;

    private ClanRank(int value, String info) {
        this.value = value;
        this.info = info;
    }

    public int getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "Rank=[" + name() + "], Info=" + "[" + getInfo() + "]";
    }
}