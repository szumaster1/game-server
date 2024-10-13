package content.global.skill.cooking.type.hotdrink

import org.rs.consts.Items
import core.api.removeItem
import core.api.replaceSlot
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

/**
 * Handles creating a nettle tea.
 */
class NettleTeaListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles creating bowl of nettle tea.
         */

        onUseWith(IntType.ITEM, Items.NETTLES_4241, Items.BOWL_OF_WATER_1921) { player, used, with ->
            if (removeItem(player, with.asItem())) {
                replaceSlot(player, used.asItem().slot, Item(Items.NETTLE_WATER_4237))
                sendMessage(player, "You place the nettles into the bowl of water.")
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /*
         * Handles the creation of milky nettle tea in a bowl.
         */

        onUseWith(IntType.ITEM, Items.BUCKET_OF_MILK_1927, Items.NETTLE_TEA_4239) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BUCKET_1925))
            replaceSlot(player, with.asItem().slot, Item(Items.NETTLE_TEA_4240))
            return@onUseWith true
        }

        /*
         * Handles the creation of milky nettle tea in a porcelain cup.
         * (Ghosts Ahoy quest - related interaction)
         */

        onUseWith(IntType.ITEM, Items.BUCKET_OF_MILK_1927, Items.CUP_OF_TEA_4245) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BUCKET_1925))
            replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_TEA_4246))
            return@onUseWith true
        }

        /*
         * Handles Pouring nettle tea from a bowl into a cup.
         */

        onUseWith(IntType.ITEM, Items.NETTLE_TEA_4239, Items.EMPTY_CUP_1980) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BOWL_1923))
            replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_TEA_4242))
            return@onUseWith true
        }

        /*
         * Handles pouring milk nettle tea from a bowl into a cup.
         */

        onUseWith(IntType.ITEM, Items.NETTLE_TEA_4240, Items.EMPTY_CUP_1980) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BOWL_1923))
            replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_TEA_4243))
            return@onUseWith true
        }

        /*
         * Handles pouring nettle tea from a bowl into a porcelain cup.
         * (Ghosts Ahoy quest - related interaction)
         */

        onUseWith(IntType.ITEM, Items.NETTLE_TEA_4239, Items.PORCELAIN_CUP_4244) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BOWL_1923))
            replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_TEA_4245))
            return@onUseWith true
        }

        /*
         * Handles pouring milky nettle tea from a bowl into a porcelain cup.
         * (Ghosts Ahoy quest - related interaction)
         */

        onUseWith(IntType.ITEM, Items.NETTLE_TEA_4240, Items.PORCELAIN_CUP_4244) { player, used, with ->
            replaceSlot(player, used.asItem().slot, Item(Items.BOWL_1923))
            replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_TEA_4246))
            return@onUseWith true
        }
    }

}
