package content.global.handlers.item.withnpc

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Rope on lady keli listener
 *
 * @constructor Rope on lady keli listener
 */
class RopeOnLadyKeliListener : InteractionListener {

    private val rope = Items.ROPE_954
    private val ladyKeli = NPCs.LADY_KELI_919

    override fun defineListeners() {
        onUseWith(IntType.NPC, rope, ladyKeli) { player, used, _ ->
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
