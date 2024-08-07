package content.global.skill.gathering.farming

import content.global.skill.gathering.farming.timers.Compost
import core.api.getOrStartTimer
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery

/**
 * Compost bins
 *
 * @property varbit
 * @constructor Compost bins
 */
enum class CompostBins(val varbit: Int) {
    /**
     * Falador Compost.
     */
    FALADOR_COMPOST(740),

    /**
     * Catherby Compost.
     */
    CATHERBY_COMPOST(741),

    /**
     * Port Phas Compost.
     */
    PORT_PHAS_COMPOST(742),

    /**
     * Ardougne Compost.
     */
    ARDOUGNE_COMPOST(743);

    companion object {
        @JvmField
        val bins = values().map { it.varbit to it }.toMap()

        @JvmStatic
        fun forObject(obj: Scenery): CompostBins?{
            return forObjectID(obj.id)
        }

        @JvmStatic
        fun forObjectID(id: Int): CompostBins?{
            val objDef = SceneryDefinition.forId(id)
            return bins[objDef.varbitID]
        }
    }

    /**
     * Get bin for player
     *
     * @param player
     * @return
     */
    fun getBinForPlayer(player: Player) : CompostBin {
        val bins = getOrStartTimer <Compost> (player)
        return bins.getBin (this)
    }
}
