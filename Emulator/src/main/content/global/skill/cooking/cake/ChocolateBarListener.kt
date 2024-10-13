package content.global.skill.cooking.cake

import core.api.addItem
import core.api.amountInInventory
import core.api.animate
import core.api.removeItem
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import org.rs.consts.Animations
import org.rs.consts.Items

class ChocolateBarListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.KNIFE_946, Items.CHOCOLATE_BAR_1973) { player, _, _ ->
            player.pulseManager.run(object : Pulse(1) {
                val cut_animation = Animations.CUTTING_CHOCOLATE_BAR_1989
                override fun pulse(): Boolean {
                    super.setDelay(4)
                    val amount = amountInInventory(player, Items.CHOCOLATE_BAR_1973)
                    if (amount > 0) removeItem(player, Items.CHOCOLATE_BAR_1973).also {
                        animate(player, cut_animation)
                        addItem(player, Items.CHOCOLATE_DUST_1975)
                    }
                    return amount <= 0
                }
            })
            return@onUseWith true
        }
    }
}
