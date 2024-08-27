package content.location.dorgeshuun

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Miltog dialogue.
 */
@Initializable
class MiltogDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_NORMAL, "Do you want to buy a lamp, surface-dweller?").also { stage++ }
            1 -> options("Yes please.", "No thanks.", "Can you fill a lamp with oil please?", "How do the lamps around the city work?").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_ASKING, "Yes please.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_ASKING, "No thanks.").also { stage = 4 }
                3 -> playerl(FacialExpression.HALF_ASKING, "Can you fill a lamp with oil please?").also { stage = 5 }
                4 -> playerl(FacialExpression.HALF_ASKING, "How do the lamps around the city work?").also { stage = 6 }
            }
            3 -> {
                end()
                openNpcShop(player, NPCs.MILTOG_5781)
            }
            4 -> npc(FacialExpression.OLD_NORMAL, "Suit yourself.").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.OLD_NORMAL, "You can do that yourself! Just put some swamp tar in the lamp oil still here and then use the lamp on the still.").also { stage = END_DIALOGUE }
            6 -> npcl(FacialExpression.OLD_NORMAL, "They work by our very own Dorgeshuun magic!").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL, "Sometimes they don't work very well, though. The light orbs blow and they have to be replaced.").also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "If you ever see a lamp that's gone out, you can fix it by replacing the light orb. You can make new light orbs out of glass, and use a piece of wire on them to add the filament.").also { stage++ }
            9 -> npcl(FacialExpression.OLD_NORMAL, "There's a machine in the south of the city that makes the wire you need.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MILTOG_5781)
    }

}
