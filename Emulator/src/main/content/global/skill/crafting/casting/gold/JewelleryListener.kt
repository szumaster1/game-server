package content.global.skill.crafting.casting.gold

import core.api.inInventory
import core.api.sendItemOnInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.Scenery

class JewelleryListener : InteractionListener {

    override fun defineListeners() {
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
    }

    companion object {
        private val goldIDs = intArrayOf(Items.GOLD_BAR_2357, Items.PERFECT_GOLD_BAR_2365)
        private val sceneryIDs = intArrayOf(Scenery.FURNACE_4304, Scenery.FURNACE_6189, Scenery.FURNACE_11010, Scenery.FURNACE_11666, Scenery.FURNACE_12100, Scenery.FURNACE_12809, Scenery.FURNACE_18497, Scenery.FURNACE_26814, Scenery.FURNACE_30021, Scenery.FURNACE_30510, Scenery.FURNACE_36956, Scenery.FURNACE_37651)
    }
}
