package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class HelemosDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Greetings. Welcome to the Heroes' Guild.")
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> options("So do you sell anything here?", "So what can I do here?").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_ASKING, "So do you sell anything good here?").also { stage++ }
                2 -> playerl(FacialExpression.HALF_ASKING, "So what can I do here?").also { stage = 5 }
            }
            3 -> npcl(FacialExpression.HAPPY, "Why yes! We DO run an exclusive shop for our members!").also { stage++ }
            4 -> {
                end()
                openNpcShop(player, NPCs.HELEMOS_797)
            }
            5 -> npcl(FacialExpression.HAPPY, "Look around... there are all sorts of things to keep our guild members entertained!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HELEMOS_797)
    }
}
