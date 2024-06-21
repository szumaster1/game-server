package core.game.system.communication;

/**
 * Represents the rank of a clan member.
 *
 * @author Emperor
 */
public enum ClanRank {
    /**
     * None clan rank.
     */
    NONE(-1, "Anyone"),
    /**
     * The Friend.
     */
    FRIEND(0, "Any friends"),
    /**
     * Recruit clan rank.
     */
    RECRUIT(1, "Recruit+"),
    /**
     * Corporal clan rank.
     */
    CORPORAL(2, "Corporal+"),
    /**
     * Sergeant clan rank.
     */
    SERGEANT(3, "Sergeant+"),
    /**
     * Lieutenant clan rank.
     */
    LIEUTENANT(4, "Lieutenant+"),
    /**
     * Captain clan rank.
     */
    CAPTAIN(5, "Captain+"),
    /**
     * General clan rank.
     */
    GENERAL(6, "General+"),
    /**
     * The Owner.
     */
    OWNER(7, "Only me"),
    /**
     * Administrator clan rank.
     */
    ADMINISTRATOR(127, "No-one");

	/**
	 * The value of the rank.
	 */
	private final int value;

	/**
	 * The requirement info.
	 */
	private final String info;

	/**
	 * Constructs a new {@code ClanRank} {@code Object}.
	 * @param value The rank value.
	 * @param info The requirement info.
	 */
	private ClanRank(int value, String info) {
		this.value = value;
		this.info = info;
	}

    /**
     * Gets the value.
     *
     * @return The value.
     */
    public int getValue() {
		return value;
	}

    /**
     * Gets the info.
     *
     * @return The info.
     */
    public String getInfo() {
		return info;
	}
	
	@Override
	public String toString() {
		return "Rank=[" + name() + "], Info=" + "[" + getInfo() + "]";
	}
}