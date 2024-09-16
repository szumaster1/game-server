package content.global.skill.production.runecrafting.data

import core.api.hasRequirement
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery

/**
 * Represents an altar an's relative information(corresponding ruin, etc)
 * @author Vexia
 */
enum class Altar(
    /**
     * Represents the object of the altar.
     */
    val `object`: Int,
    /**
     * Represents the portal object.
     */
    val portal: Int,
    /**
     * The rift id.
     */
    val riftId: Int,
    /**
     * Represents the corresponding ruin.
     */
    val ruin: MysteriousRuin?,
    /**
     * Represents the rune constructed.
     */
    val rune: Rune?
) {
    AIR(2478, 2465, 7139, MysteriousRuin.AIR, Rune.AIR),
    MIND(2479, 2466, 7140, MysteriousRuin.MIND, Rune.MIND),
    WATER(2480, 2467, 7137, MysteriousRuin.WATER, Rune.WATER),
    EARTH(2481, 2468, 7130, MysteriousRuin.EARTH, Rune.EARTH),
    FIRE(2482, 2469, 7129, MysteriousRuin.FIRE, Rune.FIRE),
    BODY(2483, 2470, 7131, MysteriousRuin.BODY, Rune.BODY),
    COSMIC(2484, 2471, 7132, MysteriousRuin.COSMIC, Rune.COSMIC),
    CHAOS(2487, 2474, 7134, MysteriousRuin.CHAOS, Rune.CHAOS),
    ASTRAL(17010, 0, 0, null, Rune.ASTRAL),
    NATURE(2486, 2473, 7133, MysteriousRuin.NATURE, Rune.NATURE),
    LAW(2485, 2472, 7135, MysteriousRuin.LAW, Rune.LAW),
    DEATH(2488, 2475, 7136, MysteriousRuin.DEATH, Rune.DEATH),
    BLOOD(30624, 2477, 7141, MysteriousRuin.BLOOD, Rune.BLOOD),
    SOUL(2489, 0, 7138, null, Rune.SOUL),
    OURANIA(26847, 0, 0, null, null);
    /**
     * Gets the object.
     * @return The object.
     */
    /**
     * Gets the portal.
     * @return The portal.
     */
    /**
     * Gets the riftId.
     * @return The riftId.
     */
    /**
     * Gets the ruin.
     * @return The ruin.
     */
    /**
     * Gets the rune.
     * @return The rune.
     */

    /**
     * Enters a rift.
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
                player.sendMessage("You can't take weapons and armour into the law rift.")
                return
            }
        }
        if (this == COSMIC && !player.getQuestRepository().isComplete("Lost City")) {
            player.packetDispatch.sendMessage("You need to have completed the Lost City quest in order to do that.")
            return
        }
        if (ruin == null) {
            return
        }
        if (ruin.end == null) {
            return
        }
        player.properties.teleportLocation = ruin.end
    }

    val isOurania: Boolean
        /**
         * Checks if it's the ourania altar.
         * @return the ourania.
         */
        get() = rune == null
    val talisman: Talisman?
        /**
         * Gets the talisman.
         * @return the talisman.
         */
        get() {
            for (talisman in Talisman.values()) {
                if (talisman.name == name) {
                    return talisman
                }
            }
            return null
        }
    val tiara: Tiara?
        /**
         * Gets the tiara.
         * @return the tiara.
         */
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
         * Method used to get the [Altar] by the object.
         *
         * @param object the object.
         * @return the [Altar] or `Null`.
         */
        @JvmStatic
        fun forScenery(`object`: Scenery): Altar? {
            for (altar in values()) {
                if (altar.`object` == `object`.id || altar.portal == `object`.id || `object`.id == altar.riftId) {
                    return altar
                }
            }
            return null
        }
    }
}
