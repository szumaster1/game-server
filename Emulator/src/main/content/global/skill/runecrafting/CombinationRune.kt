package content.global.skill.runecrafting

import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents the combination runes.
 */
enum class CombinationRune(
    val rune: Item,
    val level: Int,
    val experience: Double,
    val altars: Array<Altar>,
    vararg runes: Rune
) {
    MIST(
        Item(Items.MIST_RUNE_4695),
        6,
        8.0,
        arrayOf(Altar.WATER, Altar.AIR),
        Rune.AIR,
        Rune.WATER
    ),
    DUST(
        Item(Items.DUST_RUNE_4696),
        10,
        8.3,
        arrayOf(Altar.EARTH, Altar.AIR),
        Rune.AIR,
        Rune.EARTH
    ),
    MUD(
        Item(Items.MUD_RUNE_4698),
        13,
        9.3,
        arrayOf(Altar.EARTH, Altar.WATER),
        Rune.WATER,
        Rune.EARTH
    ),
    SMOKE(
        Item(Items.SMOKE_RUNE_4697),
        15,
        8.5,
        arrayOf(Altar.FIRE, Altar.AIR),
        Rune.AIR,
        Rune.FIRE
    ),
    STEAM(
        Item(Items.STEAM_RUNE_4694),
        19,
        9.3,
        arrayOf(Altar.WATER, Altar.FIRE),
        Rune.WATER,
        Rune.FIRE
    ),
    LAVA(
        Item(Items.LAVA_RUNE_4699),
        23,
        10.0,
        arrayOf(Altar.FIRE, Altar.EARTH),
        Rune.EARTH,
        Rune.FIRE
    );

    val runes: Array<Rune> = runes as Array<Rune>
    val highExperience: Double = experience + if (experience % 1 == 0.0) 5 else 8

    companion object {
        /**
         * Method used to get the [Altar] by the scenery id.
         *
         * @param [altar] the scenery id.
         * @param [item]  the item to check.
         * @return the [CombinationRune] or `null`.
         */
        @JvmStatic
        fun forAltar(altar: Altar, item: Item): CombinationRune? {
            return values().firstOrNull { rune ->
                rune.altars.contains(altar) &&
                        rune.runes.any { r ->
                            r.name == (if (item.name.contains("talisman")) Talisman.forItem(item)!!.name else Rune.forItem(
                                item
                            )!!.name)
                        }
            }
        }
    }
}
