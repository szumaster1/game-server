package content.region.misc.zanaris.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jukat dialogue.
 */
@Initializable
class JukatDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_DEFAULT, "Dragon swords! Here, dragon swords! Straight from Frenaskrae!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No thanks, I'm just browsing.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.JUKAT_564)
                }
                2 -> player("No thanks, I'm just browsing.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JUKAT_564)
    }
}
