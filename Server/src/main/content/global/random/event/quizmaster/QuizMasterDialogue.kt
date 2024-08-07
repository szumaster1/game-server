package content.global.random.event.quizmaster

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.log
import core.api.openInterface
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.settings
import core.network.packet.PacketRepository
import core.network.packet.context.DisplayModelContext
import core.network.packet.outgoing.DisplayModel
import core.tools.Log
import core.tools.RandomFunction

/**
 * Quiz master dialogue.
 */
//@Initializable
class QuizMasterDialogue(player: Player? = null) : Dialogue(player) {
    private var randomEvent: QuizMasterEvent? = null
    private var wrongAnswer = 0
    override fun open(vararg args: Any): Boolean {
        randomEvent = args[0] as QuizMasterEvent
        if (!randomEvent!!.isStartedQuiz) {
            randomEvent!!.isStartedQuiz = true
        } else {
            if (randomEvent!!.score == 4) {
                npc(
                    BLUE + "CONGRATULATIONS!",
                    "You are a " + RED + "WINNER</col>!",
                    "Please choose your " + BLUE + "PRIZE</col>!"
                ).also { stage = 4 }
                return true
            }
            npc(if (randomEvent!!.score == 0) "Okay! First question!" else "Okay! Next question!").also { stage = 2 }
            return true
        }
        npc(
            "WELCOME to the GREATEST QUIZ SHOW in the",
            "whole of " + settings!!.name + ".",
            "<col=7f0000>O <col=6f000f>D <col=5f001f>D <col=4f002f>O <col=3f003f>N <col=2f004f>E <col=1f005f>O <col=0f006f>U <col=00007f>T"
        )
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        var buttonId = buttonId
        when (stage) {
            0 -> player(
                FacialExpression.THINKING,
                "I'm sure I didn't ask to take part in a",
                " quiz show..."
            ).also { stage++ }

            1 -> npc(
                "Please welcome our newest contestant:",
                RED + player.username,
                "Just pick the ODD ONE OUT",
                "Four questions right, and then you win!"
            ).also { stage++ }

            2 -> {
                display(QuizSet.quizSet)
                openInterface(player, Components.MACRO_QUIZSHOW_191)
                stage++
            }

            3 -> {
                buttonId -= 4
                val wrong = wrongAnswer != buttonId
                if (wrong) {
                    randomEvent!!.resetScore()
                } else {
                    randomEvent!!.incrementScore()
                }
                if (randomEvent!!.score == 4) {
                    npc(
                        BLUE + "CONGRATULATIONS!",
                        "You are a " + RED + "WINNER</col>!",
                        "Please choose your " + BLUE + "PRIZE</col>!"
                    ).also { stage = 4 }
                }
                if (wrong) {
                    QuizUtils.WRONG
                } else {
                    QuizUtils.CORRECT
                }
                stage = 2
            }

            4 -> options("1000 Coins", "Mystery Box").also { stage++ }
            5 -> {
                player!!.pulseManager.run(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            1 -> {
                                end()
                                if (buttonId == 1) {
                                    QuizUtils.COINS
                                } else {
                                    QuizUtils.MYSTERY_BOX
                                }
                            }
                        }
                        return true
                    }
                })
                return false
            }

            else -> log(this::class.java, Log.WARN, RED + "UNHANDLED QUIZ STAGE $stage, $buttonId, $interfaceId")
        }
        return true
    }


    /**
     * Reward
     *
     * @param player
     */
    fun reward(player: Player) {
        QuizUtils.cleanup(player)
        player.pulseManager.run(object : Pulse() {
            override fun pulse(): Boolean {

                return true
            }
        })
        return
    }

    private fun display(quiz: Array<QuizSet?>) {
        val correct = quiz[0]
        val wrong = quiz[1]
        val id: MutableList<Int> = ArrayList()
        id.add(1);id.add(2);id.add(3);id.shuffle()
        for (i in 0..1) {
            sendItem(correct!!.getModel(i), id[i])
        }
        wrongAnswer = id[2]
        sendItem(wrong!!.getModel(RandomFunction.random(wrong.ids.size)), wrongAnswer)
    }

    private fun sendItem(model: Int, index: Int) {
        PacketRepository.send(
            DisplayModel::class.java,
            DisplayModelContext(player, DisplayModelContext.ModelType.MODEL, model, 190, 191, 4 + index)
        )
    }

    /**
     * Get dialogue
     *
     * @param dialogues
     * @return
     */
    fun getDialogue(dialogues: Array<Array<String?>>): Array<String?> {
        return dialogues[RandomFunction.random(dialogues.size)]
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.QUIZ_MASTER_2477)
    }

}
