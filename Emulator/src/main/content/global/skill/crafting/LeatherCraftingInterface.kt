package content.global.skill.crafting

import org.rs.consts.Components
import core.api.sendInputDialogue
import core.api.submitIndividualPulse
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

/**
 * Leather craft interface listener.
 */
class LeatherCraftingInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.LEATHER_CRAFTING_154) { player, component, opcode, buttonID, slot, itemID ->
            var amount = 0
            val soft = Leather.SoftLeather.forButton(buttonID) ?: return@on true
            when (opcode) {
                155 -> amount = 1
                196 -> amount = 5
                124 -> amount = player.inventory.getAmount(Item(Leather.LEATHER))
                199 -> {
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        submitIndividualPulse(
                            player,
                            SoftLeatherCraftPulse(
                                player,
                                Item(Leather.LEATHER),
                                soft,
                                value as Int
                            )
                        )
                    }
                    return@on true
                }
            }
            player.pulseManager.run(
                SoftLeatherCraftPulse(
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
