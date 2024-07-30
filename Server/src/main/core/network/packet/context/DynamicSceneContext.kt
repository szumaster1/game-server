package core.network.packet.context

import core.game.node.entity.player.Player

/**
 * Represents the packet context for the build dynamic
 * scene graph packet.
 * @author Emperor
 */
class DynamicSceneContext
    (player: Player, login: Boolean) : SceneGraphContext(player, login)
