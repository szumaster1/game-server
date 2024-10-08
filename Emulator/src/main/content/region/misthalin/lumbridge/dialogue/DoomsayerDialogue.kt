package content.region.misthalin.lumbridge.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Doomsayer dialogue.
 */
@Initializable
class DoomsayerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Dooooom!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Where?").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "All around us! I can feel it in the air, hear it on the", "wind smell it....also in the air!").also { stage = 2 }
            2 -> player(FacialExpression.HALF_GUILTY, "Is there anything we can do about this doom?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "There is nothing you need to do my friend! I am the", "Doomsayer, although my real title could be something like", "the Danger Tutor.").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "Danger Tutor?").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "Yes! I roam the world sensing danger.").also { stage++ }
            6 -> npc(FacialExpression.HALF_GUILTY, "If I find a dangerous area, then I put up warning signs", "that will tell you what is so dangerous about that area.").also { stage++ }
            7 -> npc(FacialExpression.HALF_GUILTY, "If you see the signs often enough, then you can", "turn them off; by that time you likely know what the", "area has in store for you.").also { stage++ }
            8 -> player(FacialExpression.HALF_GUILTY, "But what if I want to see the warning again?").also { stage++ }
            9 -> npc(FacialExpression.HALF_GUILTY, "That's why I'm waiting here!").also { stage++ }
            10 -> npc(FacialExpression.HALF_GUILTY, "If you want to see the warning messages again, I", "can turn them back on for you.").also { stage++ }
            11 -> npc(FacialExpression.HALF_GUILTY, "Do you need to turn on any warning right now?").also { stage++ }
            12 -> options("Yes, I do.", "Not right now.").also { stage++ }
            13 -> when (buttonId) {
                1 -> end().also { player.warningMessages.open(player) }
                2 -> npc(FacialExpression.HALF_GUILTY, "Ok, keep an eye out for the messages though!").also { stage++ }
            }
            14 -> player(FacialExpression.HALF_GUILTY, " I will.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return DoomsayerDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DOOMSAYER_3777)
    }
}
