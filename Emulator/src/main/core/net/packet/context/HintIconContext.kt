package core.net.packet.context

import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.net.packet.Context

/**
 * Represents the hint icon packet context.
 */
class HintIconContext @JvmOverloads constructor(
    private val player: Player,
    @JvmField val slot: Int, arrowId: Int,
    @JvmField var targetType: Int,
    target: Node,
    modelId: Int,
    height: Int = 0
) : Context {

    @JvmField
    var arrowId: Int = 0
    @JvmField
    var index: Int = 0
    @JvmField
    val modelId: Int
    @JvmField
    var location: Location? = null
    @JvmField
    var height: Int = 0

    constructor(player: Player, slot: Int, arrowId: Int, target: Node, modelId: Int) : this(
        player,
        slot,
        arrowId,
        -1,
        target,
        modelId
    ) {
        targetType = 2
        if (target is Entity) {
            targetType = if (target is Player) 10 else 1
        }
    }

    init {
        this.arrowId = arrowId
        this.modelId = modelId
        this.height = height
        if (target is Entity) {
            this.index = target.index
            this.location = null
        } else {
            this.location = target.location
            this.index = -1
        }
    }

    override fun getPlayer(): Player = player
}
