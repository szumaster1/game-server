package content.global.skill.support.agility.shortcuts

import core.api.getStatLevel
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.skill.Skills
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

class ZanarisSqueezeShortcut : InteractionListener {

    override fun defineListeners() {
        on(12127, IntType.SCENERY, "squeeze-past") { player, node ->
            if (getStatLevel(player, Skills.AGILITY) < 66 && node.location == Location(2408, 4395) || node.location == Location(2415, 4402)) {
                sendMessage(player, "You need an agility level of at least 66 to negotiate this obstacle.")
                return@on true
            }

            val to = if (player.location.y < node.location.y) node.location.transform(0, 1, 0) else node.location.transform(0, -1, 0)
            val dir = if (player.location.y < node.location.y) Direction.NORTH else Direction.SOUTH
            ForceMovement.run(player, player.location, to, Animation(2240), Animation(2240), dir, 13).endAnimation =
                Animation.RESET
            return@on true
        }
    }
}