package content.global.skill.crafting.items.armour.leather

import core.api.amountInInventory
import core.api.sendInputDialogue
import core.api.submitIndividualPulse
import core.game.interaction.InterfaceListener
import core.game.node.item.Item
import org.rs.consts.Components

/**
 * Leather craft interface listener.
 */
class LeatherCraftingInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.LEATHER_CRAFTING_154) { player, _, opcode, buttonID, _, _ ->
            var amount = 0
            val soft = Leather.SoftLeather.forButton(buttonID) ?: return@on true
            when (opcode) {
                155 -> amount = 1
                196 -> amount = 5
                124 -> amount = amountInInventory(player, Leather.LEATHER)
                199 -> {
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        submitIndividualPulse(player, SoftLeatherCraftingPulse(player, Item(Leather.LEATHER), soft, value as Int))
                    }
                    return@on true
                }
            }
            submitIndividualPulse(player, SoftLeatherCraftingPulse(player, null, soft, amount))
            return@on true
        }
    }
}
