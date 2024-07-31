package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class PhillipaDialogue(player: Player? = null) : Dialogue(player) {

    /*
       Info: Juliet's cousin. She attempted to help you save her and
       Romeo's relationship during the Romeo & Juliet quest.
       Location: 3161,3432,1
    */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello, who are you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Hi, I'm Phillipa! Juliet's cousin? I like to keep an eye on", "her, make sure that dashing young Romeo doesn't just", "steal away from here under our plain old noses!").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "He'd do it to you know... he's ever so dashing, and", "cavalier, in a wet blanket sort of way.").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "Romeo? Where would I find him then?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Well, that's a good question! Who knows where his", "head's at most of the time... in the clouds most likely!").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "But he's probably chasing the ladies who frequent", "Varrock market. He does like a bit of kiss chase so I've", "heard!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PHILLIPA_3325)
    }

}
