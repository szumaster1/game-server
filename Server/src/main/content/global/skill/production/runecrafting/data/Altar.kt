package content.global.skill.production.runecrafting.data

import core.api.hasRequirement
import core.api.isQuestComplete
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.api.consts.Scenery as Object

/**
 * Altar
 *
 * @param scenery Represents the visual aspect of the altar.
 * @param portal Indicates the portal associated with the altar.
 * @param riftId Unique identifier for the rift linked to the altar.
 * @param ruin Optional property that may hold a reference to a MysteriousRuin.
 * @param rune Optional property that may hold a reference to a Rune.
 * @constructor Altar Represents an instance of the Altar enum with specified properties.
 */
enum class Altar(val scenery: Int, val portal: Int, val riftId: Int, val ruin: MysteriousRuin?, val rune: Rune?) {
    /**
     * Air
     *
     * @constructor Air
     */
    AIR(
        scenery = Object.AIR_ALTAR_2478,
        portal = Object.AIR_ALTAR_EXIT_2465,
        riftId = Object.AIR_RIFT_7139,
        ruin = MysteriousRuin.AIR,
        rune = Rune.AIR
    ),

    /**
     * Mind
     *
     * @constructor Mind
     */
    MIND(
        scenery = Object.MIND_ALTAR_2479,
        portal = Object.MIND_ALTAR_EXIT_2466,
        riftId = Object.MIND_RIFT_7140,
        ruin = MysteriousRuin.MIND,
        rune = Rune.MIND
    ),

    /**
     * Water
     *
     * @constructor Water
     */
    WATER(
        scenery = Object.WATER_ALTAR_2480,
        portal = Object.WATER_ALTAR_EXIT_2467,
        riftId = Object.WATER_RIFT_7137,
        ruin = MysteriousRuin.WATER,
        rune = Rune.WATER
    ),

    /**
     * Earth
     *
     * @constructor Earth
     */
    EARTH(
        scenery = Object.EARTH_ALTAR_2481,
        portal = Object.EARTH_ALTAR_EXIT_2468,
        riftId = Object.EARTH_RIFT_7130,
        ruin = MysteriousRuin.EARTH,
        rune = Rune.EARTH
    ),

    /**
     * Fire
     *
     * @constructor Fire
     */
    FIRE(
        scenery = Object.FIRE_ALTAR_2482,
        portal = Object.FIRE_ALTAR_EXIT_2469,
        riftId = Object.FIRE_RIFT_7129,
        ruin = MysteriousRuin.FIRE,
        rune = Rune.FIRE
    ),

    /**
     * Body
     *
     * @constructor Body
     */
    BODY(
        scenery = Object.BODY_ALTAR_2483,
        portal = Object.BODY_ALTAR_EXIT_2470,
        riftId = Object.BODY_RIFT_7131,
        ruin = MysteriousRuin.BODY,
        rune = Rune.BODY
    ),

    /**
     * Cosmic
     *
     * @constructor Cosmic
     */
    COSMIC(
        scenery = Object.COSMIC_ALTAR_2484,
        portal = Object.COSMIC_ALTAR_EXIT_2471,
        riftId = Object.COSMIC_RIFT_7132,
        ruin = MysteriousRuin.COSMIC,
        rune = Rune.COSMIC
    ),

    /**
     * Chaos
     *
     * @constructor Chaos
     */
    CHAOS(
        scenery = Object.CHAOS_ALTAR_2487,
        portal = Object.CHAOS_ALTAR_EXIT_2474,
        riftId = Object.CHAOS_RIFT_7134,
        ruin = MysteriousRuin.CHAOS,
        rune = Rune.CHAOS
    ),

    /**
     * Astral
     *
     * @constructor Astral
     */
    ASTRAL(
        scenery = Object.ALTAR_17010,
        portal = 0,
        riftId = 0,
        ruin = null,
        rune = Rune.ASTRAL
    ),

    /**
     * Nature
     *
     * @constructor Nature
     */
    NATURE(
        scenery = Object.NATURE_ALTAR_2486,
        portal = Object.NATURE_ALTAR_EXIT_2473,
        riftId = Object.NATURE_RIFT_7133,
        ruin = MysteriousRuin.NATURE,
        rune = Rune.NATURE
    ),

    /**
     * Law
     *
     * @constructor Law
     */
    LAW(
        scenery = Object.LAW_ALTAR_2485,
        portal = Object.LAW_PORTAL_EXIT_2472,
        riftId = Object.LAW_RIFT_7135,
        ruin = MysteriousRuin.LAW,
        rune = Rune.LAW
    ),

    /**
     * Death
     *
     * @constructor Death
     */
    DEATH(
        scenery = Object.DEATH_ALTAR_2488,
        portal = Object.DEATH_ALTAR_EXIT_2475,
        riftId = Object.DEATH_RIFT_7136,
        ruin = MysteriousRuin.DEATH,
        rune = Rune.DEATH
    ),

    /**
     * Blood
     *
     * @constructor Blood
     */
    BLOOD(
        scenery = Object.BLOOD_ALTAR_30624,
        portal = Object.BLOOD_ALTAR_EXIT_2477,
        riftId = Object.BLOOD_RIFT_7141,
        ruin = MysteriousRuin.BLOOD,
        rune = Rune.BLOOD
    ),

    /**
     * Ourania
     *
     * @constructor Ourania
     */
    OURANIA(
        scenery = Object.OURANIA_ALTAR_26847,
        portal = 0,
        riftId = 0,
        ruin = null,
        rune = null
    );

    /**
     * Enter rift
     *
     * @param player
     */
    fun enterRift(player: Player) {
        if (this == ASTRAL) {
            if (!hasRequirement(player, "Lunar Diplomacy")) return
        }
        if (this == DEATH) {
            if (!hasRequirement(player, "Mourning's End Part II")) return
        }
        if (this == BLOOD) {
            if (!hasRequirement(player, "Legacy of Seergaze")) return
        }
        if (this == LAW) {
            if (!ItemDefinition.canEnterEntrana(player)) {
                sendMessage(player, "You can't take weapons and armour into the law rift.")
                return
            }
        }
        if (this == COSMIC && !isQuestComplete(player, "Lost City")) {
            sendMessage(player, "You need to have completed the Lost City quest in order to do that.")
            return
        }
        if (ruin == null) {
            return
        }
        player.properties.teleportLocation = ruin.end
    }

    val isOurania: Boolean = rune == null
    val talisman: Talisman?
        get() {
            for (talisman in Talisman.values()) {
                if (talisman.name == name) {
                    return talisman
                }
            }
            return null
        }
    val tiara: Tiara?
        get() {
            for (tiara in Tiara.values()) {
                if (tiara.name == name) {
                    return tiara
                }
            }
            return null
        }

    companion object {
        fun forScenery(scenery: Scenery): Altar? {
            for (altar in values()) {
                if (altar.scenery == scenery.id || altar.portal == scenery.id || scenery.id == altar.riftId) {
                    return altar
                }
            }
            return null
        }
    }
}
