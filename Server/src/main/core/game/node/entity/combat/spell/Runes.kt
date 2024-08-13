package core.game.node.entity.combat.spell

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Runes
 *
 * @param id
 * @constructor Runes
 */
enum class Runes(val id: Int) {
    /**
     * Air Rune
     *
     * @constructor Air Rune
     */
    AIR_RUNE(Items.AIR_RUNE_556),

    /**
     * Water Rune
     *
     * @constructor Water Rune
     */
    WATER_RUNE(Items.WATER_RUNE_555),

    /**
     * Earth Rune
     *
     * @constructor Earth Rune
     */
    EARTH_RUNE(Items.EARTH_RUNE_557),

    /**
     * Fire Rune
     *
     * @constructor Fire Rune
     */
    FIRE_RUNE(Items.FIRE_RUNE_554),

    /**
     * Mind Rune
     *
     * @constructor Mind Rune
     */
    MIND_RUNE(Items.MIND_RUNE_558),

    /**
     * Nature Rune
     *
     * @constructor Nature Rune
     */
    NATURE_RUNE(Items.NATURE_RUNE_561),

    /**
     * Chaos Rune
     *
     * @constructor Chaos Rune
     */
    CHAOS_RUNE(Items.CHAOS_RUNE_562),

    /**
     * Death Rune
     *
     * @constructor Death Rune
     */
    DEATH_RUNE(Items.DEATH_RUNE_560),

    /**
     * Cosmic Rune
     *
     * @constructor Cosmic Rune
     */
    COSMIC_RUNE(Items.COSMIC_RUNE_564),

    /**
     * Blood Rune
     *
     * @constructor Blood Rune
     */
    BLOOD_RUNE(Items.BLOOD_RUNE_565),

    /**
     * Soul Rune
     *
     * @constructor Soul Rune
     */
    SOUL_RUNE(Items.SOUL_RUNE_566),

    /**
     * Astral Rune
     *
     * @constructor Astral Rune
     */
    ASTRAL_RUNE(Items.ASTRAL_RUNE_9075),

    /**
     * Law Rune
     *
     * @constructor Law Rune
     */
    LAW_RUNE(Items.LAW_RUNE_563),

    /**
     * Body Rune
     *
     * @constructor Body Rune
     */
    BODY_RUNE(Items.BODY_RUNE_559),

    /**
     * Saradomin Staff
     *
     * @constructor Saradomin Staff
     */
    SARADOMIN_STAFF(Items.SARADOMIN_STAFF_2415),

    /**
     * Guthix Staff
     *
     * @constructor Guthix Staff
     */
    GUTHIX_STAFF(Items.GUTHIX_STAFF_2416),

    /**
     * Zamorak Staff
     *
     * @constructor Zamorak Staff
     */
    ZAMORAK_STAFF(Items.ZAMORAK_STAFF_2417),

    /**
     * Zuriels Staff
     *
     * @constructor Zuriels Staff
     */
    ZURIELS_STAFF(Items.ZURIELS_STAFF_13867);

    /**
     * Transform
     *
     * @return
     */
    fun transform(): Item {
        return Item(id)
    }

    /**
     * Get item
     *
     * @param amount
     * @return
     */
    fun getItem(amount: Int): Item {
        return Item(id, amount)
    }

    companion object {

        @JvmStatic
        fun forId(id: Int): Runes? {
            for (rune in values()) {
                if (rune.id == id) {
                    return rune
                }
            }
            return null
        }

        fun isInfinite(rune: Runes?, weapon: Item?, vararg type: SpellType): Boolean {
            if (weapon == null || rune == null) {
                return false
            }
            if (type != null) {
                if (weapon.id == 2415 && rune == SARADOMIN_STAFF && type.size == 1) {
                    if (type[0] === SpellType.GOD_STRIKE) {
                        return true
                    }
                }
                if (weapon.id == 2416 && rune == GUTHIX_STAFF && type.size == 1) {
                    if (type[0] === SpellType.GOD_STRIKE) {
                        return true
                    }
                }
                if (weapon.id == 13867 && rune == ZURIELS_STAFF && type.size == 1) {
                    if (type[0] === SpellType.BARRAGE || type[0] === SpellType.BLITZ || type[0] === SpellType.RUSH || type[0] === SpellType.BURST) {
                        return true
                    }
                }
                if (weapon.id == 2417 && rune == ZAMORAK_STAFF && type.size == 1) {
                    if (type[0] === SpellType.GOD_STRIKE) {
                        return true
                    }
                }
            }
            if (rune == AIR_RUNE) {
                if (weapon.id == Items.STAFF_OF_AIR_1381 || weapon.id == Items.AIR_BATTLESTAFF_1397) // air staff
                    return true
            } else if (rune == WATER_RUNE) {
                if (weapon.id == Items.STAFF_OF_WATER_1383 || weapon.id == Items.WATER_BATTLESTAFF_1395) // water staff
                    return true
            } else if (rune == EARTH_RUNE) {
                if (weapon.id == Items.STAFF_OF_EARTH_1385 || weapon.id == Items.EARTH_BATTLESTAFF_1399) // earth staff
                    return true
            } else if (rune == FIRE_RUNE) {
                if (weapon.id == Items.STAFF_OF_FIRE_1387 || weapon.id == Items.FIRE_BATTLESTAFF_1393) // fire staff
                    return true
            }
            return false
        }
    }
}
