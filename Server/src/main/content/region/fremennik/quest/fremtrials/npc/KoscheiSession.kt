package content.region.fremennik.quest.fremtrials.npc

import core.api.consts.NPCs
import core.game.node.entity.player.Player

/**
 * Koschei session.
 *
 * @property player The player associated with this session.
 * @constructor Initializes a Koschei session with the given player.
 */
class KoscheiSession(val player: Player) {
    private val koschei: KoscheiNPC = KoscheiNPC(
        // Initializing KoscheiNPC with the specific NPC ID
        NPCs.KOSCHEI_THE_DEATHLESS_1290,
        // Transforming player's location
        player.location?.transform(1, 0, 0),
        this
    )

    init {
        // Checking if the player already has a KoscheiSession extension
        if (player.getExtension<Any?>(KoscheiSession::class.java) != null) {
            // Removing the existing extension if present
            player.removeExtension(KoscheiSession::class.java)
        }
        // Adding the KoscheiSession extension to the player
        player.addExtension(KoscheiSession::class.java, this)
    }

    /**
     * Start the Koschei session.
     *
     */
    fun start() {
        // Initializing KoscheiNPC
        koschei.init()
        // Unlocking the player
        player.unlock()
    }

    /**
     * Close the Koschei session.
     *
     */
    fun close() {
        // Clearing KoscheiNPC
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
