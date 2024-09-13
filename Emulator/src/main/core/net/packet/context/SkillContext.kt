package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The skill context.
 */
class SkillContext(
    private val player: Player,
    @JvmField val skillId: Int
) : Context {

    override fun getPlayer(): Player = player
}
