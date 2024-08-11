package content.region.asgarnia.dialogue.portsarim

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Port Sarim brian dialogue.
 */
@Initializable
class PortSarimBrianDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Brian is a shop owner who runs Brian's Battleaxe Bazaar in Port Sarim.
     * He sells battleaxes, from bronze to adamant. He also plays a small role
     * in the One Small Favour quest, agreeing to sharpen the jungle forester's
     * axe if Jimmy Chisel is returned to him.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options("So, are you selling something?", "'Ello.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "So, are you selling something?").also { stage++ }
                2 -> player(FacialExpression.HAPPY, "'Ello.").also { stage = 3 }
            }
            1 -> npc(FacialExpression.HAPPY, "Yep, take a look at these great axes.").also { stage++ }
            2 -> end().also { openNpcShop(player, npc.id) }
            3 -> npc(FacialExpression.HAPPY, "'Ello.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BRIAN_559)
    }

}
