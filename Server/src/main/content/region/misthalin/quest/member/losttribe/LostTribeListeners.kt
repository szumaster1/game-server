package content.region.misthalin.quest.member.losttribe

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
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
        on(Scenery.CHEST_6910, IntType.SCENERY, "open") { player, _ ->
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

        on(NPCs.SIGMUND_2082, IntType.NPC, "pickpocket") { player, _ ->
            player.lock()
            GameWorld.Pulser.submit(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> player.animator.animate(Animation(881))
                        3 -> {
                            if (getQuestStage(player, "Lost Tribe") == 47 && !inInventory(player, Items.KEY_423)) {
                                addItemOrDrop(player, Items.KEY_423)
                                sendItemDialogue(player, Items.KEY_423, "You find a small key on Sigmund.")
                            } else {
                                sendNPCDialogue(player, 2082, "What do you think you're doing?!", FacialExpression.ANGRY)
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
    }
}