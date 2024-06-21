package content.region.kandarin.dialogue.ardougne

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class ZookeeperDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hi!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Welcome to the Ardougne Zoo! How can I help you?").also { stage++ }
            1 -> options("Do you have any quests for me?", "Where did you get the animals from?").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Do you have any quests for me?").also { stage = 10 }
                2 -> playerl(FacialExpression.FRIENDLY, "Where did you get the animals from?").also { stage = 20 }
            }
            10 -> npcl(FacialExpression.FRIENDLY, "Not at the moment. The explorers that come back from far away lands tell such amazing tales. Make sure you keep eyes and ears open as you may find new places to explore!").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Ooh, I will!").also { stage = END_DIALOGUE }
            20 -> npcl(FacialExpression.FRIENDLY, "We get them from all over the place! The most exotic creatures have been brought back by explorers and sold to us.").also { stage++ }
            21 -> playerl(FacialExpression.HALF_ASKING, "Where on Gielinor did you get that scary looking Cyclops?!").also { stage++ }
            22 -> npcl(FacialExpression.LAUGH, "Yes he is scary looking!").also { stage++ }
            23 -> npcl(FacialExpression.FRIENDLY, "He's from a very far away land, we couldn't find out more as the explorer who brought him back died shortly afterwards!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZOO_KEEPER_28)
    }
}
