package content.global.skill.production.crafting.handlers

import core.api.consts.Components
import core.api.consts.Items
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class StichCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.NEEDLE_1733, Items.LEATHER_1741) { player, used, _ ->
            player.attributes["leatherId"] = used.id
            openInterface(player, Components.LEATHER_CRAFTING_154)
            return@onUseWith true
        }
    }
}
