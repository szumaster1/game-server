package content.region.asgarnia.whitewolfmountain.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Lakki dwarf dialogue.
 */
@Initializable
class LakkiDwarfDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Lakki is a delivery dwarf currently wandering
     * near his broken cart south of Ice Mountain.
     * He is the starting point for the Perils of Ice Mountain quest.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "I'm sorry, I can't talk right now.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LAKKI_THE_DELIVERY_DWARF_7722)
    }

}
