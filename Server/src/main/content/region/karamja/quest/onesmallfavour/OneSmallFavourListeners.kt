package content.region.karamja.quest.onesmallfavour

import core.api.consts.Items
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * One small favour listeners.
 */
class OneSmallFavourListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Read the wheater report.
         */

        on(Items.WEATHER_REPORT_4435, IntType.ITEM, "read") { player, _ ->
            sendDialogue(player, "Generally quite changeable weather, perhaps starting quite sunny with some chances of rain, snow, or hail, and a large possibility of a thunderstorm or clear skies")
            return@on true
        }
    }
}