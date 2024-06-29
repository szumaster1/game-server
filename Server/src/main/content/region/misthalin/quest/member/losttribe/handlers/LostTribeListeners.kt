package content.region.misthalin.quest.member.losttribe.handlers

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class LostTribeListeners : InteractionListener {

    override fun defineListeners() {

        on(Items.BROOCH_5008, IntType.ITEM, "look-at") { player, _ ->
            openInterface(player, Components.BROOCH_VIEW_50)
            return@on true
        }

        on(Items.GOBLIN_SYMBOL_BOOK_5009, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.GOBLIN_SYMBOL_BOOK_183)
            return@on true
        }

        on(Scenery.BOOKCASE_6916, IntType.SCENERY, "search") { player, _ ->
            val hasAnBook = hasAnItem(player, Items.GOBLIN_SYMBOL_BOOK_5009).container != null
            if (!hasAnBook && getQuestStage(player, "Lost Tribe") >= 41) {
                sendDialogue(player, "'A History of the Goblin Race.' This must be it.")
                addItemOrDrop(player, Items.GOBLIN_SYMBOL_BOOK_5009)
            }
            return@on true
        }

        on(Scenery.CRATE_6911, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, Items.SILVERWARE_5011) && getQuestStage(player, "Lost Tribe") == 48) {
                sendItemDialogue(player, Items.SILVERWARE_5011, "You find the missing silverware!")
                addItemOrDrop(player, Items.SILVERWARE_5011)
                setQuestStage(player, "Lost Tribe", 49)
            } else {
                sendMessage(player, "You find nothing.")
            }
            return@on true
        }

        on(intArrayOf(NPCs.MISTAG_2084, NPCs.KAZGAR_2086), IntType.NPC, "follow") { player, node ->
            if(getUsedOption(player) == "follow") {
                when (node.id) {
                    NPCs.MISTAG_2084 -> GoblinFollower.sendToLumbridge(player)
                    NPCs.KAZGAR_2086 -> GoblinFollower.sendToMines(player)
                }
            }
            return@on true
        }
    }
}
