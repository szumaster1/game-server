package content.dialogue.varrock.museum

import cfg.consts.NPCs
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Teacherand pupil museum dialogue.
 */
@Initializable
class TeacherandPupilMuseumDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        stage = if (npc.location.z == 0) {
            sendNPCDialogue(player, NPCs.TEACHER_5950, "Stop pulling, we've plenty of time to see everything.", FacialExpression.ANGRY)
            0
        } else {
            sendNPCDialogue(player, NPCs.SCHOOLGIRL_5951, "That man over there talks funny, miss.", FacialExpression.HALF_GUILTY)
            1
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendNPCDialogue(player, NPCs.SCHOOLGIRL_5951, "Aww, but miss, it's sooo exciting.", FacialExpression.CHILD_FRIENDLY).also { stage = END_DIALOGUE }
            1 -> sendNPCDialogue(player, NPCs.TEACHER_5950, "That's because he's an art critic, dear. They have some very funny ideas.", FacialExpression.HALF_GUILTY).also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TEACHER_AND_PUPIL_5947)
    }

}
