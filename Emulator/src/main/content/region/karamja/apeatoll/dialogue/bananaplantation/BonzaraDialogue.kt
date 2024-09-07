package content.region.karamja.apeatoll.dialogue.bananaplantation

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Bonzara dialogue.
 */
@Initializable
class BonzaraDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_DEFAULT, "It looks like you're trying to escape. Would you like some help?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes", "No").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.WORRIED, "I ... uh ... yes.").also { stage = 10 }
                2 -> playerl(FacialExpression.ASKING, "No thank you. Who are you by the way?").also { stage = 20 }
            }
            10 -> npc(FacialExpression.OLD_DEFAULT, "Right you are.").also { stage++ }
            11 -> {
                end()
                //teleport to ape atoll
            }
            20 -> npcl(FacialExpression.OLD_DEFAULT, "Never mind that child. You should worry more about who you are and the nature of the forces that have driven you here.").also { stage++ }
            21 -> player(FacialExpression.THINKING, "I'll ... keep that in mind, thanks.").also { stage++ }
            22 -> npc(FacialExpression.OLD_DEFAULT, "We WILL meet again, " + player.name + ".").also { stage++ }
            23 -> player(FacialExpression.SUSPICIOUS, "Ok...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BONZARA_1468)
    }
}
