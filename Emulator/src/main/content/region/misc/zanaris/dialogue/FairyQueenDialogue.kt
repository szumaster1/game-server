package content.region.misc.zanaris.dialogue

import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Fairy Queen dialogue.
 */
@Initializable
class FairyQueenDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fairytale II - Cure a Queen")) {
            options("How do crops and such survive down here?", "What's so good about this place?")
        } else {
            playerl(FacialExpression.ASKING, "Have you managed to work out a plan yet?").also { stage = 3 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> playerl(FacialExpression.ASKING, "How do crops and such survive down here?").also { stage++ }
                2 -> playerl(FacialExpression.ASKING, "What's so good about this place?").also { stage = 2 }
            }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "Clearly you come from a plane dependent on sunlight. Down here, the plants grow in the aura of faerie.").also { stage = END_DIALOGUE }
            2 -> npcl(FacialExpression.OLD_DEFAULT, " Zanaris is a meeting point of cultures. Those from many worlds converge here to exchange knowledge and goods.").also { stage = END_DIALOGUE }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "Not yet, " + player.username + ", but it looks like we'll need to ask you for your help again, I'm afraid. I'll be able to tell you more once we have finished making our battle plans.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FAIRY_QUEEN_4437)
    }

}
