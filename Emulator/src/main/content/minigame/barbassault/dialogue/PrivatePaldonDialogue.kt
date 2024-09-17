package content.minigame.barbassault.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Private Paldon dialogue.
 */
@Initializable
class PrivatePaldonDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hi.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Shhh. Don't talk to me.").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY, "What? Why?").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "If I'm seen slacking off by talking to you, I'll be in deep trouble!").also { stage++ }
            3 -> playerl(FacialExpression.HALF_GUILTY, "Oh... Sorry.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRIVATE_PALDON_5034)
    }

}
