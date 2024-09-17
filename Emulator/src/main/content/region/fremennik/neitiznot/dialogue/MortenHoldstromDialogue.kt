package content.region.fremennik.neitiznot.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Morten Holdstrom dialogue.
 */
@Initializable
class MortenHoldstromDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npcl(FacialExpression.NEUTRAL, "Good day to you.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.ASKING, "Hello. What are you up to?").also { stage++ }
            1 -> npcl(FacialExpression.HAPPY, "Ah, today is a surströmming day! The herring I buried six months ago is ready to be dug up.").also { stage++ }
            2 -> playerl(FacialExpression.DISGUSTED, "Eughr! What are you going to do with it?").also { stage++ }
            3 -> npcl(FacialExpression.HAPPY, "Eat it, of course! It will be fermented just-right by now.").also { stage++ }
            4 -> playerl(FacialExpression.ASKING, "Fermented? You eat rotten fish?").also { stage++ }
            5 -> npcl(FacialExpression.HAPPY, "Hmmm, tasty. I'm guessing you don't want to come round and try it?").also { stage++ }
            6 -> playerl(FacialExpression.ANNOYED, "You guess correctly.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MORTEN_HOLDSTROM_5510)
    }

}
