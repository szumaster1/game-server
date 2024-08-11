package content.region.karamja.dialogue

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Zambo dialogue.
 */
@Initializable
class ZamboDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Zambo runs the Karamja Wines, Spirits,
     * and Beers bar on Musa Point on north-eastern Karamja.
     * Location: 2925,3143
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hey, are you wanting to try some of my fine wines", "and spririts? All brewed locally on Karamja island.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, thank you").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.ZAMBO_568)
                }
                2 -> player(FacialExpression.HALF_GUILTY, "No, thank you.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZAMBO_568)
    }
}
