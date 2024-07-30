package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * Represents the player on interface context.
 * @author Emperor
 */
class DisplayModelContext : Context {

    enum class ModelType {
        PLAYER, NPC, ITEM, MODEL
    }

    private val player: Player
    @JvmField
    val type: ModelType
    @JvmField
    val nodeId: Int
    var amount: Int = 0
    @JvmField
    var zoom: Int = 0
    @JvmField
    val interfaceId: Int
    @JvmField
    val childId: Int

    constructor(player: Player, interfaceId: Int, childId: Int) : this(
        player,
        ModelType.PLAYER,
        -1,
        0,
        interfaceId,
        childId
    )

    constructor(player: Player, nodeId: Int, interfaceId: Int, childId: Int) : this(
        player,
        ModelType.NPC,
        nodeId,
        0,
        interfaceId,
        childId
    )

    constructor(player: Player, type: ModelType, nodeId: Int, amount: Int, interfaceId: Int, childId: Int) {
        this.player = player
        this.type = type
        this.nodeId = nodeId
        this.amount = amount
        this.interfaceId = interfaceId
        this.childId = childId
    }

    constructor(
        player: Player,
        type: ModelType,
        nodeId: Int,
        zoom: Int,
        interfaceId: Int,
        childId: Int,
        vararg `object`: Any?
    ) {
        this.player = player
        this.type = type
        this.nodeId = nodeId
        this.zoom = zoom
        this.interfaceId = interfaceId
        this.childId = childId
    }

    override fun getPlayer(): Player = player
}
