package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.diary.AchievementDiary
import core.game.node.entity.player.link.diary.DiaryType
import core.tools.END_DIALOGUE

class PirateJackieDiaryDialogue : DialogueFile() {

    /*
     * Found at the Brimhaven Agility Arena entrance.
     * dialogue file related to Achievements diary.
     * Location: 2811,3191
     */

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.PIRATE_JACKIE_THE_FRUIT_1055)
        when (stage) {
            0 -> when {
                AchievementDiary.canClaimLevelRewards(player!!, DiaryType.KARAMJA, 0) -> playerl(FacialExpression.NEUTRAL, "I've done all the easy tasks in my Karamja Achievement Diary.").also { stage = 2 }
                AchievementDiary.canReplaceReward(player!!, DiaryType.KARAMJA, 0) -> playerl(FacialExpression.NEUTRAL, "I've seemed to have lost my gloves..").also { stage = 7 }
                else -> options("What is the Achievement Diary?", "What are the rewards?", "How do I claim the rewards?", "See you later.").also { stage++ }
            }
            1 -> when (buttonID) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "What is the Achievement Diary?").also { stage = 8 }
                2 -> playerl(FacialExpression.HALF_GUILTY, "What are the rewards?").also { stage = 10 }
                3 -> playerl(FacialExpression.HALF_GUILTY, "How do I claim the rewards?").also { stage = 12 }
                4 -> playerl(FacialExpression.HALF_GUILTY, "See you later.").also { stage = END_DIALOGUE }
            }
            2 -> npcl(FacialExpression.FRIENDLY, "Arr, ye have that, I see yer list. I s'pose ye'll be wanting yer reward then!").also { stage++ }
            3 -> playerl(FacialExpression.HALF_GUILTY, "Yes please.").also { stage++ }
            4 -> {
                AchievementDiary.flagRewarded(player!!, DiaryType.KARAMJA, 0)
                npcl(FacialExpression.FRIENDLY, "These 'ere Karamja gloves be a symbol of yer explorin' on the island. All the merchants will recognise" + " 'em when yer wear 'em and mabe give ye a little discount. I'll ave a word with some of the seafarin' folk who ").also { stage++ }
            }
            5 -> npcl(FacialExpression.FRIENDLY, "sail to Port Sarim and Ardougne, so they'll take ye on board half price if year wearin' them. Arrr, take this" + " lamp I found washed ashore too.").also { stage++ }
            6 -> playerl(FacialExpression.HALF_GUILTY, "Wow, thanks!").also { stage = 0 }
            7 -> {
                AchievementDiary.grantReplacement(player!!, DiaryType.KARAMJA, 0)
                npcl(FacialExpression.FRIENDLY, "Arr matey, have another pair. Ye better be more careful this time.").also { stage = 0 }
            }
            8 -> npcl(FacialExpression.FRIENDLY, "It's a diary that helps you keep track of particular achievements. Here on Karamja it can help " + "you discover some quite useful things. Eventually, with enough exploration, the people of Karamja will reward you.").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "You can see what tasks you have listed by clicking on the green button in the Quest List.").also { stage = 0 }
            10 -> sendNormalDialogue(NPC(NPCs.PIRATE_JACKIE_THE_FRUIT_1055), FacialExpression.FRIENDLY, "Well, there's three different pairs of Karamja gloves,", "which match up with the three levels of difficulty. Each", "has the same rewards as the previous level, and an", "additional one too... but I won't spoil your surprise.").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "Rest assured, the people of Karamja are happy to see you visiting the island.").also { stage = 0 }
            12 -> npcl(FacialExpression.FRIENDLY, "To claim the different Karamja gloves, speak to Kaleb Paramaya in Shilo Village, one of the jungle " + "foresters near the Kharazi Jungle, or me.").also { stage = 0 }
        }
    }
}
