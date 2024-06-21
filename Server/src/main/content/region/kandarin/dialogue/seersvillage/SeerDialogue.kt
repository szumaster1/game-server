package content.region.kandarin.dialogue.seersvillage

import content.region.kandarin.quest.scorpcather.dialogue.SeersDialogueFile
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.AchievementDiary
import core.game.node.entity.player.link.diary.DiaryType
import core.plugin.Initializable

@Initializable
class SeerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options("Talk about something else.", "Talk about achievement diary.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val level = 0

        when (stage) {
            999 -> end()
            0 -> when (buttonId) {
                1 -> {
                    npc("Many greetings.")
                    stage = 1
                }

                2 -> if (AchievementDiary.canReplaceReward(player, DiaryType.SEERS_VILLAGE, 0)) {
                    player("I seem to have lost my seers' headband...")
                    stage = 80
                } else if (AchievementDiary.hasClaimedLevelRewards(player, DiaryType.SEERS_VILLAGE, 0)) {
                    player("Can you remind me what my headband does?")
                    stage = 90
                } else if (AchievementDiary.canClaimLevelRewards(player, DiaryType.SEERS_VILLAGE, 0)) {
                    player("Hi. I've completed the Easy tasks in my Achievement", "Diary.")
                    stage = 200
                } else {
                    player("Hi! Can you help me out with the Achievement Diary", "tasks?")
                    stage = 101
                }
            }

            1 -> if (player.getQuestRepository().hasStarted("Scorpion Catcher") && player.getQuestRepository().getQuest("Scorpion Catcher").getStage(player) < 36) {
                player.dialogueInterpreter.open(SeersDialogueFile())
                stage = 999
            } else {
                options("Many greetings.", "I seek knowledge and power!")
                stage = 2
            }

            2 -> when (buttonId) {
                1 -> {
                    player("Many greetings.")
                    stage = 10
                }

                2 -> {
                    player("I seek knowledge and power!")
                    stage = 20
                }
            }

            80 -> {
                AchievementDiary.grantReplacement(player, DiaryType.SEERS_VILLAGE, 0)
                npc("Here's your replacement. Please be more careful.")
                stage = 999
            }

            90 -> {
                npc(
                    "Your headband marks you as an honourary seer.",
                    "Geoffrey - who works in the field to the",
                    "south - will give you free flax every day."
                )
                stage = 999
            }

            100 -> {
                npc(
                    "I certainly do - we have a set of tasks spanning Seers'",
                    "Village, Catherby, Hemenster and the Sinclair Mansion.",
                    "Just complete the tasks listed in the Achievement Diary",
                    "and they will be ticked off automatically."
                )
                stage = 999
            }

            101 -> {
                npc(
                    "I'm afraid not. It is important that adventurers",
                    "complete the tasks unaided. That way, only the truly",
                    "worthy collect the spoils."
                )
                stage = 999
            }

            200 -> {
                npc(
                    "Well done, adventurer. You are clearly a " + (if (player.isMale) "man" else "woman") + "of",
                    "great wisdom. I have a gift for you."
                )
                stage++
            }

            201 -> if (!AchievementDiary.flagRewarded(player, DiaryType.SEERS_VILLAGE, 0)) {
                npc("Come back when you have two free inventory slots.")
                stage = 999
            } else {
                interpreter.sendItemMessage(
                    AchievementDiary.getRewards(DiaryType.SEERS_VILLAGE, 0)[0],
                    "The seer hands you a strange-looking headband and a",
                    "rusty lamp."
                )
                stage++
            }

            202 -> {
                npc(
                    "You are now an honourary seer and Geoffrey - who",
                    "works in the field to the south - will give you free flax",
                    "every day. Don't call him 'Geoffrey' though: he prefers",
                    "to be known as 'Flax'."
                )
                stage++
            }

            203 -> {
                player("Flax? What sort of name is that for a person?")
                stage++
            }

            204 -> {
                npc(
                    "I know, I know. The poor boy is a simple soul - he just",
                    "really loves picking flax. A little too much, I fear."
                )
                stage = 999
            }

            10 -> {
                npc("Remember, whenever you set out to do something,", "something else must be done first.")
                stage = 999
            }

            20 -> {
                npc("Knowledge comes from experience, power", "comes from battleaxes.")
                stage = 999
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SEER_388)
    }

}
