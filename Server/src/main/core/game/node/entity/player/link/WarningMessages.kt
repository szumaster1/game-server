package core.game.node.entity.player.link

import core.api.setVarp
import core.game.component.Component
import core.game.node.entity.player.Player

class WarningMessages {

    private val messages: MutableList<WarningMessage> = ArrayList(20)

    fun open(player: Player) {
        player.interfaceManager.open(Component(583))
        refresh(player)
    }

    private fun refresh(player: Player) {
        setVarp(player, CONFIG, configValue, true)
    }

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


    inner class WarningMessage(val index: Int) {

        fun toggle(player: Player) {
            if (!isActive) {
                player.packetDispatch.sendMessage("You cannot toggle the warning screen on or off. You need to go to the area it")
                player.packetDispatch.sendMessage("is linked to enough times to have the option to do so.")
                return
            }
            val on = !isToggled
            refresh(player)
            player.packetDispatch.sendMessage("You have toggled this warning screen " + (if (on) "on" else "off") + ". You will " + (if (on) "see this interface again." else "no longer see this warning screen."))
        }

        private val isToggled: Boolean
            get() = false

        private val isActive: Boolean
            get() = false
    }

    companion object {

        private const val CONFIG = 1045
    }
}
