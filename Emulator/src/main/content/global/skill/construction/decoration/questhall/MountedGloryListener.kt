package content.global.skill.construction.decoration.questhall

import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class MountedGloryListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles remove the Mounted glory from wall.
         */

        on(13523, IntType.SCENERY, "Remove") { player, `object` ->
            if (player.houseManager.isBuildingMode) {
                player.dialogueInterpreter.open("con:removedec", `object`.asScenery())
            }
            return@on true
        }
    }
}
