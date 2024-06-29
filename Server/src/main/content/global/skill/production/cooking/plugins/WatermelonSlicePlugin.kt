package content.global.skill.production.cooking.plugins

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class WatermelonSlicePlugin : UseWithHandler(KNIFE.id) {

    override fun newInstance(arg: Any): Plugin<Any> {
        addHandler(5982, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        if (event.player.inventory.remove(WATERMELON)) {
            for (i in 0..2) {
                if (!event.player.inventory.add(WATERMELON_SLICE)) {
                    GroundItemManager.create(WATERMELON_SLICE, event.player)
                }
            }
            event.player.packetDispatch.sendMessage("You slice the watermelon into three slices.")
        }
        return true
    }

    companion object {
        private val KNIFE = Item(946)
        private val WATERMELON = Item(5982)
        private val WATERMELON_SLICE = Item(5984)
    }
}
