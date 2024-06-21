package content.global.interaction.item.plugins

import content.data.Dyes
import core.api.consts.Items
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

private val CAPES = arrayOf(1019, 1007, 1021, 1023, 1027, 1029, 1031, 6959)
private val DYES = Dyes.values().map { it -> it.item.id }.toIntArray()

@Initializable
class CapeDyerPlugin : UseWithHandler(*CAPES.copyOfRange(0, CAPES.size - 1).toIntArray()) {
    override fun newInstance(arg: Any?): Plugin<Any> {
        for (dye in DYES) {
            addHandler(dye, ITEM_TYPE, this)
        }
        addHandler(6959, ITEM_TYPE, this) //Pink cape has to be here because higher ID than others
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        event ?: return false
        val player = event.player
        val used = event.used.id
        val with = event.usedWith.id
        var cape: Item
        var dye: Item

        if (CAPES.contains(used) && DYES.contains(with)) {
            cape = event.used.asItem()
            dye = event.used.asItem()
        } else if (DYES.contains(used) && CAPES.contains(with)) {
            cape = event.usedWith.asItem()
            dye = event.used.asItem()
        } else {
            return false
        }

        val product = Cape.forDye(dye.id) ?: return false

        if (player.inventory.remove(dye) && player.inventory.remove(cape)) {
            player.inventory.add(product.product)
            player.inventory.add(Item(Items.VIAL_229))
            sendMessage(player, "You dye the cape.")
        }
        return true
    }

    internal enum class Cape(val dye: Dyes, val product: Item) {
        BLACK(Dyes.BLACK, Item(1019)), RED(Dyes.RED, Item(1007)), BLUE(Dyes.BLUE, Item(1021)), YELLOW(
            Dyes.YELLOW,
            Item(1023)
        ),
        GREEN(Dyes.GREEN, Item(1027)), PURPLE(Dyes.PURPLE, Item(1029)), ORANGE(Dyes.ORANGE, Item(1031)), PINK(
            Dyes.PINK,
            Item(6959)
        );

        companion object {
            fun forDye(dye: Int): Cape? {
                for (cape in values()) {
                    if (cape.dye.item.id == dye) {
                        return cape
                    }
                }
                return null
            }
        }
    }

}