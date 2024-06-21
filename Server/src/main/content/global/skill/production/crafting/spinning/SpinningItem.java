package content.global.skill.production.crafting.spinning;

/**
 * The enum Spinning item.
 */
public enum SpinningItem {
    /**
     * Wool spinning item.
     */
    WOOL(19, 1737, 1759, 1, 2.5),
    /**
     * Flax spinning item.
     */
    FLAX(17, 1779, 1777, 10, 15),
    /**
     * Root spinning item.
     */
    ROOT(23, 6051, 6038, 19, 30),
    /**
     * Root 1 spinning item.
     */
    ROOT1(23, 6043, 6038, 19, 30),
    /**
     * Root 2 spinning item.
     */
    ROOT2(23, 6045, 6038, 19, 30),
    /**
     * Root 3 spinning item.
     */
    ROOT3(23, 6047, 6038, 19, 30),
    /**
     * Root 4 spinning item.
     */
    ROOT4(23, 6049, 6038, 19, 30),
    /**
     * Root 5 spinning item.
     */
    ROOT5(23, 6053, 6038, 19, 30),
    /**
     * Sinew spinning item.
     */
    SINEW(27, 9436, 9438, 10, 15),
    /**
     * Tree roots spinning item.
     */
    TREE_ROOTS(31, 6043, 9438, 10, 15),
    /**
     * Yack spinning item.
     */
    YACK(35, 10814, 954, 30, 25);

    private final int button;

    private final int need;

    private final int product;

    private final int level;

    private final double exp;


    SpinningItem(int button, int need, int product, int level, double exp) {
        this.button = button;
        this.need = need;
        this.product = product;
        this.level = level;
        this.exp = exp;
    }

    /**
     * For id spinning item.
     *
     * @param id the id
     * @return the spinning item
     */
    public static SpinningItem forId(int id) {
        for (SpinningItem spin : SpinningItem.values()) {
            if (spin.button == id) {
                return spin;
            }
        }
        return null;
    }

    /**
     * Gets button.
     *
     * @return the button
     */
    public int getButton() {
        return button;
    }

    /**
     * Gets need.
     *
     * @return the need
     */
    public int getNeed() {
        return need;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public int getProduct() {
        return product;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets exp.
     *
     * @return the exp
     */
    public double getExp() {
        return exp;
    }

}
