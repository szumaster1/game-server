package content.global.skill.production.crafting

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.api.consts.Items

/**
 * Spider on stick listener.
 */
class SpiderOnStickListener: InteractionListener {
    val SKEWER_STICK = Items.SKEWER_STICK_6305
    val SPIDER_CARCASS = Items.SPIDER_CARCASS_6291
    val SPIDER_ON_STICK_RAW = Items.SPIDER_ON_STICK_6293

    override fun defineListeners() {
        onUseWith(IntType.ITEM, SKEWER_STICK, SPIDER_CARCASS){ player, used, _ ->
            if (player.inventory.remove(Item(Items.SKEWER_STICK_6305,1), Item(Items.SPIDER_CARCASS_6291,1))) {
                animate(player, 1309)
                playAudio(player, 1280)
                addItem(player, SPIDER_ON_STICK_RAW, 1)
                sendMessage(player, "You pierce the spider carcass with the skewer stick")
                return@onUseWith true
            }
            return@onUseWith false
        }
    }
}