package content.global.handlers.item.withscenery

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class CoalTruckListener : InteractionListener {

    override fun defineListeners() {
        on(Scenery.COAL_TRUCK_2114, IntType.SCENERY, "remove-coal") { player, _ ->
            var coalInTruck = getAttribute(player, ATTRIBUTE_COAL_TRUCK_INVENTORY, 0)
            if (coalInTruck == 0) {
                sendDialogue(player, "The coal truck is empty.")
                return@on true
            }
            var toRemove = freeSlots(player)
            if (toRemove > coalInTruck) {
                toRemove = coalInTruck
            }
            if (addItem(player, Items.COAL_453, toRemove)) {
                coalInTruck -= toRemove
                setAttribute(player, "/save:$ATTRIBUTE_COAL_TRUCK_INVENTORY", coalInTruck)
            }
            return@on true
        }

        on(Scenery.COAL_TRUCK_2114, IntType.SCENERY, "investigate") { player, _ ->
            val coalInTruck = getAttribute(player, ATTRIBUTE_COAL_TRUCK_INVENTORY, 0)
            sendDialogue(
                player, "There is currently $coalInTruck coal in the truck. " + "The truck has space for " +
                        when {
                            inEquipment(player, Items.SEERS_HEADBAND_3_14660) -> 196 - coalInTruck
                            inEquipment(player, Items.SEERS_HEADBAND_2_14659) -> 168 - coalInTruck
                            inEquipment(player, Items.SEERS_HEADBAND_1_14631) -> 140 - coalInTruck
                            else -> 120 - coalInTruck
                        }
                        + " more coal."
            )
            return@on true
        }

        onUseWith(IntType.SCENERY, Items.COAL_453, Scenery.COAL_TRUCK_2114) { player, _, _ ->
            var coalInTruck = getAttribute(player, ATTRIBUTE_COAL_TRUCK_INVENTORY, 0)
            var coalInInventory = amountInInventory(player, Items.COAL_453)
            if (inEquipment(player, Items.SEERS_HEADBAND_3_14660)) {
                if (coalInInventory + coalInTruck >= 196) {
                    coalInInventory = 196 - coalInTruck
                    sendMessage(player, message)
                }
            } else if (inEquipment(player, Items.SEERS_HEADBAND_2_14659)) {
                if (coalInInventory + coalInTruck >= 168) {
                    coalInInventory = 168 - coalInTruck
                    sendMessage(player, message)
                }
            } else if (inEquipment(player, Items.SEERS_HEADBAND_1_14631)) {
                if (coalInInventory + coalInTruck >= 140) {
                    coalInInventory = 140 - coalInTruck
                    sendMessage(player, message)
                }
            } else {
                if (coalInInventory + coalInTruck >= 120) {
                    coalInInventory = 120 - coalInTruck
                    sendMessage(player, message)
                }
            }
            if (removeItem(player, Item(Items.COAL_453, coalInInventory))) {
                coalInTruck += coalInInventory
                setAttribute(player, "/save:$ATTRIBUTE_COAL_TRUCK_INVENTORY", coalInTruck)
            }
            return@onUseWith true
        }
    }

    companion object {
        private const val ATTRIBUTE_COAL_TRUCK_INVENTORY = "coal-truck-inventory"
        val message = "You have filled up the coal truck."
    }

}
