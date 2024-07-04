package content.region.kandarin.quest.merlinsquest.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class SirGawainDialogueFile : DialogueFile() {

    val STAGE_LE_FAYE_END = 20
    val STAGE_PROGRESS = 15

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_GAWAIN_240)

        when (stage) {
            0 -> {
                npcl(FacialExpression.NEUTRAL, "Good day to you " + (if (player!!.isMale) "sir" else "madam") + "!")

                if (getQuestStage(player!!, "Merlin's Crystal") == 0) {
                    stage = 1
                } else if (getQuestStage(player!!, "Merlin's Crystal") == 10) {
                    stage = 10
                } else if (getQuestStage(player!!, "Merlin's Crystal") == 20 || getQuestStage(player!!, "Merlin's Crystal") == 30) {
                    stage = 20
                } else if (getQuestStage(player!!, "Merlin's Crystal") >= 40) {
                    stage = 40
                }
            }
            1 -> {
                showTopics(
                        Topic(FacialExpression.NEUTRAL, "Good day.", END_DIALOGUE),
                        Topic(FacialExpression.NEUTRAL,"Know you of any quests sir knight?", 2))
            }
            2 -> {
                npcl(FacialExpression.NEUTRAL, "The king is the man to talk to if you want a quest.")
                stage = END_DIALOGUE
            }

            10 -> {
                showTopics(
                        Topic(FacialExpression.NEUTRAL, "Good day.", END_DIALOGUE),
                        Topic(FacialExpression.NEUTRAL, "Any ideas on how to get Merlin out of that crystal?", 11),
                        Topic(FacialExpression.NEUTRAL,"Do you know how Merlin got trapped?", STAGE_PROGRESS))
            }

            11 -> {
                npcl(FacialExpression.NEUTRAL, "I'm a little stumped myself. We've tried opening it with anything and everything!")
                stage = END_DIALOGUE
            }

            15 -> {
                npcl(FacialExpression.ANGRY,"I would guess this is the work of the evil Morgan Le Faye!")
                stage ++
            }
            16 -> {
                playerl(FacialExpression.NEUTRAL, "And where could I find her?")
                stage ++
            }
            17 -> {
                npc("She lives in her stronghold to the south of here,", "guarded by some renegade knights led by Sir Mordred.")
                setQuestStage(player!!, "Merlin's Crystal", 20)
                player!!.getQuestRepository().syncronizeTab(player)
                stage++
            }
            18 -> {
                showTopics(
                        Topic(FacialExpression.NEUTRAL, "Any idea how to get into Moran Le Faye's stronghold?", STAGE_LE_FAYE_END),
                        Topic(FacialExpression.NEUTRAL, "Thank you for the information.", 25))
            }

            STAGE_LE_FAYE_END -> {
                npcl(FacialExpression.NEUTRAL, "No, you've got me stumped there...")
                stage = END_DIALOGUE
            }

            25 -> {
                npcl(FacialExpression.NEUTRAL, "It is the least I can do.")
                stage = END_DIALOGUE
            }

            30 -> {
                showTopics(
                        Topic(FacialExpression.NEUTRAL, "Any idea how to get into Moran Le Faye's stronghold?", STAGE_LE_FAYE_END),
                        Topic(FacialExpression.NEUTRAL, "Hello again.", END_DIALOGUE))
            }

            40 -> {
                playerl(FacialExpression.NEUTRAL, "Any ideas on finding Excalibur?")
                stage++
            }
            41 -> {
                npcl(FacialExpression.NEUTRAL, "Unfortunately not, adventurer.")
                stage = END_DIALOGUE
            }

        }
    }

}
