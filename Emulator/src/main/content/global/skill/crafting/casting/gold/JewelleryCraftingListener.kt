package content.global.skill.crafting.casting.gold

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.Scenery

class JewelleryCraftingListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles crafting gold jewellery.
         */

        onUseWith(IntType.SCENERY, goldIDs, *sceneryIDs) { player, used, _ ->
            Jewellery.open(player)
            if (used.id == Items.PERFECT_GOLD_BAR_2365 && inInventory(player, Items.RUBY_1603)) {
                if (inInventory(player, Items.RING_MOULD_1592)) {
                    sendItemOnInterface(player, Components.CRAFTING_GOLD_446, 25, Items.PERFECT_RING_773, 1)
                }
                if (inInventory(player, Items.NECKLACE_MOULD_1597)) {
                    sendItemOnInterface(player, Components.CRAFTING_GOLD_446, 47, Items.PERFECT_NECKLACE_774, 1)
                }
            }
            return@onUseWith true
        }

        /*
         * Handles gold jewellery stringing.
         */

        onUseWith(IntType.ITEM, amuletIDs, Items.BALL_OF_WOOL_1759) { player, used, with ->
            val data = Jewellery.JewelleryItem.forProduct(if (used.id == Items.ONYX_AMULET_6579) Items.ONYX_AMULET_6579 else used.asItem().id)
                ?: return@onUseWith false
            if (getStatLevel(player, Skills.CRAFTING) < data.level) {
                sendMessage(player, "You need a crafting level of at least " + data.level + " to do that.")
                return@onUseWith false
            }
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItem(player, if (data == Jewellery.JewelleryItem.ONYX_AMULET) Items.ONYX_AMULET_6581 else data.sendItem + 19)
                sendMessage(player, "You put some string on your amulet.")
            }
            return@onUseWith true
        }
    }

    companion object {
        private val goldIDs = intArrayOf(Items.GOLD_BAR_2357, Items.PERFECT_GOLD_BAR_2365)
        private val sceneryIDs = intArrayOf(Scenery.FURNACE_4304, Scenery.FURNACE_6189, Scenery.FURNACE_11010, Scenery.FURNACE_11666, Scenery.FURNACE_12100, Scenery.FURNACE_12809, Scenery.FURNACE_18497, Scenery.FURNACE_26814, Scenery.FURNACE_30021, Scenery.FURNACE_30510, Scenery.FURNACE_36956, Scenery.FURNACE_37651)
        private val amuletIDs = intArrayOf(Items.GOLD_AMULET_1673, Items.SAPPHIRE_AMULET_1675, Items.EMERALD_AMULET_1677, Items.RUBY_AMULET_1679, Items.DIAMOND_AMULET_1681, Items.DRAGONSTONE_AMMY_1683, Items.ONYX_AMULET_6579)
    }
}
