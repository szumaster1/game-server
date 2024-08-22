package content.region.asgarnia.dialogue.burthope

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Bernald dialogue.
 */
@Initializable
class BernaldDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.WORRIED, "Do you know anything about grapevine diseases?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "No, I'm afraid I don't.").also { stage++ }
            2 -> npcl(FacialExpression.GUILTY, "Oh, that's a shame. I hope I find someone soon, otherwise I could lose all of this year's crop.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BERNALD_2580)
    }
}

