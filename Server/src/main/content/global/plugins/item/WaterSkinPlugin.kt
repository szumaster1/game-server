package content.global.plugins.item

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class WaterSkinPlugin: UseWithHandler(1825, 1827, 1829, 1831) {
    private val FULL_SKIN = Item(1823)

    private val data = arrayOf(intArrayOf(1937, 1935), intArrayOf(1929, 1925), intArrayOf(1921, 1923), intArrayOf(227, 229))

    override fun newInstance(arg: Any): Plugin<Any?> {
        for (i in data.indices) {
            addHandler(data[i][0], ITEM_TYPE, this)
        }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val waterSkin = if (event.usedItem.name.contains("skin")) event.usedItem else event.baseItem
        val vessil = if (!event.usedItem.name.contains("skin")) event.usedItem else event.baseItem
        if (event.player.inventory.remove(waterSkin)) {
            event.player.inventory.add(if (vessil.id == 227) Item(waterSkin.id - 2) else FULL_SKIN)
            for (i in data.indices) {
                if (data[i][0] == vessil.id && event.player.inventory.remove(vessil)) {
                    event.player.inventory.add(Item(data[i][1]))
                }
            }
        }
        return true
    }
}
