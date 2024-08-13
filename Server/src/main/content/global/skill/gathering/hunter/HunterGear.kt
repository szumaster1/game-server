package content.global.skill.gathering.hunter

import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Hunter gear
 *
 * @property chanceRate Represents the probability of a successful hunt with this gear.
 * @constructor Represents the HunterGear enum with a chance rate and associated equipment.
 *
 * @param equipment Vararg parameter that allows multiple items to be associated with the gear.
 */
enum class HunterGear(val chanceRate: Double, vararg equipment: Item) {
    /**
     * Glove Of Silence.
     */
    GLOVE_OF_SILENCE(3.0, Item(10075)),

    /**
     * Spotier Cape.
     */
    SPOTIER_CAPE(5.0, Item(10071)),

    /**
     * Spotted Cape.
     */
    SPOTTED_CAPE(5.0, Item(10069)),

    /**
     * Larupia.
     */
    LARUPIA(10.00, Item(10045), Item(10043), Item(10041)),

    /**
     * Desert Gear.
     */
    DESERT_GEAR(10.00, Item(12568), Item(10063), Item(10061)),

    /**
     * Graahk Gear.
     */
    GRAAHK_GEAR(10.00, Item(10051), Item(10047), Item(10049)),

    /**
     * Kyatt Gear.
     */
    KYATT_GEAR(10.00, Item(10039), Item(10035), Item(10037)),

    /**
     * Jungle Gear.
     */
    JUNGLE_GEAR(8.00, Item(10059), Item(10057)),

    /**
     * Polar Gear.
     */
    POLAR_GEAR(8.00, Item(10065), Item(10067));

    val equipment: Array<Item> = equipment as Array<Item>

    companion object {

        @JvmStatic
        fun inGear(player: Player): HunterGear? {
            var contained = 0
            for (type in values()) {
                for (i in type.equipment) {
                    if (player.equipment.containsItem(i)) {
                        contained += 1
                    }
                    if (contained == type.equipment.size) {
                        return type
                    }
                }
            }
            return null
        }

        @JvmStatic
        fun getChanceRate(player: Player): Double {
            val gear = inGear(player) ?: return 0.0
            return gear.chanceRate
        }
    }
}