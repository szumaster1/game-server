package content.region.asgarnia.falador.quest.rd.handlers

import org.rs.consts.Components
import org.rs.consts.NPCs
import content.region.asgarnia.falador.quest.rd.RecruitmentDrive
import content.region.asgarnia.falador.quest.rd.dialogue.SirRenItchwoodDialogueFile
import core.api.*
import core.game.interaction.InterfaceListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player

/**
 * Combo lock interface listener.
 */
class ComboLockInterfaceListener : InterfaceListener {

    companion object {
        private const val INITIAL_VALUE = 65
        private const val LOWER_BOUND = 65
        private const val UPPER_BOUND = 90
        private const val LOCK_COUNT = 4
        private val lockArray = arrayOf("rd:lock1", "rd:lock2", "rd:lock3", "rd:lock4")
        private val answers = arrayOf("BITE", "FISH", "LAST", "MEAT", "RAIN", "TIME")
    }

    override fun defineInterfaceListeners() {
        /**
         * Open the new combination lock interface.
         */
        onOpen(Components.RD_COMBOLOCK_285) { player, _ ->
            initializeLocks(player)
            return@onOpen true
        }

        /**
         * Exit the new combination lock interface.
         */
        onClose(Components.RD_COMBOLOCK_285) { player, _ ->
            clearLocks(player)
            return@onClose true
        }

        /**
         * Handle buttons for interface.
         */
        on(Components.RD_COMBOLOCK_285) { player, _, _, buttonID, _, _ ->
            handleButtonPress(player, buttonID)
            return@on true
        }
    }

    /**
     * Initialize the locks.
     */
    private fun initializeLocks(player: Player) {
        lockArray.forEach { lock ->
            setAttribute(player, lock, INITIAL_VALUE)
        }
    }

    /**
     * Clear the locks.
     */
    private fun clearLocks(player: Player) {
        lockArray.forEach { lock ->
            removeAttribute(player, lock)
        }
    }

    /**
     * Handle button press
     *
     * @param player the player.
     * @param buttonID the button id.
     */
    private fun handleButtonPress(player: Player, buttonID: Int) {
        if (buttonID in 10..17) {
            adjustLockValue(player, buttonID)
        } else if (buttonID == 18) {
            validateAnswer(player)
        }
    }

    /**
     * Adjusts the lock value for a given player based on the button ID.
     *
     * @param player The player whose lock value is to be adjusted.
     * @param buttonID The ID of the button that determines the adjustment.
     */
    private fun adjustLockValue(player: Player, buttonID: Int) {
        val position = (buttonID - 10) / 2
        val direction = (buttonID - 10) % 2
        var newValue = getAttribute(player, lockArray[position], INITIAL_VALUE) + if (direction == 0) -1 else 1

        newValue = when {
            newValue < LOWER_BOUND -> UPPER_BOUND
            newValue > UPPER_BOUND -> LOWER_BOUND
            else -> newValue
        }

        setAttribute(player, lockArray[position], newValue)
        sendString(player, newValue.toChar().toString(), Components.RD_COMBOLOCK_285, position + 6)
    }

    /**
     * Validates the player's answer.
     *
     * @param player The player.
     */
    private fun validateAnswer(player: Player) {
        val answer = lockArray.joinToString("") { getAttribute(player, it, INITIAL_VALUE).toChar().toString() }
        closeInterface(player)
        if (answers[getAttribute(player, SirRenItchwoodDialogueFile.ATTRIBUTE_CLUE, 0)] == answer) {
            if (!getAttribute(player, RecruitmentDrive.stageFail, false)) {
                setAttribute(player, RecruitmentDrive.stagePass, true)
            }
            sendNPCDialogue(player, NPCs.SIR_REN_ITCHOOD_2287, "Your wit is sharp, your brains quite clear; You solved my puzzle with no fear. At puzzles I rank you quite the best, now enter the portal for your next test.")
        } else {
            setAttribute(player, RecruitmentDrive.stageFail, true)
            openDialogue(player, SirRenItchwoodDialogueFile(2), NPC(NPCs.SIR_REN_ITCHOOD_2287))
        }
    }
}
