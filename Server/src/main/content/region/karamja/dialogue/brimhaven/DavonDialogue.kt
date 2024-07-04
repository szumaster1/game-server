package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class DavonDialogue(player: Player? = null): Dialogue(player) {

    /*
        Davon is a trader found standing along the southern
        shore of Brimhaven towards the fence.
        He runs Davon's Amulet Store.
        Location: 2803,3152
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.SUSPICIOUS, "Pssst! Come here if you want to do some amulet", "trading.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What are you selling?", "What do you mean pssst?", "Why don't you ever restock some types of amulets?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "What are you selling?").also { stage = 10 }
                2 -> player(FacialExpression.SUSPICIOUS, "What do you mean pssst?").also { stage = 20 }
                3 -> player(FacialExpression.HALF_ASKING, "Why don't you ever restock some types of amulets?").also { stage = 30 }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.DAVON_588)
            }
            20 -> npc(FacialExpression.SUSPICIOUS, "Errr, I was...I was clearing my throat.").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.HALF_GUILTY, "Some of these amulets are very hard to get. I have to", "wait until an adventurer supplies me.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DAVON_588)
    }
}
