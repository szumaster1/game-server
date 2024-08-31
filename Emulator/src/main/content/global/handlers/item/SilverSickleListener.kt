package content.global.handlers.item

import content.region.morytania.swamp.quest.druidspirit.NSUtils.castBloom
import core.api.animate
import core.api.getQuestStage
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Silver sickle listener.
 */
class SilverSickleListener : InteractionListener {

    private val silverSickle = 2963
    private val castBloomAnim = 9021

    override fun defineListeners() {

        on(silverSickle, IntType.ITEM, "operate", "cast bloom"){ player, _ ->
            if (getQuestStage(player, "Nature Spirit") >= 75) {
                animate(player, castBloomAnim)
                castBloom(player)
            } else {
                sendDialogue(player, "You must complete Nature Spirit to use this.")
            }
            return@on true
        }
    }
}
