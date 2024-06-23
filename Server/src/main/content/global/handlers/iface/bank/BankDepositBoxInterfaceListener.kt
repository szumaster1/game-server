package content.global.handlers.iface.bank

import core.api.*
import core.api.consts.Animations
import core.game.interaction.InterfaceListener
import core.game.interaction.QueueStrength

class BankDepositBoxInterfaceListener : InterfaceListener {

    private val menuElement = 11
    private val itemExamine = 168
    private val depositBob = 13
    private val depositOne = 155
    private val depositFive = 196
    private val depositTen = 124
    private val depositAll = 199
    private val depositX = 234

    override fun defineInterfaceListeners() {
        on(menuElement) { player, _, opcode, buttonID, slot, _ ->
            val item = player.inventory.get(slot) ?: return@on true

            if (opcode == itemExamine) {
                sendMessage(player, item.definition.examine)
                return@on true
            }

            if (buttonID == depositBob) {
                dumpBeastOfBurden(player)
                return@on true
            }

            queueScript(player, 0, QueueStrength.STRONG) {
                when (opcode) {
                    depositOne -> player.bank.addItem(slot, 1)
                    depositFive -> player.bank.addItem(slot, 5)
                    depositTen -> player.bank.addItem(slot, 10)
                    // Needs to have a callback here because the depositing moment is independent of the world task.
                    depositX -> BankUtils.transferX(player, slot, false, player.bank::refreshDepositBoxInterface)
                    depositAll -> player.bank.addItem(slot, player.inventory.getAmount(item))
                    else -> player.debug("Unknown deposit box menu opcode $opcode")
                }
                player.bank.refreshDepositBoxInterface()
                animate(player, Animations.HUMAN_BANK_DEPOSIT_BOX_834)
                return@queueScript stopExecuting(player)
            }
            return@on true
        }
    }
}
