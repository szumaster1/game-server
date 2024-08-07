package content.region.kandarin.dialogue.stronghold

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Rometti dialogue.
 */
@Initializable
class RomettiDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Hello, traveller. Have a look at my latest range of gnome fashion. Rometti is the ultimate label in gnome high-society.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Really.").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Pastels are all the rage this season.").also { stage++ }
            3 -> options("Okay then, let's have a look.", "I've no time for fashion.").also { stage++ }
            4 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Okay then, let's have a look.").also { stage = 6 }
                2 -> playerl(FacialExpression.NEUTRAL, "I've no time for fashion.").also { stage = 5 }
            }
            5 -> npcl(FacialExpression.OLD_NORMAL, "Hmm...I did wonder.").also { stage = END_DIALOGUE }
            6 -> end().also { openNpcShop(player, NPCs.ROMETTI_601) }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ROMETTI_601)
    }
}
