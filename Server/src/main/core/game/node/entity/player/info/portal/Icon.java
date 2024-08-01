package core.game.node.entity.player.info.portal;

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

    private final int id;

    private final int indexId;

    private Icon(int id, int indexId) {
        this.id = id;
        this.indexId = indexId;
    }

    public static Icon forId(int id) {
        for (Icon icon : values()) {
            if (icon.getId() == id) {
                return icon;
            }
        }
        return GREEN;
    }

    public int getId() {
        return id;
    }

    public int getIndexId() {
        return indexId;
    }

}
