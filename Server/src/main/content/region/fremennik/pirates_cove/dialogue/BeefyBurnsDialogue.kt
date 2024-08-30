package content.region.fremennik.pirates_cove.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Beefy burns dialogue.
 */
@Initializable
class BeefyBurnsDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_ASKING, "What are you cooking?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.LAUGH, "My speciality! What else could I be cooking?").also { stage++ }
            1 -> player(FacialExpression.THINKING, "Ok, and your speciality is...?").also { stage++ }
            2 -> npcl(FacialExpression.LAUGH, "Boiled shark guts with a hint of rosemary and a dash of squid ink.").also { stage++ }
            3 -> player(FacialExpression.FRIENDLY, "I think I'll stick to making my own food.").also { stage++ }
            4 -> npc(FacialExpression.FRIENDLY, "Your loss!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BEEFY_BURNS_4541)
    }
}
