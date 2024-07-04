package content.region.asgarnia.dialogue.dwarvmines

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class RoladDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Oh, hello... do I know you?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_ASKING, "Ehm... well... my name is " + player.username + ", if that rings any bell?").also { stage++ }
            1 -> npc(FacialExpression.OLD_NORMAL, "No, never heard of you.").also { stage = END_DIALOGUE }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ROLAD_1841)
    }

}
