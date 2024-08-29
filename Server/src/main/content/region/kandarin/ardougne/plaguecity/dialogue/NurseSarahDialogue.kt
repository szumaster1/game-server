package content.region.kandarin.ardougne.plaguecity.dialogue

import content.region.kandarin.quest.biohazard.dialogue.NurseSarahDialogueFile
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
 * Represents the Nurse Sarah dialogue.
 */
@Initializable
class NurseSarahDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestInProgress(player, "Biohazard", 6, 7)) {
            end()
            openDialogue(player, NurseSarahDialogueFile())
        }
        playerl(FacialExpression.FRIENDLY, "Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Hello my dear, how are you feeling?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Hello my dear, how are you feeling?").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Well in that case I'd better get back to work. Take care.").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "You too.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NURSE_SARAH_373)
    }

}
