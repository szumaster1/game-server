package content.region.tirannwn.dialogue.lletya

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Eoin dialogue.
 */
@Initializable
class EoinDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Info: Elven child in the lodge of Lletya.
     * He is a member of the Ithell Clan.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Sorry, I cannot stop or Iona will catch me, we are playing tag!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EOIN_2368)
    }

}
