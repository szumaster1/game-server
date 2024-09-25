package content.global.handlers.item.withitem

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Sounds

/**
 * Spider on stick listener.
 */
class SpiderOnStick : InteractionListener {

    private val SKEWER_STICK = Items.SKEWER_STICK_6305
    private val SPIDER_CARCASS = Items.SPIDER_CARCASS_6291
    private val SPIDER_ON_STICK_RAW = Items.SPIDER_ON_STICK_6293

    override fun defineListeners() {
        onUseWith(IntType.ITEM, SKEWER_STICK, SPIDER_CARCASS) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                animate(player, Animations.CRAFT_SOMETHING_1309)
                playAudio(player, Sounds.TBCU_SPIDER_STICK_1280)
                addItem(player, SPIDER_ON_STICK_RAW, 1)
                sendMessage(player, "You pierce the spider carcass with the skewer stick")
                return@onUseWith true
            }
            return@onUseWith false
        }
    }
}