package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The context for ClientScript configurations.
 */
class CSConfigContext(
    private var player: Player,
    @JvmField val id: Int,
    @JvmField val value: Int,
    val types: String,
    val parameters: Array<Any>
) : Context {

    override fun getPlayer(): Player = player

    fun setPlayer(player: Player): Context {
        this.player = player
        return this
    }
}
