package content.region.kandarin.ardougne.dialogue

import content.global.transport.charter.Ships
import cfg.consts.Items
import core.api.playJingle
import core.api.removeItem
import core.api.sendMessage
import core.game.dialogue.DialogueFile
import core.game.node.item.Item
import core.tools.END_DIALOGUE

/**
 * Represents the Captain Barnaby dialogue.
 */
class CaptainBarnabyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl("Do you want to go on a trip to Brimhaven?").also { stage += 1 }
            1 -> npcl("The trip will cost you 30 coins.").also { stage += 1 }
            2 -> options("Yes please.", "No, thank you.").also { stage += 1 }
            3 -> when (buttonID) {
                1 -> playerl("Yes please.").also { stage = 10 }
                2 -> playerl("No, thank you.").also { stage = END_DIALOGUE }
            }

            10 -> {
                if (removeItem(player!!, Item(Items.COINS_995, 30))) {
                    sendMessage(player!!, "You pay 30 coins and board the ship.")
                    playJingle(player!!, 171)
                    Ships.ARDOUGNE_TO_BRIMHAVEN.sail(player!!)
                    stage = END_DIALOGUE
                } else {
                    playerl("Sorry, I don't seem to have enough coins.").also { stage = END_DIALOGUE }
                }
            }
        }
    }

}
