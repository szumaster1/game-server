package content.region.kandarin.quest.waterfall.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.GameWorld.settings
import core.tools.END_DIALOGUE

/**
 * Represents the Hadley dialogue.
 */
class HadleyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        player(FacialExpression.HALF_GUILTY, "Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> if (player.inventory.contains(292, 1)) {
                npc(FacialExpression.HALF_GUILTY, "I hope you're enjoying your stay, there should be lots", "of useful information in that book: places to go, people to", "see.").also { stage = END_DIALOGUE }
            } else {
                npc(FacialExpression.HALF_GUILTY, "Are you on holiday? If so you've come to the right", "place. I'm Hadley the tourist guide, anything you need", "to know just ask me. We have some of the most unspoilt", "wildlife and scenery in " + settings!!.name + ".").also { stage++ }
            }
            1 -> npc(FacialExpression.HALF_GUILTY, "People come from miles around to fish in the clear lakes", "or to wander the beautiful hill sides.").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "It is quite pretty.").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Surely pretty is an understatement kind " + (if (player.isMale) "Sir. " else "Lady. ") + "Beautiful,", "amazing or possibly life-changing would be more suitable", "wording. Have you seen the Baxtorian waterfall?", "Named after the elf king who was buried underneath.").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "Thanks then, goodbye.").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "Enjoy your visit.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("hadley_dialogue"), NPCs.HADLEY_302)
    }

}