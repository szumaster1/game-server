package content.region.kandarin.quest.merlinsquest.dialogue

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.removeItem
import core.api.sendItemDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.diary.AchievementDiary
import core.game.node.entity.player.link.diary.DiaryType
import core.tools.END_DIALOGUE

/**
 * Sir kay dialogue file.
 */
class SirKayDialogueFile : DialogueFile() {

    var STAGE_NOT_STARTED_MERLIN = 4
    var STAGE_UNFORTUNATELY_NOT = 5
    var STAGE_GET_MERLIN_OUT = 10
    var STAGE_MORDRED = 6
    var diaryLevel = 2

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_KAY_241)

        when (stage) {
            0 -> {
                options("Hello.", "Talk about achievement diary.")
                stage = 1
            }

            1 -> {
                if (buttonID == 1) {
                    playerl(FacialExpression.NEUTRAL, "Hello.")
                    stage++
                } else {
                    if (AchievementDiary.canReplaceReward(player!!, DiaryType.SEERS_VILLAGE, diaryLevel)) {
                        player("I seem to have lost my seers' headband...")
                        stage = 35
                    } else if (AchievementDiary.hasClaimedLevelRewards(player!!, DiaryType.SEERS_VILLAGE, diaryLevel)) {
                        player("Can you remind me what my headband does?")
                        stage = 40
                    } else if (AchievementDiary.canClaimLevelRewards(player!!, DiaryType.SEERS_VILLAGE, diaryLevel)) {
                        player("Greetings, Sir Kay. I have completed all of the Hard", "tasks in my Achievement Diary. May I have a reward?")
                        stage = 45
                    } else {
                        playerl(FacialExpression.NEUTRAL, "Hi! Can you help me out with the Achievement Diary tasks?")
                        stage = 60
                    }
                }
            }

            2 -> {
                npcl(FacialExpression.NEUTRAL, "Good morrow " + (if (player!!.isMale) "sirrah" else "madam") + "!")
                stage++

            }
            3 -> {
                if (getQuestStage(player!!, "Merlin's Crystal") == 0) {
                    playerl(FacialExpression.NEUTRAL, "Morning. Know where an adventurer has to go to find a quest around here?")
                    stage = STAGE_NOT_STARTED_MERLIN
                } else if (getQuestStage(player!!, "Merlin's Crystal") == 10) {
                    playerl(FacialExpression.NEUTRAL, "Any ideas on getting Merlin out of that crystal?")
                    stage = STAGE_GET_MERLIN_OUT
                } else if (getQuestStage(player!!, "Merlin's Crystal") == 20 || getQuestStage(player!!, "Merlin's Crystal") == 30) {
                    playerl(FacialExpression.NEUTRAL, "Any ideas on getting into Mordred's fort?")
                    stage = STAGE_MORDRED
                } else if (getQuestStage(player!!, "Merlin's Crystal") >= 40) {
                    playerl(FacialExpression.NEUTRAL, "Any ideas on finding Excalibur?")
                    stage = STAGE_UNFORTUNATELY_NOT
                }
            }

            STAGE_NOT_STARTED_MERLIN -> {
                npcl(FacialExpression.NEUTRAL, "An adventurer eh? There is no service finer than serving the bountiful King Arthur, and I happen to know there's an important quest to fulfill.")
                stage = END_DIALOGUE
            }

            STAGE_UNFORTUNATELY_NOT -> {
                npcl(FacialExpression.NEUTRAL, "Unfortunately not, " + (if (player!!.isMale) "sirrah" else "madam") + ".")
                stage = END_DIALOGUE
            }

            6 -> {
                npcl(FacialExpression.NEUTRAL, "Mordred... So you think he may be involved with the curse upon Merlin?")
                stage++
            }
            7 -> {
                playerl(FacialExpression.NEUTRAL, "Good a guess as any right?")
                stage++
            }
            8 -> {
                npcl(FacialExpression.NEUTRAL, "I think you may be on to something there. Unfortunately his fortress is impregnable!")
                stage++
            }
            9 -> {
                playerl(FacialExpression.NEUTRAL, "... I'll figure something out.")
                stage = END_DIALOGUE
            }

            STAGE_GET_MERLIN_OUT -> {
                playerl(FacialExpression.NEUTRAL, "Any ideas on getting Merlin out of that crystal?")
                stage = STAGE_UNFORTUNATELY_NOT
            }

            // option 1
            35 -> {
                npcl(FacialExpression.NEUTRAL, "Here's your replacement. Please be more careful.")
                AchievementDiary.grantReplacement(player!!, DiaryType.SEERS_VILLAGE, diaryLevel)
                stage = END_DIALOGUE
            }

            // option 2
            40 -> {
                npcl(FacialExpression.NEUTRAL, "Your headband will help you get experience when woodcutting maple trees, and an extra log or two when cutting normal trees. I've also told Geoff to increase")
                stage++
            }

            41 -> {
                npcl(FacialExpression.NEUTRAL, "your flax allowance in acknowledgement of your standing.")
                stage = END_DIALOGUE
            }

            // option 3
            45 -> {
                npcl(FacialExpression.NEUTRAL, "Well done, young " + (if (player!!.isMale) "sir" else "madam") + ". You must be a mighty adventurer indeed to have completed the Hard tasks.")
                stage++
            }

            46 -> {
                if (!removeItem(player!!, Items.SEERS_HEADBAND_2_14659)) {
                    npcl(FacialExpression.NEUTRAL, "I need your headband. Come back when you have it.")
                    stage = END_DIALOGUE
                } else {
                    AchievementDiary.flagRewarded(player!!, DiaryType.SEERS_VILLAGE, diaryLevel)
                    sendItemDialogue(player!!, Items.SEERS_HEADBAND_3_14660,"You hand Sir Kay your headband and he concentrates for a moment. Some mysterious knightly energy passes through his hands and he gives the headband back to you, along with an old lamp.")
                    stage++
                }
            }

            47 -> {
                npcl(FacialExpression.NEUTRAL, "You will find that your headband now blesses you with the power to spin fabrics at extreme speed in Seers' Village. I will also instruct Geoff-erm-Flax to offer you a far larger flax allowance. Use your new powers")
                stage++
            }

            48 -> {
                npcl(FacialExpression.NEUTRAL, "wisely.")
                stage++
            }

            49 -> {
                playerl(FacialExpression.NEUTRAL, "Thank you, Sir Kay, I'll try not to harm anyone with my spinning.")
                stage++
            }

            50 -> {
                npcl(FacialExpression.NEUTRAL, "You are most welcome. You may also find that the Lady of the Lake is prepared to reward you for your services if you wear the headband in her presence.")
                stage = END_DIALOGUE
            }

            //option 4
            60 -> {
                npcl(FacialExpression.NEUTRAL, "I'm afraid not. It is important that adventurers complete the tasks unaided. That way, only the truly worthy collect the spoils.")
                stage = END_DIALOGUE
            }

        }
    }

}
