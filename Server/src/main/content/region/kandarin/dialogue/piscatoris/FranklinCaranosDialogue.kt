package content.region.kandarin.dialogue.piscatoris

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class FranklinCaranosDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.ASKING, "Oh, hello again. Want some ore?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Hello again, " + player.username + ".").also { stage++ }
            1 -> playerl(FacialExpression.ASKING, "Hello. How's the repair work going?").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "I'm working on it. I can always do with more iron sheets, so if you've got any more, I'll give you 20 gp per sheet.").also { stage++ }
            3 -> playerl(FacialExpression.NEUTRAL, "Thanks, I'll remember that.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FRANKLIN_CARANOS_3823)
    }

}
