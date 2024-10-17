package content.global.handlers.item

import content.region.morytania.swamp.quest.druidspirit.handlers.NSUtils.castBloom
import core.api.animate
import core.api.getQuestStage
import core.api.sendDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.QuestName

/**
 * Handles silver sickle item options.
 */
class SilverSickleListener : InteractionListener {

    override fun defineListeners() {
        on(Items.SILVER_SICKLEB_2963, IntType.ITEM, "operate", "cast bloom") { player, _ ->
            if (getQuestStage(player, QuestName.NATURE_SPIRIT) >= 75) {
                animate(player, Animations.EMERALD_SICKLE_BLOOM_LEGACY_OF_SEERGAZE_9021)
                castBloom(player)
            } else {
                sendDialogue(player, "You must complete Nature Spirit to use this.")
            }
            return@on true
        }
    }
}
