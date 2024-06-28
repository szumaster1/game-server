package content.minigame.barbassault

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

class RewardInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Reset points.
         */

        onOpen(Components.BARBASSAULT_REWARD_SHOP_491) { player, components ->
            for (i in BA_POINTS) {
                setInterfaceText(player, "0", 491, i)
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
