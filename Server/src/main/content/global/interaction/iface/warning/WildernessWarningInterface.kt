package content.global.interaction.iface.warning

import core.api.closeInterface
import core.api.consts.Components
import core.api.playAudio
import core.api.removeAttribute
import core.game.component.Component
import core.game.global.action.DoorActionHandler
import core.game.interaction.InterfaceListener
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

class WildernessWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.WILDERNESS_WARNING_382, ::handle)
    }

    fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        closeInterface(player)
        if (button != 18) {
            return true
        }
        if (player.getAttribute<Any?>("wildy_ditch") != null) handleDitch(player) else if (player.getAttribute<Any?>("wildy_gate") != null) handleGate(player)
        return true
    }

    companion object {

        private val ANIMATION = Animation.create(6132)

        fun handleDitch(player: Player) {
            val ditch = player.getAttribute<Scenery>("wildy_ditch")
            removeAttribute(player, "wildy_ditch")
            val l = ditch.location
            val x = player.location.x
            val y = player.location.y
            if (ditch.rotation % 2 == 0) {
                if (y <= l.y) {
                    ForceMovement.run(
                        player, Location.create(x, l.y - 1, 0), Location.create(x, l.y + 2, 0), ANIMATION, 20
                    ).endAnimation = null
                } else {
                    ForceMovement.run(
                        player, Location.create(x, l.y + 2, 0), Location.create(x, l.y - 1, 0), ANIMATION, 20
                    ).endAnimation = null
                }
            } else {
                if (x > l.x) {
                    ForceMovement.run(
                        player, Location.create(l.x + 2, y, 0), Location.create(l.x - 1, y, 0), ANIMATION, 20
                    ).endAnimation = null
                } else {
                    ForceMovement.run(
                        player, Location.create(l.x - 1, y, 0), Location.create(l.x + 2, y, 0), ANIMATION, 20
                    ).endAnimation = null
                }
            }
            playAudio(player, 2462, 10)
        }

        fun handleGate(player: Player) {
            val gate = player.getAttribute<Scenery>("wildy_gate")
            removeAttribute(player, "wildy_gate")
            DoorActionHandler.handleAutowalkDoor(player, gate)
        }
    }
}
