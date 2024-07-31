package content.global.skill.support.agility.shortcuts

import core.api.consts.Scenery
import core.api.forceMove
import core.api.sendMessageWithDelay
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Direction

class TreeGnomeVillageFenceShortcut : InteractionListener {

    override fun defineListeners() {

        /*
         * Squeeze through fence between labirynth and Tree gnome village.
         */

        on(Scenery.LOOSE_RAILING_2186, IntType.SCENERY, "squeeze-through") { player, _ ->
            var dest = if (player.location.y >= 3161) player.location.transform(
                Direction.SOUTH,
                1
            ) else player.location.transform(Direction.NORTH, 1)
            forceMove(player, player.location, dest, 0, 80, anim = 3844)
            sendMessageWithDelay(player, "You squeeze through the loose railing.", 1)
            return@on true
        }
    }
}