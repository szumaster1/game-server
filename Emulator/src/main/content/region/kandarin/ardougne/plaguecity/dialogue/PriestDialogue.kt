package content.region.kandarin.ardougne.plaguecity.dialogue

import core.api.isQuestComplete
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Priest dialogue (Default dialogue).
 */
@Initializable
class PriestDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if(isQuestComplete(player, QuestName.PLAGUE_CITY)) {
            npcl(FacialExpression.HAPPY, "Praise Saradomin! The Mourners are gone, and we can live free of the fear of plague!").also { stage = END_DIALOGUE }
        } else {
            playerl(FacialExpression.FRIENDLY, "Hello there.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "I wish there was more I could do for these people.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRIEST_358)
    }

}
