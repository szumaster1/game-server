package content.region.kandarin.ardougne.plaguecity.handlers

import content.region.kandarin.ardougne.plaguecity.quest.elena.PlagueCityUtils
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Handles hand over the cat to civilians at plague city.
 */
class ExchangeCatListener : InteractionListener {

    private val civilians = intArrayOf(NPCs.CIVILIAN_785, NPCs.CIVILIAN_786, NPCs.CIVILIAN_787)
    private val cats = PlagueCityUtils.grownCatItemIds.size
    private val kittens = PlagueCityUtils.kittenItemIds

    override fun defineListeners() {

        /*
         * Handles using a cat on the civilian.
         */

        onUseWith(IntType.NPC, cats, *civilians) { player, used, _ ->
            player.familiarManager.removeDetails(used.idHash)
            sendItemDialogue(player, Items.DEATH_RUNE_560, "You hand over the cat.<br>You are given 100 Death Runes.")
            replaceSlot(player, used.asItem().slot, Item(Items.DEATH_RUNE_560, 100))
            return@onUseWith true
        }

        /*
         * Handles using a kitten on the civilian.
         */

        onUseWith(IntType.NPC, kittens, *civilians) { player, _, _ ->
            sendNPCDialogue(player, civilians.size, "That kitten isn't big enough; come back when it's bigger.")
            return@onUseWith true
        }
    }
}
