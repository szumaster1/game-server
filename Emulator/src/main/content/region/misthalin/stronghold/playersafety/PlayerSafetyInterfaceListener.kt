package content.region.misthalin.stronghold.playersafety

import core.api.*
import cfg.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the player safety Test interface listener.
 */
@Initializable
class PlayerSafetyInterfaceListener : InterfaceListener {

    companion object {
        private val testQuestions = listOf(
            TestQuestion(Components.PLAYER_SAFETY_EXAM_697, 26, mapOf(4 to 37, 3 to 40, 5 to 43), 4),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_699, 20, mapOf(4 to 31, 3 to 34), 4),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_707, 20, mapOf(3 to 31, 4 to 35), 3),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_710, 20, mapOf(9 to 31, 10 to 34), 9),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_704, 26, mapOf(10 to 37, 12 to 43, 11 to 40), 10),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_708, 29, mapOf(12 to 40, 13 to 43), 12),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_696, 20, mapOf(4 to 31, 3 to 34), 4),
            TestQuestion(Components.PLAYER_SAFETY_EXAM_705, 26, mapOf(10 to 37, 12 to 43, 11 to 40), 10),
         // TestQuestion(Components.PLAYER_SAFETY_EXAM_694, 40, mapOf(24 to 15, 23 to 16), 24),
        )
        private var testQuestionNumber = 0
    }

    /**
     * Test question.
     *
     * @param interfaceId the base ID for the test question.
     * @param baseChild the ID of the Component child.
     * @param answers A map of button press: child to display.
     * @param correctOption The button number for the correct option.
     * @constructor Test question.
     */
    class TestQuestion(val interfaceId: Int, val baseChild: Int, val answers: Map<Int, Int>, val correctOption: Int) {

        /**
         * Show answer.
         *
         * @param player the player.
         * @param button the button.
         */
        fun showAnswer(player: Player, button: Int) {
            setComponentVisibility(player, interfaceId, baseChild, false)
            answers[button]?.let { setComponentVisibility(player, interfaceId, it, false) }
            return
        }
    }

    /**
     * Check answer.
     *
     * @param player the player.
     * @param button the button.
     */
    private fun checkAnswer(player: Player, button: Int) {
        val question = testQuestions[testQuestionNumber]
        question.showAnswer(player, button)
        if (button == question.correctOption) {
            testQuestionNumber += 1
        }
    }

    /**
     * This function processes the completion of a test for a given player.
     * It takes a Player object as a parameter to identify which player's test is being completed.
     *
     * @param player The Player object representing the player whose test is completed.
     */
    private fun completedTest(player: Player) {
        closeInterface(player)
        player.savedData.globalData.setTestStage(2)
        sendMessage(player, "Well done! You completed the exam.")
        sendDialogueLines(player, "Congratulations! The test has been completed. Hand the paper in to", "Professor Henry for marking.")
    }

    /**
     * Updates the player's information in the system.
     *
     * @param player The Player object containing updated information.
     */
    fun update(player: Player) {
        closeInterface(player)
        testQuestions.forEachIndexed { index, testQuestion ->
            if (index == testQuestionNumber) {
                openInterface(player, testQuestion.interfaceId)
            }
        }
    }

    override fun defineInterfaceListeners() {
        onOpen(Components.PLAYER_SAFETY_EXAM_697) { _, _ ->
            testQuestionNumber = 0
            return@onOpen true
        }
        for (question in testQuestions) {
            on(question.interfaceId) { player, _, _, buttonID, _, _ ->
                if (buttonID in 0..35) {
                    checkAnswer(player, buttonID)
                } else {
                    update(player)
                    if (testQuestionNumber == testQuestions.size) {
                        completedTest(player)
                        return@on true
                    }
                }
                return@on true
            }
        }
    }
}

