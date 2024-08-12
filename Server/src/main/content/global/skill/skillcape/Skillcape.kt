package content.global.skill.skillcape

/**
 * Skillcape.
 */
enum class Skillcape {
    /**
     * Attack.
     */
    ATTACK,

    /**
     * Strength.
     */
    STRENGTH,

    /**
     * Defence.
     */
    DEFENCE,

    /**
     * Ranging.
     */
    RANGING,

    /**
     * Prayer.
     */
    PRAYER,

    /**
     * Magic.
     */
    MAGIC,

    /**
     * Runecrafting.
     */
    RUNECRAFTING,

    /**
     * Hitpoints.
     */
    HITPOINTS,

    /**
     * Agility.
     */
    AGILITY,

    /**
     * Herblore.
     */
    HERBLORE,

    /**
     * Thieving.
     */
    THIEVING,

    /**
     * Crafting.
     */
    CRAFTING,

    /**
     * Fletching.
     */
    FLETCHING,

    /**
     * Slayer.
     */
    SLAYER,

    /**
     * Construction.
     */
    CONSTRUCTION,

    /**
     * Mining.
     */
    MINING,

    /**
     * Smithing.
     */
    SMITHING,

    /**
     * Fishing.
     */
    FISHING,

    /**
     * Cooking.
     */
    COOKING,

    /**
     * Firemaking.
     */
    FIREMAKING,

    /**
     * Woodcutting.
     */
    WOODCUTTING,

    /**
     * Farming.
     */
    FARMING,

    /**
     * Hunting.
     */
    HUNTING,

    /**
     * Summoning.
     */
    SUMMONING,

    /**
     * None.
     */
    NONE;

    companion object {
        fun forId(id: Int): Skillcape {
            return when (id) {
                9747, 9748 -> ATTACK
                9750, 9751 -> STRENGTH
                9753, 9754 -> DEFENCE
                9756, 9757 -> RANGING
                9759, 9760 -> PRAYER
                9762, 9763 -> MAGIC
                9765, 9766 -> RUNECRAFTING
                9768, 9769 -> HITPOINTS
                9771, 9772 -> AGILITY
                9774, 9775 -> HERBLORE
                9777, 9778 -> THIEVING
                9780, 9781 -> CRAFTING
                9783, 9784 -> FLETCHING
                9786, 9787 -> SLAYER
                9789, 9790 -> CONSTRUCTION
                9792, 9793 -> MINING
                9795, 9796 -> SMITHING
                9798, 9799 -> FISHING
                9801, 9802 -> COOKING
                9804, 9805 -> FIREMAKING
                9807, 9808 -> WOODCUTTING
                9810, 9811 -> FARMING
                9948, 9949 -> HUNTING
                12169, 12170 -> SUMMONING
                else -> NONE
            }
        }
    }
}
