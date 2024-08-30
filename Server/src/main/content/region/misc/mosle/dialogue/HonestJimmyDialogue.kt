package content.region.misc.mosle.dialogue

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
 * Represents the Honest jimmy dialogue.
 */
@Initializable
class HonestJimmyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "You here for the...stuff?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> if (!inInventory(player, Items.BOOK_O_PIRACY_7144)) {
                npcl(FacialExpression.FRIENDLY, "Arr? Be ye wantin' te go on account with our gang o' fillibusters?").also { stage = 1 }
            } else {
                playerl(FacialExpression.ASKING, "What stuff?").also { stage = 4 }
            }
            1 -> npcl(FacialExpression.FRIENDLY, "The powder monkey be takin' a caulk after gettin' rowdy on bumboo, so there be plenty of room for ye.").also { stage++ }
            2 -> player(FacialExpression.STRUGGLE, "Riiiiight...").also { stage++ }
            3 -> playerl(FacialExpression.STRUGGLE, "I'll just be over here if you need me.").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.NEUTRAL, "Look pal I got the goods if you have the cash.").also { stage++ }
            5 -> npcl(FacialExpression.NEUTRAL, "We talkin' the same language yet?").also { stage++ }
            6 -> options("Yes, I am here for the stuff.", "I have no idea what you are talking about.").also { stage++ }
            7 -> when (buttonId) {
                1 -> {
                    openNpcShop(player, NPCs.HONEST_JIMMY_4362)
                    end()
                }
                2 -> playerl(FacialExpression.STRUGGLE, "I have no idea what you are talking about.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HONEST_JIMMY_4362)
    }

}

