package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class LoweDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Owner of Lowe's Archery Emporium,
     *  found in Varrock just east of the general store.
     *  Location: 3232,3423
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Welcome to Lowe's Archery Emporium. Do you want", "to see my wares?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, I prefer to bash things close up.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, please.").also { stage = 2 }
                2 -> player(FacialExpression.EVIL_LAUGH, "No, I prefer to bash things close up.").also { stage = 3 }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.LOWE_550)
            }
            3 -> npc(FacialExpression.ANNOYED, "Humph, philistine.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LOWE_550)
    }

}
