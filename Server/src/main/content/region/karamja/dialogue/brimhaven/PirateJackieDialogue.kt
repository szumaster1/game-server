package content.region.karamja.dialogue.brimhaven

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.openDialogue
import core.api.openInterface
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Pirate jackie dialogue.
 */
class PirateJackieDialogue : DialogueFile() {

    /*
     * Pirate Jackie the Fruit can be found
     * at the Brimhaven Agility Arena entrance.
     * Also run the Agility Arena Ticket Exchange.
     * Location: 2811,3191
     */

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.PIRATE_JACKIE_THE_FRUIT_1055)
        when (stage) {
            0 -> playerl(FacialExpression.NEUTRAL, "Ahoy there!").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "Ahoy!").also { stage++ }
            2 -> options("What is this place?", "What do you do?", "I'd like to trade in my tickets, please.", "I have a question about my Achievement Diary.", "See you later.").also { stage++ }

            3 -> when (buttonID) {
                1 -> playerl(FacialExpression.NEUTRAL, "What is this place?").also { stage = 10 }
                2 -> playerl(FacialExpression.NEUTRAL, "What do you do?").also { stage = 20 }
                3 -> playerl(FacialExpression.NEUTRAL, "I'd like to trade in my tickets, please.").also { stage = 30 }
                4 -> playerl(FacialExpression.NEUTRAL, "I have a question about my Achievement Diary.").also { stage = 40 }
                5 -> playerl(FacialExpression.NEUTRAL, "See you later.").also { stage = END_DIALOGUE }
            }

            10 -> npcl(FacialExpression.NEUTRAL, "Welcome to the Brimhaven Agility Arena!").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "If ye want to know more talk to Cap'n Izzy, he found it!").also { stage = END_DIALOGUE }
            20 -> npcl(FacialExpression.NEUTRAL, "I be the Jack o' tickets. I exchange the tickets ye collect in the Agility Arena for " + "more stuff. Ye can obtain more agility experience or some items ye won't find anywhere else!").also { stage++ }
            21 -> playerl(FacialExpression.NEUTRAL, "Sounds good!").also { stage = END_DIALOGUE }
            30 -> {
                npcl(FacialExpression.NEUTRAL, "Aye, ye be on the right track.").also { stage = END_DIALOGUE }
                end()
                openInterface(player!!, Components.AGILITYARENA_TRADE_6)
            }

            40 -> {
                end()
                openDialogue(player!!, PirateJackieDiaryDialogue())
            }
        }
    }
}
