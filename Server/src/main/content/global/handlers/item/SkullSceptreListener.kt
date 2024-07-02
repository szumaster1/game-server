package content.global.handlers.item

import core.api.*
import core.api.consts.Animations
import core.api.consts.Graphics
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic

class SkullSceptreListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Skull sceptre options interaction.
         */
        on(SKULL_SCEPTRE, IntType.ITEM, "invoke", "divine", "operate") { player, node ->
            when (getUsedOption(player)) {
                "invoke" -> {
                    if (inInventory(player, SKULL_SCEPTRE, 1)) {
                        sceptreTeleport(player)
                    }
                    setCharge(node, getCharge(node) - 200)
                    if (getCharge(node) < 1) {
                        removeItem(player, SKULL_SCEPTRE, Container.INVENTORY)
                        sendMessage(player, "Your staff crumbles to dust as you use its last charge.")
                    }
                }

                "divine" -> {
                    if (getCharge(node) < 1) {
                        sendMessage(player, "You don't have enough charges left.")
                    }
                    sendMessage(
                        player,
                        "Concentrating deeply, you divine that the sceptre has " + (getCharge(node) / 200) + " charges left."
                    )
                }

                "operate" -> {
                    if (inEquipment(player, SKULL_SCEPTRE, 1)) {
                        sceptreTeleport(player)
                    }
                    setCharge(node, getCharge(node) - 200)
                    if (getCharge(node) < 1) {
                        removeItem(player, SKULL_SCEPTRE, Container.EQUIPMENT)
                        sendMessage(player, "Your staff crumbles to dust as you use its last charge.")
                    }
                }
            }
            return@on true
        }
    }

    private fun sceptreTeleport(player: Player) {
        lock(player, 3)
        animate(player, ANIMATION)
        Graphic.send(Graphic(GRAPHIC, 100), player.location)
        submitIndividualPulse(player, object : Pulse(2, player) {
            override fun pulse(): Boolean {
                teleport(player, DESTINATION, TeleportManager.TeleportType.INSTANT)
                return true
            }
        })
    }

    companion object {
        const val SKULL_SCEPTRE = Items.SKULL_SCEPTRE_9013
        const val ANIMATION = Animations.HUMAN_USE_SCEPTRE_9601
        const val GRAPHIC = Graphics.USE_SCEPTRE_1683
        val DESTINATION: Location = Location(3081, 3421, 0)
    }
}
