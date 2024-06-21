package content.region.karamja.dialogue

import core.api.consts.NPCs
import core.api.hasRequirement
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class TiadecheDialogue(player: Player? = null) : Dialogue(player) {

    private val randomConversation = arrayOf("Just leave a depressed fish hunter alone...", "I'll never be able to catch a Karambwan...", "Go away! Can't you see I'm concentrating?")

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!hasRequirement(player, "Tai Bwo Wannai Trio", false)) {
            npcl(FacialExpression.NEUTRAL, randomConversation.random()).also { stage = END_DIALOGUE }
        } else {
            player("Hello, Tiadeche.").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Hello, Bwana! Would you like to buy some Karambwan?").also { stage++ }
            1 -> options("Yes", "No.").also { stage++ }
            2 -> when (buttonId) {
                1 -> end().also { openNpcShop(player, npc.id) }
                2 -> npc(FacialExpression.FRIENDLY, "Very well. Let me know if you change your mind.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TIADECHE_1164) // WRAPPER_ID 2483 for 1163
    }

}
