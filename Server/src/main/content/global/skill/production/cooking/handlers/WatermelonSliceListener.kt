package content.global.skill.production.cooking.handlers

import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItemManager
import core.game.node.item.Item

class WatermelonSliceListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.KNIFE_946, Items.WATERMELON_5982) { player, _, _ ->
            if(removeItem(player, Items.WATERMELON_5982)){
                for (i in 0..2){
                    if (!player.inventory.add(Item(Items.WATERMELON_SLICE_5984))) {
                        GroundItemManager.create(Item(Items.WATERMELON_SLICE_5984), player)
                    }
                }
                sendMessage(player,"You slice the watermelon into three slices.")
            }

            return@onUseWith true
        }
    }
}
