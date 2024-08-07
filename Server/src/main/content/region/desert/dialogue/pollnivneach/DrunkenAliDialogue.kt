package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Drunken ali dialogue.
 */
@Initializable
class DrunkenAliDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if(!isQuestComplete(player, "The Feud")){
            npcl(FacialExpression.DRUNK,"Ahh, a kind stranger. Get this old man another drink so that he may wet his throat and talk to you.").also { stage = END_DIALOGUE }
        } else {
            npcl(FacialExpression.DRUNK, "What were we talking about again? Yes yes, when I was a boy..... no that's not it.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DRUNKEN_ALI_1863)
    }

}
