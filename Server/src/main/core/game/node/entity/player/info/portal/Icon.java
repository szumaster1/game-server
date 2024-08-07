package core.game.node.entity.player.info.portal;

/**
 * The enum Icon.
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

    private final int id;

    private final int indexId;

    private Icon(int id, int indexId) {
        this.id = id;
        this.indexId = indexId;
    }

    /**
     * For id icon.
     *
     * @param id the id
     * @return the icon
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
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets index id.
     *
     * @return the index id
     */
    public int getIndexId() {
        return indexId;
    }

}
