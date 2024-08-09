package content.global.handlers.item.withitem

import core.api.addItem
import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Crystal key create listener.
 */
class CrystalKeyCreateListener : InteractionListener {

    private val loopKeyPiece = Items.LOOP_HALF_OF_A_KEY_987
    private val toothKeyPiece = Items.TOOTH_HALF_OF_A_KEY_985
    private val crystalKey = Items.CRYSTAL_KEY_989

    override fun defineListeners() {

        /*
         * Combining Crystal key interaction.
         */

        onUseWith(IntType.ITEM, loopKeyPiece, toothKeyPiece) { player, used, with ->
            if (!removeItem(player, used)) {
                return@onUseWith false
            }
            if (!removeItem(player, with)) {
                return@onUseWith false
            }
            addItem(player, crystalKey)
            sendMessage(player, "You join the two halves of the key together.")
            return@onUseWith true
        }
    }
}
