package content.global.skill.gathering.fishing.barb

import content.global.skill.BarbarianTraining
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class BarbFishingListeners : InteractionListener {

    private val fishCuttingIds = intArrayOf(Items.LEAPING_TROUT_11328, Items.LEAPING_SALMON_11330, Items.LEAPING_STURGEON_11332)
    private val barbFishingSpot = NPCs.FISHING_SPOT_1176
    private val barbarianBed = Scenery.BARBARIAN_BED_25268
    private val barbarianRod = Items.BARBARIAN_ROD_11323

    override fun defineListeners() {
        on(barbarianBed, IntType.SCENERY, "search") { player, _ ->
            if (getAttribute(player, BarbarianTraining.ATTR_BARB_TRAIN_FISHING_BEGIN, false)) {
                if (!inInventory(player, barbarianRod) && freeSlots(player) > 0) {
                    sendMessage(player, "You find a heavy fishing rod under the bed and take it.")
                    addItem(player, barbarianRod)
                } else {
                    sendMessage(player, "You don't find anything that interests you.")
                }
            } else {
                sendMessage(player, "You don't find anything that interests you.")
            }
            return@on true
        }

        on(barbFishingSpot, IntType.NPC, "fish") { player, _ ->
            sendMessage(player, "You cast out your line...")
            player.pulseManager.run(BarbFishingPulse(player))
            return@on true
        }

        onUseWith(IntType.ITEM, Items.KNIFE_946, *fishCuttingIds){ player, _, with ->
            player.pulseManager.run(BarbFishCuttingPulse(player, with.id))
            return@onUseWith true
        }
    }

}