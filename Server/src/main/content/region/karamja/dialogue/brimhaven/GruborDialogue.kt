package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Grubor dialogue.
 */
@Initializable
class GruborDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Grubor is a Black Arm Gang contact that the players
     * must meet to get some ID papers during the Heroes' Quest.
     * He is found in a small house to the east of the bar in Brimhaven.
     * Location: 2811,3167
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Yes? What do you want?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Would you like your hedges trimming?", "I want to come in.", "Do you want to trade?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Would you like your hedges trimming?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "I want to come in.").also { stage = 20 }
                3 -> player(FacialExpression.HALF_GUILTY, "Do you want to trade?").also { stage = 30 }
            }
            10 -> npc(FacialExpression.HALF_GUILTY, "Eh? Don't be daft! We don't even HAVE any hehdges!").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.HALF_GUILTY, "No, go away.").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.HALF_GUILTY, "No, I'm busy.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRUBOR_789)
    }
}
