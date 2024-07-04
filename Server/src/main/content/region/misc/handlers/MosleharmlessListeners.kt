package content.region.misc.handlers

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location
import core.tools.START_DIALOGUE

class MosleharmlessListeners : InteractionListener {

    companion object {
        private val SHOP_OWNERS = intArrayOf(3166, 3161, 3164, 3163, 4362)
        private const val BERET_AND_MASK = Items.BERET_AND_MASK_11282
    }

    override fun defineListeners() {

        on(NPCs.HONEST_JIMMY_4362, IntType.NPC, "Join-Team") { player, _ ->
            sendMessage(player, "There must be at least 3 people on each team for the game to start.")
            return@on true
        }

        on(NPCs.PATCHY_4359, IntType.NPC, "sew") { player, _ ->
            openInterface(player, Components.SEW_INTERFACE_419)
            return@on true
        }

        onUseWith(IntType.ITEM, BERET_AND_MASK, NPCs.PATCHY_4359) { player, used, _ ->
            if (used.id != Items.BERET_AND_MASK_11282) {
                sendNPCDialogue(player, NPCs.PATCHY_4359, "Sorry, I can't do anythin' with that.")
            } else {
                openDialogue(player, SeparateItemsDialogue())
            }
            return@onUseWith true
        }

        on(SHOP_OWNERS, IntType.NPC, "trade") { player, node ->
            val pirateBook = hasAnItem(player, Items.BOOK_O_PIRACY_7144).container != null
            if (!pirateBook) {
                player.dialogueInterpreter.open(node.asNpc().id, node)
            } else {
                openNpcShop(player, node.asNpc().id)
            }
            return@on true
        }
    }

    override fun defineDestinationOverrides() {

        setDest(IntType.NPC, intArrayOf(NPCs.MIKE_3166), "talk-to", "trade") { _, _ ->
            return@setDest Location.create(3693, 2975, 0)
        }

        setDest(IntType.NPC, intArrayOf(NPCs.CHARLEY_3161), "talk-to", "trade") { _, _ ->
            return@setDest Location.create(3674, 2968, 0)
        }

        setDest(IntType.NPC, intArrayOf(NPCs.MAMA_3164), "talk-to", "trade") { player, _ ->
            if (player.location.y < 76) {
                return@setDest Location.create(3665, 2980, 0).transform(0, -2, 0)
            } else {
                return@setDest Location.create(3665, 2980, 0).transform(0, 2, 0)
            }
        }

        setDest(IntType.NPC, intArrayOf(NPCs.JOE_3163), "talk-to", "trade") { player, _ ->
            if (inBorders(player, 3666, 2990, 3670, 2997)) {
                return@setDest Location.create(3667, 2992, 0)
            } else {
                return@setDest Location.create(3665, 2992, 0)
            }
        }
    }


    class SeparateItemsDialogue : DialogueFile() {
        override fun handle(componentID: Int, buttonID: Int) {
            npc = NPC(NPCs.PATCHY_4359)
            when (stage) {
                START_DIALOGUE -> {
                    if (!removeItem(player!!, Items.BERET_AND_MASK_11282)) {
                        npc("Sorry, I can't do anythin' with that.")
                    } else if (freeSlots(player!!) < 2) {
                        npc("YYe don't seem te have enough free space few the two items.", "Ye might want te visit the bank.")
                    } else {
                        addItemOrDrop(player!!, Items.BLACK_BERET_10694)
                        addItemOrDrop(player!!, Items.MIME_MASK_3057)
                    }
                }
            }
        }
    }
}
