package content.global.handlers.item

import core.api.sendItemDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items

/**
 * Cadava potion options.
 */
class CadavaPotion : InteractionListener {

    override fun defineListeners() {
        on(Items.CADAVA_POTION_756, IntType.ITEM, "drink") { player, _ ->
            sendItemDialogue(player, Items.CADAVA_POTION_756, "You dare not drink.")
            return@on true
        }
        on(Items.CADAVA_POTION_756, IntType.ITEM, "look-at") { player, _ ->
            sendItemDialogue(player, Items.CADAVA_POTION_756, "This looks very colourful.")
            return@on true
        }
    }

}
