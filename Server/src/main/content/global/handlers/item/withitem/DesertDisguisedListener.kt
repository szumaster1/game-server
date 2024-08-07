package content.global.handlers.item.withitem

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Desert disguised listener
 *
 * @constructor Desert disguised listener
 */
class DesertDisguisedListener : InteractionListener {

    private val headPiece = Items.KARIDIAN_HEADPIECE_4591
    private val fakeBeard = Items.FAKE_BEARD_4593
    private val desertDisguise = Items.DESERT_DISGUISE_4611

    override fun defineListeners() {

        /*
         * Creating Desert disguise (The Feud quest).
         */

        onUseWith(IntType.ITEM, headPiece, fakeBeard) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, desertDisguise)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }
    }

}
