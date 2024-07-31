package content.region.kandarin.dialogue.stronghold

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class HudoDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Hello there, traveller. Would you like some groceries? I have a large selection.").also { stage++ }
            1 -> options("I'll take a look.", "No, thank you.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "I'll take a look.").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "No, thank you.").also { stage = END_DIALOGUE }
            }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Great stuff.").also { stage++ }
            4 -> end().also { openNpcShop(player, NPCs.HUDO_600) }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HUDO_600)
    }
}
