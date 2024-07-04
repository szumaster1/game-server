package content.region.misthalin.dialogue.digsite

import core.api.freeSlots
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.diary.AchievementDiary
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.GameWorld
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import core.api.consts.NPCs

class CouncilWorkmanDiaryDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.COUNCIL_WORKMAN_1287)
        when (stage) {
            START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hello").also { stage = 1 }
            1 -> options("So you fixed the bridge?", "About the Achievement Diary...").also { stage = 2 }
            2 -> when (buttonID) {
                1 -> player(FacialExpression.HALF_ASKING, "So you fixed the bridge?").also { stage = 3 }
                2 -> if (player!!.achievementDiaryManager.getDiary(DiaryType.FREMENNIK).isComplete(0, true) && !AchievementDiary.hasClaimedLevelRewards(player, DiaryType.FREMENNIK, 0)) {
                    player(FacialExpression.FRIENDLY, "I have question about my Achievement Diary").also { stage = 11 }
                } else if (AchievementDiary.canReplaceReward(player, DiaryType.FREMENNIK, 0)) {
                    player(FacialExpression.FRIENDLY, "I need a new pair of boots.").also { stage = 15 }
                } else {
                    player(FacialExpression.FRIENDLY, "I have question about my Achievement Diary").also { stage = 5 }
                }
            }
            3 -> npc(FacialExpression.HAPPY, "Aye, that I did. 'Twas real thirsty work too. If only", "some kind stranger would buy us a bit of bear to sup,", "eh? What with that inn at Seers' Village so close by'", "and all, eh?",).also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "Just make sure yer don't get me none of that non", "alcoholic rubbish from that poison salesman guy", "at the tavern!",).also { stage = END_DIALOGUE }
            5 -> options("What is the Achievement Diary?", "What are the rewards?", "How do I claim the rewards?", "See you later.",).also { stage++ }
            6 -> when (buttonID) {
                1 -> playerl(FacialExpression.ASKING, "What is the Achievement Diary?").also { stage = 7 }
                2 -> playerl(FacialExpression.ASKING, "What are the rewards?").also { stage = 17 }
                3 -> playerl(FacialExpression.ASKING, "How do I claim the rewards?").also { stage = 10 }
                4 -> playerl(FacialExpression.ASKING, "See you later.").also { stage = END_DIALOGUE }
            }
            7 -> npc(FacialExpression.FRIENDLY, "Very well; the Achievements Diary is a collection of", "Tasks you may wish to complete while adventuring", "world of ${GameWorld.settings!!.name}. You can earn special rewards", "for completing Tasks.").also { stage++ }
            8 -> npc(FacialExpression.NEUTRAL, "Some also give items that will help to complete", "other Tasks, and many count as progress towards", "the set for the area they're in.",).also { stage++ }
            9 -> npc(FacialExpression.NEUTRAL, "Some of the Fremennik diary tasks are simple, some will", "require certain skill levels, and some might require", "quests to be started or completed.",).also { stage = 5 }
            10 -> npc("To claim your Fremennik Diaries rewards", "speak to Yrsa in Rellekka, Advisor Ghrim on Miscellania,", "or myself.",).also { stage = 5 }
            11 -> player(FacialExpression.HAPPY, "I've completed all of the easy tasks in my Fremennik", "Achievement Diary.").also { stage++ }
            12 -> npc(FacialExpression.HAPPY, "Ah, that's a relief. You can wear Fremennik sea boots", "now - I have a pair for you.",).also { stage++ }
            13 -> if (freeSlots(player!!) < 1) {
                end()
                npc(FacialExpression.FRIENDLY, "Although, you'll have to come back when you've the space", "to take them.").also { stage = END_DIALOGUE }
            } else {
                npc(FacialExpression.NEUTRAL, "They're strange, them boots. I reckon they'll impress", "the Fremennik and their strange spirits. Look at them", "carefully, see if you can't work out what they do.",).also {
                    AchievementDiary.flagRewarded(player, DiaryType.FREMENNIK, 0)
                    stage = 14
                }
            }
            14 -> player(FacialExpression.HAPPY, "Thanks!").also { stage = 5 }
            15 -> npc(FacialExpression.HAPPY, "Ah, that's a relief. You can wear Fremennik sea boots", "now - I have a pair for you.",).also { stage++ }
            16 -> if (freeSlots(player!!) < 1) {
                end()
                npc(FacialExpression.FRIENDLY, "Although, you'll have to come back when you've the space", "to take them.").also { stage = END_DIALOGUE }
            } else {
                npc(FacialExpression.NEUTRAL, "They're strange, them boots. I reckon they'll impress", "the Fremennik and their strange spirits. Look at them", "carefully, see if you can't work out what they do.",).also {
                    AchievementDiary.grantReplacement(player, DiaryType.FREMENNIK, 0)
                    stage = 14
                }
            }
            17 -> npc(FacialExpression.NEUTRAL, "For completing the Fremennik diaries, you are presented", "with a pair of sea boots. These boots will become increasingly", "useful with each difficulty level of the set that you complete.",).also { stage++ }
            18 -> npc(FacialExpression.NEUTRAL, "When you are presented with your rewards,", "you will be told of their uses.").also { stage = 5 }
        }
    }
}
