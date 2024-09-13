package core.net.packet.context

import core.game.node.entity.player.Player
import core.net.packet.Context

/**
 * The access mask context.
 * @author Emperor
 */
class AccessMaskContext(
    private var player: Player,
    val id: Int,
    val childId: Int,
    val interfaceId: Int,
    val offset: Int,
    val length: Int
) : Context {

    /**
     * Transforms this access mask context into a new instance with the player
     * instance and id value changed.
     *
     * @param player The player to set.
     * @param id The id to set.
     * @return The access mask context.
     */
    fun transform(player: Player, id: Int): AccessMaskContext {
        return AccessMaskContext(player, id, childId, interfaceId, offset, length)
    }

    /**
     * Sets the player for the context.
     *
     * @param player The player to set.
     * @return The updated context with the new player.
     */
    fun setPlayer(player: Player): Context {
        this.player = player
        return this
    }

    override fun getPlayer(): Player = player

}
