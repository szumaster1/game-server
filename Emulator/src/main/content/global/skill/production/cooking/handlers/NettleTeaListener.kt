package content.global.skill.production.cooking.handlers

import core.api.addItemOrDrop
import cfg.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Nettle tea listener
 *
 * @constructor Nettle tea listener
 */
class NettleTeaListener : InteractionListener {

    override fun defineListeners() {

        /**
         * Added nettles to bowl of water to creating nettle-water.
         */
        onUseWith(IntType.ITEM, Items.NETTLES_4241, Items.BOWL_OF_WATER_1921) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.NETTLE_WATER_4237, 1)
                sendMessage(player, "You place the nettles into the bowl of water.")
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Extract nettle tea from bowl to cup.
         */
        onUseWith(IntType.ITEM, Items.EMPTY_CUP_1980, Items.NETTLE_TEA_4239) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.CUP_OF_TEA_4242, 1)
                addItemOrDrop(player, Items.BOWL_1923, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Extract nettle milky tea from bowl to cup.
         */
        onUseWith(IntType.ITEM, Items.EMPTY_CUP_1980, Items.NETTLE_TEA_4240) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.CUP_OF_TEA_4243, 1)
                addItemOrDrop(player, Items.BOWL_1923, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Extract nettle tea from bowl to porcelain cup.
         */
        onUseWith(IntType.ITEM, Items.NETTLE_TEA_4239, Items.PORCELAIN_CUP_4244) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.CUP_OF_TEA_4245, 1)
                addItemOrDrop(player, Items.BOWL_1923, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Extract nettle milky tea from bowl to porcelain cup.
         */
        onUseWith(IntType.ITEM, Items.NETTLE_TEA_4240, Items.PORCELAIN_CUP_4244) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.CUP_OF_TEA_4246, 1)
                addItemOrDrop(player, Items.BOWL_1923, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Added milk to nettle tea (bowl).
         */
        onUseWith(IntType.ITEM, Items.BUCKET_OF_MILK_1927, Items.NETTLE_TEA_4239) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.NETTLE_TEA_4240, 1)
                addItemOrDrop(player, Items.BUCKET_1925, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /**
         * Added milk to nettle tea (porcelain cup).
         */
        onUseWith(IntType.ITEM, Items.BUCKET_OF_MILK_1927, Items.CUP_OF_TEA_4245) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.CUP_OF_TEA_4246, 1)
                addItemOrDrop(player, Items.BUCKET_1925, 1)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

    }

}
