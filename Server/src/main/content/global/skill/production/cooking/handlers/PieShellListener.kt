package content.global.skill.production.cooking.handlers

import core.api.addItem
import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class PieShellListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.PASTRY_DOUGH_1953, Items.PIE_DISH_2313) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItem(player, Items.PIE_SHELL_2315)
                sendMessage(player, "You put the pastry dough into the pie dish to make a pie shell.")
            }

            return@onUseWith true
        }
    }
}
