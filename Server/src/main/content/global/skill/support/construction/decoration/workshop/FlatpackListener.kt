package content.global.skill.support.construction.decoration.workshop

import content.global.skill.support.construction.BuildHotspot
import content.global.skill.support.construction.BuildingUtils.buildDecoration
import content.global.skill.support.construction.Decoration
import core.api.consts.Items
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Flatpack listener.
 */
class FlatpackListener: InteractionListener {
    val FLATPACK = Decoration.values().map { it.flatpackItemID }.toIntArray()
    val HOTSPOT = BuildHotspot.values().map { it.objectId }.toIntArray()

    override fun defineListeners() {
        for (hotspot in HOTSPOT) {
            onUseWith(IntType.SCENERY, FLATPACK, hotspot) { player, used, with ->
                return@onUseWith buildFlatpackOnHotspot(player, used.asItem(), with.asScenery())
            }
        }
    }

    private fun buildFlatpackOnHotspot(player: Player, used: Item, with: Scenery):Boolean {
        val hotspotUsed = player.houseManager.getHotspot(with)
        val DecorationUsed = Decoration.forFlatpackItemId(used.id)

        if(!hotspotUsed.hotspot.decorations.contains(DecorationUsed)) {
            sendMessage(player, "You can't build that here.")
            return false
        }
        if (!player.inventory.containsItems(Item(Items.HAMMER_2347),Item(Items.SAW_8794))) {
            sendMessage(player, "You need a hammer and a saw to build this.")
            return false
        }
        buildDecoration(player, hotspotUsed, DecorationUsed, with.asScenery(),true)
        return true

    }
}