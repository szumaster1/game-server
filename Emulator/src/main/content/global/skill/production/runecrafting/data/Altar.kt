package content.global.skill.production.runecrafting.data

import core.api.hasRequirement
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery

/**
 * Represents an altar relative information.
 */
enum class Altar
/**
 * Constructs a new `Altar` `Object`.
 * @param object the object.
 * @param ruin the ruin.
 * @param rune the rune.
 */(
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
    /**
     * The air altar.
     */
    AIR(2478, 2465, 7139, MysteriousRuin.AIR, Rune.AIR),

    /**
     * The mind altar.
     */
    MIND(2479, 2466, 7140, MysteriousRuin.MIND, Rune.MIND),

    /**
     * The water altar.
     */
    WATER(2480, 2467, 7137, MysteriousRuin.WATER, Rune.WATER),

    /**
     * The earth altar.
     */
    EARTH(2481, 2468, 7130, MysteriousRuin.EARTH, Rune.EARTH),

    /**
     * The fire altar.
     */
    FIRE(2482, 2469, 7129, MysteriousRuin.FIRE, Rune.FIRE),

    /**
     * The body altar.
     */
    BODY(2483, 2470, 7131, MysteriousRuin.BODY, Rune.BODY),

    /**
     * The cosmic altar.
     */
    COSMIC(2484, 2471, 7132, MysteriousRuin.COSMIC, Rune.COSMIC),

    /**
     * The chaos altar.
     */
    CHAOS(2487, 2474, 7134, MysteriousRuin.CHAOS, Rune.CHAOS),

    /**
     * The astral altar.
     */
    ASTRAL(17010, 0, 0, null, Rune.ASTRAL),

    /**
     * The nature altar.
     */
    NATURE(2486, 2473, 7133, MysteriousRuin.NATURE, Rune.NATURE),

    /**
     * The law altar.
     */
    LAW(2485, 2472, 7135, MysteriousRuin.LAW, Rune.LAW),

    /**
     * The death altar.
     */
    DEATH(2488, 2475, 7136, MysteriousRuin.DEATH, Rune.DEATH),

    /**
     * The blood altar.
     */
    BLOOD(30624, 2477, 7141, MysteriousRuin.BLOOD, Rune.BLOOD),

    /**
     * The soul altar.
     */
    SOUL(2489, 0, 7138, null, Rune.SOUL),

    /**
     * The ourania altar.
     */
    OURANIA(26847, 0, 0, null, null);


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
                player.sendMessage("You can't take weapons and armour into the law rift.")
                return
            }
        }
        if (this == COSMIC && !player.getQuestRepository().isComplete("Lost City")) {
            player.packetDispatch.sendMessage("You need to have completed the Lost City quest in order to do that.")
            return
        }
        if (getRuin() == null) {
            return
        }
        if (getRuin()!!.end == null) {
            return
        }
        player.properties.teleportLocation = getRuin()!!.end
    }

    /**
     * Gets the ruin.
     *
     * @return The ruin.
     */
    fun getRuin(): MysteriousRuin? {
        return ruin
    }

    /**
     * Gets the rune.
     *
     * @return The rune.
     */
    fun getRune(): Rune? {
        return rune
    }

    val isOurania: Boolean
        /**
         * Checks if its the ourania altar.
         *
         * @return the ourania.
         */
        get() = getRune() == null

    val talisman: Talisman?
        /**
         * Gets the talisman.
         *
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
         *
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
         * Method used to get the `Altar` by the object.
         *
         * @param object the object.
         * @return the `Altar` or `Null`.
         */
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