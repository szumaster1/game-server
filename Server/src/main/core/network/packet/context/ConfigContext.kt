package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The config packet context.
 * @author Emperor
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
