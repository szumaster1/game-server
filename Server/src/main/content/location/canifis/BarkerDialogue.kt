package content.location.canifis

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Barker dialogue.
 */
@Initializable
class BarkerDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Barker's Haberdashery is a clothes shop that
     * sells a variety of clothing sets. It is located in
     * the north-east of Canifis.
     * Location: 3500,3505
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HAPPY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HAPPY, "You are looking for clothes, yes? You look at my", "products! I have very many nice clothes, yes?").also { stage++ }
            1 -> options("Yes, please.", "No, thanks.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes, please.").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "No thanks.").also { stage = 4 }
            }
            3 -> {
                end()
                openNpcShop(player, NPCs.BARKER_1039)
            }
            4 -> npc(FacialExpression.HALF_GUILTY, "Unfortunate for you, yes?", "Many bargains, won't find elsewhere!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return BarkerDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARKER_1039)
    }

}
