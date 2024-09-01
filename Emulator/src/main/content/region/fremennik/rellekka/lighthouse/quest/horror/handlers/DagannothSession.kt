package content.region.fremennik.rellekka.lighthouse.quest.horror.handlers

import cfg.consts.NPCs
import core.api.location
import core.game.node.entity.player.Player

/**
 * Represents the session with Dagannoth.
 */
class DagannothSession(
    /**
     * The player.
     */
    val player: Player) {
    /**
     * The Dagannoth NPC.
     */
    private val dagannoth: DagannothMotherNPC = DagannothMotherNPC(NPCs.DAGANNOTH_MOTHER_1351, location(2520, 4645, 0), this)

    /**
     * Constructs a new `DagannothSession` `Object`.
     * @param player the player.
     */
    init {
        if (player.getExtension<Any?>(DagannothSession::class.java) != null) {
            player.removeExtension(DagannothSession::class.java)
        }
        player.addExtension(DagannothSession::class.java, this)
    }

    /**
     * Starts the session.
     */
    fun start() {
        dagannoth.init()
        player.unlock()
    }

    /**
     * Closes the session.
     */
    fun close() {
        dagannoth.clear()
        player.removeExtension(DagannothSession::class.java)
    }

    companion object {
        /**
         * Creates the Dagannoth session.
         * @param player the player.
         * @return the session.
         */
        fun create(player: Player): DagannothSession {
            return DagannothSession(player)
        }

        /**
         * Gets the Dagannoth session.
         * @param player the player.
         * @return the session.
         */
        fun getSession(player: Player): DagannothSession {
            return player.getExtension(DagannothSession::class.java)
        }
    }
}