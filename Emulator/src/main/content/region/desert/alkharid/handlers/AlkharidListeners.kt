package content.region.desert.alkharid.handlers

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import content.region.desert.alkharid.dialogue.AlKharidHealDialogue
import content.region.desert.alkharid.dialogue.AliTheLeafletDropperDialogue
import content.region.desert.alkharid.dialogue.BorderGuardDialogue
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.system.command.sets.STATS_ALKHARID_GATE
import core.game.system.command.sets.STATS_BASE

/**
 * Represents the Alkharid listeners.
 */
class AlkharidListeners : InteractionListener {

    companion object {
        private const val FADLI_NPC = NPCs.FADLI_958
        private const val LEAFLET_DROPPER = NPCs.ALI_THE_LEAFLET_DROPPER_3680
        private val HEALERS_NPC = intArrayOf(959, 960, 961, 962)
        val TOLL_GATES = intArrayOf(35551, 35549)
        private const val BORDER_GUARD = NPCs.BORDER_GUARD_925
    }

    override fun defineListeners() {

        /*
         * Talk to interaction with Border guards.
         */

        on(BORDER_GUARD, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, BorderGuardDialogue())
            return@on true
        }

        /*
         * Toll gates interaction between Lumbridge and Al-Kharid.
         */

        on(TOLL_GATES, IntType.SCENERY, "open", "pay-toll(10gp)") { player, node ->
            if (getUsedOption(player) == "pay-toll(10gp)") {
                if (getQuestStage(player, "Prince Ali Rescue") > 50) {
                    sendMessage(player, "The guards let you through for free.")
                    DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                } else {
                    if (!removeItem(player, Item(Items.COINS_995, 10))) {
                        sendMessage(player, "You need 10 gold to pass through the gates.")
                    } else {
                        sendMessage(player, "You quickly pay the 10 gold toll and go through the gates.")
                        player.incrementAttribute("/save:$STATS_BASE:$STATS_ALKHARID_GATE", 10)
                        DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                        return@on true
                    }
                }
            } else {
                openDialogue(player, BorderGuardDialogue())
            }
            return@on true
        }

        /*
         * Duel arena healers.
         */

        on(HEALERS_NPC, IntType.NPC, "heal") { player, _ ->
            openDialogue(player, AlKharidHealDialogue(false))
            return@on true
        }

        /*
         * Fadli shop interaction.
         */

        on(FADLI_NPC, IntType.NPC, "buy") { player, _ ->
            openNpcShop(player, NPCs.FADLI_958)
            return@on true
        }

        /*
         * Interaction with Ali the leaflet.
         */

        on(LEAFLET_DROPPER, IntType.NPC, "Take-flyer") { player, _ ->
            if (player.inventory.containItems(Items.AL_KHARID_FLYER_7922)) {
                openDialogue(player, AliTheLeafletDropperDialogue(2))
            } else {
                if (addItem(player, Items.AL_KHARID_FLYER_7922)) {
                    openDialogue(player, AliTheLeafletDropperDialogue(1))
                } else {
                    return@on false
                }
            }
            return@on true
        }
    }
}