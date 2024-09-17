package content.region.asgarnia.taverley.dialogue

import org.rs.consts.NPCs
import core.api.getStatLevel
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Brian o richard dialogue.
 */
@Initializable
class BrianORichardDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Brian O'Richard is located in the Rogues' Den
     * underneath the Pick and Lute in Taverley.
     * He used to operate the Rogues' Den maze.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hi there, looking for a challenge are you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, actually, what've you got?", "What is this place?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes actually, what've you got?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "What is this place?").also { stage = 20 }
                3 -> player(FacialExpression.HALF_GUILTY, "No thanks.").also { stage = END_DIALOGUE }
            }
            10 -> if (getStatLevel(player, Skills.THIEVING) < 50) {
                end()
                npc(FacialExpression.HALF_GUILTY, "Shame, I don't think I have anything for you. Train up", "your Thieving skill to at least 50 and I might be able to", "help you out.")
            } else {
                end()
            }
            20 -> npc(FacialExpression.HALF_GUILTY, "Ah welcome to my humble home, well actually it belongs", "to mummies but she's getting on a bit so I look after", "the place for her.").also { stage++ }
            21 -> npc(FacialExpression.HALF_GUILTY, "So are you interested in a challenge?").also { stage++ }
            22 -> options( "Yes actually, what've you got?", "No thanks.").also { stage++ }
            23 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes actually, what've you got?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "No, thanks.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BRIAN_ORICHARD_2266)
    }

}
