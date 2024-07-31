package content.global.skill.combat.magic.modern

import core.api.consts.Graphics
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.node.item.Item
import core.game.world.update.flag.context.Graphic

enum class ChargeOrbData(
    val obelisk: Int,
    val requiredRunes: Array<Item>,
    val level: Int,
    val experience: Double,
    val graphics: Graphic,
    val sound: Int,
    val chargedOrb: Int
) {
    CHARGE_WATER_ORB(
        obelisk = Scenery.OBELISK_OF_WATER_2151,
        requiredRunes = arrayOf(Item(Items.COSMIC_RUNE_564, 3), Item(Items.WATER_RUNE_555, 30), Item(Items.UNPOWERED_ORB_567)),
        level = 56,
        experience = 66.0,
        graphics = Graphic(Graphics.POWERING_WATER_ORB_149, 90),
        sound = Sounds.CHARGE_WATER_ORB_118,
        chargedOrb = Items.WATER_ORB_571
    ),
    CHARGE_EARTH_ORB(
        obelisk = Scenery.OBELISK_OF_EARTH_29415,
        requiredRunes = arrayOf(Item(Items.COSMIC_RUNE_564, 3), Item(Items.EARTH_RUNE_557, 30), Item(Items.UNPOWERED_ORB_567)),
        level = 60,
        experience = 70.0,
        graphics = Graphic(Graphics.POWERING_EARTH_ORB_151, 90),
        sound = Sounds.CHARGE_EARTH_ORB_115,
        chargedOrb = Items.EARTH_ORB_575
    ),
    CHARGE_FIRE_ORB(
        obelisk = Scenery.OBELISK_OF_FIRE_2153,
        requiredRunes = arrayOf(Item(Items.COSMIC_RUNE_564, 3), Item(Items.FIRE_RUNE_554, 30), Item(Items.UNPOWERED_ORB_567)),
        level = 63,
        experience = 73.0,
        graphics = Graphic(Graphics.POWERING_FIRE_ORB_152, 90),
        sound = Sounds.CHARGE_FIRE_ORB_117,
        chargedOrb = Items.FIRE_ORB_569
    ),
    CHARGE_AIR_ORB(
        obelisk = Scenery.OBELISK_OF_AIR_2152,
        requiredRunes = arrayOf(Item(Items.COSMIC_RUNE_564, 3), Item(Items.AIR_RUNE_556, 30), Item(Items.UNPOWERED_ORB_567)),
        level = 66,
        experience = 76.0,
        graphics = Graphic(Graphics.POWERING_AIR_ORB_150, 90),
        sound = Sounds.CHARGE_AIR_ORB_116,
        chargedOrb = Items.AIR_ORB_573
    );

    companion object {
        val spellMap = HashMap<Int, ChargeOrbData>()

        init {
            for (spell in ChargeOrbData.values()) {
                spellMap[spell.obelisk] = spell
            }
        }
    }
}