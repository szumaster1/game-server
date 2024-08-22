package content.region.kandarin.quest.holygrail.dialogue

import content.region.kandarin.quest.holygrail.HolyGrail
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Sir gawain holy grail quest dialogue file.
 */
class SirGawainHolyGrailQuestDialogueFile  : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_GAWAIN_240)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Good day to you sir!").also {
                if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 0 || isQuestComplete(player!!, HolyGrail.HOLY_GRAIL)) {
                    stage = 1
                } else if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) >= 10) {
                    stage = 10
                }
            }
            1 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "Good day.", END_DIALOGUE),
                    Topic(FacialExpression.NEUTRAL, "Know you of any quests sir knight?", 5))

            5 -> npcl(FacialExpression.NEUTRAL, "I think you've done the main quest we were on right now...").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.NEUTRAL, "I seek the Grail in the name of Camelot!").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "The Grail? That is truly a noble quest indeed. None but Galahad have come close.").also { stage++ }
            12 -> playerl(FacialExpression.NEUTRAL, "Galahad? Who is he?").also { stage++ }
            13 -> npcl(FacialExpression.NEUTRAL, "He used to be one of the Knights of the Round Table, but he mysteriously disappeared many years ago.").also { stage++ }
            14 -> playerl(FacialExpression.NEUTRAL, "Why would he quit being a Knight?").also { stage++ }
            15 -> npcl(FacialExpression.NEUTRAL, "That is a good question.").also { stage++ }
            16 -> npcl(FacialExpression.NEUTRAL, "I'm afraid I don't have the answer.").also { stage = END_DIALOGUE }

        }
    }

}
