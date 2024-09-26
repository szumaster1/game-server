package content.global.handlers.item.withnpc

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Handles tie up a Lady Keli.
 */
class RopeOnLadyKeliListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.NPC, Items.ROPE_954, NPCs.LADY_KELI_919) { player, used, _ ->
            if (getQuestStage(player, "Prince Ali Rescue") in 40..50 && getAttribute(player, "guard-drunk", false)) {
                if (removeItem(player, used.asItem())) {
                    sendDialogue(player, "You overpower Keli, tie her up, and put her in a cupboard.")
                    setQuestStage(player, "Prince Ali Rescue", 50)
                    setAttribute(player, "keli-gone", getWorldTicks() + 350)
                }
            } else {
                if (getQuestStage(player, "Prince Ali Rescue") == 40) {
                    sendMessage(player, "You need to do something about the guard first.")
                }
            }
            return@onUseWith true
        }
    }
}
