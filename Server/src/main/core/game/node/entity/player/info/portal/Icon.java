package core.game.node.entity.player.info.portal;

/**
 * Represents a chat icon.
 *
 * @author Vexia
 */
public enum Icon {
    /**
     * Nothing icon.
     */
    NOTHING(0, 0),
    /**
     * Green icon.
     */
    GREEN(1, 5),
    /**
     * Red icon.
     */
    RED(2, 6),
    /**
     * Yellow icon.
     */
    YELLOW(3, 7),
    /**
     * Blue icon.
     */
    BLUE(4, 8),
    /**
     * Orange icon.
     */
    ORANGE(5, 9),
    /**
     * Pink icon.
     */
    PINK(6, 10),
    /**
     * Purple icon.
     */
    PURPLE(7, 11),
    /**
     * Brown icon.
     */
    BROWN(8, 12);

	/**
	 * The id.
	 */
	private final int id;

	/**
	 * The index id.
	 */
	private final int indexId;

	/**
	 * Constructs a new {@Code Icons} {@Code Object}
	 * @param id the id.
	 * @param indexId the indexid.
	 */
	private Icon(int id, int indexId) {
		this.id = id;
		this.indexId = indexId;
	}

    /**
     * Gets an icon for the id.
     *
     * @param id the id.
     * @return the id.S
     */
    public static Icon forId(int id) {
		for (Icon icon : values()) {
			if (icon.getId() == id) {
				return icon;
			}
		}
		return GREEN;
	}

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
		return id;
	}

    /**
     * Gets the indexId.
     *
     * @return the indexId
     */
    public int getIndexId() {
		return indexId;
	}

}
