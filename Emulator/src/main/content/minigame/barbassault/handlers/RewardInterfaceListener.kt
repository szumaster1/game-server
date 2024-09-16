package content.minigame.barbassault.handlers

import core.api.*
import cfg.consts.Components
import core.game.interaction.InterfaceListener

/**
 * Reward interface listener.
 */
class RewardInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        // Reset points.
        onOpen(Components.BARBASSAULT_REWARD_SHOP_491) { player, _ ->
            for (i in BA_POINTS) {
                sendInterfaceText(player, "0", 491, i)
            }
            return@onOpen true
        }

    }

    companion object {
        val HEAD_REQUIREMENTS = intArrayOf(115, 122, 129, 136)
        val BODY_PARTS_REQUIREMENTS = intArrayOf(143, 150, 157, 164)
        val BA_POINTS = (219..222)
    }
}
