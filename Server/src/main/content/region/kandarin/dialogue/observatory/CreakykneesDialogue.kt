package content.region.kandarin.dialogue.observatory

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Creakyknees dialogue.
 */
@Initializable
class CreakykneesDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_ASKING, "Where did you get that lens?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL, "From that strange metal thing up on the hill.").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "You should give that back!").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL,"Even if it's cracked?").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Ah, well, I suppose it's of no use. But, still.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CREAKYKNEES_6129)
    }

}