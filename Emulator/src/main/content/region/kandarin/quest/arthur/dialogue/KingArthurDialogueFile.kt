package content.region.kandarin.quest.arthur.dialogue
import cfg.consts.NPCs
import core.api.finishQuest
import core.api.getQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the King Arthur dialogue file.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.arthur.MerlinCrystal] quest.
 * @author lostmyphat
 */
class KingArthurDialogueFile : DialogueFile() {

    val STAGE_MERLIN_FINISH = 20
    val STAGE_STARTED_QUEST = 30

    override fun handle(componentID: Int, buttonID: Int) {
        val quest = player!!.getQuestRepository().getQuest("Merlin's Crystal")
        npc = NPC(NPCs.KING_ARTHUR_251)

        when (stage) {
            0 -> {
                if (quest.getStage(player) == 60) {
                    playerl(FacialExpression.NEUTRAL, "I have freed Merlin from his crystal!").also { stage++ }
                    stage = STAGE_MERLIN_FINISH
                } else {
                    npcl(FacialExpression.NEUTRAL, "Welcome to my court. I am King Arthur.")
                    stage++
                }
            }

            1 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "I want to become a Knight of the Round Table!", 2),
                    Topic(FacialExpression.NEUTRAL, "So what are you doing in Gielinor?", 10),
                    Topic(FacialExpression.NEUTRAL, "Thank you very much.", END_DIALOGUE))

            // option 1
            2 -> {
                if (getQuestStage(player!!, "Merlin's Crystal") > 0) {
                    npcl(FacialExpression.NEUTRAL, "Well then you must complete your quest to rescue Merlin. Talk to my knights if you need any help.").also { stage = END_DIALOGUE }
                } else {
                    npcl(FacialExpression.NEUTRAL, "Well, in that case I think you need to go on a quest to prove yourself worthy.")
                    stage++
                }
            }
            3 -> {
                npcl(FacialExpression.NEUTRAL,"My knights all appreciate a good quest.")
                stage++
            }
            4 -> {
                npcl(FacialExpression.DISGUSTED, "Unfortunately, our current quest is to rescue Merlin.")
                stage++
            }
            5 -> {
                npcl(FacialExpression.THINKING, "Back in England, he got himself trapped in some sort of magical Crystal. We've moved him from the cave we found him in and now he's upstairs in his tower.")
                stage++
            }
            6 -> {
                playerl(FacialExpression.NEUTRAL, "I will see what I can do then.")
                quest.start(player)
                stage++
            }
            7 -> {
                npcl(FacialExpression.NEUTRAL, "Talk to my knights if you need any help.")
                stage = END_DIALOGUE
            }

            // option 2
            10 -> npcl(FacialExpression.NEUTRAL, "Well legend says we will return to Britain in its time of greatest need. But that's not for quite a while yet.").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "So we've moved the whole outfit here for now.").also { stage++ }
            12 -> npcl(FacialExpression.NEUTRAL, "We're passing the time in Gielinor!").also { stage = END_DIALOGUE }

            20 -> {
                npcl(FacialExpression.NEUTRAL, "Ah, A good job, well done. I dub thee a Knight Of The Round Table. You are now an honorary knight.")
                stage++
            }
            21 -> {
                end()
                finishQuest(player!!, "Merlin's Crystal")
            }

        }
    }
}
