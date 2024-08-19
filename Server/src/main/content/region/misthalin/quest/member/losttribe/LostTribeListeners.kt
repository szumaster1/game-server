package content.region.misthalin.quest.member.losttribe

import content.region.misthalin.quest.member.losttribe.handlers.GoblinFollower
import core.api.*
import core.api.consts.*
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation

/**
 * Represents the Lost tribe listeners.
 */
class LostTribeListeners : InteractionListener {

    override fun defineListeners() {

        // Listener for opening the chest
        on(CHEST, IntType.SCENERY, "open") { player, _ ->
            // Check if the player is at the correct quest stage and has the required key
            if (getQuestStage(player, "Lost Tribe") == 47 && inInventory(player, Items.KEY_423, 1)) {
                // Remove the key from the player's inventory
                removeItem(player, Item(Items.KEY_423))
                // Add the items to the player's inventory or drop them on the ground if inventory is full
                for (item in arrayOf(Items.HAM_ROBE_4300, Items.HAM_SHIRT_4298, Items.HAM_HOOD_4302).map { Item(it) }) {
                    if (!player.inventory.add(item)) {
                        GroundItemManager.create(item, player)
                    }
                }
                // Update the quest stage
                setQuestStage(player, "Lost Tribe", 48)
            } else {
                // Inform the player that a key is required to open the chest
                sendMessage(player, "This chest requires a key.")
            }
            return@on true
        }

        // Listener for pickpocketing Sigmund
        on(SIGMUND, IntType.NPC, "pickpocket") { player, _ ->
            player.lock() // Lock the player to prevent other actions
            GameWorld.Pulser.submit(object : Pulse() {
                var counter = 0 // Counter to track pulse iterations
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> player.animator.animate(Animation(Animations.PICK_POCKET_881)) // Animate pickpocket action
                        3 -> {
                            // Check if the player is at the correct quest stage and does not have the key
                            if (getQuestStage(player, "Lost Tribe") == 47 && !inInventory(player, Items.KEY_423)) {
                                // Add the key to the player's inventory and inform them
                                addItemOrDrop(player, Items.KEY_423)
                                sendItemDialogue(player, Items.KEY_423, "You find a small key on Sigmund.")
                            } else {
                                // Inform the player that their action is noticed
                                sendNPCDialogue(player, SIGMUND, "What do you think you're doing?!", FacialExpression.ANGRY)
                            }
                            player.unlock() // Unlock the player after the action
                            return true
                        }
                    }
                    return false // Continue the pulse until the action is complete
                }
            })
            return@on true
        }

        // Listener for looking at the brooch
        on(BROOCH, IntType.ITEM, "look-at") { player, _ ->
            openInterface(player, Components.BROOCH_VIEW_50) // Open the brooch viewing interface
            return@on true
        }

        // Listener for reading the goblin book
        on(GOBLIN_BOOK, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.GOBLIN_SYMBOL_BOOK_183) // Open the goblin symbol book interface
            return@on true
        }

        // Listener for searching the bookcase
        on(BOOKCASE, IntType.SCENERY, "search") { player, _ ->
            val hasAnBook = hasAnItem(player, Items.GOBLIN_SYMBOL_BOOK_5009).container != null // Check if the player has the goblin book
            if (!hasAnBook && getQuestStage(player, "Lost Tribe") >= 41) {
                // Inform the player about finding the book and add it to their inventory
                sendDialogue(player, "'A History of the Goblin Race.' This must be it.")
                addItemOrDrop(player, Items.GOBLIN_SYMBOL_BOOK_5009)
            }
            return@on true
        }

        // Listener for searching the crate
        on(CRATE, IntType.SCENERY, "search") { player, _ ->
            // Check if the player does not have the silverware and is at the correct quest stage
            if (!inInventory(player, Items.SILVERWARE_5011) && getQuestStage(player, "Lost Tribe") == 48) {
                // Inform the player about finding the silverware and add it to their inventory
                sendItemDialogue(player, Items.SILVERWARE_5011, "You find the missing silverware!")
                addItemOrDrop(player, Items.SILVERWARE_5011)
                // Update the quest stage
                setQuestStage(player, "Lost Tribe", 49)
            } else {
                // Inform the player that nothing was found
                sendMessage(player, "You find nothing.")
            }
            return@on true
        }

        // Listener for goblin followers
        on(GOBLIN_FOLLOWERS, IntType.NPC, "follow") { player, node ->
            // Check which goblin is being followed and send the player to the appropriate location
            if (node.asNpc().id == NPCs.MISTAG_2084) {
                GoblinFollower.sendToLumbridge(player)
            } else {
                GoblinFollower.sendToMines(player)
            }
            return@on true
        }

    }

    companion object {
        // Array of goblin follower NPC IDs
        val GOBLIN_FOLLOWERS = intArrayOf(NPCs.MISTAG_2084, NPCs.KAZGAR_2086)
        private const val GOBLIN_BOOK = Items.GOBLIN_SYMBOL_BOOK_5009 // Constant for the goblin book item
        private const val BROOCH = Items.BROOCH_5008 // Constant for the brooch item
        private const val CRATE = Scenery.CRATE_6911 // Constant for the crate scenery
        private const val BOOKCASE = Scenery.BOOKCASE_6916 // Constant for the bookcase scenery
        private const val CHEST = Scenery.CHEST_6910 // Constant for the chest scenery
        private const val SIGMUND = NPCs.SIGMUND_2082 // Constant for the Sigmund NPC
    }
}