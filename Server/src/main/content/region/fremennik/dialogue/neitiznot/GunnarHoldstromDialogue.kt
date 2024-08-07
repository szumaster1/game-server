package content.region.fremennik.dialogue.neitiznot

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Gunnar holdstrom dialogue.
 */
@Initializable
class GunnarHoldstromDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HAPPY, "Ah, isn't it a lovely day?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.ASKING, "It's not bad. What puts you in such a good mood?").also { stage++ }
            1 -> npcl(FacialExpression.HAPPY, "Oh, I just love my job. The smell of the sea breeze, the smell of the arctic pine sap, the smell of the yaks. I find it all so bracing.").also { stage++ }
            2 -> playerl(FacialExpression.ASKING, "Bracing? Hmmm. I think I might have chosen a different word.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUNNAR_HOLDSTROM_5511)
    }

}
