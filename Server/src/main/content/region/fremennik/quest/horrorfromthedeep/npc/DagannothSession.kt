package content.region.fremennik.quest.horrorfromthedeep.npc

import core.api.consts.NPCs
import core.api.location
import core.game.node.entity.player.Player

/**
 * Dagannoth session.
 */
class DagannothSession(val player: Player) {
    private val dagannoth: DagannothMotherNPC = DagannothMotherNPC(NPCs.DAGANNOTH_MOTHER_1351, location(2520, 4645, 0), this)

    init {
        if (player.getExtension<Any?>(DagannothSession::class.java) != null) {
            player.removeExtension(DagannothSession::class.java)
        }
        player.addExtension(DagannothSession::class.java, this)
    }

    /**
     * Start
     *
     */
    fun start() {
        dagannoth.init()
        player.unlock()
    }

    /**
     * Close
     *
     */
    fun close() {
        dagannoth.clear()
        player.removeExtension(DagannothSession::class.java)
    }

    companion object {
        fun create(player: Player): DagannothSession {
            return DagannothSession(player)
        }

        fun getSession(player: Player): DagannothSession {
            return player.getExtension(DagannothSession::class.java)
        }
    }
}