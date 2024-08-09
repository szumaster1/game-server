package content.global.handlers.item.withscenery

import core.api.EquipmentSlot
import core.api.sendDialogue
import core.cache.def.impl.ItemDefinition
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Hat stand listener.
 */
class HatStandListener : InteractionListener {

    val hats = ItemDefinition.getDefinitions().values.filter { it.getConfiguration("equipment_slot", 0) == EquipmentSlot.HEAD.ordinal }.map { it.id }.toIntArray()
    private val hatStand = 374

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, hats, hatStand) { player, _, _ ->
            sendDialogue(player, "It'd probably fall off if I tried to do that.")
            return@onUseWith true
        }
    }

}
