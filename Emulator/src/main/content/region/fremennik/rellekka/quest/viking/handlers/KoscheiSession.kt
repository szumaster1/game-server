package content.region.fremennik.rellekka.quest.viking.handlers

import org.rs.consts.NPCs
import core.game.node.entity.player.Player

/**
 * Koschei session.
 *
 * @param player The player associated with this session.
 * @constructor Represents a Koschei session with the given player.
 */
class KoscheiSession(val player: Player) {

    private val koschei: KoscheiNPC = KoscheiNPC(
        NPCs.KOSCHEI_THE_DEATHLESS_1290,
        player.location?.transform(1, 0, 0),
        this
    )

    init {
        if (player.getExtension<Any?>(KoscheiSession::class.java) != null) {
            player.removeExtension(KoscheiSession::class.java)
        }
        player.addExtension(KoscheiSession::class.java, this)
    }

    /**
     * Start the Koschei session.
     *
     */
    fun start() {
        // Initializing KoscheiNPC
        koschei.init()
        player.unlock()
    }

    /**
     * Close the Koschei session.
     *
     */
    fun close() {
        koschei.clear()
        // Removing the KoscheiSession extension from the player
        player.removeExtension(KoscheiSession::class.java)
    }

    companion object {
        /**
         * Create a new Koschei session for the player.
         *
         * @param player The player for whom the session is created.
         * @return A new instance of KoscheiSession.
         */
        fun create(player: Player): KoscheiSession {
            return KoscheiSession(player)
        }

        /**
         * Get the existing Koschei session for the player.
         *
         * @param player The player for whom the session is retrieved.
         * @return The existing KoscheiSession associated with the player.
         */
        fun getSession(player: Player): KoscheiSession {
            return player.getExtension(KoscheiSession::class.java)
        }
    }
}
