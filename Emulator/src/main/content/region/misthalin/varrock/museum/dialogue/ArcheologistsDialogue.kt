package content.region.misthalin.varrock.museum.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Archeologists dialogue.
 */
@Initializable
class ArcheologistsDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Archaeologists currently tasked by the
     * Varrock Museum with examining finds excavated
     * from the Digsite.
     * Location: 3266,3445
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HALF_GUILTY, "Hello there; I see you're qualified. Come to help us out?").also { stage++ }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.BARNABUS_HURMA_5932,
            NPCs.MARIUS_GISTE_5933,
            NPCs.CADEN_AZRO_5934,
            NPCs.THIAS_LEACKE_5935,
            NPCs.SINCO_DOAR_5936,
            NPCs.TINSE_TORPE_5937
        )
    }

}