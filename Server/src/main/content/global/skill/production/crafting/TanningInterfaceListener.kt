package content.global.skill.production.crafting

import content.global.skill.production.crafting.data.TanningData
import core.api.consts.Components
import core.api.sendInputDialogue
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

/**
 * Tanning interface listener.
 */
class TanningInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.TANNER_324) { player, _, opcode, buttonID, _, _ ->
            var type: TanningData? = null
            when (buttonID) {
                1 -> type = TanningData.SOFT_LEATHER
                2 -> type = TanningData.HARD_LEATHER
                3 -> type = TanningData.SNAKESKIN
                4 -> type = TanningData.SNAKESKIN2
                5 -> type = TanningData.GREEN_DHIDE
                6 -> type = TanningData.BLUEDHIDE
                7 -> type = TanningData.REDDHIDE
                8 -> type = TanningData.BLACKDHIDE
            }
            if (type == null) {
                return@on true
            }
            var amount = 0
            val product: TanningData = type
            when (opcode) {
                155 -> amount = 1
                196 -> amount = 5
                124 -> {
                    amount = 10
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        TanningData.tan(player, value as Int, product)
                    }
                }

                199 -> sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    TanningData.tan(player, value as Int, product)
                }

                234 -> amount = player.inventory.getAmount(Item(type.item, 1))
            }
            TanningData.tan(player, amount, type)
            return@on true
        }
    }
}
