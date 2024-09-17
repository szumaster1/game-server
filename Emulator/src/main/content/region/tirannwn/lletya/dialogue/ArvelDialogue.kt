package content.region.tirannwn.lletya.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Arvel dialogue.
 */
@Initializable
class ArvelDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Elven citizens of Lletya. She is a member of the Ithell Clan.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.FRIENDLY, "Good day traveller. You are far from home, what brings you here?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I am a wandering " + if (player.isMale) "hero" else "heroine" + ". I come here in search of adventure.").also { stage++ }
            1 -> npc(FacialExpression.FRIENDLY, "Sounds ghastly, I just want to live in peace.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Unfortunately without people like me, peace doesn't last for long.").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "True, but then again most adventurers cause as much trouble as they put right.").also { stage++ }
            4 -> player(FacialExpression.FRIENDLY, "You've got a point there... Hmm...").also { stage++ }
            5 -> npc(FacialExpression.FRIENDLY, "Well, good day traveller. And always do the right thing.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ARVEL_2365)
    }

}
