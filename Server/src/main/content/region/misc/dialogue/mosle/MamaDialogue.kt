package content.region.misc.dialogue.mosle

import cfg.consts.Items
import cfg.consts.NPCs
import core.api.inInventory
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Mama dialogue.
 */
@Initializable
class MamaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hello!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> if (!inInventory(player, Items.BOOK_O_PIRACY_7144)) {
                npcl(FacialExpression.FRIENDLY, "Ar, darlin'! How might ya' Mama help ye?").also { stage++ }
            } else {
                npcl(FacialExpression.FRIENDLY, "Hello stranger, you come for a drink?").also { stage = 4 }
            }
            1 -> npcl(FacialExpression.FRIENDLY, "The powder monkey be takin' a caulk after gettin' rowdy on bumboo, so there be plenty of room for ye.").also { stage++ }
            2 -> player(FacialExpression.STRUGGLE, "Riiiiight...").also { stage++ }
            3 -> playerl(FacialExpression.STRUGGLE, "I'll just be over here if you need me.").also { stage = END_DIALOGUE }
            4 -> playerl(FacialExpression.HALF_ASKING, "What sort of drinks do you have?").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "We got some cold beers, some warm stews and some 'rum'.").also { stage++ }
            6 -> playerl(FacialExpression.HALF_ASKING, "Not, Captain Braindeath's 'rum'?").also { stage++ }
            7 -> npcl(FacialExpression.FRIENDLY, "Yes! There was some sorta problem at the brewery, but they got it all sorted out now.").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "You wanna buy something?").also { stage++ }
            9 -> options("Yes", "No").also { stage++ }
            10 -> {
                when (buttonId) {
                    1 -> {
                        openNpcShop(player, NPCs.MAMA_3164)
                        end()
                    }
                    2 -> player("Nevermind.").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAMA_3164)
    }
}
