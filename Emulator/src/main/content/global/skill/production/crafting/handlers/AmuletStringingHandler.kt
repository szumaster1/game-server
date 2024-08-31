package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.Jewellery.JewelleryItem
import content.global.skill.production.crafting.data.Jewellery.JewelleryItem.Companion.forProduct
import cfg.consts.Items
import core.api.getStatLevel
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Amulet stringing handler.
 */
@Initializable
class AmuletStringingHandler : UseWithHandler(
    Items.GOLD_AMULET_1673,
    Items.SAPPHIRE_AMULET_1675,
    Items.EMERALD_AMULET_1677,
    Items.RUBY_AMULET_1679,
    Items.DIAMOND_AMULET_1681,
    Items.DRAGONSTONE_AMMY_1683,
    Items.ONYX_AMULET_6579
) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.BALL_OF_WOOL_1759, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val data = forProduct(if (event.usedItem.id == Items.ONYX_AMULET_6579) Items.ONYX_AMULET_6579 else event.usedWith.id)
            ?: return true
        if (getStatLevel(player, Skills.CRAFTING) < data.level) {
            sendMessage(player, "You need a Crafting level of at least " + data.level + " to do that.")
            return true
        }
        if (player.inventory.remove(event.usedItem, event.baseItem)) {
            player.inventory.add(Item(if (data === JewelleryItem.ONYX_AMULET) Items.ONYX_AMULET_6581 else data.sendItem + 19))
        }
        return true
    }
}
