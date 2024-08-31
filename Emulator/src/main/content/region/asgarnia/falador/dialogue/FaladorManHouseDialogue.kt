package content.region.asgarnia.falador.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Falador man house dialogue.
 */
@Initializable
class FaladorManHouseDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "What are you doing in my house?").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "I was just exploring.").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "You're exploring my house?").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "You don't mind, do you?").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "But... why are you exploring in my house?").also { stage++ }
            5 -> player(FacialExpression.HALF_GUILTY, "Oh, I don't know. I just wandered in, saw you and thought", "it'd be fun to speak to you.").also { stage++ }
            6 -> npc(FacialExpression.HALF_GUILTY, "... you are very strange...").also { stage++ }
            7 -> player(FacialExpression.HALF_GUILTY, "Perhaps I should go now.").also { stage++ }
            8 -> npc(FacialExpression.HALF_GUILTY, "Yes, please go away now.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAN_3223)
    }

}
