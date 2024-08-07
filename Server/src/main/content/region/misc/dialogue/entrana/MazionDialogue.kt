package content.region.misc.dialogue.entrana

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Mazion dialogue.
 */
@Initializable
class MazionDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when ((1..3).random()) {
            1 -> npc(FacialExpression.FRIENDLY, "Nice weather we're having today!").also { stage = END_DIALOGUE }
            2 -> npc(FacialExpression.FRIENDLY, "Hello " + player.name + ", fine day today!").also { stage = END_DIALOGUE }
            3 -> npc(FacialExpression.ANNOYED, "Please leave me alone, a parrot stole my banana.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAZION_3114)
    }
}
