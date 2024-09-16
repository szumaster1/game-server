package content.region.kandarin.quest.grail.dialogue

import content.region.kandarin.quest.grail.HolyGrail
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Sir lucan holy grail quest dialogue file.
 */
class SirLucanHolyGrailQuestDialogueFile  : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_LUCAN_245)

        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Hello there adventurer.").also {
                if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 0 || isQuestComplete(player!!, HolyGrail.HOLY_GRAIL)) {
                    stage = 1
                } else if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) >= 10){
                    stage = 10
                }
            }
            1 -> npcl(FacialExpression.FRIENDLY, "Congratulations on freeing Merlin!").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "I seek the Grail of legend!").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "I'm afraid I don't have any suggestions...").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Thanks. I'll try and find someone who does.").also { stage = END_DIALOGUE }
        }
    }

}
