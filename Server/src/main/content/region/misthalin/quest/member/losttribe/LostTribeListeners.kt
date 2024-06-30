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

class LostTribeListeners : InteractionListener {

    override fun defineListeners() {

        on(CHEST, IntType.SCENERY, "open") { player, _ ->
            if (getQuestStage(player, "Lost Tribe") == 47 && inInventory(player, Items.KEY_423, 1)) {
                removeItem(player, Item(Items.KEY_423))
                for (item in arrayOf(Items.HAM_ROBE_4300, Items.HAM_SHIRT_4298, Items.HAM_HOOD_4302).map { Item(it) }) {
                    if (!player.inventory.add(item)) {
                        GroundItemManager.create(item, player)
                    }
                }
                setQuestStage(player, "Lost Tribe", 48)
            } else {
                sendMessage(player, "This chest requires a key.")
            }
            return@on true
        }

        on(SIGMUND, IntType.NPC, "pickpocket") { player, _ ->
            player.lock()
            GameWorld.Pulser.submit(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> player.animator.animate(Animation(Animations.PICK_POCKET_881))
                        3 -> {
                            if (getQuestStage(player, "Lost Tribe") == 47 && !inInventory(player, Items.KEY_423)) {
                                addItemOrDrop(player, Items.KEY_423)
                                sendItemDialogue(player, Items.KEY_423, "You find a small key on Sigmund.")
                            } else {
                                sendNPCDialogue(player, SIGMUND, "What do you think you're doing?!", FacialExpression.ANGRY)
                            }
                            player.unlock()
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }

        on(BROOCH, IntType.ITEM, "look-at") { player, _ ->
            openInterface(player, Components.BROOCH_VIEW_50)
            return@on true
        }

        on(GOBLIN_BOOK, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.GOBLIN_SYMBOL_BOOK_183)
            return@on true
        }

        on(BOOKCASE, IntType.SCENERY, "search") { player, _ ->
            val hasAnBook = hasAnItem(player, Items.GOBLIN_SYMBOL_BOOK_5009).container != null
            if (!hasAnBook && getQuestStage(player, "Lost Tribe") >= 41) {
                sendDialogue(player, "'A History of the Goblin Race.' This must be it.")
                addItemOrDrop(player, Items.GOBLIN_SYMBOL_BOOK_5009)
            }
            return@on true
        }

        on(CRATE, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, Items.SILVERWARE_5011) && getQuestStage(player, "Lost Tribe") == 48) {
                sendItemDialogue(player, Items.SILVERWARE_5011, "You find the missing silverware!")
                addItemOrDrop(player, Items.SILVERWARE_5011)
                setQuestStage(player, "Lost Tribe", 49)
            } else {
                sendMessage(player, "You find nothing.")
            }
            return@on true
        }

        on(GOBLIN_FOLLOWERS, IntType.NPC, "follow") { player, node ->
            if (node.asNpc().id == NPCs.MISTAG_2084) {
                GoblinFollower.sendToLumbridge(player)
            } else {
                GoblinFollower.sendToMines(player)
            }
            return@on true
        }

    }

    companion object {
        val GOBLIN_FOLLOWERS = intArrayOf(NPCs.MISTAG_2084, NPCs.KAZGAR_2086)
        private const val GOBLIN_BOOK = Items.GOBLIN_SYMBOL_BOOK_5009
        private const val BROOCH = Items.BROOCH_5008
        private const val CRATE = Scenery.CRATE_6911
        private const val BOOKCASE = Scenery.BOOKCASE_6916
        private const val CHEST = Scenery.CHEST_6910
        private const val SIGMUND = NPCs.SIGMUND_2082
    }
}
