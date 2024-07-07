package content.global.plugins.item

import content.data.Dyes
import core.api.addItem
import core.api.consts.Items
import core.api.removeItem
import core.api.replaceSlot
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

private val CAPES = arrayOf(Items.BLACK_CAPE_1019, Items.RED_CAPE_1007, Items.BLUE_CAPE_1021, Items.YELLOW_CAPE_1023, Items.GREEN_CAPE_1027, Items.PURPLE_CAPE_1029, Items.ORANGE_CAPE_1031, Items.PINK_CAPE_6959)

private val DYES = Dyes.values().map { it.item.id }.toIntArray()

@Initializable
class CapeDyerPlugin : UseWithHandler(*CAPES.copyOfRange(0, CAPES.size - 1).toIntArray()) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (dye in DYES) {
            addHandler(dye, ITEM_TYPE, this)
        }
        addHandler(Items.PINK_CAPE_6959, ITEM_TYPE, this)
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
            sendMessage(player, "You don't have the required item(s) to make this.")
            return false
        }

        val product = Cape.forDye(dye.id) ?: return false

        if (removeItem(player, dye)) {
            replaceSlot(player, cape.index, Item(product.product.slot))
            addItem(player, Items.VIAL_229)
            sendMessage(player, "You dye the cape.")
        }
        return true
    }

    internal enum class Cape(val dye: Dyes, val product: Item) {
        BLACK(
            dye = Dyes.BLACK,
            product = Item(Items.BLACK_CAPE_1019)
        ),
        RED(
            dye = Dyes.RED,
            product = Item(Items.RED_CAPE_1007)
        ),
        BLUE(
            dye = Dyes.BLUE,
            product = Item(Items.BLUE_CAPE_1021)
        ),
        YELLOW(
            dye = Dyes.YELLOW,
            product = Item(Items.YELLOW_CAPE_1023)
        ),
        GREEN(
            dye = Dyes.GREEN,
            product = Item(Items.GREEN_CAPE_1027)
        ),
        PURPLE(
            dye = Dyes.PURPLE,
            product = Item(Items.PURPLE_CAPE_1029)
        ),
        ORANGE(
            dye = Dyes.ORANGE,
            product = Item(Items.ORANGE_CAPE_1031)
        ),
        PINK(
            dye = Dyes.PINK,
            product = Item(Items.PINK_CAPE_6959)
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
