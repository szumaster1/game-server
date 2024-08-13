package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.network.packet.Context

/**
 * Build scenery context.
 * @author Emperor
 *
 * @property player The player associated with the context
 * @property scenery The scenery object to be built
 * @constructor Represents the BuildSceneryContext with a player and scenery
 */
class BuildSceneryContext(
    private val player: Player,
    val scenery: Scenery
) : Context {

    override fun getPlayer(): Player = player
}
