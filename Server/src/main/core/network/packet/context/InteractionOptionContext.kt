package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The context implementation used for the InteractionOption
 * outgoing packet.
 * @author Emperor
 */
class InteractionOptionContext : Context {
    private val player: Player
    @JvmField
    val index: Int
    var isRemove: Boolean = false
    @JvmField val name: String

    constructor(player: Player, index: Int, name: String) {
        this.player = player
        this.index = index
        this.name = name
        this.isRemove = false
    }

    constructor(player: Player, index: Int, name: String, remove: Boolean) {
        this.player = player
        this.index = index
        this.name = name
        this.isRemove = remove
    }

    override fun getPlayer(): Player = player
}
