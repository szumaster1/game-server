package content.dialogue.rellekka

import cfg.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Volf olasfson dialogue.
 */
@Initializable
class VolfOlasfsonDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npc(FacialExpression.ANNOYED, "Sorry, outlander, but I have things to be doing.").also { stage = END_DIALOGUE }
        } else if (isQuestComplete(player, "Fremennik Trials") && !isQuestComplete(player, "Olaf's Quest")) {
            npc(FacialExpression.FRIENDLY, "Hello there. Enjoying the view?").also { stage = 0 }
        } else if (isQuestComplete(player, "Fremennik Trials") && isQuestComplete(player, "Olaf's Quest")) {
            npcl(FacialExpression.ASKING, "Hello again, friend! Does my father send any word... or treasures like before?").also { stage = 2 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.FRIENDLY, "Yes I am. You have a lovely yurt.").also { stage++ }
            1 -> npc(FacialExpression.FRIENDLY, "Thanks! I exercise it regularly.").also { stage = END_DIALOGUE }
            2 -> playerl(FacialExpression.HALF_GUILTY, "Not today, but if he does, you will be the first to know.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VOLF_OLAFSON_3695)
    }
}
