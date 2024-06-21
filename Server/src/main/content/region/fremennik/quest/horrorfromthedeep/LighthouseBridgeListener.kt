package content.region.fremennik.quest.horrorfromthedeep

import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.impl.ForceMovement
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

class LighthouseBridgeListener : InteractionListener {

    override fun defineListeners() {
        // Repair bridge side between Rellekka & Lighthouse.
        onUseWith(IntType.SCENERY, Items.PLANK_960, HFTDUtils.BROKEN_BRIDGE_1) { player, _, _ ->
            if (getQuestStage(player, "Horror from the Deep") == 0) {
                sendDialogue(player, "That won't help fix the bridge.")
                return@onUseWith false
            }
            if (getQuestStage(player, "Horror from the Deep") >= 10 && getAttribute(player, HFTDUtils.FIX_BRIDGE, 0) == 1) {
                sendDialogue(player, "You have already fixed this half of the bridge.")
                return@onUseWith false
            }
            if (getQuestStage(player, "Horror from the Deep") >= 5 && !inInventory(player, Items.HAMMER_2347, 1) && inInventory(player, Items.PLANK_960, 1) && amountInInventory(player, Items.STEEL_NAILS_1539) >= 30) {
                sendDialogue(player, "You need a hammer to force the nails in with.")
                return@onUseWith false
            }
            if (amountInInventory(player, Items.STEEL_NAILS_1539) < 30) {
                sendDialogue(player, "You need 30 steel nails to attach the plank with.")
                return@onUseWith false
            }

            lock(player, 2)
            removeItem(player, Item(Items.STEEL_NAILS_1539, 30))
            removeItem(player, Item(Items.PLANK_960, 1))
            queueScript(player, 1, QueueStrength.SOFT) {
                setAttribute(player, HFTDUtils.FIX_BRIDGE, 1)
                animate(player, 898)
                setQuestStage(player, "Horror from the Deep", 10)
                sendDialogue(player, "You create half a makeshift walkway out of the plank.")
                return@queueScript stopExecuting(player)
            }
            return@onUseWith true
        }

        // Bridge side between Lighthouse & Rellekka.
        onUseWith(IntType.SCENERY, Items.PLANK_960, HFTDUtils.BROKEN_BRIDGE_2) { player, _, _ ->
            if (getQuestStage(player, "Horror from the Deep") < 10) {
                sendDialogue(player, "That won't help fix the bridge.")
                return@onUseWith false
            }

            if (getAttribute(player, HFTDUtils.FIX_BRIDGE, 0) == 1 && !inInventory(player, Items.HAMMER_2347, 1) && inInventory(player, Items.PLANK_960, 1) && amountInInventory(player, Items.STEEL_NAILS_1539) >= 30) {
                sendDialogue(player, "You need a hammer to force the nails in with.")
                return@onUseWith false
            }

            if (amountInInventory(player, Items.STEEL_NAILS_1539) < 30) {
                sendDialogue(player, "You need 30 steel nails to attach the plank with.")
                return@onUseWith false
            }

            lock(player, 3)
            removeItem(player, Item(Items.STEEL_NAILS_1539, 30))
            removeItem(player, Item(Items.PLANK_960, 1))
            queueScript(player, 1, QueueStrength.SOFT) {
                setAttribute(player, HFTDUtils.FIX_BRIDGE, 2)
                animate(player, 898)
                setQuestStage(player, "Horror from the Deep", 20)
                sendDialogue(player, "You have now made a makeshift walkway over the bridge.")
                return@queueScript stopExecuting(player)
            }
            return@onUseWith true
        }

        // Cross after repair first side.
        on(HFTDUtils.BROKEN_BRIDGE_1, IntType.SCENERY, "Cross") { player, _ ->
            if (getAttribute(player, HFTDUtils.FIX_BRIDGE, 0) == 1) {
                lock(player, 6)
                submitIndividualPulse(player, object : Pulse() {
                    var count = 0
                    override fun pulse(): Boolean {
                        when (count++) {
                            0 -> forceMove(player, Location(2595, 3608, 0), Location(2596, 3608, 0), 0, 30, Direction.EAST)
                            2 -> animate(player, 3067)
                            3 -> teleport(player, Location(2598, 3608, 0))
                            4 -> animate(player, 767, true)
                            5 -> forceWalk(player, Location(2599, 3608, 0), "smart").also { return true }
                        }
                        return false
                    }
                })
            } else {
                sendMessage(player, "I might be able to make to the other side.")
            }

            // Cross after repair both sides or after quest.
            if (getAttribute(player, HFTDUtils.FIX_BRIDGE, 0) == 2 || isQuestComplete(player, "Horror from the Deep")) {
                lock(player, 6)
                submitIndividualPulse(player, object : Pulse(1) {
                    var count = 0
                    override fun pulse(): Boolean {
                        when (count++) {
                            0 -> ForceMovement.run(player, Location(2596, 3608, 0), Location(2597, 3608, 0), Animation(753), Animation(756), Direction.EAST)
                            2 -> ForceMovement.run(player, Location(2597, 3608, 0), Location(2598, 3608, 0), Animation(757), Animation(756), Direction.EAST)
                            3 -> ForceMovement.run(player, Location(2598, 3608, 0), Location(2599, 3608, 0), Animation(756), Animation(759), Direction.EAST).also { return true }
                        }
                        return false
                    }
                })
            } else {
                sendMessage(player, "I might be able to make to the other side.")
            }
            return@on true
        }

        // Cross bridge to Lighthouse.
        on(HFTDUtils.BROKEN_BRIDGE_2, IntType.SCENERY, "Cross") { player, _ ->
            if (getAttribute(player, HFTDUtils.FIX_BRIDGE, 0) == 2 || isQuestComplete(player, "Horror from the Deep")) {
                lock(player, 6)
                submitIndividualPulse(player, object : Pulse(1) {
                    var count = 0
                    override fun pulse(): Boolean {
                        when (count++) {
                            0 -> ForceMovement.run(player, Location(2598, 3608, 0), Location(2597, 3608, 0), Animation(752), Animation(754), Direction.WEST)
                            2 -> ForceMovement.run(player, Location(2597, 3608, 0), Location(2596, 3608, 0), Animation(755), Animation(754), Direction.WEST)
                            3 -> ForceMovement.run(player, Location(2596, 3608, 0), Location(2595, 3608, 0), Animation(754), Animation(758), Direction.WEST).also { return true }
                        }
                        return false
                    }
                })
            } else {
                sendMessage(player, "I might be able to make to the other side.")
            }
            return@on true
        }
    }
}