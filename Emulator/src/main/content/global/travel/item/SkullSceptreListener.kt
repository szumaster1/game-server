package content.global.travel.item

import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.Items
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.TeleportManager
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic

/**
 * Skull sceptre assemble listener.
 */
class SkullSceptreListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling the use of the Skull Sceptre.
         */

        on(Items.SKULL_SCEPTRE_9013, IntType.ITEM, "invoke", "divine", "operate") { player, node ->
            if (getUsedOption(player) == "invoke" || getUsedOption(player) == "operate") {
                if (node.id != null && inEquipmentOrInventory(player, Items.SKULL_SCEPTRE_9013)) {
                    lock(player, 3)
                    animate(player, Animations.HUMAN_USE_SCEPTRE_9601)
                    Graphic.send(Graphic(Graphics.USE_SCEPTRE_1683, 100), player.location)
                    submitIndividualPulse(player, object : Pulse(2, player) {
                        override fun pulse(): Boolean {
                            teleport(player, DESTINATION, TeleportManager.TeleportType.INSTANT)
                            return true
                        }
                    })
                }
                setCharge(node, getCharge(node) - 200)
                if (getCharge(node) < 1) {
                    removeItem(player, SKULL_SCEPTRE, Container.INVENTORY)
                    sendMessage(player, "Your staff crumbles to dust as you use its last charge.")
                }
            } else {
                if (getCharge(node) < 1) {
                    sendMessage(player, "You don't have enough charges left.")
                }
                sendMessage(player, "Concentrating deeply, you divine that the sceptre has " + (getCharge(node) / 200) + " charges left.")
            }
            return@on true
        }

        /*
         * Assemble left skull half with the right skull half.
         */

        onUseWith(IntType.ITEM, Items.LEFT_SKULL_HALF_9008, Items.RIGHT_SKULL_HALF_9007) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                sendItemDialogue(player, Items.STRANGE_SKULL_9009, "The two halves of the skull fit perfectly.")
                replaceSlot(player, with.asItem().index, Item(Items.STRANGE_SKULL_9009, 1))
            }
            return@onUseWith true
        }

        /*
         * Assemble top of sceptre with the bottom of sceptre.
         */

        onUseWith(IntType.ITEM, Items.BOTTOM_OF_SCEPTRE_9011, Items.TOP_OF_SCEPTRE_9010) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                sendItemDialogue(player, Items.RUNED_SCEPTRE_9012, "The two halves of the sceptre fit perfectly.")
                replaceSlot(player, with.asItem().index, Item(Items.RUNED_SCEPTRE_9012, 1))
            }
            return@onUseWith true
        }

        /*
         * Assemble Sceptre using runed sceptre with strange skull.
         */

        onUseWith(IntType.ITEM, Items.STRANGE_SKULL_9009, Items.RUNED_SCEPTRE_9012) { player, used, with ->
            if (removeItem(player, used.asItem())) {
                sendDoubleItemDialogue(player, -1, Items.SKULL_SCEPTRE_9013, "The skull fits perfectly atop the Sceptre.")
                replaceSlot(player, with.asItem().index, Item(Items.SKULL_SCEPTRE_9013, 1))
            }
            return@onUseWith true
        }
    }

    companion object {
        const val SKULL_SCEPTRE = Items.SKULL_SCEPTRE_9013
        const val ANIMATION = Animations.HUMAN_USE_SCEPTRE_9601
        const val GRAPHIC = Graphics.USE_SCEPTRE_1683
        val DESTINATION: Location = Location(3081, 3421, 0)
    }
}
