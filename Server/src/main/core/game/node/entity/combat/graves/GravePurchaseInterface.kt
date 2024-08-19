package core.game.node.entity.combat.graves

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

/**
 * Grave purchase interface.
 * @author Ceikry
 */
class GravePurchaseInterface : InterfaceListener {
    val BUTTON_CONFIRM = 34 // Constant for the confirm button ID
    val AVAILABLE_GRAVES_BITFIELD = 0xFFF // Bitfield representing available graves; excludes quest-locked graves
    val AVAILABLE_GRAVES_VARBIT = 4191 // Variable bit for available graves
    val CURRENT_GRAVE_VARBIT = 4190 // Variable bit for the current grave type

    override fun defineInterfaceListeners() {
        // Listener for when the grave purchase interface is opened
        onOpen(Components.GRAVESTONE_SHOP_652) { player, _ ->
            val userType = GraveController.getGraveType(player).ordinal // Get the current grave type of the player
            setVarbit(player, AVAILABLE_GRAVES_VARBIT, AVAILABLE_GRAVES_BITFIELD) // Set available graves for the player
            setVarbit(player, CURRENT_GRAVE_VARBIT, userType) // Set the current grave type for the player

            // Build and send interface settings to the player
            val settings = IfaceSettingsBuilder()
                .enableAllOptions() // Enable all options in the interface
                .build() // Build the settings
            player.packetDispatch.sendIfaceSettings(settings, 34, Components.GRAVESTONE_SHOP_652, 0, 13) // Dispatch the settings to the player
            return@onOpen true // Return true to indicate successful handling
        }

        // Listener for the confirm button click in the grave purchase interface
        on(Components.GRAVESTONE_SHOP_652, BUTTON_CONFIRM) { player, _, _, _, slot, _ ->
            val selectedType = GraveType.values()[slot] // Get the grave type selected by the player
            val userType = GraveController.getGraveType(player) // Get the current grave type of the player
            val activeGrave = GraveController.activeGraves[player.details.uid] // Check if the player has an active grave

            // Check if the player has an active grave
            if (activeGrave != null) {
                sendDialogue(player, "You cannot change graves while you have a grave active.") // Notify the player
                return@on true // Return true to indicate successful handling
            }

            // Check if the selected grave type is the same as the current one
            if (selectedType == userType) {
                sendDialogue(player, "You already have that gravestone!") // Notify the player
                return@on true // Return true to indicate successful handling
            }

            val cost = selectedType.cost // Get the cost of the selected grave type
            val requirement = selectedType.requiredQuest // Get the quest requirement for the selected grave type

            // Check if the selected grave type has a quest requirement and if it is completed
            if (requirement.isNotEmpty() && !isQuestComplete(player, requirement)) {
                sendDialogue(player, "That gravestone requires completion of $requirement.") // Notify the player
                return@on true // Return true to indicate successful handling
            }

            // Check if the player has enough coins to afford the selected grave type
            if (selectedType != GraveType.MEM_PLAQUE && amountInInventory(player, Items.COINS_995) < cost) {
                sendDialogue(player, "You do not have enough coins to afford that gravestone.") // Notify the player
                return@on true // Return true to indicate successful handling
            }

            // Check if the selected grave type is a memorial plaque or if the player can remove the required coins
            if (selectedType == GraveType.MEM_PLAQUE || removeItem(player, Item(995, cost))) {
                GraveController.updateGraveType(player, selectedType) // Update the player's grave type
                sendDialogue(player, "Your grave has been updated.") // Notify the player
            }

            closeInterface(player) // Close the grave purchase interface for the player
            return@on true // Return true to indicate successful handling
        }
    }
}