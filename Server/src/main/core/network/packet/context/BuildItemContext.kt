package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.network.packet.Context

/**
 * Context for building an item.
 * @author Emperor
 *
 * @property player The player who is building the item
 * @property item The item being built
 * @property oldAmount The previous amount of the item
 * @constructor Creates a context for building an item
 */
class BuildItemContext @JvmOverloads constructor(
    private val player: Player,
    val item: Item,
    val oldAmount: Int = 0
) : Context {

    override fun getPlayer(): Player = player
}
