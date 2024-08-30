package content.region.misthalin.stronghold.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Local gnomes dialogue.
 */
@Initializable
class LocalGnomesDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello little man.")
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Little man stronger than big man. Hee hee, lardi dee, lardi da.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LOCAL_GNOME_484)
    }
}
