package content.global.skill.production.runecrafting.data

import core.api.hasRequirement
import core.api.isQuestComplete
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import cfg.consts.Scenery as Object

/**
 * Represents an altar relative information.
 *
 * @param scenery   the altar.
 * @param portal    the portal associated with the altar.
 * @param riftId    the rift linked to the altar.
 * @param ruin      reference to a [MysteriousRuin].
 * @param rune      reference to a [Rune].
 * @return the [Altar].
 */
enum class Altar(val scenery: Int, val portal: Int, val riftId: Int, val ruin: MysteriousRuin?, val rune: Rune?) {
    /**
     * The air altar.
     */
    AIR(
        scenery = Object.AIR_ALTAR_2478,
        portal = Object.AIR_ALTAR_EXIT_2465,
        riftId = Object.AIR_RIFT_7139,
        ruin = MysteriousRuin.AIR,
        rune = Rune.AIR
    ),

    /**
     * The mind altar.
     */
    MIND(
        scenery = Object.MIND_ALTAR_2479,
        portal = Object.MIND_ALTAR_EXIT_2466,
        riftId = Object.MIND_RIFT_7140,
        ruin = MysteriousRuin.MIND,
        rune = Rune.MIND
    ),

    /**
     * The water altar.
     */
    WATER(
        scenery = Object.WATER_ALTAR_2480,
        portal = Object.WATER_ALTAR_EXIT_2467,
        riftId = Object.WATER_RIFT_7137,
        ruin = MysteriousRuin.WATER,
        rune = Rune.WATER
    ),

    /**
     * The earth altar.
     */
    EARTH(
        scenery = Object.EARTH_ALTAR_2481,
        portal = Object.EARTH_ALTAR_EXIT_2468,
        riftId = Object.EARTH_RIFT_7130,
        ruin = MysteriousRuin.EARTH,
        rune = Rune.EARTH
    ),

    /**
     * The fire altar.
     */
    FIRE(
        scenery = Object.FIRE_ALTAR_2482,
        portal = Object.FIRE_ALTAR_EXIT_2469,
        riftId = Object.FIRE_RIFT_7129,
        ruin = MysteriousRuin.FIRE,
        rune = Rune.FIRE
    ),

    /**
     * The body altar.
     */
    BODY(
        scenery = Object.BODY_ALTAR_2483,
        portal = Object.BODY_ALTAR_EXIT_2470,
        riftId = Object.BODY_RIFT_7131,
        ruin = MysteriousRuin.BODY,
        rune = Rune.BODY
    ),

    /**
     * The cosmic altar.
     */
    COSMIC(
        scenery = Object.COSMIC_ALTAR_2484,
        portal = Object.COSMIC_ALTAR_EXIT_2471,
        riftId = Object.COSMIC_RIFT_7132,
        ruin = MysteriousRuin.COSMIC,
        rune = Rune.COSMIC
    ),

    /**
     * The chaos altar.
     */
    CHAOS(
        scenery = Object.CHAOS_ALTAR_2487,
        portal = Object.CHAOS_ALTAR_EXIT_2474,
        riftId = Object.CHAOS_RIFT_7134,
        ruin = MysteriousRuin.CHAOS,
        rune = Rune.CHAOS
    ),

    /**
     * The astral altar.
     */
    ASTRAL(
        scenery = Object.ALTAR_17010,
        portal = 0,
        riftId = 0,
        ruin = null,
        rune = Rune.ASTRAL
    ),

    /**
     * The nature altar.
     */
    NATURE(
        scenery = Object.NATURE_ALTAR_2486,
        portal = Object.NATURE_ALTAR_EXIT_2473,
        riftId = Object.NATURE_RIFT_7133,
        ruin = MysteriousRuin.NATURE,
        rune = Rune.NATURE
    ),

    /**
     * The law altar.
     */
    LAW(
        scenery = Object.LAW_ALTAR_2485,
        portal = Object.LAW_PORTAL_EXIT_2472,
        riftId = Object.LAW_RIFT_7135,
        ruin = MysteriousRuin.LAW,
        rune = Rune.LAW
    ),

    /**
     * The death altar.
     */
    DEATH(
        scenery = Object.DEATH_ALTAR_2488,
        portal = Object.DEATH_ALTAR_EXIT_2475,
        riftId = Object.DEATH_RIFT_7136,
        ruin = MysteriousRuin.DEATH,
        rune = Rune.DEATH
    ),

    /**
     * The blood altar.
     */
    BLOOD(
        scenery = Object.BLOOD_ALTAR_30624,
        portal = Object.BLOOD_ALTAR_EXIT_2477,
        riftId = Object.BLOOD_RIFT_7141,
        ruin = MysteriousRuin.BLOOD,
        rune = Rune.BLOOD
    ),

    /**
     * The ourania altar.
     */
    OURANIA(
        scenery = Object.OURANIA_ALTAR_26847,
        portal = 0,
        riftId = 0,
        ruin = null,
        rune = null
    );

    /**
     * Enters a rift.
     *
     * @param player the player.
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

    /**
     * Checks if it's the ourania altar.
     *
     * @return the ourania.
     */
    val isOurania: Boolean = rune == null

    /**
     * Gets the talisman.
     *
     * @return the talisman.
     */
    val talisman: Talisman?
        get() {
            for (talisman in Talisman.values()) {
                if (talisman.name == name) {
                    return talisman
                }
            }
            return null
        }

    /**
     * Gets the tiara.
     *
     * @return the tiara.
     */
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
        /**
         * Method used to get the `Altar` by the object.
         *
         * @param object the object.
         * @return the `Altar` or `Null`.
         */
        @JvmStatic
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
