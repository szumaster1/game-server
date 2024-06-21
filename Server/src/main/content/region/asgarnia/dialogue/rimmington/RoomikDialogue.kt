package content.region.asgarnia.dialogue.rimmington

import core.api.consts.NPCs
import core.api.openNpcShop
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class RoomikDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Would you like to buy some Crafting equipment?").also { stage = 0 }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(player, "Choose an option:", "Let's see what you've got, then.", "No thanks, I've got all the crafting equipment I need.").also { stage++ }
            1 -> when (buttonId) {
                1 -> end().also { openNpcShop(player, NPCs.ROMMIK_585)}
                2 -> player(FacialExpression.HALF_GUILTY, "No thanks, I've got all the crafting equipment I need.").also { stage++ }
            }
            2 -> npc(FacialExpression.FRIENDLY, "Okay. Fare well on your travels.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ROMMIK_585)
    }

}
