package content.global.skill.skillcape

import org.rs.consts.Items

/**
 * Represents the skillcape.
 */
enum class Skillcape {
    ATTACK,
    STRENGTH,
    DEFENCE,
    RANGING,
    PRAYER,
    MAGIC,
    RUNECRAFTING,
    HITPOINTS,
    AGILITY,
    HERBLORE,
    THIEVING,
    CRAFTING,
    FLETCHING,
    SLAYER,
    CONSTRUCTION,
    MINING,
    SMITHING,
    FISHING,
    COOKING,
    FIREMAKING,
    WOODCUTTING,
    FARMING,
    HUNTING,
    SUMMONING,
    NONE;

    companion object {
        fun forId(id: Int): Skillcape {
            return when (id) {
                Items.ATTACK_CAPE_9747,      Items.ATTACK_CAPET_9748     -> ATTACK
                Items.STRENGTH_CAPE_9750,    Items.STRENGTH_CAPET_9751   -> STRENGTH
                Items.DEFENCE_CAPE_9753,     Items.DEFENCE_CAPET_9754    -> DEFENCE
                Items.RANGING_CAPE_9756,     Items.RANGING_CAPET_9757    -> RANGING
                Items.PRAYER_CAPE_9759,      Items.PRAYER_CAPET_9760     -> PRAYER
                Items.MAGIC_CAPE_9762,       Items.MAGIC_CAPET_9763      -> MAGIC
                Items.RUNECRAFT_CAPE_9765,   Items.RUNECRAFT_CAPET_9766  -> RUNECRAFTING
                Items.HITPOINTS_CAPE_9768,   Items.HITPOINTS_CAPET_9769  -> HITPOINTS
                Items.AGILITY_CAPE_9771,     Items.AGILITY_CAPET_9772    -> AGILITY
                Items.HERBLORE_CAPE_9774,    Items.HERBLORE_CAPET_9775   -> HERBLORE
                Items.THIEVING_CAPE_9777,    Items.THIEVING_CAPET_9778   -> THIEVING
                Items.CRAFTING_CAPE_9780,    Items.CRAFTING_CAPET_9781   -> CRAFTING
                Items.FLETCHING_CAPE_9783,   Items.FLETCHING_CAPET_9784  -> FLETCHING
                Items.SLAYER_CAPE_9786,      Items.SLAYER_CAPET_9787     -> SLAYER
                Items.CONSTRUCT_CAPE_9789,   Items.CONSTRUCT_CAPET_9790  -> CONSTRUCTION
                Items.MINING_CAPE_9792,      Items.MINING_CAPET_9793     -> MINING
                Items.SMITHING_CAPE_9795,    Items.SMITHING_CAPET_9796   -> SMITHING
                Items.FISHING_CAPE_9798,     Items.FISHING_CAPET_9799    -> FISHING
                Items.COOKING_CAPE_9801,     Items.COOKING_CAPET_9802    -> COOKING
                Items.FIREMAKING_CAPE_9804,  Items.FIREMAKING_CAPET_9805 -> FIREMAKING
                Items.WOODCUTTING_CAPE_9807, Items.WOODCUT_CAPET_9808    -> WOODCUTTING
                Items.FARMING_CAPE_9810,     Items.FARMING_CAPET_9811    -> FARMING
                Items.HUNTER_CAPE_9948,      Items.HUNTER_CAPET_9949     -> HUNTING
                Items.SUMMONING_CAPE_12169,  Items.SUMMONING_CAPET_12170 -> SUMMONING
                else -> NONE
            }
        }
    }
}
