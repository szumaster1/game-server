package content.region.asgarnia.entrana.quest.zep

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.QuestName

class EnlightenedJourneyListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling using branches on a basket.
         */

        onUseWith(IntType.SCENERY, Items.WILLOW_BRANCH_5933, Scenery.BASKET_19132) { player, _, _ ->
            if (getQuestStage(player, QuestName.ENLIGHTENED_JOURNEY) >= 7) return@onUseWith true
            if (!removeItem(player, Item(Items.WILLOW_BRANCH_5933, 12))) {
                sendMessage(player, "You do not have enough willow branches.")
            } else {
                sendNPCDialogue(player, NPCs.AUGUSTE_5049, "Great! Let me just put it together and we'll be ready to lift off! Speak to me again in a moment.")
                setQuestStage(player, QuestName.ENLIGHTENED_JOURNEY, 8)
            }
            return@onUseWith true
        }
    }

}