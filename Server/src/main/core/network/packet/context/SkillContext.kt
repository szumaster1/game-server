package core.network.packet.context

import core.game.node.entity.player.Player
import core.network.packet.Context

/**
 * The skill context.
 * @author Emperor
 */
class SkillContext(
    private val player: Player,
    @JvmField val skillId: Int
) : Context {

    override fun getPlayer(): Player = player
}
