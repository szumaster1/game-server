package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.network.packet.Context


class BuildItemContext@JvmOverloads constructor(
    private val player: Player,
    val item: Item,
    val oldAmount: Int = 0
) : Context {

    override fun getPlayer(): Player = player
}
