package content.global.handlers.item

import cfg.consts.Items
import core.api.produceGroundItem
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Mining helmet listener.
 */
class MiningHelmetListener : InteractionListener {

    private val miningHelmet = Items.MINING_HELMET_5013

    override fun defineListeners() {
        on(miningHelmet, IntType.ITEM, "drop") { player, _ ->
            val removed = removeItem(player, Items.MINING_HELMET_5013)
            if (removed) produceGroundItem(player, Items.MINING_HELMET_5014)
            sendMessage(player, "The helmet goes out as you drop it.")
            return@on true
        }
    }

}
