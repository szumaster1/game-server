package content.region.misthalin.varrock.quest.phoenixgang.handlers

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Certificate plugin.
 */
@Initializable
class CertificatePlugin : UseWithHandler(11173) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(11174, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        if (player.inventory.remove(Item(event.usedItem.id, 1), Item(event.baseItem.id, 1))) {
            player.inventory.add(CERTIFICATE)
        }
        return true
    }

    companion object {
        private val CERTIFICATE = Item(769)
    }
}
