package content.region.desert.dialogue.alkharid

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class LouieLegsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hey, wanna buy some armour?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("What have you got?", "No, thank you.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.THINKING, "What have you got?").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "No, thank you.").also { stage = END_DIALOGUE }

            }
            2 -> npc(FacialExpression.HAPPY, "I provide items to help you keep your legs!").also { stage++ }
            3 -> {
                end()
                openNpcShop(player, NPCs.LOUIE_LEGS_542)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LOUIE_LEGS_542)
    }
}
