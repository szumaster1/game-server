package content.region.misc.zanaris.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Gatekeeper dialogue.
 */
@Initializable
class GateKeeperDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player("What happened to the old man who used to", "be the doorman?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_CALM_TALK1, "You mean my father? He went into retirement.", "I've taken over the family business.").also { stage++ }
            1 -> player("Your father? But you don't look anything like him!").also { stage++ }
            2 -> npc(FacialExpression.OLD_CALM_TALK1, "No. Fortunately for me, I inherited my good looks from", "my mother.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GATEKEEPER_3307)
    }

}
