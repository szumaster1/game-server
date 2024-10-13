package content.global.skill.herblore.herbs

import core.api.replaceSlot
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items

class HerbTeaListener: InteractionListener {

    override fun defineListeners() {

        /*
         * Handles pouring the hot water.
         */

       onUseWith(IntType.ITEM, Items.BOWL_OF_HOT_WATER_4456, Items.EMPTY_CUP_1980) { player, used, with ->
           replaceSlot(player, used.asItem().slot, Item(Items.BOWL_1923))
           replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_HOT_WATER_4460))
           sendMessage(player, "You pour the hot water into the tea cup.")
           return@onUseWith true
       }
    }
}