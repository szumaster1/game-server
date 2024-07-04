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
class HorvikDialogue(player: Player? = null) : Dialogue(player) {

    /*
       Info: Horvik Ravitz owns Horvik's Armour Shop in central Varrock.
       He sells platebodies, chainbodies, med helms, square shields, platelegs and plateskirts.
       Location: 3229,3438 | Shop ID: 129
    */
    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hello, do you need any help?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Do you want to trade?", "No, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.HORVIK_549)
                }
                2 -> player(FacialExpression.FRIENDLY, "No, thanks. I'm just looking around.").also { stage++ }
            }
            2 -> npcl(FacialExpression.HAPPY, "Well, come and see me if you're ever in need of armour!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HORVIK_549)
    }

}
