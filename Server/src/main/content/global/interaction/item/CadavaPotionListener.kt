package content.global.interaction.item

import core.api.consts.Items
import core.api.sendItemDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class CadavaPotionListener : InteractionListener {

    private val cadavaPotion = Items.CADAVA_POTION_756

    override fun defineListeners() {
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