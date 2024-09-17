package content.region.kandarin.seers_village.achievement.dialogue

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.AchievementDiary
import core.game.node.entity.player.link.diary.DiaryType
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Stankers diary dialogue.
 */
@Initializable
class StankersDiaryDialogue(player: Player? = null) : Dialogue(player) {

    var diaryLevel = 1

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "Hello bold adventurer.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Are these your trucks?", "Hello Stankers.", "Talk about Achievement Diary.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("Are these your trucks?").also { stage = 10 }
                2 -> player("Hello Mr. Stankers.").also { stage = 20 }
                3 -> if (AchievementDiary.canReplaceReward(player, DiaryType.SEERS_VILLAGE, 1)) {
                    player("I seem to have lost my seers' headband...").also { stage = 80 }
                } else if (AchievementDiary.hasClaimedLevelRewards(player, DiaryType.SEERS_VILLAGE, diaryLevel)) {
                    player("Can you remind me what my headband does?").also { stage = 90 }
                } else if (AchievementDiary.canClaimLevelRewards(player, DiaryType.SEERS_VILLAGE, diaryLevel)) {
                    player("Hi. I've completed the Medium tasks in my Achievement", "Diary. Can I have a reward?").also { stage = 200 }
                } else {
                    player("Hi! Can you help me out with the Achievement Diary", "tasks?").also { stage = 101 }
                }
            }
            10 -> npc(FacialExpression.OLD_DEFAULT, "Yes, I use them to transport coal over the river.", "I will let other people use them too, I'm a nice", "person like that...").also { stage++ }
            11 -> npc(FacialExpression.OLD_DEFAULT, "Just put coal in a truck and I'll move it down to", "my depot over the river.").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.OLD_DEFAULT, "Would you like a poison chalice?").also { stage++ }
            21 -> options("Yes please.", "What's a poison chalice?", "No thank you.").also { stage++ }
            22 -> when (buttonId) {
                1 -> player("Yes please.").also { stage = 30 }
                2 -> player("What's a poison chalice?").also { stage = 40 }
                3 -> player("No thank you.").also { stage = 1 }
            }
            30 -> {
                sendMessage(player, "Stankers hands you a glass of strangely coloured liquid...")
                addItemOrDrop(player, Items.POISON_CHALICE_197)
                end()
            }
            40 -> npc(FacialExpression.OLD_DEFAULT, "It's an exciting drink I've invented. I don't know", "what it tastes like, I haven't tried it myself.").also { stage++ }
            41 -> options("Yes please.", "No thank you.").also { stage++ }
            42 -> when (buttonId) {
                1 -> player("Yes please.").also { stage = 30 }
                2 -> player("No thank you.").also { stage = 1 }
            }
            80 -> {
                AchievementDiary.grantReplacement(player, DiaryType.SEERS_VILLAGE, diaryLevel)
                npc(FacialExpression.OLD_DEFAULT, "Here's your replacement. Please be more careful.")
                end()
            }
            90 -> npc(FacialExpression.OLD_DEFAULT, "Your headband will help you get experience when", "woodcutting maple trees, and an extra log or two when", "cutting normal trees. I've also told Geoff to increase").also { stage++ }
            91 -> npc(FacialExpression.OLD_DEFAULT, "your flax allowance in acknowledgement of your", "standing.").also { stage = END_DIALOGUE }
            100 -> npc(FacialExpression.OLD_DEFAULT, "I certainly do - we have a set of tasks spanning Seers'", "Village, Catherby, Hemenster and the Sinclair Mansion.", "Just complete the tasks listed in the Achievement Diary", "and they will be ticked off automatically.").also { stage = END_DIALOGUE }
            101 -> npc(FacialExpression.OLD_DEFAULT, "I'm afraid not. It is important that adventurers", "complete the tasks unaided. That way, only the truly", "worthy collect the spoils.").also { stage = END_DIALOGUE }
            200 -> npc(FacialExpression.OLD_DEFAULT, "Well done! Yes, I have a reward for you. I'll just", "anoint your headband with one of my mixtures. Oh and", "here's an old lamp I had lying around.").also { stage++ }
            201 -> {
                if (!removeItem(player, Items.SEERS_HEADBAND_1_14631)) {
                    npc(FacialExpression.OLD_DEFAULT, "I need your headband to anoint it! Come back when", "you have it.").also { stage = END_DIALOGUE }
                } else {
                    AchievementDiary.flagRewarded(player, DiaryType.SEERS_VILLAGE, diaryLevel)
                    sendDialogue("Stankers produces a chalice containing a vile-looking concoction that", "he pours all over your headband.").also { stage++ }
                }
            }
            202 -> player("Erm, thank you... I guess.").also { stage++ }
            203 -> npc(FacialExpression.OLD_DEFAULT, "Don't worry, it may be a little sticky for a while, but", "now your headband will help you get experience when", "woodcutting maple trees, and an extra log or two when", "cutting normal trees. I'll also tell Geoff - erm - Flax to").also { stage++ }
            204 -> npc(FacialExpression.OLD_DEFAULT, "increase your flax allowance in acknowledgement of your", "standing.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.STANKERS_383)
    }

}
