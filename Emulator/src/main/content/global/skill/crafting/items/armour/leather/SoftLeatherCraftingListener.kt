package content.global.skill.crafting.items.armour.leather

import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items

class SoftLeatherCraftingListener: InteractionListener {

    override fun defineListeners() {
        /*
         * Handles soft leather crafting.
         */

        onUseWith(IntType.ITEM, Items.LEATHER_1741, Items.NEEDLE_1733) { player, _, _ ->
            SoftLeather.open(player)
            return@onUseWith true
        }
    }
}