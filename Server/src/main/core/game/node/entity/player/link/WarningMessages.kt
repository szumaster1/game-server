package core.game.node.entity.player.link

import core.api.consts.Components
import core.api.consts.Vars
import core.api.openInterface
import core.api.sendMessage
import core.api.setVarp
import core.game.component.Component
import core.game.node.entity.player.Player

/**
 * Warning messages
 *
 * @constructor Warning messages
 */
class WarningMessages {

    private val messages: MutableList<WarningMessage> = ArrayList(20)

    /**
     * Open
     *
     * @param player
     */
    fun open(player: Player) {
        openInterface(player, Components.CWS_DOOMSAYER_583)
        refresh(player)
    }

    private fun refresh(player: Player) {
        setVarp(player, CONFIG, configValue, true)
    }

    /**
     * Get message
     *
     * @param index
     * @return
     */
    fun getMessage(index: Int): WarningMessage {
        for (m in messages) {
            if (m.index == index) {
                return m
            }
        }
        val message = WarningMessage(index)
        messages.add(message)
        return message
    }

    private val configValue: Int
        get() = 0

    /**
     * Warning message
     *
     * @property index
     * @constructor Warning message
     */
    inner class WarningMessage(val index: Int) {

        /**
         * Toggle
         *
         * @param player
         */
        fun toggle(player: Player) {
            if (!isActive) {
                sendMessage(player, "You cannot toggle the warning screen on or off. You need to go to the area it")
                sendMessage(player, "is linked to enough times to have the option to do so.")
                return
            }
            val on = !isToggled
            refresh(player)
            sendMessage(player,"You have toggled this warning screen " + (if (on) "on" else "off") + ". You will " + (if (on) "see this interface again." else "no longer see this warning screen."))
        }

        private val isToggled: Boolean
            get() = false

        private val isActive: Boolean
            get() = false
    }

    companion object {
        private const val CONFIG = Vars.VARBIT_IFACE_WARNING_MESSAGES_1045
    }
}
