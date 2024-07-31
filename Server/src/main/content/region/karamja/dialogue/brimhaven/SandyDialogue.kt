package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SandyDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Sandy is the CEO of Sandy's Sand Corp.
     * His office is located just north of the bar in Brimhaven.
     * Location: 2789,3175
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Nice day for sand isn't it?").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(3110, NPCs.SANDY_3112, NPCs.SANDY_3113)
    }
}
