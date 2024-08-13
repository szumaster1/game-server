package content.global.skill.combat.magic.modern

import core.api.consts.Graphics
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.node.item.Item
import core.game.world.update.flag.context.Graphic


/**
 * Charge orb data.
 *
 * @param obelisk       The ID of the obelisk used to charge the orb.
 * @param requiredRunes The array of required runes to charge the orb.
 * @param level         The required Magic level to charge the orb.
 * @param experience    The amount of Magic experience gained from charging the orb.
 * @param graphics      The graphic displayed when charging the orb.
 * @param sound         The sound played when charging the orb.
 * @param chargedOrb    The ID of the charged orb.
 * @constructor Creates a new ChargeOrbData instance.
 */
enum class ChargeOrbData(
    val obelisk: Int, // The ID of the obelisk used for charging
    val requiredRunes: Array<Item>, // Array of required runes for charging
    val level: Int, // Required Magic level for charging
    val experience: Double, // Experience gained from charging
    val graphics: Graphic, // Graphic displayed during charging
    val sound: Int, // Sound played during charging
    val chargedOrb: Int // ID of the resulting charged orb
) {
    /**
     * Charge Water Orb.
     */
    CHARGE_WATER_ORB(
        obelisk = Scenery.OBELISK_OF_WATER_2151,
        requiredRunes = arrayOf(
            Item(Items.COSMIC_RUNE_564, 3),
            Item(Items.WATER_RUNE_555, 30),
            Item(Items.UNPOWERED_ORB_567)
        ),
        level = 56,
        experience = 66.0,
        graphics = Graphic(Graphics.POWERING_WATER_ORB_149, 90),
        sound = Sounds.CHARGE_WATER_ORB_118,
        chargedOrb = Items.WATER_ORB_571
    ),

    /**
     * Charge Earth Orb.
     */
    CHARGE_EARTH_ORB(
        obelisk = Scenery.OBELISK_OF_EARTH_29415,
        requiredRunes = arrayOf(
            Item(Items.COSMIC_RUNE_564, 3),
            Item(Items.EARTH_RUNE_557, 30),
            Item(Items.UNPOWERED_ORB_567)
        ),
        level = 60,
        experience = 70.0,
        graphics = Graphic(Graphics.POWERING_EARTH_ORB_151, 90),
        sound = Sounds.CHARGE_EARTH_ORB_115,
        chargedOrb = Items.EARTH_ORB_575
    ),

    /**
     * Charge Fire Orb.
     */
    CHARGE_FIRE_ORB(
        obelisk = Scenery.OBELISK_OF_FIRE_2153,
        requiredRunes = arrayOf(
            Item(Items.COSMIC_RUNE_564, 3),
            Item(Items.FIRE_RUNE_554, 30),
            Item(Items.UNPOWERED_ORB_567)
        ),
        level = 63,
        experience = 73.0,
        graphics = Graphic(Graphics.POWERING_FIRE_ORB_152, 90),
        sound = Sounds.CHARGE_FIRE_ORB_117,
        chargedOrb = Items.FIRE_ORB_569
    ),

    /**
     * Charge Air Orb.
     */
    CHARGE_AIR_ORB(
        obelisk = Scenery.OBELISK_OF_AIR_2152,
        requiredRunes = arrayOf(
            Item(Items.COSMIC_RUNE_564, 3),
            Item(Items.AIR_RUNE_556, 30),
            Item(Items.UNPOWERED_ORB_567)
        ),
        level = 66,
        experience = 76.0,
        graphics = Graphic(Graphics.POWERING_AIR_ORB_150, 90),
        sound = Sounds.CHARGE_AIR_ORB_116,
        chargedOrb = Items.AIR_ORB_573
    );

    companion object {
        // Map to associate obelisk IDs with ChargeOrbData
        val spellMap = HashMap<Int, ChargeOrbData>()

        init {
            // Iterate through all ChargeOrbData values
            for (spell in ChargeOrbData.values()) {
                // Map obelisk ID to corresponding ChargeOrbData
                spellMap[spell.obelisk] = spell
            }
        }
    }
}
