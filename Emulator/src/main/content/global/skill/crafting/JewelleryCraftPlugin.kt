package content.global.skill.crafting

import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.Scenery
import core.api.inInventory
import core.api.sendItemOnInterface
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Jewellery craft plugin.
 */
@Initializable
class JewelleryCraftPlugin : UseWithHandler(Items.GOLD_BAR_2357, Items.PERFECT_GOLD_BAR_2365) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (i in IDS) {
            addHandler(i, OBJECT_TYPE, this)
        }
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        event ?: return false
        Jewellery.open(event.player).also {
            if (inInventory(event.player, Items.PERFECT_GOLD_BAR_2365) && inInventory(
                    event.player,
                    Items.RING_MOULD_1592
                ) && inInventory(event.player, Items.RUBY_1603)
            ) {
                sendItemOnInterface(event.player, Components.CRAFTING_GOLD_446, 25, Items.PERFECT_RING_773, 1)
            }
            if (inInventory(event.player, Items.PERFECT_GOLD_BAR_2365) && inInventory(
                    event.player,
                    Items.NECKLACE_MOULD_1597
                ) && inInventory(event.player, Items.RUBY_1603)
            ) {
                sendItemOnInterface(event.player, Components.CRAFTING_GOLD_446, 47, Items.PERFECT_NECKLACE_774, 1)
            }
        }
        return true
    }

    companion object {
        private val IDS = intArrayOf(
            Scenery.FURNACE_4304,
            Scenery.FURNACE_6189,
            Scenery.FURNACE_11010,
            Scenery.FURNACE_11666,
            Scenery.FURNACE_12100,
            Scenery.FURNACE_12809,
            Scenery.FURNACE_18497,
            Scenery.FURNACE_26814,
            Scenery.FURNACE_30021,
            Scenery.FURNACE_30510,
            Scenery.FURNACE_36956,
            Scenery.FURNACE_37651
        )
    }
}
