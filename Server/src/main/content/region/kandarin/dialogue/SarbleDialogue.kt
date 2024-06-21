package content.region.kandarin.dialogue

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class SarbleDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        sendDialogue(player, "This gnome lady appears to be singing ... to her toads.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "La la la, toadies in their holeys... Oh hello, I didn't notice you.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Hello, What do you do here?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "I look after the toads and worms. They are very useful in gnome cooking, don't you know.").also { stage++ }
            3 -> playerl(FacialExpression.NEUTRAL, "Does singing to the toads help them grow?").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Don't be silly, I just sing to pass the time while looking after them!").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Of course...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SARBLE_4599)
    }

}
