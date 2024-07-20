package content.global.skill.support.firemaking

import content.global.skill.support.firemaking.data.GnomishFirelighters
import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class FireLighterListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Use gnomish firelighter to colour the log.
         */

        onUseWith(IntType.ITEM, NORMAL_LOG, *FIRELIGHTER) { player, used, with ->
            var firelighter = GnomishFirelighters.forProduct(with.id)
            if (with.asItem().id == firelighter!!.product || used.id == firelighter.base) {
                sendMessage(player, "You can't do that.")
                return@onUseWith false
            }

            if (!removeItem(player, Item(with.id, 1), Container.INVENTORY)) {
                sendMessage(player, "You don't have required items in your inventory.")
            } else {
                replaceSlot(player, used.asItem().slot, Item(firelighter.product, 1))
                sendMessage(player, "You coat the log with the " + getItemName(firelighter.base).replaceFirst("firelighter", "chemicals").lowercase() + ".")
            }
            return@onUseWith true
        }
    }

    companion object {
        val NORMAL_LOG = Items.LOGS_1511
        val FIRELIGHTER = intArrayOf(7329, 7330, 7331, 10326, 10327)
    }
}