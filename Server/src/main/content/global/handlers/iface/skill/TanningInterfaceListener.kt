package content.global.handlers.iface.skill

import content.global.skill.production.crafting.TanningProduct
import core.api.consts.Components
import core.api.sendInputDialogue
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

class TanningInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.TANNER_324) { player, _, opcode, buttonID, _, _ ->
            var type: TanningProduct? = null
            when (buttonID) {
                1 -> type = TanningProduct.SOFT_LEATHER
                2 -> type = TanningProduct.HARD_LEATHER
                3 -> type = TanningProduct.SNAKESKIN
                4 -> type = TanningProduct.SNAKESKIN2
                5 -> type = TanningProduct.GREEN_DHIDE
                6 -> type = TanningProduct.BLUEDHIDE
                7 -> type = TanningProduct.REDDHIDE
                8 -> type = TanningProduct.BLACKDHIDE
            }
            if (type == null) {
                return@on true
            }
            var amount = 0
            val product: TanningProduct = type
            when (opcode) {
                155 -> amount = 1
                196 -> amount = 5
                124 -> {
                    amount = 10
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        TanningProduct.tan(player, value as Int, product)
                    }
                }

                199 -> sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    TanningProduct.tan(player, value as Int, product)
                }

                234 -> amount = player.inventory.getAmount(Item(type.item, 1))
            }
            TanningProduct.tan(player, amount, type)
            return@on true
        }
    }
}
