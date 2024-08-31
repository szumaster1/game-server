package content.region.kandarin.ardougne.dialogue

import content.region.kandarin.ardougne.quest.biohazard.dialogue.JericoDialogueFile
import cfg.consts.NPCs
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jerico dialogue.
 */
@Initializable
class JericoDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Jerico is a pigeon enthusiast living between
     * the chapel and the northern bank of East Ardougne.
     * Location: 2612,3324
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestInProgress(player, "Biohazard", 1, 100)) {
            end().also { openDialogue(player, JericoDialogueFile()) }
        } else {
            npcl(FacialExpression.SUSPICIOUS, "Hello.")
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
