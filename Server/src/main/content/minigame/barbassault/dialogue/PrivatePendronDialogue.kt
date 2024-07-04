package content.minigame.barbassault.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class PrivatePendronDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hi there.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> npcl(FacialExpression.HALF_GUILTY, "Don't suppose you've seen a battleaxe around here?").also { stage++ }
            2 -> playerl(FacialExpression.HALF_GUILTY, "A battleaxe? Nope, afraid not.").also { stage++ }
            3 -> npcl(FacialExpression.HALF_GUILTY, "The captain is going to kill me if he finds out I've lost my weapon.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRIVATE_PENDRON_5032)
    }

}
