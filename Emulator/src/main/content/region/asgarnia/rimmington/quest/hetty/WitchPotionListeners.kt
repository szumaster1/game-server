package content.region.asgarnia.rimmington.quest.hetty

import org.rs.consts.Scenery
import core.api.getQuestStage
import core.api.sendMessage
import core.api.sendPlayerDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Witch potion listeners.
 */
class WitchPotionListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Interaction with cauldron at Hetty house.
         */

        on(Scenery.CAULDRON_2024, IntType.SCENERY, "Drink From") { player, _ ->
            when(getQuestStage(player, "Witch's Potion")){
                20, 100 -> sendPlayerDialogue(player, "As nice as that looks I think I'll give it a miss for now.")
                40 -> player.dialogueInterpreter.open(307, true, 1)
                else -> sendMessage(player, "Nothing interesting happens.")
            }
            return@on true
        }
    }

}