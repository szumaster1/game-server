package content.region.kandarin.handlers

import core.api.*
import core.api.consts.Animations
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location

class PiscatorisListeners : InteractionListener {

    companion object {
        private const val NET_SCENERY = 14973
        private const val EMPTY_NET_SCENERY = 14972
        private const val KATHY_CORKAT = NPCs.KATHY_CORKAT_3831
        private const val CHAROS_RING_A = Items.RING_OF_CHAROSA_6465
    }

    override fun defineListeners() {

        /*
            Rowing boat travel interaction.
         */

        on(KATHY_CORKAT, IntType.NPC, "travel") { player, _ ->
            if (!hasRequirement(player, "Swan Song")) return@on true
            if (inEquipment(player, CHAROS_RING_A)) {
                setAttribute(player, "charos-travel-discount", true)
            }

            if (amountInInventory(player, 995) < 50) {
                if (getAttribute(player, "charos-travel-discount", false)) {
                    return@on true
                } else {
                    sendDialogue(player, "You don't have enough coins for that.")
                }
            }

            lock(player, 1000)
            lockInteractions(player, 1000)
            GameWorld.Pulser.submit(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> {
                            player.dialogueInterpreter.sendPlainMessage(true, "", "", "Kathy Corkat rows you up the river...", "")
                            openInterface(player, Components.FADE_TO_BLACK_120)
                        }
                        3 -> teleport(player, Location(2369, 3484, 0))
                        4 -> openInterface(player, Components.FADE_FROM_BLACK_170)
                        8 -> {
                            unlock(player)
                            player.interfaceManager.closeChatbox()
                            openInterface(player, Components.CHATDEFAULT_137)
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }

        /*
            Net scenery interaction.
         */

        on(NET_SCENERY, IntType.SCENERY, "Take-from") { player, node ->
            if (!hasRequirement(player, "Swan Song")) return@on true

            if (!hasSpaceFor(player, Item(Items.SEAWEED_401, 1))) {
                sendMessage(player, "You do not have space in your inventory.")
                return@on true
            }
            submitIndividualPulse(player, object : Pulse() {
                private var tick = 0
                override fun pulse(): Boolean {
                    when (tick++) {
                        0 -> animate(player, Animations.HUMAN_BURYING_BONES_827)
                        1 -> {
                            if (addItem(player, Items.SEAWEED_401)) {
                                SceneryBuilder.replace(node.asScenery(), Scenery(EMPTY_NET_SCENERY, node.location, node.asScenery().rotation), 5)
                            }
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