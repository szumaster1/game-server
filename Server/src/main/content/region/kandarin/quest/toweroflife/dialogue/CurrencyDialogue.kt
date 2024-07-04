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

@Initializable
class CurrencyDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Tower of Life")) {
            in 0..2 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "This is quite the tower.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Yeah, it cost a fair bit.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "How much?").also { stage++ }
                3 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Well, I had to 'alch' a fair few belongings of my fellow alchemists to afford it."
                ).also { stage++ }

                4 -> playerl(FacialExpression.FRIENDLY, "I bet they love you for that.").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "Everyone loves someone that can create money.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "I don't.").also { stage = END_DIALOGUE }
            }

            3 -> {
                if (getAttribute(player, TolUtils.TOL_PLAYER_ENTER_TOWER_ATTRIBUTE, 0) == 1) {
                    when (stage) {
                        START_DIALOGUE -> playerl(
                            FacialExpression.FRIENDLY,
                            "Hi. Sorry, but why do they call you 'Currency'?"
                        ).also { stage++ }

                        1 -> npcl(
                            FacialExpression.FRIENDLY,
                            "That's down to my interest in all things of monetary value."
                        ).also { stage++ }

                        2 -> playerl(FacialExpression.FRIENDLY, "You must be pretty rich then.").also { stage++ }
                        3 -> npcl(FacialExpression.FRIENDLY, "I wish. And I was, up until recently.").also { stage++ }
                        4 -> playerl(FacialExpression.FRIENDLY, "What happened?").also { stage++ }
                        5 -> npcl(
                            FacialExpression.FRIENDLY,
                            "This tower. Set me back a fair few million."
                        ).also { stage++ }

                        6 -> playerl(
                            FacialExpression.FRIENDLY,
                            "I'll bet! It pleases you, then, to know that I have just become a builder and have been granted permission to go mess around inside."
                        ).also { stage++ }

                        7 -> npcl(FacialExpression.FRIENDLY, "This is not a good day for me.").also {
                            stage = END_DIALOGUE
                        }
                    }
                } else {
                    when (stage) {
                        START_DIALOGUE -> playerl(
                            FacialExpression.FRIENDLY,
                            "Hi, Currency. Got any spare cash?"
                        ).also { stage++ }

                        1 -> npcl(FacialExpression.FRIENDLY, "Nope; you?").also { stage++ }
                        2 -> playerl(FacialExpression.FRIENDLY, "No, do you?").also { stage++ }
                        3 -> npcl(FacialExpression.FRIENDLY, "Afraid not. You?").also { stage++ }
                        4 -> playerl(
                            FacialExpression.FRIENDLY,
                            "I'm going to stop before this gets violent."
                        ).also { stage = END_DIALOGUE }
                    }
                }
            }

            6 -> {
                if (getAttribute(player, TolUtils.TOL_START_CUTSCENE_ATTRIBUTE, false)) {
                    when (stage) {
                        START_DIALOGUE -> playerl(
                            FacialExpression.FRIENDLY,
                            "That is one very sad creature."
                        ).also { stage++ }

                        1 -> npcl(
                            FacialExpression.FRIENDLY,
                            "It'll be alright: it just needs a pat on the back."
                        ).also { stage++ }

                        2 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Why am I suddenly glad that you're not responsible for sorting out this mess?"
                        ).also { stage = END_DIALOGUE }
                    }
                }
            }

            8 -> {
                when (stage) {
                    START_DIALOGUE -> playerl(
                        FacialExpression.FRIENDLY,
                        "The Homunculus is a bit better off now."
                    ).also { stage++ }

                    1 -> npcl(FacialExpression.FRIENDLY, "Excellent. So we can go back in.").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "Not just yet.").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CURRENCY_THE_ALCHEMIST_5587)
    }
}
