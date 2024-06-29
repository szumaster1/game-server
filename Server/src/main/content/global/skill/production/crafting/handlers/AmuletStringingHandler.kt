package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.JewelleryData.JewelleryItem
import content.global.skill.production.crafting.data.JewelleryData.JewelleryItem.Companion.forProduct
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class AmuletStringingHandler : UseWithHandler(1673, 1675, 1677, 1679, 1681, 1683, 6579) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(1759, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val data = forProduct(if (event.usedItem.id == 6579) 6579 else event.usedWith.id)
            ?: return true
        if (player.getSkills().getLevel(Skills.CRAFTING) < data.level) {
            player.packetDispatch.sendMessage("You need a Crafting level of at least " + data.level + " to do that.")
            return true
        }
        if (player.inventory.remove(event.usedItem, event.baseItem)) {
            player.inventory.add(Item(if (data === JewelleryItem.ONYX_AMULET) 6581 else data.sendItem + 19))
        }
        return true
    }
}
