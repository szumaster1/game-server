package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Frawd dialogue.
 */
@Initializable
class FrawdDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.CHILD_NORMAL, "What you want, human?").also { stage++ }
            1 -> options("So what do you have for sale, then?", "Never mind.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "So what do you have for sale, then?").also { stage = 3 }
                2 -> playerl(FacialExpression.FRIENDLY, "Never mind.").also { stage = END_DIALOGUE }
            }
            3 -> {
                end()
                openNpcShop(player, NPCs.FRAWD_7048)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FRAWD_7048)
    }
}

//Talking to her
//Frawd:
//Go away, human. Can't you see me busy here?
//Player:
//Oh, well, excuse me! My mistake entirely! Why would I ever think that someone standing behind a cash register might be interested in helping me?
//Trade option
//Frawd:
//Go away, human. We not open yet!
//Player:
//Well, excuse me!
