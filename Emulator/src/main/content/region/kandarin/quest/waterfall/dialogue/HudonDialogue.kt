package content.region.kandarin.quest.waterfall.dialogue

import content.region.kandarin.quest.waterfall.WaterFall
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import org.rs.consts.QuestName

/**
 * Represents the Hudon dialogue.
 */
class HudonDialogue(player: Player? = null) : Dialogue(player) {

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("hudon_dialogue"), 305)
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest(QuestName.WATERFALL_QUEST)
        when (stage) {
            100 -> end()
            101 -> {
                player.packetDispatch.sendMessage("Hudon is refusing to leave the waterfall.")
                end()
            }

            0 -> if (quest.getStage(player) == 10) {
                interpreter.sendDialogues(305, FacialExpression.CHILD_NORMAL, "It looks like you need the help.")
                stage = 1
            }

            1 -> {
                player(FacialExpression.HALF_GUILTY, "Your mum sent me to find you.")
                stage = 2
            }

            2 -> {
                interpreter.sendDialogues(
                    305,
                    FacialExpression.CHILD_NORMAL,
                    "Don't play nice with me, I know you're looking for the",
                    "treasure too."
                )
                stage = 3
            }

            3 -> {
                player(FacialExpression.HALF_GUILTY, "Where is this treasure you talk of?")
                stage = 4
            }

            4 -> {
                interpreter.sendDialogues(
                    305,
                    FacialExpression.CHILD_SUSPICIOUS,
                    "Just because I'm small doesn't mean I'm dumb! If I",
                    "told you, you would take it all for yourself."
                )
                stage = 5
            }

            5 -> {
                player(FacialExpression.HALF_GUILTY, "Maybe I could help.")
                stage = 6
            }

            6 -> {
                interpreter.sendDialogues(305, FacialExpression.CHILD_NORMAL, "I'm fine alone.")
                quest.setStage(player, 20)
                stage = 101
            }

            20 -> {
                interpreter.sendDialogues(
                    305,
                    FacialExpression.CHILD_NORMAL,
                    "I'll find that treasure soon, just you wait and see."
                )
                stage = if (quest.getStage(player) == 100) {
                    21
                } else {
                    100
                }
            }

            21 -> {
                player(FacialExpression.HALF_GUILTY,
                    "I hate to break it to you kid, but I found the treasure."
                )
                stage = 22
            }

            22 -> {
                interpreter.sendDialogues(
                    305,
                    FacialExpression.CHILD_NORMAL,
                    "Wha- are you serious? Are you going to at least share?"
                )
                stage = 23
            }

            23 -> {
                player(FacialExpression.HALF_GUILTY, "No.")
                stage = 24
            }

            24 -> {
                interpreter.sendDialogues(
                    305,
                    FacialExpression.CHILD_NORMAL,
                    "Aww, come on... I helped you find it.",
                    "This isn't fair!"
                )
                stage = 25
            }

            25 -> {
                player(FacialExpression.HALF_GUILTY, "Life ain't fair kid.")
                stage = 100
            }
        }
        return true
    }

    override fun open(vararg args: Any): Boolean {
        val quest = player.getQuestRepository().getQuest(QuestName.WATERFALL_QUEST)
        stage = if (quest.getStage(player) >= 20) {
            player(FacialExpression.HALF_GUILTY, "So you're still here.")
            20
        } else {
            player(FacialExpression.HALF_GUILTY, "Hello son, are you okay? You need help?")
            0
        }
        return true
    }
}