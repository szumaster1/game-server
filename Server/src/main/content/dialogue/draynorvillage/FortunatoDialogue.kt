package content.dialogue.draynorvillage

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Fortunato dialogue.
 */
@Initializable
class FortunatoDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.ASKING, "Can I help you at all?").also { stage++ }
            1 -> showTopics(Topic(FacialExpression.FRIENDLY, "Yes, what are you selling?", 2), Topic(FacialExpression.FRIENDLY, "Not at the moment", 3),)
            2 -> {
                end()
                openNpcShop(player, NPCs.FORTUNATO_3671)
            }
            3 -> npcl(FacialExpression.ANGRY, "Then move along, you filthy ragamuffin, I have customers to serve!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FORTUNATO_3671)
    }

}
