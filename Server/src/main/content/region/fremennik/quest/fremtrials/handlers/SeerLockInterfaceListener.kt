package content.region.fremennik.quest.fremtrials.handlers

import cfg.consts.Components
import core.api.*
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player

/**
 * Seer lock interface listener.
 */
class SeerLockInterfaceListener : InterfaceListener {

    private val letters = ('A'..'Z').toList()
    private val letterBacks = listOf(39, 35, 31, 27)
    private val letterForwards = listOf(40, 36, 32, 28)
    private val enterButton = 1
    private val doorLockInterface = Components.SEER_COMBOLOCK_298

    override fun defineInterfaceListeners() {
        // Called when the door lock interface is opened.
        onOpen(doorLockInterface) { player, _ ->
            resetPlayerAttributes(player)
            return@onOpen true
        }

        // Called when the door lock interface is closed.
        onClose(doorLockInterface) { player, _ ->
            clearPlayerAttributes(player)
            return@onClose true
        }

        // Called when a button is clicked in the door lock interface.
        on(doorLockInterface) { player, _, _, buttonID, _, _ ->
            handleButtonClick(player, buttonID)
            return@on true
        }
    }

    /**
     * Resets the player's attributes related to the riddle.
     *
     * @param player The player whose attributes are to be reset.
     */
    private fun resetPlayerAttributes(player: Player) {
        (618..621).forEach { player.packetDispatch.sendVarcUpdate(it.toShort(), 0) }
        player.packetDispatch.sendIfaceSettings(0, 2, 298, 0, 1)
        (1..4).forEach { setAttribute(player, "riddle-letter-$it", 0) }
    }

    /**
     * Clears the player's attributes related to the riddle.
     *
     * @param player The player whose attributes are to be cleared.
     */
    private fun clearPlayerAttributes(player: Player) {
        (1..4).forEach { removeAttribute(player, "riddle-letter-$it") }
    }

    /**
     * Handles the button click event for the riddle interface.
     *
     * @param player   The player who clicked the button.
     * @param buttonID The ID of the button that was clicked.
     */
    private fun handleButtonClick(player: Player, buttonID: Int) {
        when (buttonID) {
            in letterBacks -> updateLetter(player, letterBacks.indexOf(buttonID), -1)
            in letterForwards -> updateLetter(player, letterForwards.indexOf(buttonID), 1)
            enterButton -> checkRiddleSolution(player)
        }
    }

    /**
     * Updates the selected letter for the riddle based on the button clicked.
     *
     * @param player The player whose letter is being updated.
     * @param index  The index of the letter being updated.
     * @param change The amount to change the letter index by (positive or negative).
     */
    private fun updateLetter(player: Player, index: Int, change: Int) {
        val letterKey = "riddle-letter-${index + 1}"
        val currentValue = getAttribute(player, letterKey, 0)
        if (currentValue == 0 && change < 0) {
            setAttribute(player, letterKey, 25)
        } else {
            player.incrementAttribute(letterKey, change)
        }
    }

    /**
     * Checks if the player's selected letters match the riddle solution.
     *
     * @param player The player whose riddle solution is being checked.
     */
    private fun checkRiddleSolution(player: Player) {
        val lettersSelected = (1..4).map { letters[getAttribute(player, "riddle-letter-$it", 0)] }
        val riddleAnswers = listOf("LIFE", "MIND", "TIME", "WIND")

        val currentRiddle = getAttribute(player, "PeerRiddle", 0)
        if (lettersSelected.joinToString("") == riddleAnswers[currentRiddle]) {
            sendMessage(player, "You have solved the riddle!")
            removeAttribute(player, "PeerRiddle")
            setAttribute(player, "/save:riddlesolved", true)
        } else {
            sendMessage(player, "You have failed to solve the riddle.")
        }
        player.interfaceManager.close()
    }
}