package content.region.misthalin.handlers.draynorvillage

import content.global.handlers.iface.plugin.DiangoReclaimInterfacePlugin
import content.region.misthalin.dialogue.draynorvillage.TreeGuardDialogue
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.activity.ActivityManager
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Draynor village listeners.
 */
class DraynorVillageListeners : InteractionListener {

    companion object {
        private val BOOKSHELVES = intArrayOf(7065, 7066, 7068)
        private val randomDialogue = arrayOf(
            "Hey - gerroff me!",
            "You'll blow my cover! I'm meant to be hidden!",
            "Don't draw attention to me!",
            "Will you stop that?",
            "Watch what you're doing with that hatchet, you nit!",
            "Ooooch!",
            "Ow! That really hurt!",
            "Oi!"
        )
    }


    override fun defineListeners() {

        /**
         * Make dyes interaction.
         */
        on(NPCs.AGGIE_922, IntType.NPC, "make-dyes") { player, node ->
            openDialogue(player, node.asNpc().id, node, true)
            return@on true
        }

        /**
         * Interaction that allows you to see through the
         * telescope in the wise old man's house.
         */
        on(Scenery.TELESCOPE_7092, IntType.SCENERY, "observe") { player, _ ->
            ActivityManager.start(player, "draynor telescope", false)
            return@on true
        }

        /**
         * Handling trapdoor interaction.
         */
        on(Scenery.TRAPDOOR_6434, IntType.SCENERY, "open") { _, node ->
            replaceScenery(node.asScenery(), 6435, 500)
            return@on true
        }

        /**
         * Diango holiday items reclaimable interaction.
         */
        on(NPCs.DIANGO_970, IntType.NPC, "holiday-items") { player, _ ->
            DiangoReclaimInterfacePlugin.open(player)
            return@on true
        }

        /**
         * Searching the bookshelves in the Wise Old Man's house.
         */
        on(BOOKSHELVES, IntType.SCENERY, "search") { player, node ->
            if (freeSlots(player) == 0) {
                sendDialogue(player, "You need at least one free inventory space to take from the shelves.")
                return@on true
            }
            when (node.id) {
                7065 -> if (!inInventory(player, Items.STRANGE_BOOK_5507)) {
                    sendMessage(player, "You search the bookcase and find a book named 'Strange Book'.")
                    addItem(player, Items.STRANGE_BOOK_5507)
                }

                7066 -> if (!inInventory(player, Items.BOOK_OF_FOLKLORE_5508)) {
                    sendMessage(player, "You search the bookcase and find a book named 'Book of folklore'.")
                    addItem(player, Items.BOOK_OF_FOLKLORE_5508)
                }

                7068 -> if (!inInventory(player, Items.BOOK_ON_CHICKENS_7464)) {
                    sendMessage(player, "You search the bookcase and find a book named 'Book on chickens'.")
                    addItem(player, Items.BOOK_ON_CHICKENS_7464)
                }

                else -> sendMessage(player, "You search the bookcase and find nothing of interest.")
            }
            return@on true
        }

        /**
         * Darius 'Suave' Aniseed interaction.
         */
        on(Scenery.TREE_10041, IntType.SCENERY, "chop down", "talk to") { player, _ ->
            when (getUsedOption(player)) {
                "chop down" -> sendNPCDialogue(
                    player,
                    NPCs.GUARD_345,
                    randomDialogue.random(),
                    FacialExpression.ANNOYED
                )

                "talk to" -> openDialogue(player, TreeGuardDialogue())
            }
            return@on true
        }
    }
}
