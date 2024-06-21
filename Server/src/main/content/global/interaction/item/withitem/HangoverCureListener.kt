package content.global.interaction.item.withitem

import core.api.addItem
import core.api.consts.Items
import core.api.removeItem
import core.api.sendItemDialogue
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class HangoverCureListener : InteractionListener {

    private val chocolateDust = Items.CHOCOLATE_DUST_1975
    private val chocolateMilk = Items.CHOCOLATEY_MILK_1977
    private val milkBucket = Items.BUCKET_OF_MILK_1927
    private val snapeGrass = Items.SNAPE_GRASS_231
    private val hangoverCure = Items.HANGOVER_CURE_1504


    override fun defineListeners() {
        onUseWith(IntType.ITEM, chocolateDust, milkBucket) { player, _, _ ->
            if (removeItem(player, chocolateDust) && removeItem(player, milkBucket)) {
                sendItemDialogue(player, chocolateMilk, "You mix the chocolate into the bucket.")
                addItem(player, chocolateMilk)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, snapeGrass, chocolateMilk) { player, _, _ ->
            if (removeItem(player, snapeGrass) && removeItem(player, chocolateMilk)) {
                sendItemDialogue(player, hangoverCure, "You mix the snape grass into the bucket.")
                addItem(player, hangoverCure)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }
    }
}