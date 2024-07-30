package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.network.packet.Context

class BuildSceneryContext(
    private val player: Player,
    val scenery: Scenery
) : Context {

    override fun getPlayer(): Player = player
}
