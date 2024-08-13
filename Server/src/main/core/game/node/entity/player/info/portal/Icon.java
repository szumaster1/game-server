package core.game.node.entity.player.info.portal;

/**
 * The enum Icon represents different icons used in the game.
 */
public enum Icon {
    NOTHING(0, 0),
    GREEN(1, 5),
    RED(2, 6),
    YELLOW(3, 7),
    BLUE(4, 8),
    ORANGE(5, 9),
    PINK(6, 10),
    PURPLE(7, 11),
    BROWN(8, 12);

    private final int id; // Unique identifier for the icon
    private final int indexId; // Index used for referencing the icon

    /**
     * Constructor for the Icon enum.
     *
     * @param id the unique identifier for the icon
     * @param indexId the index used for referencing the icon
     */
    private Icon(int id, int indexId) {
        this.id = id; // Assigning the unique identifier
        this.indexId = indexId; // Assigning the index identifier
    }

    /**
     * For id icon retrieves the icon based on its id.
     *
     * @param id the id of the icon to retrieve
     * @return the corresponding icon, or GREEN if not found
     */
    public static Icon forId(int id) {
        for (Icon icon : values()) { // Iterating through all icon values
            if (icon.getId() == id) { // Checking if the current icon's id matches the provided id
                return icon; // Returning the matching icon
            }
        }
        return GREEN; // Returning GREEN as a default icon if no match is found
    }

    /**
     * Gets id.
     *
     * @return the id of the icon
     */
    public int getId() {
        return id; // Returning the unique identifier of the icon
    }

    /**
     * Gets index id.
     *
     * @return the index id of the icon
     */
    public int getIndexId() {
        return indexId; // Returning the index identifier of the icon
    }
}
