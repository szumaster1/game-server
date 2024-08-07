package content.global.handlers.item.equipment.gloves;

import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.skill.Skills;

import java.util.HashMap;

/**
 * The enum Brawling gloves.
 */
public enum BrawlingGloves {
    /**
     * Melee brawling gloves.
     */
    MELEE(13845, "melee", 500, 1, Skills.ATTACK),
    /**
     * Ranged brawling gloves.
     */
    RANGED(13846, "ranged", 500, 2, Skills.RANGE),
    /**
     * Magic brawling gloves.
     */
    MAGIC(13847, "magic", 500, 3, Skills.MAGIC),
    /**
     * Prayer brawling gloves.
     */
    PRAYER(13848, "prayer", 450, 4, Skills.PRAYER),
    /**
     * Agility brawling gloves.
     */
    AGILITY(13849, "agility", 450, 5, Skills.AGILITY),
    /**
     * Woodcutting brawling gloves.
     */
    WOODCUTTING(13850, "wc", 450, 6, Skills.WOODCUTTING),
    /**
     * Firemaking brawling gloves.
     */
    FIREMAKING(13851, "fm", 450, 7, Skills.FIREMAKING),
    /**
     * Mining brawling gloves.
     */
    MINING(13852, "mining", 450, 8, Skills.MINING),
    /**
     * Hunter brawling gloves.
     */
    HUNTER(13853, "hunter", 450, 9, Skills.HUNTER),
    /**
     * Thieving brawling gloves.
     */
    THIEVING(13854, "thieving", 450, 10, Skills.THIEVING),
    /**
     * Smithing brawling gloves.
     */
    SMITHING(13855, "smithing", 450, 11, Skills.SMITHING),
    /**
     * Fishing brawling gloves.
     */
    FISHING(13856, "fishing", 450, 12, Skills.FISHING),
    /**
     * Cooking brawling gloves.
     */
    COOKING(13857, "cooking", 450, 13, Skills.COOKING);
    private final int id;
    private final String name;
    private final int charges;
    private final int indicator;
    private int skillSlot;

    BrawlingGloves(int id, String name, int charges, int indicator, int skillSlot) {
        this.id = id;
        this.name = ItemDefinition.forId(id).getName();
        this.charges = charges;
        this.indicator = indicator;
        this.skillSlot = skillSlot;
    }

    /**
     * The constant glovesMap.
     */
    public static HashMap<Integer, BrawlingGloves> glovesMap = new HashMap<>();
    /**
     * The constant skillMap.
     */
    public static HashMap<Integer, BrawlingGloves> skillMap = new HashMap<>();
    /**
     * The constant indicatorMap.
     */
    public static HashMap<Integer, BrawlingGloves> indicatorMap = new HashMap<>();

    static {
        for (BrawlingGloves gloves : BrawlingGloves.values()) {
            glovesMap.putIfAbsent(gloves.id, gloves);
            skillMap.putIfAbsent(gloves.skillSlot, gloves);
            indicatorMap.putIfAbsent(gloves.indicator, gloves);
        }
    }


    /**
     * For id brawling gloves.
     *
     * @param id the id
     * @return the brawling gloves
     */
    public static BrawlingGloves forId(int id) {
        return glovesMap.get(id);
    }

    /**
     * For indicator brawling gloves.
     *
     * @param indicator the indicator
     * @return the brawling gloves
     */
    public static BrawlingGloves forIndicator(int indicator) {
        return indicatorMap.get(indicator);
    }

    /**
     * For skill brawling gloves.
     *
     * @param skillSlot the skill slot
     * @return the brawling gloves
     */
    public static BrawlingGloves forSkill(int skillSlot) {
        return skillMap.get(skillSlot);
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
     * Gets charges.
     *
     * @return the charges
     */
    public int getCharges() {
        return charges;
    }

    /**
     * Gets indicator.
     *
     * @return the indicator
     */
    public int getIndicator() {
        return indicator;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets skill slot.
     *
     * @return the skill slot
     */
    public int getSkillSlot() {
        return skillSlot;
    }
}
