package content.global.skill.gathering.farming

import content.global.skill.gathering.farming.timers.Compost
import core.api.getOrStartTimer
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery

/**
 * Compost bins.
 */
enum class CompostBins(val varbit: Int) {
    FALADOR_COMPOST(740),
    CATHERBY_COMPOST(741),
    PORT_PHAS_COMPOST(742),
    ARDOUGNE_COMPOST(743);

    companion object {
        @JvmField
        val bins = values().map { it.varbit to it }.toMap()

        /**
         * Returns the CompostBins enum for a given Scenery object.
         *
         * @param obj The Scenery object to find the CompostBins for.
         * @return The corresponding CompostBins enum.
         */
        @JvmStatic
        fun forObject(obj: Scenery): CompostBins?{
            return forObjectID(obj.id)
        }

        /**
         * Returns the CompostBins enum for a given object ID.
         *
         * @param id The ID of the object to find the CompostBins for.
         * @return The corresponding CompostBins enum.
         */
        @JvmStatic
        fun forObjectID(id: Int): CompostBins?{
            val objDef = SceneryDefinition.forId(id)
            return bins[objDef.varbitID]
        }
    }

    /**
     * Get the CompostBin for a specific player.
     *
     * @param player The player for whom to get the CompostBin.
     * @return The CompostBin associated with the player.
     */
    fun getBinForPlayer(player: Player) : CompostBin {
        val bins = getOrStartTimer <Compost> (player)
        return bins.getBin (this)
    }
}
