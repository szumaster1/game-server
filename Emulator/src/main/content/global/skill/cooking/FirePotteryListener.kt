package content.global.skill.cooking

import org.rs.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class FirePotteryListener : InteractionListener {

    override fun defineListeners() {
        on(sceneryIDs, IntType.SCENERY, "fire") { player, node ->
            player.faceLocation(node.location)
            player.dialogueInterpreter.open(99843, true, true)
            return@on true
        }
    }

    companion object {
        val sceneryIDs = intArrayOf(Scenery.POTTERY_OVEN_2643, Scenery.POTTERY_OVEN_4308, Scenery.POTTERY_OVEN_11601, Scenery.POTTERY_OVEN_34802)
    }
}
