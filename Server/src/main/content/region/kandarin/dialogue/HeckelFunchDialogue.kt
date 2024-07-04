package content.region.kandarin.dialogue

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class HeckelFunchDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Good day to you, my friend, and a beautiful one at that. Would you like some groceries? I have all sorts. Alcohol also, if you're partial to a drink.").also { stage++ }
            1 -> options("I'll have a look.", "No, thank you.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "I'll have a look.").also { stage = 4 }
                2 -> playerl(FacialExpression.NEUTRAL, "No, thank you.").also { stage = 3 }
            }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Ahh well, all the best to you.").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.OLD_NORMAL, "There's a good human.").also { stage++ }
            5 -> {
                end()
                openNpcShop(player, NPCs.HECKEL_FUNCH_603)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HECKEL_FUNCH_603)
    }
}
