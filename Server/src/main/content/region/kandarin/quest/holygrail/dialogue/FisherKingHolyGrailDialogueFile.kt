package content.region.kandarin.quest.holygrail.dialogue

import content.region.kandarin.quest.holygrail.HolyGrail
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Fisher king holy grail dialogue file.
 *
 * Related to [Holy Grail][content.region.kandarin.quest.holygrail.HolyGrail] quest.
 * @author lostmyphat
 */
class FisherKingHolyGrailDialogueFile : DialogueFile() {

    var STAGE_SEEK_GRAIL = 10
    var STAGE_LOOK_WELL = 20
    var STAGE_HOW_KNOW = 2
    var STAGE_LOOK_AROUND = 5

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.THE_FISHER_KING_220)

        when (stage) {

            0 -> {
                if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) >= 50) {
                    npcl(FacialExpression.HAPPY, "You missed all the excitement!")
                    stage = 30
                } else {
                    npcl(FacialExpression.NEUTRAL, "Ah! You got inside at last! You spent all that time fumbling around outside, I thought you'd never make it here.")
                    stage++
                }
            }
            1 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "How did you know what I have been doing?", STAGE_HOW_KNOW),
                    Topic(FacialExpression.NEUTRAL, "I seek the Holy Grail.", STAGE_SEEK_GRAIL),
                    Topic(FacialExpression.NEUTRAL, "You don't look too well.", STAGE_LOOK_WELL))

            STAGE_HOW_KNOW -> npcl(FacialExpression.NEUTRAL, "Oh, I can see what is happening in my realm. I have sent clues to help you get here, such as the fisherman, and the crone.").also { stage++ }
            3 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "I seek the Holy Grail.", STAGE_SEEK_GRAIL),
                    Topic(FacialExpression.NEUTRAL, "You don't look too well.", STAGE_LOOK_WELL),
                    Topic(FacialExpression.NEUTRAL, "Do you mind if I have a look around?", STAGE_LOOK_AROUND))

            STAGE_LOOK_AROUND -> npcl(FacialExpression.NEUTRAL, "No, not at all. Please, be my guest.").also { stage = END_DIALOGUE }

            STAGE_SEEK_GRAIL -> npcl(FacialExpression.HAPPY, "Ah excellent. A knight come to seek the Holy Grail. Maybe our land can be restored to its former glory.").also { stage++ }
            11 -> npcl(FacialExpression.HAPPY, "At the moment the Grail cannot be removed from the castle. Legend has it a questing knight will one day work out how to restore our land; then he will claim the Grail as his prize.").also { stage++ }
            12 -> playerl(FacialExpression.NEUTRAL, "Any ideas how I can restore the land?").also { stage++ }
            13 -> npcl(FacialExpression.SAD, "None at all.").also { stage++ }
            14 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "You don't look too well.", STAGE_LOOK_WELL),
                    Topic(FacialExpression.NEUTRAL, "Do you mind if I have a look around?", STAGE_LOOK_AROUND))

            STAGE_LOOK_WELL -> npcl(FacialExpression.SAD, "Nope, I don't feel so good either.").also { stage++ }
            21 -> npcl(FacialExpression.SAD, "I fear my life is running short... Alas, my son and heir is not here. I am waiting for my son to return to this castle.").also { stage++ }
            22 -> npcl(FacialExpression.NEUTRAL, "If you could find my son, that would be a great weight off my shoulders.").also { stage++ }
            23 -> playerl(FacialExpression.NEUTRAL, "Who is your son?").also { stage++ }
            24 -> npcl(FacialExpression.NEUTRAL, "He is known as Percival.").also { stage++ }
            25 -> npcl(FacialExpression.NEUTRAL, "I believe he is a knight of the round table.").also { stage++ }
            26 -> playerl(FacialExpression.NEUTRAL, "I shall go and see if I can find him.").also {
                stage = END_DIALOGUE
                if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 30) {
                    setQuestStage(player!!, HolyGrail.HOLY_GRAIL, 40)
                }
            }

            30 -> npcl(FacialExpression.HAPPY, "I got here and agreed to take over duties as king here, then before my eyes the most miraculous changes occured here... grass and trees were growing outside before our very eyes!").also { stage++ }
            31 -> npcl(FacialExpression.HAPPY, "Thank you very much for showing me the way home.").also { stage = END_DIALOGUE }


        }
    }

}
