package content.region.kandarin.quest.waterfall.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.GameWorld.settings

class HadleyDialogue(player: Player? = null) : Dialogue(player) {

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("hadley_dialogue"), 302)
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            100 -> end()
            0 -> stage = if (player.inventory.contains(292, 1)) {
                interpreter.sendDialogues(
                    302,
                    FacialExpression.HALF_GUILTY,
                    "I hope you're enjoying your stay, there should be lots",
                    "of useful information in that book: places to go, people to",
                    "see."
                )
                100
            } else {
                interpreter.sendDialogues(
                    302,
                    FacialExpression.HALF_GUILTY,
                    "Are you on holiday? If so you've come to the right",
                    "place. I'm Hadley the tourist guide, anything you need",
                    "to know just ask me. We have some of the most unspoilt",
                    "wildlife and scenery in " + settings!!.name + "."
                )
                1
            }

            1 -> {
                interpreter.sendDialogues(
                    302,
                    FacialExpression.HALF_GUILTY,
                    "People come from miles around to fish in the clear lakes",
                    "or to wander the beautiful hill sides."
                )
                stage = 2
            }

            2 -> {
                player(FacialExpression.HALF_GUILTY, "It is quite pretty.")
                stage = 3
            }

            3 -> {
                interpreter.sendDialogues(
                    302,
                    FacialExpression.HALF_GUILTY,
                    "Surely pretty is an understatement kind " + (if (player.isMale) "Sir. " else "Lady. ") + "Beautiful,",
                    "amazing or possibly life-changing would be more suitable",
                    "wording. Have you seen the Baxtorian waterfall?",
                    "Named after the elf king who was buried underneath."
                )
                stage = 4
            }

            4 -> {
                player(FacialExpression.HALF_GUILTY, "Thanks then, goodbye.")
                stage = 5
            }

            5 -> {
                interpreter.sendDialogues(302, FacialExpression.HALF_GUILTY, "Enjoy your visit.")
                stage = 100
            }
        }
        return true
    }


    override fun open(vararg args: Any): Boolean {
        player(FacialExpression.HALF_GUILTY, "Hello there.")
        stage = 0
        return true
    }
}