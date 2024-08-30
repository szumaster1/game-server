package content.global.dialogue

import cfg.consts.Components
import cfg.consts.Items
import core.api.openInterface
import core.api.sendDialogue
import core.api.sendItemDialogue
import core.game.dialogue.DialogueFile
import core.game.node.item.Item
import core.tools.START_DIALOGUE

/**
 * Represents the Bank help dialogue.
 */
class BankHelpDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            START_DIALOGUE -> options("Check Bank Value", "Banking Assistance", "Close").also { stage++ }
            1 -> when (buttonID) {
                1 -> player?.let {
                    end()
                    val wealth = it.bank.wealth
                    if (wealth > 0) {
                        val word = if (wealth != 1) "coins" else "coin"
                        sendItemDialogue(
                            it,  if (wealth < 2)     Item(Items.COINS_8890, wealth)
                            else if (wealth < 3)     Item(Items.COINS_8891, wealth)
                            else if (wealth < 4)     Item(Items.COINS_8892, wealth)
                            else if (wealth < 5)     Item(Items.COINS_8893, wealth)
                            else if (wealth < 25)    Item(Items.COINS_8894, wealth)
                            else if (wealth < 100)   Item(Items.COINS_8895, wealth)
                            else if (wealth < 250)   Item(Items.COINS_8896, wealth)
                            else if (wealth < 1000)  Item(Items.COINS_8897, wealth)
                            else if (wealth < 10000) Item(Items.COINS_8898, wealth)
                            else Item(Items.COINS_8899, wealth), "Your bank is worth <col=a52929>${wealth}</col> ${word}.")
                    } else {
                        sendDialogue(it, "You have no valuables in your bank.")
                    }
                }
                2 -> player?.let {
                    end()
                    it.bank.close()
                    openInterface(it, Components.BANK_V2_HELP_767)
                }
                3 -> end()
            }
        }
    }
}
