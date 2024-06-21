package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.START_DIALOGUE

@Initializable
class DawgDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> {
                if (!isQuestComplete(player, "As a First Resort")) {
                    npcl(FacialExpression.CHILD_NEUTRAL, "Grrrr!").also { stage++ }
                } else {
                    playerl(FacialExpression.FRIENDLY, "Hi there, um, puppy... cat... thing. Good Dawg.").also { stage = 3 }
                }
            }
            1 -> sendNPCDialogue(player, NPCs.CHIEF_TESS_7051, "Watch out, human; Dawg like human for breakfast.", FacialExpression.CHILD_NORMAL).also { stage++ }
            2 -> end()
            3 -> npc(FacialExpression.CHILD_NEUTRAL, "Grrrr!").also { stage++ }
            4 -> sendNPCDialogue(player, NPCs.CHIEF_TESS_7051, "Huh, huh! Me think Dawg like you now.", FacialExpression.CHILD_NORMAL).also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "He has a funny way of showing it!").also { stage = 2 }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DAWG_7104)
    }
}
