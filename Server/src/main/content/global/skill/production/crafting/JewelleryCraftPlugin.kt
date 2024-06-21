package content.global.skill.production.crafting

import content.global.skill.production.crafting.jewellery.JewelleryCrafting
import core.api.consts.Items
import core.api.inInventory
import core.api.sendItemOnInterface
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class JewelleryCraftPlugin : UseWithHandler(2357, 2365) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (i in IDS) {
            addHandler(i, OBJECT_TYPE, this)
        }
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        event ?: return false
        JewelleryCrafting.open(event.player).also {
            if (inInventory(event.player, 2365) && inInventory(event.player, Items.RING_MOULD_1592) && inInventory(event.player, Items.RUBY_1603)) {
                sendItemOnInterface(event.player, 446, 25, 773, 1)
            }
            if (inInventory(event.player, 2365) && inInventory(event.player, Items.NECKLACE_MOULD_1597) && inInventory(event.player, Items.RUBY_1603)) {
                sendItemOnInterface(event.player, 446, 47, 774, 1)
            }
        }
        return true
    }

    companion object {
        private val IDS = intArrayOf(4304, 6189, 11010, 11666, 12100, 12809, 18497, 26814, 30021, 30510, 36956, 37651)
    }
}
