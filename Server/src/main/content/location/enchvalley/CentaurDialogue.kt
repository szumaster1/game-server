package content.location.enchvalley

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Centaur dialogue.
 */
@Initializable
class CentaurDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when(npc.id) {
            4438 -> npc(FacialExpression.OLD_HAPPY, "Hello, human, welcome to our valley.").also { stage = END_DIALOGUE }
            else -> npc(FacialExpression.OLD_LAUGH1, "What a funny creature you are! Why, you", "only have 2 legs!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CENTAUR_4438, NPCs.CENTAUR_4439)
    }
}
