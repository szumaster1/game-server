package content.global.skill.support.thieving;

import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The enum Stall.
 */
public enum Stall {
    /**
     * The Vegetable stall.
     */
    VEGETABLE_STALL(new Integer[]{4706, 4708}, new Integer[]{634}, 2, new Item[]{new Item(1957, 1), new Item(1965, 1), new Item(1942, 1), new Item(1982, 1), new Item(1550, 1)}, 10, 4, "vegetables"),
    /**
     * The Baker stall.
     */
    BAKER_STALL(new Integer[]{2561, 6163, 34384}, new Integer[]{634, 6984, 34381}, 5, new Item[]{new Item(1891, 1), new Item(2309, 1), new Item(1901, 1)}, 16, 4, "pastries"),
    /**
     * The Crafting stall.
     */
    CRAFTING_STALL(new Integer[]{4874, 6166}, new Integer[]{4797, 6984}, 5, new Item[]{new Item(1592, 1), new Item(1597, 1), new Item(1755, 1)}, 16, 12, "crafting supplies"),
    /**
     * The Tea stall.
     */
    TEA_STALL(new Integer[]{635, 6574}, new Integer[]{634, 6573}, 5, new Item[]{new Item(712, 1)}, 16, 12, "tea"),
    /**
     * The Silk stall.
     */
    SILK_STALL(new Integer[]{34383, 2560}, new Integer[]{34381, 634}, 20, new Item[]{new Item(950, 1)}, 24, 13, "silk"),
    /**
     * The Wine stall.
     */
    WINE_STALL(new Integer[]{2046, 14011}, new Integer[]{634}, 22, new Item[]{new Item(1935, 1), new Item(1937, 1), new Item(1993, 1), new Item(7919, 1)}, 27, 27, "wine"), //OBJECT MISSING IN CACHE
    /**
     * The Market seed stall.
     */
    MARKET_SEED_STALL(new Integer[]{7053}, new Integer[]{634}, 27, new Item[]{new Item(5096, 1), new Item(5097, 1), new Item(5101, 1), new Item(5318, 1), new Item(5319, 1), new Item(5324, 1)}, 10, 19, "seeds"),
    /**
     * The Fur stall.
     */
    FUR_STALL(new Integer[]{34387, 2563, 4278}, new Integer[]{34381, 634, 634}, 35, new Item[]{new Item(6814, 1), new Item(958, 1)}, 36, 25, "fur"),
    /**
     * The Fish stall.
     */
    FISH_STALL(new Integer[]{4277, 4705, 4707}, new Integer[]{634, 634, 634}, 42, new Item[]{new Item(331, 1), new Item(359, 1), new Item(377, 1)}, 42, 27, "fish"),
    /**
     * The Crossbow stall.
     */
    CROSSBOW_STALL(new Integer[]{17031}, new Integer[]{6984}, 49, new Item[]{new Item(877, 3), new Item(9420, 1), new Item(9440, 1)}, 52, 19, "equipment"),
    /**
     * The Silver stall.
     */
    SILVER_STALL(new Integer[]{2565, 6164, 34382}, new Integer[]{634, 6984, 34381}, 50, new Item[]{new Item(442, 1)}, 54, 50, "jewellery"),
    /**
     * The Spice stall.
     */
    SPICE_STALL(new Integer[]{34386, 6166}, new Integer[]{34381, 6984}, 65, new Item[]{new Item(2007, 1)}, 81, 134, "spices"),
    /**
     * The Gem stall.
     */
    GEM_STALL(new Integer[]{2562, 6162, 34385}, new Integer[]{634, 6984, 34381}, 75, new Item[]{new Item(1623, 1), new Item(1605, 1), new Item(1603, 1), new Item(1601, 1)}, 160, 300, "gems"),
    /**
     * The Scimitar stall.
     */
    SCIMITAR_STALL(new Integer[]{4878}, new Integer[]{4797}, 65, new Item[]{new Item(1323, 1)}, 100, 134, "equipment"),
    /**
     * The Magic stall.
     */
    MAGIC_STALL(new Integer[]{4877}, new Integer[]{4797}, 65, new Item[]{new Item(556, 1), new Item(557, 1), new Item(554, 1), new Item(555, 1), new Item(563, 1)}, 100, 134, "equipment"),
    /**
     * The General stall.
     */
    GENERAL_STALL(new Integer[]{4876}, new Integer[]{4797}, 5, new Item[]{new Item(1931, 1), new Item(2347, 1), new Item(590, 1)}, 16, 12, "goods"),
    /**
     * The Food stall.
     */
    FOOD_STALL(new Integer[]{4875}, new Integer[]{4797}, 5, new Item[]{new Item(1963, 1)}, 16, 12, "food"),
    /**
     * The Candles.
     */
    CANDLES(new Integer[]{19127}, new Integer[]{19127}, 20, new Item[]{new Item(36, 1)}, 20, 0, "candles");
    //CRAFTING_STALL (Ape Atoll) shares same drops/exp as regular crafting stall

    /**
     * The constant idMap.
     */
//Quest Stalls Rocking Out
    //CUSTOMS_EVIDENCE_FILES(new Integer[]{FIND OBJ ID}, FIND OBJ EMPTY ID, 63, new Item[]{new Item(1333, 1), new Item(1617, 1), new Item(1619, 1), new Item(1623, 1), new Item(385, 1), new Item(2359, 1), new Item(2357, 1), new Item(2351, 1), new Item(7114, 1), new Item(7134, 1), new Item(1025, 1), new Item(1281, 1), new Item(1325, 1), new Item(1323, 1), new Item(1321, 1), new Item(995, 300)}, 75, 100);
    public static HashMap<Integer, Stall> idMap = new HashMap<>();

    static {
        Arrays.stream(Stall.values()).forEach(entry -> entry.full_ids.stream().forEach(id -> idMap.putIfAbsent(id, entry)));
    }

    /**
     * Full ids stall.
     */
    List<Integer> full_ids;
    /**
     * Empty ids stall.
     */
    List<Integer> empty_ids;
    /**
     * The Level.
     */
    int level, /**
     * Delay stall.
     */
    delay;
    /**
     * Rewards stall.
     */
    Item[] rewards;
    /**
     * The Experience.
     */
    double experience;
    /**
     * The Msg item.
     */
    String msgItem;

    Stall(Integer[] full_ids, Integer[] empty_ids, int level, Item[] rewards, double experience, int delay, String msgItem) {
        this.full_ids = new ArrayList<Integer>(Arrays.asList(full_ids));
        this.empty_ids = new ArrayList<Integer>(Arrays.asList(empty_ids));
        this.level = level;
        this.delay = delay;
        this.rewards = rewards;
        this.experience = experience;
        this.msgItem = msgItem;
    }

    /**
     * Gets full i ds.
     *
     * @return the full i ds
     */
    public List<Integer> getFullIDs() {
        return full_ids;
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
     * Get rewards item [ ].
     *
     * @return the item [ ]
     */
    public Item[] getRewards() {
        return rewards;
    }

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Gets empty.
     *
     * @param id the id
     * @return the empty
     */
    public int getEmpty(int id) {
        int fullIndex = full_ids.indexOf(id);
        return this.empty_ids.get(fullIndex);
    }

    /**
     * Gets delay.
     *
     * @return the delay
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Gets random loot.
     *
     * @return the random loot
     */
    public Item getRandomLoot() {
        return rewards[RandomFunction.random(rewards.length)];
    }

    /**
     * For object stall.
     *
     * @param object the object
     * @return the stall
     */
    public static Stall forObject(final Scenery object) {
        return idMap.get(object.getId());
    }
}
