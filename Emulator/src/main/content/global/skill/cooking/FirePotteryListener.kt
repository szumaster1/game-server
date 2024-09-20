package content.global.skill.cooking

import org.rs.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Fire pottery listener.
 */
class FirePotteryListener : InteractionListener {

    override fun defineListeners() {
        on(FIRE_POTTERY, IntType.SCENERY, "fire") { player, node ->
            player.faceLocation(node.location)
            player.dialogueInterpreter.open(99843, true, true)
            return@on true
        }
    }

    companion object {
        val FIRE_POTTERY = intArrayOf(
            Scenery.POTTERY_OVEN_2643,
            Scenery.POTTERY_OVEN_4308,
            Scenery.POTTERY_OVEN_11601,
            Scenery.POTTERY_OVEN_34802
        )
    }
}
