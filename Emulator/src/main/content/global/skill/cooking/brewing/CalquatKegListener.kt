package content.global.skill.cooking.brewing

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Calquat keg listener.
 */
class CalquatKegListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.KNIFE_946, Items.CALQUAT_FRUIT_5980) { player, used, with ->
            val anim = Animation(Animations.HUMAN_FRUIT_CUTTING_1192)
            if (used.id == Items.KNIFE_946) {
                animate(player, anim)
                queueScript(player, animationDuration(anim), QueueStrength.NORMAL) {
                    replaceSlot(player, with.asItem().slot, Item(Items.CALQUAT_KEG_5769))
                    return@queueScript stopExecuting(player)
                }
            }
            return@onUseWith true
        }
    }


}