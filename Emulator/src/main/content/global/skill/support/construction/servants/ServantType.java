package content.global.skill.support.construction.servants;


import org.rs.consts.NPCs;
import core.game.node.item.Item;

/**
 * The enum Servant type.
 */
public enum ServantType {
    /**
     * None servant type.
     */
    NONE(-1, -1, -1, -1, -1),
    /**
     * Rick servant type.
     */
    RICK(NPCs.RICK_4235, 500, 6, 20, 60),
    /**
     * The Maid.
     */
    MAID(NPCs.MAID_4237, 1000, 10, 25, 50, new Item(2003)),
    /**
     * The Cook.
     */
    COOK(NPCs.COOK_4239, 3000, 16, 30, 17, new Item(2301), new Item(712)),
    /**
     * The Butler.
     */
    BUTLER(NPCs.BUTLER_4241, 5000, 20, 40, 12, new Item(1897), new Item(712)),
    /**
     * The Demon butler.
     */
    DEMON_BUTLER(NPCs.DEMON_BUTLER_4243, 10000, 26, 50, 7, new Item(2011));

    private int npcId;
    private int cost;
    private int capacity;
    private int level;
    private int timer;
    private Item[] food;

    private ServantType(int npcId, int cost, int capacity, int level, int timer, Item... food) {
        this.npcId = npcId;
        this.cost = cost;
        this.capacity = capacity;
        this.level = level;
        this.timer = timer;
        this.food = food;
    }

    /**
     * For id servant type.
     *
     * @param id the id
     * @return the servant type
     */
    public static ServantType forId(int id) {
        for (ServantType s : ServantType.values()) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return npcId;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
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
     * Gets timer.
     *
     * @return the timer
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Get food item [ ].
     *
     * @return the item [ ]
     */
    public Item[] getFood() {
        return food;
    }
}