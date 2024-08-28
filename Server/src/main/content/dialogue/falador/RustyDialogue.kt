package content.dialogue.falador

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Rusty dialogue.
 */
@Initializable
class RustyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hiya. Are you carrying anything valuable?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Why are you asking?").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "Um... It's a quiz. I'm asking everyone I meet if they're", "carrying anything valuable.").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "What would you do if I said I had loads of expensive items", "with me?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Ooh, do you? It's been ages since anyone said they'd got", "anything worth stealing.").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "'Anything worth stealing'?").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "Um... Not that I'd dream of stealing anything!").also { stage++ }
            6 -> player(FacialExpression.HALF_GUILTY, "Well, I'll say I'm not carrying anything valuable at all.").also { stage++ }
            7 -> npc(FacialExpression.HALF_GUILTY, "Oh, what a shame.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RUSTY_3239)
    }
}
