package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityShortcut
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.api.sendMessage
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.skill.Skills

/**
 * The Zanaris squeeze shortcut.
 */
@Initializable
class ZanarisSqueezeShortcut : AgilityShortcut(intArrayOf(12127), 46, 0.0, "squeeze-past") {

    private companion object {
        val ANIMATION = Animation(2240)
    }

    override fun run(player: Player, scenery: Scenery, option: String, failed: Boolean) {
        if (player.skills.getLevel(Skills.AGILITY) < 66 && (scenery.location == Location(2408, 4395) || scenery.location == Location(2415, 4402))) {
            sendMessage(player, "You need an agility level of at least 66 to negotiate this obstacle.")
            return
        }
        val to = if (player.location.y < scenery.location.y) scenery.location.transform(0, 1, 0) else scenery.location.transform(0, -1, 0)
        val dir = if (player.location.y < scenery.location.y) Direction.NORTH else Direction.SOUTH
        ForceMovement.run(player, player.location, to, ANIMATION, ANIMATION, dir, 13).endAnimation = Animation.RESET
    }
}
