package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The config packet context.
 */
class ConfigContext(
    private var player: Player,
    @JvmField val id: Int,
    @JvmField val value: Int,
) : Context {

    override fun getPlayer(): Player = player

    fun setPlayer(player: Player): Context {
        this.player = player
        return this
    }
}
