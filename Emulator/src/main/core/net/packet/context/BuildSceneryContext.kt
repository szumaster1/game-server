package core.net.packet.context

import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.net.packet.Context

/**
 * Build scenery context.
 * @author Emperor
 *
 * @param [player]  the player associated with the context
 * @param [scenery] the scenery object to be built
 * @constructor Represents the [BuildSceneryContext] with a player and scenery.
 */
class BuildSceneryContext(
    private val player: Player,
    val scenery: Scenery
) : Context {

    override fun getPlayer(): Player = player
}
