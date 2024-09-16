package content.global.skill.production.runecrafting.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the combination runes.
 *
 * @param rune          the item id.
 * @param level         the required level.
 * @param experience    the experience.
 * @param altars        the related altar.
 * @param runes         the related runes.
 */
enum class CombinationRune(
    val rune: Item,
    val level: Int,
    val experience: Double,
    val altars: Array<Altar>,
    vararg runes: Rune,
) {
    /**
     * The mist rune.
     */
    MIST(
        rune = Item(Items.MIST_RUNE_4695),
        level = 6,
        experience = 8.0,
        altars = arrayOf(Altar.WATER, Altar.AIR),
        runes = arrayOf(Rune.AIR, Rune.WATER)
    ),

    /**
     * The dust rune.
     */
    DUST(
        rune = Item(Items.DUST_RUNE_4696),
        level = 10,
        experience = 8.3,
        altars = arrayOf(Altar.EARTH, Altar.AIR),
        runes = arrayOf(Rune.AIR, Rune.EARTH)
    ),

    /**
     * The mud rune.
     */
    MUD(
        rune = Item(Items.MUD_RUNE_4698),
        level = 13,
        experience = 9.3,
        altars = arrayOf(Altar.EARTH, Altar.WATER),
        runes = arrayOf(Rune.WATER, Rune.EARTH)
    ),

    /**
     * The smoke rune.
     */
    SMOKE(
        rune = Item(Items.SMOKE_RUNE_4697),
        level = 15,
        experience = 8.5,
        altars = arrayOf(Altar.FIRE, Altar.AIR),
        runes = arrayOf(Rune.AIR, Rune.FIRE)
    ),

    /**
     * The steam rune.
     */
    STEAM(
        rune = Item(Items.STEAM_RUNE_4694),
        level = 19,
        experience = 9.3,
        altars = arrayOf(Altar.WATER, Altar.FIRE),
        runes = arrayOf(Rune.WATER, Rune.FIRE)
    ),

    /**
     * The lava rune.
     */
    LAVA(
        rune = Item(Items.LAVA_RUNE_4699),
        level = 23,
        experience = 10.0,
        altars = arrayOf(Altar.FIRE, Altar.EARTH),
        runes = arrayOf(Rune.EARTH, Rune.FIRE)
    );

    val runes: Array<Rune> = runes as Array<Rune>

    val highExperience: Double = if (experience % 1 == 0.0) experience + 5 else experience + 8

    companion object {
        /**
         * Method used to get the [Altar] by the scenery.
         *
         * @param altar the scenery.
         * @return the [Altar] or `null`.
         */
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