package content.global.skill.runecrafting.`object`

import content.global.skill.runecrafting.items.Talisman
import content.global.skill.runecrafting.items.Tiara
import content.global.skill.runecrafting.runes.Rune
import core.api.hasRequirement
import core.api.isQuestComplete
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import org.rs.consts.Scenery as Object

/**
 * Represents an altar relative information.
 *
 * @property objs       The object id representing the altar.
 * @property exit       The exit id for the altar.
 * @property rift       The rift id associated with the altar.
 * @property ruin       The mysterious ruins associated with the altar, if any.
 * @property rune       The rune associated with the altar, if any.
 */
enum class Altar(val objs: Int, val exit: Int, val rift: Int, val ruin: MysteriousRuins?, val rune: Rune?) {
    AIR(
        objs = Object.AIR_ALTAR_2478,
        exit = Object.AIR_ALTAR_EXIT_2465,
        rift = Object.AIR_RIFT_7139,
        ruin = MysteriousRuins.AIR,
        rune = Rune.AIR
    ),
    MIND(
        objs = Object.MIND_ALTAR_2479,
        exit = Object.MIND_ALTAR_EXIT_2466,
        rift = Object.MIND_RIFT_7140,
        ruin = MysteriousRuins.MIND,
        rune = Rune.MIND
    ),
    WATER(
        objs = Object.WATER_ALTAR_2480,
        exit = Object.WATER_ALTAR_EXIT_2467,
        rift = Object.WATER_RIFT_7137,
        ruin = MysteriousRuins.WATER,
        rune = Rune.WATER
    ),
    EARTH(
        objs = Object.EARTH_ALTAR_2481,
        exit = Object.EARTH_ALTAR_EXIT_2468,
        rift = Object.EARTH_RIFT_7130,
        ruin = MysteriousRuins.EARTH,
        rune = Rune.EARTH
    ),
    FIRE(
        objs = Object.FIRE_ALTAR_2482,
        exit = Object.FIRE_ALTAR_EXIT_2469,
        rift = Object.FIRE_RIFT_7129,
        ruin = MysteriousRuins.FIRE,
        rune = Rune.FIRE
    ),
    BODY(
        objs = Object.BODY_ALTAR_2483,
        exit = Object.BODY_ALTAR_EXIT_2470,
        rift = Object.BODY_RIFT_7131,
        ruin = MysteriousRuins.BODY,
        rune = Rune.BODY
    ),
    COSMIC(
        objs = Object.COSMIC_ALTAR_2484,
        exit = Object.COSMIC_ALTAR_EXIT_2471,
        rift = Object.COSMIC_RIFT_7132,
        ruin = MysteriousRuins.COSMIC,
        rune = Rune.COSMIC
    ),
    CHAOS(
        objs = Object.CHAOS_ALTAR_2487,
        exit = Object.CHAOS_ALTAR_EXIT_2474,
        rift = Object.CHAOS_RIFT_7134,
        ruin = MysteriousRuins.CHAOS,
        rune = Rune.CHAOS
    ),
    ASTRAL(
        objs = Object.ALTAR_17010,
        exit = 0,
        rift = 0,
        ruin = null,
        rune = Rune.ASTRAL
    ),
    NATURE(
        objs = Object.NATURE_ALTAR_2486,
        exit = Object.NATURE_ALTAR_EXIT_2473,
        rift = Object.NATURE_RIFT_7133,
        ruin = MysteriousRuins.NATURE,
        rune = Rune.NATURE
    ),
    LAW(
        objs = Object.LAW_ALTAR_2485,
        exit = Object.LAW_PORTAL_EXIT_2472,
        rift = Object.LAW_RIFT_7135,
        ruin = MysteriousRuins.LAW,
        rune = Rune.LAW
    ),
    DEATH(
        objs = Object.DEATH_ALTAR_2488,
        exit = Object.DEATH_ALTAR_EXIT_2475,
        rift = Object.DEATH_RIFT_7136,
        ruin = MysteriousRuins.DEATH,
        rune = Rune.DEATH
    ),
    BLOOD(
        objs = Object.BLOOD_ALTAR_30624,
        exit = Object.BLOOD_ALTAR_EXIT_2477,
        rift = Object.BLOOD_RIFT_7141,
        ruin = MysteriousRuins.BLOOD,
        rune = Rune.BLOOD
    ),
    OURANIA(
        objs = Object.OURANIA_ALTAR_26847,
        exit = 0,
        rift = 0,
        ruin = null,
        rune = null
    );

    companion object {
        private val altarByScenery: Map<Int, Altar> = values().associateBy { it.objs }
        private val altarByPortal: Map<Int, Altar> = values().associateBy { it.exit }
        private val altarByRiftId: Map<Int, Altar> = values().associateBy { it.rift }

        /**
         * Method used to get the [Altar] by the object.
         *
         * @param [scenery] the object.
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
     * @param [player] the player.
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
