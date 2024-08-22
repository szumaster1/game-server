package content.region.misthalin.dialogue.varrock.museum

import cfg.consts.NPCs
import core.api.location
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Art critic jacques dialogue.
 */
@Initializable
class ArtCriticJacquesDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: art critic currently evaluating art in Misthalin,
     * particularly the capital city of Varrock.
     * Location: 3257,3454,2
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HALF_GUILTY, "I sit in the sky like a sphinx misunderstood").also { stage++ }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "I combine a heart of snow to the whiteness of swans;", "I hate the movement that moves the lines;", " ", "And I never cry and I never laugh.").also { stage++ }

            1 -> {
                end()
                npc.faceLocation(location(3257, 3455, 2))
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ART_CRITIC_JACQUES_5930)
    }

}