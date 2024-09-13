package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The config packet context.
 */
class ConfigContext(
    private var player: Player, // Declares a private variable to store the player associated with the context.
    @JvmField val id: Int, // Declares a constant field to store the ID of the context.
    @JvmField val value: Int, // Declares a constant field to store the value of the context.
) : Context {

    override fun getPlayer(): Player = player // Returns the player associated with the context.

    fun setPlayer(player: Player): Context { // Sets the player associated with the context.
        this.player = player
        return this
    }
}
