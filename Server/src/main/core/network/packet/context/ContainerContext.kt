package core.network.packet.context

import core.game.container.Container
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.network.packet.Context

/**
 * Represents the context for the container packet.
 * @author Emperor
 */
class ContainerContext : Context {
    private var player: Player
    val interfaceId: Int
    val childId: Int
    val containerId: Int
    val items: Array<Item?>?
    val length: Int
    val isSplit: Boolean
    val slots: IntArray?
    var isClear: Boolean = false

    constructor(player: Player, interfaceId: Int, childId: Int, clear: Boolean) : this(player, interfaceId, childId, 0, null, 1, false) {
        this.isClear = clear
    }

    constructor(player: Player, interfaceId: Int, childId: Int, containerId: Int, container: Container, split: Boolean) : this(player, interfaceId, childId, containerId, container.toArray(), container.toArray().size, split)

    constructor(player: Player, interfaceId: Int, childId: Int, containerId: Int, items: Array<Item?>, split: Boolean) : this(player, interfaceId, childId, containerId, items, items.size, split)

    constructor(player: Player, interfaceId: Int, childId: Int, containerId: Int, items: Array<Item?>?, length: Int, split: Boolean) {
        this.player = player
        this.interfaceId = interfaceId
        this.childId = childId
        this.containerId = containerId
        this.items = items
        this.length = length
        this.isSplit = split
        this.slots = null
    }

    constructor(player: Player, interfaceId: Int, childId: Int, containerId: Int, items: Array<Item?>, split: Boolean, vararg slots: Int) {
        this.player = player
        this.interfaceId = interfaceId
        this.childId = childId
        this.containerId = containerId
        this.items = items
        this.length = items.size
        this.isSplit = split
        this.slots = slots
    }

    override fun getPlayer(): Player = player
}
