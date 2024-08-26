package content.region.kandarin.handlers.miniquest.knightwave

import cfg.consts.NPCs
import core.game.node.entity.player.Player
import core.game.world.map.Location

/**
 * Represents the Training ground session.
 */
class TrainingGroundSession(val player: Player) {

    private val knight: KnightNPC = KnightNPC(NPCs.SIR_BEDIVERE_6177, Location.create(2758, 3508, 2), this)

    init {
        if (player.getExtension<Any?>(TrainingGroundSession::class.java) != null) {
            player.removeExtension(TrainingGroundSession::class.java)
        }
        player.addExtension(TrainingGroundSession::class.java, this)
    }

    fun start() {
        knight.init()
    }

    fun close() {
        knight.clear()
        player.removeExtension(TrainingGroundSession::class.java)
    }

    companion object {
        fun create(player: Player): TrainingGroundSession {
            return TrainingGroundSession(player)
        }

        fun getSession(player: Player): TrainingGroundSession {
            return player.getExtension(TrainingGroundSession::class.java)
        }
    }
}
