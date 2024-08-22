package content.region.kandarin.dialogue.witchaven

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Col o niall dialogue.
 */
@Initializable
class ColONiallDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Hello. What can I do for you?").also { stage++ }
            1 -> player(FacialExpression.FRIENDLY, "Oh, I'm just wondering what you're doing.").also { stage++ }
            2 -> npc(FacialExpression.FRIENDLY, "A spot of fishing.").also { stage++ }
            3 -> player(FacialExpression.FRIENDLY, "That doesn't look much like a fishing rod.").also { stage++ }
            4 -> npc(FacialExpression.FRIENDLY, "That my friend, depends on what you're fishing for.").also { stage++ }
            5 -> player(FacialExpression.FRIENDLY, "And what would that be?").also { stage++ }
            6 -> npcl(FacialExpression.FRIENDLY, "A little of this, a little of that; the usual things.").also { stage++ }
            7 -> player(FacialExpression.FRIENDLY, "Have you caught much?").also { stage++ }
            8 -> npc(FacialExpression.FRIENDLY, "The odd bite here and there. Hmm.").also { stage++ }
            9 -> player(FacialExpression.FRIENDLY, "What?").also { stage++ }
            10 -> npcl(FacialExpression.FRIENDLY, "You look like a capable lad. Tell you what, when you've got a bit more experience under your belt, get yourself over to Falador.").also { stage++ }
            11 -> player(FacialExpression.FRIENDLY, "What's in Falador?").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "An old friend of mine. You'll find him sitting on a bench in Falador Park. See what he can do for you.").also { stage++ }
            13 -> player(FacialExpression.FRIENDLY, "What's his name? Who should I say sent me?").also { stage++ }
            14 -> npcl(FacialExpression.FRIENDLY, "None of that matters if you can find him and if you're ready my name isn't necessary.").also { stage++ }
            15 -> player(FacialExpression.FRIENDLY, "Oh right. I'll get going then. Goodbye.").also { stage++ }
            16 -> npc(FacialExpression.FRIENDLY, "Goodbye and good luck.").also { stage = END_DIALOGUE }
        }
        return true
    }
    override fun getIds(): IntArray {
        return intArrayOf(NPCs.COL_ONIALL_4872)
    }
}
