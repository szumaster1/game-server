package content.region.misthalin.quest.member.animalmagnetism

import core.api.consts.Items
import core.api.isQuestComplete
import core.api.sendMessage
import core.game.interaction.InteractionListener

/**
 * Represents the Animal Magnetism listeners.
 */
class AnimalMagnetismListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Handle equip the crone made amulet.
         */

        onEquip(Items.CRONE_MADE_AMULET_10500) { player, _ ->
            if (!isQuestComplete(player, "Animal Magnetism")) {
                sendMessage(player, "Your ghostliness isn't ethereal enough to wear this.")
                return@onEquip false
            } else {
                return@onEquip true
            }
        }
    }

}
