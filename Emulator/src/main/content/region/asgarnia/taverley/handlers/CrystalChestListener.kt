package content.region.asgarnia.taverley.handlers

import org.rs.consts.Items
import org.rs.consts.Scenery
import core.api.freeSlots
import core.api.inInventory
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.RandomFunction

class CrystalChestListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Scenery.CLOSED_CHEST_172, Items.CRYSTAL_KEY_989) { player, _, _ ->
            if (!inInventory(player, 989, 1)) {
                sendMessage(player, "This chest is securely locked shut.")
                return@onUseWith false
            }
            if (freeSlots(player) == 0) {
                sendMessage(player, "Not enough inventory space.")
                return@onUseWith false
            }
            if (removeItem(player, KEY)) {
                val reward = Reward.getReward(player)
                for (i in reward!!.items) {
                    player.inventory.add(i, player)
                }
                sendMessage(player, "You unlock the chest with your key.")
                sendMessage(player, "You find some treasure in the chest!")
            }
            return@onUseWith true
        }
    }

    enum class Reward(val chance: Double, vararg items: Item) {
        FIRST(39.69, Item(1631, 1), Item(1969, 1), Item(995, 2000)),
        SECOND(16.72, Item(1631, 1)),
        THIRD(10.57, Item(1631, 1), Item(371, 5), Item(995, 1000)),
        FOURTH(7.73, Item(1631, 1), Item(556, 50), Item(555, 50), Item(557, 50), Item(554, 50), Item(559, 50), Item(558, 50), Item(565, 10), Item(9075, 10), Item(566, 10)),
        FIFTH(6.55, Item(1631, 1), Item(454, 100)),
        SIXTH(4.23, Item(1631, 1), Item(1603, 2), Item(1601, 2)),
        SEVENTH(3.67, Item(1631, 1), Item(985, 1), Item(995, 750)),
        EIGHT(3.51, Item(1631, 1), Item(2363, 3)),
        NINTH(3.26, Item(1631, 1), Item(987, 1), Item(995, 750)),
        TENTH(2.75, Item(1631, 1), Item(441, 150)),
        ELEVENTH(1.06, Item(1631, 1), Item(1183, 1)),
        TWELFTH(0.26, Item(1631, 1), Item(1079, 1)),
        TWELFTH_FEMALE(0.26, Item(1631, 1), Item(1093, 1));

        val items: Array<Item> = items as Array<Item>

        companion object {
            fun getReward(player: Player): Reward? {
                var totalChance = 0
                for (r in values()) {
                    if (r == TWELFTH_FEMALE && !player.appearance.isMale) {
                        continue
                    }
                    totalChance = (totalChance + r.chance).toInt()
                }
                val random = RandomFunction.random(totalChance)
                var total = 0
                for (r in values()) {
                    total = (total + r.chance).toInt()
                    if (random < total) {
                        return r
                    }
                }
                return null
            }
        }
    }

    companion object {
        private val KEY = Item(989)
    }
}
