package content.region.kandarin.quest.holygrail.dialogue

import content.region.kandarin.quest.holygrail.HolyGrail
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.tools.END_DIALOGUE

/**
 * Merlin holy grail quest dialogue file.
 */
class MerlinHolyGrailQuestDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MERLIN_249)

        when (stage) {
            0 -> {
                if (isQuestComplete(player!!, HolyGrail.HOLY_GRAIL)) {
                    npcl(FacialExpression.NEUTRAL, "Congratulations, brave knight, on aiding Camelot in so many ways! If we ever require help again, I will make sure to call upon you!")
                    stage = 20
                } else if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) >= 50 && player!!.hasItem(Item(Items.HOLY_GRAIL_19, 1))) {
                    npcl(FacialExpression.NEUTRAL, "My magic powers tell me that you have discovered the Grail! Take it to Arthur immediately!")
                    stage = END_DIALOGUE
                } else {
                    playerl(FacialExpression.NEUTRAL, "Hello. King Arthur has sent me on a quest for the Holy Grail. He thought you could offer some assistance.").also { stage++ }
                }
            }

            1 -> npcl(FacialExpression.NEUTRAL, "Ah yes... the Holy Grail...").also {
                if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) >= 10) {
                    setQuestStage(player!!, HolyGrail.HOLY_GRAIL, 20)
                }
                stage++
            }

            2 -> npcl(FacialExpression.NEUTRAL, "That is a powerful artefact indeed. Returning it here would help Camelot a lot.").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "Due to its nature the Holy Grail is likely to reside in a holy place.").also { stage++ }
            4 -> playerl(FacialExpression.NEUTRAL, "Any suggestions?").also { stage++ }
            5 -> npcl(FacialExpression.NEUTRAL, "I believe there is a holy island somewhere not far away... I'm not entirely sure... I spent too long inside that crystal! Anyway, go and talk to someone over there.").also { stage++ }
            6 -> npcl(FacialExpression.NEUTRAL, "I suppose you could also try speaking to Sir Galahad?").also { stage++ }
            7 -> npcl(FacialExpression.NEUTRAL, "He returned from the quest many years after everyone else. He seems to know something about it, but he can only speak about those experiences cryptically.").also { stage++ }
            8 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "Thank you for the advice.", END_DIALOGUE),
                    Topic(FacialExpression.NEUTRAL, "Where can I find Sir Galahad?", 15))

            15 -> npcl(FacialExpression.NEUTRAL, "Galahad now lives a life of religious contemplation. He lives somewhere west of McGrubor's Wood I think.").also { stage = END_DIALOGUE }

            20 -> playerl(FacialExpression.NEUTRAL, "Thanks!").also { stage = END_DIALOGUE }

        }
    }

}
