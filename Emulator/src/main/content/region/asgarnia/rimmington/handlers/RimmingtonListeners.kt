package content.region.asgarnia.rimmington.handlers

import cfg.consts.Scenery
import content.region.asgarnia.rimmington.dialogue.CustomsSergeantDialogue
import core.api.openDialogue
import core.api.sendMessage
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class RimmingtonListeners : InteractionListener {

    override fun defineListeners() {
        /*
         * Handling the Scenery NPC (Rocking Out)
         * The Rimmington customs officer should have a yellow dot on the minimap. After (18 August 2009)
         */

        on(Scenery.CUSTOMS_SERGEANT_31459, IntType.SCENERY, "talk-to") { player, _ ->
            if (player.location.x >= 2963) {
                openDialogue(player, CustomsSergeantDialogue())
            }
            return@on true
        }

        on(Scenery.DOOR_1534, IntType.SCENERY, "close", "open") { player, node ->
            if(node.location.x == 2950 && node.location.y == 3207) {
                sendMessage(player, "The doors appear to be stuck.")
                return@on false
            }
            DoorActionHandler.handleDoor(player, node.asScenery().wrapper)
            return@on true
        }
    }
}