package content.global.skill.production.crafting

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.SoftLeather
import content.global.skill.production.crafting.item.SoftCraftPulse
import core.api.consts.Components
import core.api.sendInputDialogue
import core.api.submitIndividualPulse
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

class LeatherCraftInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.LEATHER_CRAFTING_154) { player, component, opcode, buttonID, slot, itemID ->
            var amount = 0
            val soft = SoftLeather.forButton(buttonID) ?: return@on true
            when (opcode) {
                155 -> amount = 1
                196 -> amount = 5
                124 -> amount = player.inventory.getAmount(Item(LeatherData.LEATHER))
                199 -> {
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        submitIndividualPulse(
                            player,
                            SoftCraftPulse(
                                player,
                                Item(LeatherData.LEATHER),
                                soft,
                                value as Int
                            )
                        )
                    }
                    return@on true
                }
            }
            player.pulseManager.run(
                SoftCraftPulse(
                    player,
                    null,
                    soft,
                    amount
                )
            )
            return@on true
        }
    }
}
