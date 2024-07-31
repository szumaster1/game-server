package content.region.kandarin.dialogue.seersvillage

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class LegendsGuardDialogue(player: Player? = null) : Dialogue(player) {

    fun gender(male: String = "sir", female: String = "madam") = if (player.isMale) male else female

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "! ! ! Attention ! ! !").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Legends Guild Member Approaching").also { stage++ }
            1 -> npc(FacialExpression.FRIENDLY, "Welcome " + gender() + "!", "I hope you enjoy your time in the Legends Guild.").also { stage = END_DIALOGUE }
            //while doing legend's quest
            10 -> npc(FacialExpression.FRIENDLY, "I hope the quest is going well " + gender() + ".").also { stage = END_DIALOGUE }
            //after the legend's quest is done
            20 -> npc(FacialExpression.FRIENDLY, "Legends Guild Member Approaching!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LEGENDS_GUARD_398, NPCs.LEGENDS_GUARD_399)
    }
}
