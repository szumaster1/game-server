package content.region.kandarin.ardougne.dialogue

import content.global.travel.charter.Ship
import core.api.*
import org.rs.consts.Items
import core.game.dialogue.DialogueFile
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.tools.END_DIALOGUE

/**
 * Represents the Captain Barnaby dialogue.
 */
class CaptainBarnabyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        /*
         * Discount if player has completed easy karamja diary.
         */
        var amount = if (isDiaryComplete(player!!, DiaryType.KARAMJA, 0)) 15 else 30
        when (stage) {
            0 -> npcl("Do you want to go on a trip to Brimhaven?").also { stage++ }
            1 -> npcl("The trip will cost you 30 coins.").also { stage++ }
            2 -> options("Yes please.", "No, thank you.").also { stage++ }
            3 -> when (buttonID) {
                1 -> playerl("Yes please.").also { stage++ }
                2 -> playerl("No, thank you.").also { stage = END_DIALOGUE }
            }

            4 -> {

                if (!inInventory(player!!, Items.COINS_995, amount)) {
                    sendMessage(player!!, "You can not afford that.").also { stage = END_DIALOGUE }
                }
                if (isDiaryComplete(player!!, DiaryType.KARAMJA, 0)) {
                    npcl("Wait a minute, didn't you earn Karamja gloves? Thought I'd seen you helping around the island. You can go on half price.").also { stage++ }
                } else {
                    sendMessage(player!!, "You pay $amount coins and board the ship.").also { stage++ }
                }
            }
            5 -> {
                removeItem(player!!, Item(Items.COINS_995, amount))
                sendMessage(player!!, "You pay 30 coins and board the ship.")
                playJingle(player!!, 171)
                Ship.ARDOUGNE_TO_BRIMHAVEN.sail(player!!)
                sendDialogue(player!!, "The ship arrives at Brimhaven.")
            }
        }
    }

}
