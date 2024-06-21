package content.region.kandarin.dialogue.ardougne

import content.region.kandarin.quest.biohazard.dialogue.JericoDialogueFile
import core.api.consts.NPCs
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class JericoDialogue(player: Player? = null) : Dialogue(player) {

    /*
        Jerico is a pigeon enthusiast living between
        the chapel and the northern bank of East Ardougne.
        Location: 2612,3324
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestInProgress(player, "Biohazard", 1, 100)) {
            end().also { openDialogue(player, JericoDialogueFile()) }
        } else {
            npcl(FacialExpression.SUSPICIOUS, "Hello.").also { stage = 0 }
            stage = 0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Can I help you?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Just passing by.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JERICO_366)
    }
}
