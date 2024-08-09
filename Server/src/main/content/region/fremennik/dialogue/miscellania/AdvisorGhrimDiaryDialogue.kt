package content.region.fremennik.dialogue.miscellania

import core.api.consts.NPCs
import core.api.freeSlots
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.diary.AchievementDiary
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.GameWorld
import core.tools.END_DIALOGUE

/**
 * Advisor ghrim diary dialogue.
 */
class AdvisorGhrimDiaryDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.ADVISOR_GHRIM_1375)
        when (stage) {
            0 -> if (player!!.achievementDiaryManager.getDiary(DiaryType.FREMENNIK)!!.isComplete(2, true) && !AchievementDiary.hasClaimedLevelRewards(player!!, DiaryType.FREMENNIK, 2)) {
                player(FacialExpression.FRIENDLY, "About the Achievement Diary...").also { stage = 10 }
            } else if (AchievementDiary.canReplaceReward(player!!, DiaryType.FREMENNIK, 2)) {
                player(FacialExpression.FRIENDLY, "I need a new pair of boots.").also { stage = 12 }
            } else {
                player(FacialExpression.FRIENDLY, "About the Achievement Diary...").also { stage = 13 }
            }
            10 -> player("I've completed all of the hard tasks in my Fremennik", "Achievement Diary.").also { stage++ }
            11 -> if (freeSlots(player!!) < 1) {
                end()
                npc(FacialExpression.FRIENDLY, "Although, you'll have to come back when you've the space", "to take them.").also { stage = END_DIALOGUE }
            } else {
                npc("There you go. They're powerful things in their own", "way, so take care to study them and find all the ways", "they can help you.").also {
                    AchievementDiary.flagRewarded(player!!, DiaryType.FREMENNIK, 2)
                    stage = 21
                }
            }
            12 -> if (freeSlots(player!!) < 1) {
                end()
                npc(FacialExpression.FRIENDLY, "Although, you'll have to come back when you've the space", "to take them.").also { stage = END_DIALOGUE }
            } else {
                npc("So you have. I have some special boots I've kept aside", "for you.").also {
                    AchievementDiary.grantReplacement(player!!, DiaryType.FREMENNIK, 2)
                    stage = 21
                }
            }
            13 -> options("What is the Achievement Diary?", "What are the rewards?", "How do I claim the rewards?", "See you later.",).also { stage++ }
            14 -> when (buttonID) {
                1 -> playerl(FacialExpression.ASKING, "What is the Achievement Diary?").also { stage = 15 }
                2 -> playerl(FacialExpression.ASKING, "What are the rewards?").also { stage = 18 }
                3 -> playerl(FacialExpression.ASKING, "How do I claim the rewards?").also { stage = 20 }
                4 -> playerl(FacialExpression.ASKING, "See you later.").also { stage = END_DIALOGUE }
            }
            15 -> npc(FacialExpression.FRIENDLY, "Very well; the Achievements Diary is a collection of", "Tasks you may wish to complete while adventuring", "world of ${GameWorld.settings!!.name}. You can earn special rewards", "for completing Tasks.").also { stage++ }
            16 -> npc(FacialExpression.NEUTRAL, "Some also give items that will help to complete", "other Tasks, and many count as progress towards", "the set for the area they're in.",).also { stage++ }
            17 -> npc(FacialExpression.NEUTRAL, "Some of the Fremennik set's Tasks are simple, some will", "require certain skill levels, and some might require", "quests to be started or completed.",).also { stage = 13 }
            18 -> npc(FacialExpression.NEUTRAL, "For completing the Fremennik diaries, you are presented", "with a pair of sea boots. These boots will become increasingly", "useful with each difficulty level of the set that you complete.",).also { stage++ }
            19 -> npc(FacialExpression.NEUTRAL, "When you are presented with your rewards,", "you will be told of their uses.").also { stage = 13 }
            20 -> npc("To claim your Fremennik Diaries rewards", "speak to Council Workman south of the Rellekka,", "Advisor Ghrim on Miscellania, or myself.",).also { stage = 13 }
            21 -> playerl(FacialExpression.HAPPY, "Thanks!").also { stage = 13 }
            100 -> end()
        }
    }
}
