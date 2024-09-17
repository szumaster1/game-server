package content.global.skill.production.cooking.handlers

import core.api.addItem
import core.api.amountInInventory
import core.api.animate
import org.rs.consts.Animations
import org.rs.consts.Items
import core.api.removeItem
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Chocolate bar handler.
 */
@Initializable
class ChocolateBarHandler : UseWithHandler(Items.KNIFE_946) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.CHOCOLATE_BAR_1973, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent?): Boolean {
        event ?: return false
        val player = event.player
        player.pulseManager.run(object : Pulse(1) {
            val cut_animation = Animation(Animations.CUTTING_CHOCOLATE_BAR_1989)
            override fun pulse(): Boolean {
                super.setDelay(4)
                val amount = amountInInventory(player, Items.CHOCOLATE_BAR_1973)
                if (amount > 0) removeItem(player, Item(Items.CHOCOLATE_BAR_1973)).also {
                    animate(player, cut_animation);
                    addItem(player, Items.CHOCOLATE_DUST_1975)
                }
                return amount <= 0
            }
        })
        return true
    }

}
