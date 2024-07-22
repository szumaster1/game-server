package content.global.plugins.item

import core.api.consts.Items
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class CleaninigClothPlugin : UseWithHandler(950) {

    private val CLOTH = Item(Items.CLEANING_CLOTH_3188)
    private val SILK = Item(Items.SILK_950)

    init {
        addHandler(431, ITEM_TYPE, this)
    }

    override fun newInstance(arg: Any): Plugin<Any?> {
        addHandler(431, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player as Player
        if (player.inventory.remove(SILK)) {
            player.inventory.add(CLOTH)
            player.packetDispatch.sendMessage("You pour some of the Karamjan rum over the silk.")
        }
        return true
    }
}