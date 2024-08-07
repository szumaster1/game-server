package content.region.kandarin.quest.toweroflife.dialogue

import content.region.kandarin.quest.toweroflife.util.TolUtils
import core.api.consts.NPCs
import core.api.getAttribute
import core.api.getQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Gummy dialogue.
 */
@Initializable
class GummyDialogue(player: Player? = null) : Dialogue(player) {


    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Tower of Life")) {
            in 0..1 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "What are you up to?").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Rock, Paper, Scissors. Can't you tell?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "It was more of a rhetorical question.").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Rhetora-what?").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Shouldn't you be working?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "We are. It's team building.").also { stage = END_DIALOGUE }
            }

            2 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Why, hello there!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "What do you want?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Clothing.").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Nope. Need mine.").also { stage++ }
                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Ah, come on. You know you want to give me free stuff."
                ).also { stage++ }

                5 -> npcl(FacialExpression.FRIENDLY, "Stop bothering me, can't you see I'm busy?").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Yes, VERY busy I'm sure.").also { stage++ }
                7 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Okay, okay. The other day I was drying my clothes on a line down by the shore. A storm hit and some of my clothing went missing."
                ).also { stage++ }

                8 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Likely story. Sure you weren't skinny dipping?"
                ).also { stage++ }

                9 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Just go look and leave me be! Search around the tower and you may find them."
                ).also { stage = END_DIALOGUE }
            }

            3 -> {
                if (getAttribute(player, TolUtils.TOL_PLAYER_ENTER_TOWER_ATTRIBUTE, 0) == 1) {
                    when (stage) {
                        START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "You winning?").also { stage++ }
                        1 -> npcl(
                            FacialExpression.FRIENDLY,
                            "It's a draw at the moment. First it was best of three, then it was best of five."
                        ).also { stage++ }

                        2 -> playerl(FacialExpression.FRIENDLY, "And now?").also { stage++ }
                        3 -> npcl(FacialExpression.FRIENDLY, "Best of a hundred and one.").also { stage = END_DIALOGUE }
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUMMY_5591)
    }
}
