package core.net.packet.context

import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.net.packet.Context

/**
 * Context for building an item.
 * @author Emperor
 *
 * @param player The player who is building the item
 * @param item The item being built
 * @param oldAmount The previous amount of the item
 * @constructor Creates a context for building an item
 */
class BuildItemContext @JvmOverloads constructor(
    private val player: Player,
    val item: Item,
    val oldAmount: Int = 0
) : Context {

    override fun getPlayer(): Player = player
}
