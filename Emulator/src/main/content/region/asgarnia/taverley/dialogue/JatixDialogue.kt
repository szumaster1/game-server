package content.region.asgarnia.taverley.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jatix dialogue.
 */
@Initializable
class JatixDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Jatix is the herblore skill tutor and the owner of Jatix's Herblore Shop,
     * a Herblore shop in eastern Taverley.
     * His name ends with "-ix", which is a reference to the Asterix comic books.
     * Sources: https://runescape.wiki/w/Jatix%27s_Herblore_Shop?oldid=1772601
     */

    override fun newInstance(player: Player): Dialogue {
        return JatixDialogue(player)
    }

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello, how can I help you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What are you selling?", "You can't; I'm beyond help.", "I'm okay, thank you.").also { stage++ }
            1 -> when(buttonId){
                1 -> end().also { openNpcShop(player, NPCs.JATIX_587) }
                2 -> player(FacialExpression.NEUTRAL, "You can't; I'm beyond help.").also { stage = END_DIALOGUE }
                3 -> player("I'm okay, thank you.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JATIX_587)
    }

}
