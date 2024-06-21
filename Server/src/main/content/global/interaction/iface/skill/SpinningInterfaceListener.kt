package content.global.interaction.iface.skill

import content.global.skill.production.crafting.spinning.SpinningItem
import content.global.skill.production.crafting.spinning.SpinningPulse
import core.api.*
import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

class SpinningInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.CRAFTING_SPINNING_459) { player, _, opcode, buttonID, _, _ ->
            val spin = SpinningItem.forId(buttonID) ?: return@on true
            if (!inInventory(player, spin.need, 1)) {
                sendMessage(player, "You need " + getItemName(spin.need).lowercase() + " to make this.")
                return@on true
            }
            var amt = -1
            when (opcode) {
                155 -> amt = 1
                196 -> amt = 5
                124 -> amt = player.inventory.getAmount(Item(spin.need))
                199 -> sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    if (value is String) {
                        submitIndividualPulse(player, SpinningPulse(player, Item(spin.need, 1), value.toInt(), spin))
                    } else {
                        submitIndividualPulse(player, SpinningPulse(player, Item(spin.need, 1), value as Int, spin))
                    }
                }
            }
            if (opcode == 199) {
                return@on true
            }
            player.pulseManager.run(SpinningPulse(player, Item(spin.need, 1), amt, spin))
            return@on true
        }
    }
}
