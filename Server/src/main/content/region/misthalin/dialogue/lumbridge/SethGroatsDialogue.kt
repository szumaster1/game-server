package content.region.misthalin.dialogue.lumbridge

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import cfg.consts.NPCs

/**
 * Represents the Seth Groats dialogue.
 */
@Initializable
class SethGroatsDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Seth Groats is a farmer who lives on the north-east side
     * of Lumbridge (east of the river and north of the goblins).
     * Seth is the father of Gillie Groats, a young milkmaid in Lumbridge.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "M'arnin'... going to milk me cowsies!").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SETH_GROATS_452)
    }
}
