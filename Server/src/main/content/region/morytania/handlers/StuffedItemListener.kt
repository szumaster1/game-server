package content.region.morytania.handlers

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.item.Item

/**
 * Represents the Stuffed item listener.
 */
class StuffedItemListener : InteractionListener {

    private val headIds = StuffedItem.values().map { it.dropId }.toIntArray()

    override fun defineListeners() {

        /**
         * Exchange npc heads for stuffed items.
         */
        onUseWith(IntType.NPC, headIds, NPCs.TAXIDERMIST_4246) { player, used, _ ->
            val stuffed = StuffedItem.stuffedItemMap[used.id] ?: return@onUseWith true
            face(findNPC(NPCs.TAXIDERMIST_4246)!!, player, 3)
            if (amountInInventory(player, stuffed.dropId) == 1) {
                openDialogue(player, object : DialogueFile() {
                    override fun handle(componentID: Int, buttonID: Int) {
                        npc = NPC(NPCs.TAXIDERMIST_4246)
                        when (stage) {
                            0 -> npc(*splitLines(stuffed.message)).also { stage++ }
                            1 -> npc("I can preserve that for you for ${stuffed.price} coins.").also { stage++ }
                            2 -> options("Yes please.", "No thanks.").also { stage++ }
                            3 -> when (buttonID) {
                                1 -> {
                                    end()
                                    if (!removeItem(
                                            player,
                                            Item(Items.COINS_995, stuffed.price),
                                            Container.INVENTORY
                                        )
                                    ) {
                                        sendDialogue(player, "You don't have enough coins in order to do that.")
                                    } else {
                                        if (removeItem(player, Item(stuffed.dropId, 1), Container.INVENTORY)) {
                                            addItem(player, stuffed.stuffedId, 1, Container.INVENTORY)
                                            npc("There you go!")
                                        }
                                    }
                                }

                                2 -> {
                                    end()
                                    npc("All right, come back if you change your mind, eh?")
                                }
                            }
                        }
                    }
                })
            }
            return@onUseWith true
        }
    }

}