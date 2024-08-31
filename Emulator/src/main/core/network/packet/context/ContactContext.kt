package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The packet context for the contact packets.
 */
class ContactContext : Context {
    private val player: Player
    @JvmField
    var type: Int
    @JvmField
    var name: String? = null
    @JvmField
    var worldId: Int = 0

    constructor(player: Player, type: Int) {
        this.player = player
        this.type = type
    }

    constructor(player: Player, name: String?, worldId: Int) {
        this.player = player
        this.name = name
        this.worldId = worldId
        this.type = UPDATE_FRIEND_TYPE
    }

    override fun getPlayer(): Player = player

    val isOnline: Boolean = worldId > 0

    companion object {
        const val UPDATE_STATE_TYPE: Int = 0
        const val UPDATE_FRIEND_TYPE: Int = 1
        const val IGNORE_LIST_TYPE: Int = 2
    }
}
