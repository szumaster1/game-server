package content.region.misc.zanaris.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Coordinator dialogue.
 */
@Initializable
class CoordinatorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello, what are you doing?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when ((1..5).random()) {
                1 -> npcl(FacialExpression.OLD_DISTRESSED, "Sorry, I don't have time for idle chit-chat, I need to find a Winter Fairy to send to Trollheim!").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.OLD_DISTRESSED, "Sorry, I don't have time for idle chit-chat, I need to send a fairy to get little Freddie's tooth!").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.OLD_DISTRESSED, "Sorry, I don't have time for idle chit-chat, I need to send an Autumn Fairy off to Burthorpe!").also { stage = END_DIALOGUE }
                4 -> npcl(FacialExpression.OLD_DISTRESSED, "Sorry, I don't have time to talk, I need to send a Tooth Fairy to visit Sarah-Jane!").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.OLD_DISTRESSED, "Sorry, I don't have time to stop, I need to send a weather fairy off to Etceteria!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CO_ORDINATOR_3302)
    }

}
