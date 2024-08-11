package content.region.misc.quest.enlightenedjourney

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

/**
 * Enlightened journey listeners.
 */
class EnlightenedJourneyListeners : InteractionListener {

    companion object {
        const val BASKET = Scenery.BASKET_19132
    }

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.WILLOW_BRANCH_5933, BASKET) { player, _, _ ->
            if (getQuestStage(player, "Enlightened Journey") >= 7) return@onUseWith true
            if (!removeItem(player, Item(Items.WILLOW_BRANCH_5933, 12))) {
                sendMessage(player, "You do not have enough willow branches.")
            } else {
                sendNPCDialogue(player, NPCs.AUGUSTE_5049, "Great! Let me just put it together and we'll be ready to lift off! Speak to me again in a moment.")
                setQuestStage(player, "Enlightened Journey", 8)
            }
            return@onUseWith true
        }
    }
}