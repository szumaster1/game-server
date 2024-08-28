package content.dialogue.ooglog

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Snarrl dialogue.
 */
@Initializable
class SnarrlDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.CHILD_NORMAL, "Hi, human.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Hi, ogre.").also { stage++ }
            2 -> options("How are you today?", "Can you tell me about this salt-water spring?").also { stage++ }
            3 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_ASKING, "How are you today, little ogre?").also { stage++ }
                2 -> playerl(FacialExpression.HALF_ASKING, "Can you tell me about this copper-coloured pool?").also { stage = 8 }
            }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Me wanna go visit Fycie 'n Bugs!").also { stage++ }
            5 -> playerl(FacialExpression.HALF_ASKING, "Yes, they're both delightful individuals.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Will you take me to see dem, human?").also { stage++ }
            7 -> playerl(FacialExpression.HALF_ASKING, "Didn't your mother ever teach you not to talk to strangers?").also { stage = END_DIALOGUE }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah, sure, human! De water flows fast from underground. When you bathe here, it make you flow fast overground for long, long time.").also { stage++ }
            9 -> playerl(FacialExpression.HALF_ASKING, "Flow fast?").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "You know, flow on your feetsies. Fast-like. Quick, like a bunny!").also { stage++ }
            11 -> playerl(FacialExpression.HALF_ASKING, "Are you talking about running?").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah, run, flow - same thing. Have bath here, it not matter how heavy you are, how much you carry - you can flow long time!").also { stage++ }
            13 -> playerl(FacialExpression.HALF_ASKING, "Thanks, that's good to know.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SNARRL_7069, NPCs.CHOMP_7074)
    }
}
