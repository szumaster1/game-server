package content.global.skill.firemaking

import core.api.*
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItem
import core.game.node.item.Item

/**
 * Firemaking listener.
 */
class Firemaking : InteractionListener {

    companion object {
        val log = Items.LOGS_1511
        val logs = intArrayOf(
            Items.LOGS_1511,
            Items.OAK_LOGS_1521,
            Items.WILLOW_LOGS_1519,
            Items.MAPLE_LOGS_1517,
            Items.YEW_LOGS_1515,
            Items.MAGIC_LOGS_1513,
            Items.ACHEY_TREE_LOGS_2862,
            Items.PYRE_LOGS_3438,
            Items.OAK_PYRE_LOGS_3440,
            Items.WILLOW_PYRE_LOGS_3442,
            Items.MAPLE_PYRE_LOGS_3444,
            Items.YEW_PYRE_LOGS_3446,
            Items.MAGIC_PYRE_LOGS_3448,
            Items.TEAK_PYRE_LOGS_6211,
            Items.MAHOGANY_PYRE_LOG_6213,
            Items.MAHOGANY_LOGS_6332,
            Items.TEAK_LOGS_6333,
            Items.RED_LOGS_7404,
            Items.GREEN_LOGS_7405,
            Items.BLUE_LOGS_7406,
            Items.PURPLE_LOGS_10329,
            Items.WHITE_LOGS_10328,
            Items.SCRAPEY_TREE_LOGS_8934,
            Items.DREAM_LOG_9067,
            Items.ARCTIC_PYRE_LOGS_10808,
            Items.ARCTIC_PINE_LOGS_10810,
            Items.SPLIT_LOG_10812,
            Items.WINDSWEPT_LOGS_11035,
            Items.EUCALYPTUS_LOGS_12581,
            Items.EUCALYPTUS_PYRE_LOGS_12583,
            Items.JOGRE_BONES_3125
        )
        val firelighter = intArrayOf(7329, 7330, 7331, 10326, 10327)
    }

    override fun defineListeners() {

        /*
         * Handling fire logs from inventory.
         */

        onUseWith(IntType.ITEM, Items.TINDERBOX_590, *logs) { player, _, with ->
            player.pulseManager.run(FireMakingPulse(player, with.asItem(), null))
            return@onUseWith true
        }

        /*
         * Handling fire logs from ground.
         */

        onUseWith(IntType.GROUNDITEM, Items.TINDERBOX_590, *logs) { player, _, with ->
            player.pulseManager.run(FireMakingPulse(player, with.asItem(), with as GroundItem))
            return@onUseWith true
        }

        /*
         * Handling the gnomish firelighters.
         */

        onUseWith(IntType.ITEM, log, *firelighter) { player, used, with ->
            var firelighter = GnomishFirelighters.forProduct(with.id)
            if (with.asItem().id == firelighter!!.product || used.id == firelighter.base) {
                sendMessage(player, "You can't do that.")
                return@onUseWith false
            }

            if (!removeItem(player, Item(with.id, 1), Container.INVENTORY)) {
                sendMessage(player, "You don't have required items in your inventory.")
            } else {
                replaceSlot(player, used.asItem().slot, Item(firelighter.product, 1))
                sendMessage(
                    player,
                    "You coat the log with the " + getItemName(firelighter.base).replaceFirst(
                        "firelighter",
                        "chemicals"
                    ).lowercase() + "."
                )
            }
            return@onUseWith true
        }
    }
}
