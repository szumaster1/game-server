package content.dialogue.neitiznot

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Kjedelig Uppsen dialogue.
 */
@Initializable
class KjedeligUppsenDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.NEUTRAL, "I'm guarding the king, I cannot speak.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.NEUTRAL, "Sorry.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KJEDELIG_UPPSEN_5518)
    }

}
