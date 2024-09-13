package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The packet context of the grand exchange update
 * packet.
 */
class GrandExchangeContext(
    private val player: Player,
    val idx: Byte,
    val state: Byte,
    val itemID: Short,
    val isSell: Boolean,
    val value: Int,
    val amt: Int,
    val completedAmt: Int,
    val totalCoinsExchanged: Int
) : Context {

    override fun getPlayer(): Player = player
}
