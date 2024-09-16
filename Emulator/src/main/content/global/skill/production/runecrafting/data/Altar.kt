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
    AIR(
        scenery = Object.AIR_ALTAR_2478,
        portal = Object.AIR_ALTAR_EXIT_2465,
        riftId = Object.AIR_RIFT_7139,
        ruin = MysteriousRuin.AIR,
        rune = Rune.AIR
    ),
    MIND(
        scenery = Object.MIND_ALTAR_2479,
        portal = Object.MIND_ALTAR_EXIT_2466,
        riftId = Object.MIND_RIFT_7140,
        ruin = MysteriousRuin.MIND,
        rune = Rune.MIND
    ),
    WATER(
        scenery = Object.WATER_ALTAR_2480,
        portal = Object.WATER_ALTAR_EXIT_2467,
        riftId = Object.WATER_RIFT_7137,
        ruin = MysteriousRuin.WATER,
        rune = Rune.WATER
    ),
    EARTH(
        scenery = Object.EARTH_ALTAR_2481,
        portal = Object.EARTH_ALTAR_EXIT_2468,
        riftId = Object.EARTH_RIFT_7130,
        ruin = MysteriousRuin.EARTH,
        rune = Rune.EARTH
    ),
    FIRE(
        scenery = Object.FIRE_ALTAR_2482,
        portal = Object.FIRE_ALTAR_EXIT_2469,
        riftId = Object.FIRE_RIFT_7129,
        ruin = MysteriousRuin.FIRE,
        rune = Rune.FIRE
    ),
    BODY(
        scenery = Object.BODY_ALTAR_2483,
        portal = Object.BODY_ALTAR_EXIT_2470,
        riftId = Object.BODY_RIFT_7131,
        ruin = MysteriousRuin.BODY,
        rune = Rune.BODY
    ),
    COSMIC(
        scenery = Object.COSMIC_ALTAR_2484,
        portal = Object.COSMIC_ALTAR_EXIT_2471,
        riftId = Object.COSMIC_RIFT_7132,
        ruin = MysteriousRuin.COSMIC,
        rune = Rune.COSMIC
    ),
    CHAOS(
        scenery = Object.CHAOS_ALTAR_2487,
        portal = Object.CHAOS_ALTAR_EXIT_2474,
        riftId = Object.CHAOS_RIFT_7134,
        ruin = MysteriousRuin.CHAOS,
        rune = Rune.CHAOS
    ),
    ASTRAL(
        scenery = Object.ALTAR_17010,
        portal = 0,
        riftId = 0,
        ruin = null,
        rune = Rune.ASTRAL
    ),
    NATURE(
        scenery = Object.NATURE_ALTAR_2486,
        portal = Object.NATURE_ALTAR_EXIT_2473,
        riftId = Object.NATURE_RIFT_7133,
        ruin = MysteriousRuin.NATURE,
        rune = Rune.NATURE
    ),
    LAW(
        scenery = Object.LAW_ALTAR_2485,
        portal = Object.LAW_PORTAL_EXIT_2472,
        riftId = Object.LAW_RIFT_7135,
        ruin = MysteriousRuin.LAW,
        rune = Rune.LAW
    ),
    DEATH(
        scenery = Object.DEATH_ALTAR_2488,
        portal = Object.DEATH_ALTAR_EXIT_2475,
        riftId = Object.DEATH_RIFT_7136,
        ruin = MysteriousRuin.DEATH,
        rune = Rune.DEATH
    ),
    BLOOD(
        scenery = Object.BLOOD_ALTAR_30624,
        portal = Object.BLOOD_ALTAR_EXIT_2477,
        riftId = Object.BLOOD_RIFT_7141,
        ruin = MysteriousRuin.BLOOD,
        rune = Rune.BLOOD
    ),
    OURANIA(
        scenery = Object.OURANIA_ALTAR_26847,
        portal = 0,
        riftId = 0,
        ruin = null,
        rune = null
    );

    companion object {
        private val altarByScenery: Map<Int, Altar> = values().associateBy { it.scenery }
        private val altarByPortal: Map<Int, Altar> = values().associateBy { it.portal }
        private val altarByRiftId: Map<Int, Altar> = values().associateBy { it.riftId }

        /**
         * Method used to get the `Altar` by the object.
         *
         * @param scenery the object.
         * @return the `Altar` or `Null`.
         */
        @JvmStatic
        fun forScenery(scenery: Scenery): Altar? {
            return altarByScenery[scenery.id] ?: altarByPortal[scenery.id] ?: altarByRiftId[scenery.id]
        }
    }

    /**
     * Enters a rift.
     *
     * @param player the player.
     */
    fun enterRift(player: Player) {
        when (this) {
            ASTRAL -> if (!hasRequirement(player, "Lunar Diplomacy")) return
            DEATH -> if (!hasRequirement(player, "Mourning's End Part II")) return
            BLOOD -> if (!hasRequirement(player, "Legacy of Seergaze")) return
            LAW -> if (!ItemDefinition.canEnterEntrana(player)) {
                sendMessage(player, "You can't take weapons and armour into the law rift.")
                return
            }

            COSMIC -> if (!isQuestComplete(player, "Lost City")) {
                sendMessage(player, "You need to have completed the Lost City quest in order to do that.")
                return
            }

            else -> Unit
        }
        player.properties.teleportLocation = ruin?.end
    }

    /**
     * Checks if it's the ourania altar.
     */
    val isOurania: Boolean = rune == null

    /**
     * Gets the talisman for the altar.
     */
    val talisman: Talisman? = Talisman.values().find { it.name == name }

    /**
     * Gets the tiara for the altar.
     */
    val tiara: Tiara? = Tiara.values().find { it.name == name }
}
