package content.region.desert.sophanem.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Embalmer dialogue.
 */
@Initializable
class EmbalmerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.SUSPICIOUS, "What are you doing?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I have this acne potion which I thought might help ease your itching.").also { stage++ }
            1 -> npcl(FacialExpression.ANNOYED, "If I thought that these spots could be cured by some potion, I would have mixed up one myself before now.").also { stage++ }
            2 -> player(FacialExpression.SAD, "Sorry, I was just trying to help.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EMBALMER_1980)
    }
}
