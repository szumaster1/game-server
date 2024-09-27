package content.region.karamja.shilovillage.dialogue

import content.region.karamja.shilovillage.handlers.ShilovillageListener
import core.api.hasRequirement
import core.api.inInventory
import core.api.openDialogue
import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.tools.END_DIALOGUE
import org.rs.consts.Items
import org.rs.consts.QuestName

/**
 * Represents the Cart travel dialogue.
 */
class CartTravelDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        if (!hasRequirement(player!!, QuestName.SHILO_VILLAGE)) return
        val shilo = npc?.id == 510
        when (stage) {
            0 -> npcl("I am offering a cart ride to " + (if (shilo) "Shilo Village" else "Brimhaven") + " if you're interested? It will cost 10 gold coins. Is that Ok?").also { stage++ }
            1 -> {
                if (!inInventory(player!!, Items.COINS_995, 10)) {
                    playerl("Sorry, I don't seem to have enough coins.").also { stage = END_DIALOGUE }
                } else {
                    playerl("Yes please, I'd like to go to " + (if (shilo) "Shilo Village" else "Brimhaven") + ".").also { stage++ }
                }
            }
            2 -> npcl("Great! Just hop into the cart then and we'll go!").also { stage++ }
            3 -> sendDialogue(player!!, "You hop into the cart and the driver urges the horses on. You take a taxing journey through the jungle to " + (if (shilo) "Shilo Village" else "Brimhaven") + ".").also { stage++ }
            4 -> openDialogue(player!!, ShilovillageListener.Companion.CartQuickPay(), npc!!)
        }
    }

}
