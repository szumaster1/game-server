package content.global.skill.crafting.casting.gold

import core.api.addItem
import core.api.getStatLevel
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import org.rs.consts.Items

class JewelleryStringingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, itemIDs, Items.BALL_OF_WOOL_1759) { player, used, with ->
            val data = Jewellery.JewelleryItem.forProduct(if (used.id == Items.ONYX_AMULET_6579) Items.ONYX_AMULET_6579 else used.asItem().id)
                ?: return@onUseWith false
            if (getStatLevel(player, Skills.CRAFTING) < data.level) {
                sendMessage(player, "You need a crafting level of at least " + data.level + " to do that.")
                return@onUseWith false
            }
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItem(player, if (data === Jewellery.JewelleryItem.ONYX_AMULET) Items.ONYX_AMULET_6581 else data.sendItem + 19)
            }

            return@onUseWith true
        }
    }

    companion object {
        private val itemIDs = intArrayOf(Items.GOLD_AMULET_1673, Items.SAPPHIRE_AMULET_1675, Items.EMERALD_AMULET_1677, Items.RUBY_AMULET_1679, Items.DIAMOND_AMULET_1681, Items.DRAGONSTONE_AMMY_1683, Items.ONYX_AMULET_6579)
    }
}
