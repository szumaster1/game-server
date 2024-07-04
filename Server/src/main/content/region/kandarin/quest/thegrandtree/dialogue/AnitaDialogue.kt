package content.region.kandarin.quest.thegrandtree.dialogue

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.sendItemDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class AnitaDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (getQuestStage(player!!, "The Grand Tree")) {
            0 -> core.api.sendDialogue(player!!, "Anita seems too busy to talk.").also { stage = END_DIALOGUE }
            60 -> {
                if (player!!.hasItem(Item(Items.GLOUGHS_KEY_788)) && stage < 12) {
                    when (stage) {
                        0 -> npcl(FacialExpression.OLD_DEFAULT, "Have you taken that key to Glough yet?").also { stage++ }
                        1 -> playerl(FacialExpression.NEUTRAL, "No, I'm still carrying it around.").also { stage++ }
                        2 -> npcl(FacialExpression.OLD_DEFAULT, "Oh. Please take it to Glough!").also { stage = END_DIALOGUE }
                    }
                } else {
                    when (stage) {
                        0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                        1 -> npcl(FacialExpression.OLD_DEFAULT, "Oh hello, I've seen you with the King.").also { stage++ }
                        2 -> playerl(FacialExpression.FRIENDLY, "Yes, I'm helping him with a problem.").also { stage++ }
                        3 -> npcl(FacialExpression.OLD_DEFAULT, "You must know my boyfriend Glough then?").also { stage++ }
                        4 -> playerl(FacialExpression.FRIENDLY, "Indeed!").also { stage++ }
                        5 -> npcl(FacialExpression.OLD_DEFAULT, "Could you do me a favour?").also { stage++ }
                        6 -> playerl(FacialExpression.THINKING, "I suppose so.").also { stage++ }
                        7 -> npcl(FacialExpression.OLD_DEFAULT, "Please give this key to Glough, he left it here last night.").also { stage++ }
                        8 -> sendItemDialogue(player!!, Items.GLOUGHS_KEY_788, "Anita gives you a key.").also {
                            addItemOrDrop(player!!, Items.GLOUGHS_KEY_788)
                            stage++
                        }
                        9 -> npcl(FacialExpression.OLD_DEFAULT, "Thanks a lot.").also { stage++ }
                        10 -> playerl(FacialExpression.HAPPY, "No...thank you!").also { stage = END_DIALOGUE }
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ANITA_672)
    }

}
