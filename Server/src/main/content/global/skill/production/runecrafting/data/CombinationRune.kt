package content.global.skill.production.runecrafting.data

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Combination rune
 *
 * @param rune Represents the item associated with the combination rune.
 * @param level Indicates the required level to use the combination rune.
 * @param experience Specifies the experience gained from using the combination rune.
 * @param altars Lists the altars where the combination rune can be utilized.
 * @constructor
 *
 * @param runes A variable number of runes that can be combined with this combination rune.
 */
enum class CombinationRune(
    val rune: Item, // The item that represents the combination rune.
    val level: Int, // The level required to use this combination rune.
    val experience: Double, // The experience points awarded for using this rune.
    val altars: Array<Altar>, // The altars where this combination rune can be used.
    vararg runes: Rune // A variable number of runes that can be combined with this combination rune.
) {
    /**
     * Mist
     *
     * @constructor Mist
     */
    MIST(
        rune = Item(Items.MIST_RUNE_4695),
        level = 6,
        experience = 8.0,
        altars = arrayOf(Altar.WATER, Altar.AIR),
        Rune.AIR, Rune.WATER
    ),

    /**
     * Dust
     *
     * @constructor Dust
     */
    DUST(
        rune = Item(Items.DUST_RUNE_4696),
        level = 10,
        experience = 8.3,
        altars = arrayOf(Altar.EARTH, Altar.AIR),
        Rune.AIR, Rune.EARTH
    ),

    /**
     * Mud
     *
     * @constructor Mud
     */
    MUD(
        rune = Item(Items.MUD_RUNE_4698),
        level = 13,
        experience = 9.3,
        altars = arrayOf(Altar.EARTH, Altar.WATER),
        Rune.WATER, Rune.EARTH
    ),

    /**
     * Smoke
     *
     * @constructor Smoke
     */
    SMOKE(
        rune = Item(Items.SMOKE_RUNE_4697),
        level = 15,
        experience = 8.5,
        altars = arrayOf(Altar.FIRE, Altar.AIR),
        Rune.AIR, Rune.FIRE
    ),

    /**
     * Steam
     *
     * @constructor Steam
     */
    STEAM(
        rune = Item(Items.STEAM_RUNE_4694),
        level = 19,
        experience = 9.3,
        altars = arrayOf(Altar.WATER, Altar.FIRE),
        Rune.WATER, Rune.FIRE
    ),

    /**
     * Lava
     *
     * @constructor Lava
     */
    LAVA(
        rune = Item(Items.LAVA_RUNE_4699),
        level = 23,
        experience = 10.0,
        altars = arrayOf(Altar.FIRE, Altar.EARTH),
        Rune.EARTH, Rune.FIRE
    );

    val runes: Array<Rune> = runes as Array<Rune>

    val highExperience: Double = if (experience % 1 == 0.0) experience + 5 else experience + 8

    companion object {

        @JvmStatic
        fun forAltar(altar: Altar, item: Item): CombinationRune? {
            for (rune in values()) {
                for (alt in rune.altars) {
                    if (alt === altar) {
                        val altarElement = alt.name
                        val talismanElement =
                            if (item.name.contains("talisman")) Talisman.forItem(item)!!.name else Rune.forItem(item)!!.name
                        if (altarElement == talismanElement) {
                            continue
                        }
                        for (r in rune.runes) {
                            if (r.name == talismanElement) {
                                return rune
                            }
                        }
                        continue
                    }
                }
            }
            return null
        }
    }
}