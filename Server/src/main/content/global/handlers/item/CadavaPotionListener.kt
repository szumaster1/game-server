package content.global.handlers.item

import core.api.consts.Items
import core.api.sendItemDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Cadava potion listener
 *
 * @constructor Cadava potion listener
 */
class CadavaPotionListener : InteractionListener {

    private val cadavaPotion = Items.CADAVA_POTION_756

    override fun defineListeners() {

        /*
         * Cadava potion interaction.
         */

        on(cadavaPotion, IntType.ITEM, "drink") { player, _ ->
            sendItemDialogue(player, cadavaPotion, "You dare not drink.")
            return@on true
        }
        on(cadavaPotion, IntType.ITEM, "look-at") { player, _ ->
            sendItemDialogue(player, cadavaPotion, "This looks very colourful.")
            return@on true
        }
    }

}
