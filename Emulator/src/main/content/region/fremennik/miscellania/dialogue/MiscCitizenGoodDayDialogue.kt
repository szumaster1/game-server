package content.region.fremennik.miscellania.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Misc citizen good day dialogue.
 */
@Initializable
class MiscCitizenGoodDayDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Good day, Your Royal Highness.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALRIK_1381, NPCs.RUNOLF_3924, NPCs.BRODDI_1390, NPCs.EINAR_1380, NPCs.INGRID_3926, NPCs.RAGNAR_1379, NPCs.RAGNVALD_1392, NPCs.RANNVEIG_1386, NPCs.THORA_3927, NPCs.THORA_1387, NPCs.THORHILD_1382, NPCs.VALGERD_1388, NPCs.TJORVI_3925)
    }
}
