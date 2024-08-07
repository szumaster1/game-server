package content.region.asgarnia.dialogue.whitewolfmountain

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Grimgnash dialogue.
 */
@Initializable
class GrimgnashDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Grimgnash Skullrot is a griffin
     * featured in Grim Tales.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "What you want, little human? Grimgnash hungry. Want", "tasty morsel like you!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Like me? Why?  Who are you?").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "I Grimngnash and I hungry! Perhaps I eat you!").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "I'm really not that tasty. I think I should be going now.", "Goodbye.").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Human lucky Grimgnash too tired to hunt for food. Stupid", "wolves keep Grimgnsh awake with howling. Grimgnash", "can't sleep.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(5997)
    }
}
