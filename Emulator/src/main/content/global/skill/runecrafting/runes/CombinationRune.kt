package content.global.skill.runecrafting.runes

import content.global.skill.runecrafting.altars.Altar
import content.global.skill.runecrafting.items.Talisman
import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents the combination runes.
 */
enum class CombinationRune(val rune: Item, val level: Int, val experience: Double, val altars: Array<Altar>, vararg runes: Rune) {
    MIST(
        rune = Item(Items.MIST_RUNE_4695),
        level = 6,
        experience = 8.0,
        altars = arrayOf(Altar.WATER, Altar.AIR),
        runes = arrayOf(Rune.AIR, Rune.WATER)
    ),
    DUST(
        rune = Item(Items.DUST_RUNE_4696),
        level = 10,
        experience = 8.3,
        altars = arrayOf(Altar.EARTH, Altar.AIR),
        runes = arrayOf(Rune.AIR, Rune.EARTH)
    ),
    MUD(
        rune = Item(Items.MUD_RUNE_4698),
        level = 13,
        experience = 9.3,
        altars = arrayOf(Altar.EARTH, Altar.WATER),
        runes = arrayOf(Rune.WATER, Rune.EARTH)
    ),
    SMOKE(
        rune = Item(Items.SMOKE_RUNE_4697),
        level = 15,
        experience = 8.5,
        altars = arrayOf(Altar.FIRE, Altar.AIR),
        runes = arrayOf(Rune.AIR, Rune.FIRE)
    ),
    STEAM(
        rune = Item(Items.STEAM_RUNE_4694),
        level = 19,
        experience = 9.3,
        altars = arrayOf(Altar.WATER, Altar.FIRE),
        runes = arrayOf(Rune.WATER, Rune.FIRE)
    ),
    LAVA(
        rune = Item(Items.LAVA_RUNE_4699),
        level = 23,
        experience = 10.0,
        altars = arrayOf(Altar.FIRE, Altar.EARTH),
        runes = arrayOf(Rune.EARTH, Rune.FIRE)
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
