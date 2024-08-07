package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Garv dialogue.
 */
@Initializable
class GarvDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Garv guards the Black Arm Gang hideout in Brimhaven.
     * Only players who are aligned with the Black Arm can
     * infiltrate this hideout, which is done during the Heroes' Quest.
     * Location: 2773,3187
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello. What do you want?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Can I go in there?", "I want for nothing!").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Can I go in there?").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "I want for nothing!").also { stage = 3 }
            }
            2 -> npc(FacialExpression.HALF_GUILTY, "No. In there is private.").also { stage = END_DIALOGUE }
            3 -> npc(FacialExpression.HALF_GUILTY, "You're one of a very lucky few then.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GARV_788)
    }
}
