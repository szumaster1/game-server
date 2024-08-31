package content.region.kandarin.quest.waterfall.dialogue

import content.region.kandarin.quest.waterfall.WaterFall
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player

/**
 * Represents the Almera dialogue.
 */
class AlmeraDialogue(player: Player? = null) : Dialogue(player) {

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("almera_dialogue"), 304)
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest(WaterFall.NAME)
        when (stage) {
            0 -> if (quest.getStage(player) == 0) {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.ASKING,
                    "Ah, hello there. Nice to see an outsider for a change,",
                    "are you busy? I have a problem."
                )
                stage = 1
            } else if (quest.getStage(player) == 10) {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.JOLLY,
                    "Hello brave adventurer, have you seen my boy yet?"
                )
                stage = 200
            } else if (quest.getStage(player) >= 20) {
                interpreter.sendDialogues(304, FacialExpression.JOLLY, "Well hello, you're still around then.")
                stage = 202
            } else {
            }

            1 -> {
                options( "I'm afraid I'm in a rush.", "How can I help?")
                stage = 2
            }

            2 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "I'm afraid I am in a rush.")
                    stage = 99
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "How can I help?")
                    stage = 3
                }
            }

            3 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.DISGUSTED,
                    "It's my son Hudon, he's always getting into trouble, the",
                    "boy's convinced there's hidden treasure in the river and",
                    "I'm a bit worried about his safety, the poor lad can't",
                    "even swim."
                )
                stage = 4
            }

            4 -> {
                player(FacialExpression.HALF_GUILTY,
                    "I could go and take a look for you if you like?"
                )
                stage = 5
            }

            5 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.DISGUSTED,
                    "Would you? You are kind. You can use the small raft",
                    "out back if you wish, do be careful, the current down",
                    "stream is very strong."
                )
                quest.start(player)
                stage = 100
            }

            6 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.HALF_GUILTY,
                    "Perhaps you can speak to Hadley a further bit down",
                    "south for more information on how to find the treasure."
                )
                stage = 100
            }

            7 -> {
                player(FacialExpression.HALF_GUILTY,
                    "I did it, I found the treasure under the waterfall!"
                )
                stage = 8
            }

            8 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.HALF_GUILTY,
                    "Ah, very well done, adventurer!",
                    "My boy Hudon was searching for that treasure too."
                )
                stage = 9
            }

            9 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.HALF_GUILTY,
                    "Maybe you could share it with him, he's just a boy."
                )
                stage = 10
            }

            10 -> {
                player(FacialExpression.HALF_GUILTY,
                    "On second thought, I really have to go."
                )
                stage = 100
            }

            99 -> {
                interpreter.sendDialogues(304, FacialExpression.HALF_GUILTY, "Oh okay, never mind.")
                stage = 100
            }

            100 -> end()
            200 -> {
                player(FacialExpression.HALF_GUILTY,
                    "I'm afraid not, but I'm sure he hasn't gone far."
                )
                stage = 201
            }

            201 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.HALF_GUILTY,
                    "I do hope so, you can't be too careful these days."
                )
                stage = 100
            }

            202 -> {
                player(FacialExpression.HALF_GUILTY,
                    "I saw Hudon by the river but he refused to come back",
                    "with me."
                )
                stage = 203
            }

            203 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.HALF_GUILTY,
                    "Yes he told me, the foolish lad came in drenched to the",
                    "bone, he had fallen into the waterfall, lucky he wasn't",
                    "killed! Now he can spend the rest of the summer in his",
                    "room."
                )
                stage = 204
            }

            204 -> {
                player(FacialExpression.HALF_GUILTY,
                    "Any ideas on what I could do while I'm here?"
                )
                stage = 205
            }

            205 -> {
                interpreter.sendDialogues(
                    304,
                    FacialExpression.HALF_GUILTY,
                    "Why don't you visit the tourist centre south of the",
                    "waterfall?"
                )
                stage = 100
            }
        }
        return true
    }

    override fun open(vararg args: Any): Boolean {
        val quest = player.getQuestRepository().getQuest(WaterFall.NAME)
        stage = if (quest.getStage(player) == 100) {
            player(FacialExpression.HALF_GUILTY, "Hello Almera.")
            7
        } else {
            player(FacialExpression.NEUTRAL, "Hello.")
            0
        }
        return true
    }
}