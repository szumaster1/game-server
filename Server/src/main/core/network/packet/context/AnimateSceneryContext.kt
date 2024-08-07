package core.network.packet.context

import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.network.packet.Context

/**
 * Represents the context of animating a scenery.
 * @author Emperor, Vexia
 */
class AnimateSceneryContext(private val player: Player, val animation: Animation) : Context {

    override fun getPlayer(): Player = player
}
